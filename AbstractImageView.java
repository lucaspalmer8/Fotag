import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.nio.*;
import java.nio.file.*;
import java.nio.file.Path;
import java.nio.file.attribute.*;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;

public abstract class AbstractImageView extends JPanel implements ViewInterface {

	protected ImageModel m_model;
	protected RatingBar m_ratingBar;
	private JDialog m_dialog;
	private static Image STAR = null;
	private static Image EMPTY_STAR = null;

	public AbstractImageView(ImageModel model) {
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

		//Add the photo to the center panel.
		BufferedImage img = null;
		Image newimg = null;
		try {
			img = ImageIO.read(new File(m_model.getPath()));
		} catch (IOException ex) {}
		int width = img.getWidth();
		int height = img.getHeight();
		float scale;
		if (width/200f >= height/220f) {
			scale = 200f/width;
		} else {
			scale = 220f/height;
		}
		newimg = img.getScaledInstance((int)(scale*width), (int)(scale*height), Image.SCALE_SMOOTH);
		JLabel wIcon = new JLabel(new ImageIcon(newimg));
		add(wIcon, BorderLayout.CENTER);

		if (width >= height) {
			scale = 600f/width;
		} else {
			scale = 600f/height;
		}
		m_dialog = new JDialog();
		m_dialog.setPreferredSize(new Dimension((int)(scale*width), (int)(scale*height)));
		m_dialog.setResizable(false);
		Image zoom = img.getScaledInstance((int)(scale*width), (int)(scale*height), Image.SCALE_SMOOTH);
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
	}

	public int getRating() {
		return m_model.getRating();
	}
	
	@Override
	public void notifyView() {
		int rating = m_model.getRating();
		m_ratingBar.updateRatingBar(rating);
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
					notifyView();
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
					notifyView();
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
}
