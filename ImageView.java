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
import java.text.SimpleDateFormat;

public class ImageView extends JPanel implements ViewInterface {

	public static int WIDTH = 250;
	public static int HEIGHT = 300;
	private ImageModel m_model;
	//To keep track of the rating bar we have to replace
	private JPanel m_bottomPanel = new JPanel();
	private RatingBar m_ratingBar;
	private JDialog m_dialog;
	private static Image STAR = null;
	private static Image EMPTY_STAR = null;

   	public ImageView(ImageModel model) {
		if (STAR == null) {
            BufferedImage img = null;
            Image newimg = null;
            try {
                img = ImageIO.read(new File("fullstar.png"));
            } catch (IOException e) {}
            STAR = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        }

		if (EMPTY_STAR == null) {
            BufferedImage img = null;
            Image newimg = null;
            try {
                img = ImageIO.read(new File("emptystar.png"));
            } catch (IOException e) {}
            EMPTY_STAR = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        }

		m_model = model;
		m_ratingBar = new RatingBar(m_model.getRating());
		setBorder(BorderFactory.createLineBorder(Color.black, 1));
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);

		BufferedImage img = null;
        Image newimg = null;
		try {
			img = ImageIO.read(new File(m_model.getPath()));
		} catch (IOException ex) {
			System.out.println(ex);
		}
		newimg = img.getScaledInstance(200, 220, Image.SCALE_SMOOTH);

		JLabel wIcon = new JLabel(new ImageIcon(newimg));
		add(wIcon, BorderLayout.CENTER);

		m_dialog = new JDialog();
        m_dialog.setPreferredSize(new Dimension(500,500));
        m_dialog.setResizable(false);
        Image zoom = img.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        JLabel labelpic = new JLabel(new ImageIcon(zoom));
        m_dialog.add(labelpic);
        m_dialog.pack();
        wIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                m_dialog.setLocationRelativeTo(m_model.getFrame());
                m_dialog.setVisible(true);
            }
        });

		//add(m_currentRatingBar, BorderLayout.SOUTH);

		Path file = Paths.get(m_model.getPath());
		BasicFileAttributes attrs = null;
		try {
    		attrs = Files.readAttributes(file, BasicFileAttributes.class);
		} catch (IOException ex) {
			System.out.println(ex);
		}
		m_bottomPanel.setLayout(new BoxLayout(m_bottomPanel, BoxLayout.Y_AXIS));
		m_bottomPanel.setBackground(Color.WHITE);
		
		JPanel date = new JPanel(new FlowLayout(FlowLayout.LEFT));
		date.setBackground(Color.WHITE);
		JLabel dateCreated = new JLabel();
		dateCreated.setBackground(Color.WHITE);
	
		String DATE_FORMAT = "MM/dd/yyyy";
    	SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
    	//System.out.println("Formated Date " + sdf.format(date));

		dateCreated.setText(sdf.format(new Date(attrs.creationTime().toMillis())).toString());
		date.add(dateCreated);
		date.setBackground(Color.WHITE);
		JPanel name = new JPanel(new FlowLayout(FlowLayout.LEFT));
		name.setBackground(Color.WHITE);
		JLabel fileName = new JLabel(new File(m_model.getPath()).getName());
		fileName.setBackground(Color.WHITE);
		name.add(fileName);

		date.add(m_ratingBar);

		try {
            img = ImageIO.read(new File("reset.png"));
        } catch (IOException e) {}
        newimg = img.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		JButton reset = new JButton(new ImageIcon(newimg));
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m_model.setRating(0);
                updateView();
			}
		});
		reset.setPreferredSize(new Dimension(20, 20));
        reset.setFocusPainted(false);

		date.add(reset);

		m_bottomPanel.add(name);
		m_bottomPanel.add(date);
		//m_bottomPanel.add(m_ratingBar);

		add(m_bottomPanel, BorderLayout.SOUTH);
    }

	public int getRating() {
		return m_model.getRating();
	}
	
	@Override
	public void notifyView() {
		updateView();
	}

	private void updateView() {
		int rating = m_model.getRating();
		//m_bottomPanel.remove(m_ratingBar);
		//m_ratingBar = new RatingBar(rating);
		//m_bottomPanel.add(m_ratingBar);
		m_ratingBar.updateRatingBar(rating);
		//revalidate();
		//repaint();
	}

	private class FullStar extends JLabel {
		private int m_index;

		public FullStar(int index) {
			setBackground(Color.WHITE);
			setOpaque(true);
			m_index = index;
			setIcon(new ImageIcon(STAR));
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
			setBackground(Color.WHITE);
            setOpaque(true);
			m_index = index;
            setIcon(new ImageIcon(EMPTY_STAR));
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
		private int m_rating;
	
		public RatingBar(int rating) {
			setBackground(Color.WHITE);
			m_rating = rating;
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
		
		public void updateRatingBar(int rating) {
			if (rating == m_rating) {
				return;
			}
			m_rating = rating;
			removeAll();
			JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            for (int i = 1; i <= rating; i++) {
                panel.add(new FullStar(i));
            }
            for (int i = rating + 1; i <= 5; i++) {
                panel.add(new EmptyStar(i));
            }
			add(panel);
			revalidate();
			repaint();
		}
	}

	@Override
    public Dimension getPreferredSize() {
   		return new Dimension(WIDTH, HEIGHT);
    }

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

	@Override
	public Dimension getMinimumSize() {
		return new Dimension(WIDTH, HEIGHT);
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
