import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.nio.file.*;
import java.nio.*;

import java.nio.file.*;
import java.nio.file.Path;
import java.nio.file.attribute.*;
import java.io.IOException;

import java.util.Date;

public class ImageView extends JPanel {//implements ViewInterface {

	private ImageModel m_model;
	//To keep track of the rating bar we have to replace
	private JPanel m_bottomPanel = new JPanel();
	private RatingBar m_ratingBar = new RatingBar(0);

   	public ImageView(ImageModel model) {
		m_model = model;
		setBorder(BorderFactory.createLineBorder(Color.black, 5));
		setLayout(new BorderLayout());
		setBackground(Color.BLACK);

		BufferedImage img = null;
        Image newimg = null;
		try {
			img = ImageIO.read(new File(m_model.getPath()));
		} catch (IOException ex) {
			System.out.println(ex);
		}
		newimg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);

		JLabel wIcon = new JLabel(new ImageIcon(newimg));
		add(wIcon, BorderLayout.CENTER);
		//add(m_currentRatingBar, BorderLayout.SOUTH);

		Path file = Paths.get(m_model.getPath());
		BasicFileAttributes attrs = null;
		try {
    		attrs = Files.readAttributes(file, BasicFileAttributes.class);
		} catch (IOException ex) {
			System.out.println(ex);
		}
		m_bottomPanel.setLayout(new BoxLayout(m_bottomPanel, BoxLayout.Y_AXIS));
		
		JPanel date = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel dateCreated = new JLabel();
		dateCreated.setText(new Date(attrs.creationTime().toMillis()).toString());
		date.add(dateCreated);
		JPanel name = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel fileName = new JLabel(new File(m_model.getPath()).getName());
		name.add(fileName);
		m_bottomPanel.add(name);
		m_bottomPanel.add(date);
		m_bottomPanel.add(m_ratingBar);

		add(m_bottomPanel, BorderLayout.SOUTH);
    }
	
	private void updateView() {
		int rating = m_model.getRating();
		m_bottomPanel.remove(m_ratingBar);
		m_ratingBar = new RatingBar(rating);
		m_bottomPanel.add(m_ratingBar);
		revalidate();
		repaint();
	}

	private class FullStar extends JLabel {
		private int m_index;

		public FullStar(int index) {
			m_index = index;
			BufferedImage img = null;
            Image newimg = null;
            try {
                img = ImageIO.read(new File("fullstar.png"));
            } catch (IOException e) {}
            newimg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			setIcon(new ImageIcon(newimg));
			addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    m_model.setRating(m_index);
					updateView();
                }
            });
		}
	}

	private class EmptyStar extends JLabel {
        private int m_index;

        public EmptyStar(int index) {
			m_index = index;
            BufferedImage img = null;
            Image newimg = null;
            try {
                img = ImageIO.read(new File("emptystar.png"));
            } catch (IOException e) {}
            newimg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(newimg));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
					m_model.setRating(m_index);
                    updateView();
                }
            });
        }
    }

	private class RatingBar extends JPanel {

		public RatingBar(int rating) {
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			for (int i = 1; i <= rating; i++) {
				panel.add(new FullStar(i));
			}
			for (int i = rating + 1; i <= 5; i++) {
				panel.add(new EmptyStar(i));
			}
			setLayout(new FlowLayout(FlowLayout.LEFT));
			add(panel);
		}
	}

	@Override
    public Dimension getPreferredSize() {
    	return new Dimension(250, 300);
    }

	@Override    
   	public void paintComponent(Graphics g) {
		super.paintComponent(g);
       	Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
       	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
           	    RenderingHints.VALUE_ANTIALIAS_ON);
       	//for (Model.Stroke stroke : m_model.getStrokeList()) {
  	}
}
