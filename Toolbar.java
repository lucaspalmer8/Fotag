import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.*;

public class Toolbar extends JPanel implements ViewInterface {

	private ImageCollectionModel m_model;
	private JButton m_gridView;
	private JButton m_listView;
	private JFileChooser m_chooser = new JFileChooser();
	private RatingBar m_ratingBar;
	private static Image STAR = null;
	private static Image EMPTY_STAR = null;

   	public Toolbar(ImageCollectionModel model) {
		if (STAR == null) {
            BufferedImage img = null;
            Image newimg = null;
            try {
                img = ImageIO.read(new File("fullstar.png"));
            } catch (IOException e) {}
            STAR = img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        }

        if (EMPTY_STAR == null) {
            BufferedImage img = null;
            Image newimg = null;
            try {
                img = ImageIO.read(new File("emptystar.png"));
            } catch (IOException e) {}
            EMPTY_STAR = img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        }

		m_chooser.setMultiSelectionEnabled(true);
		m_model = model;
		m_ratingBar = new RatingBar(0);
		setBorder(BorderFactory.createLineBorder(Color.black, 2));
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
	
		BufferedImage img = null;
        Image newimg = null;
        try {
            img = ImageIO.read(new File("grid.png"));
        } catch (IOException e) {}
        newimg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

		m_gridView = new JButton(new ImageIcon(newimg));
		m_gridView.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m_gridView.setEnabled(false);
				m_model.enableGridView();
				m_listView.setEnabled(true);
			}
		});
		m_gridView.setBackground(Color.WHITE);
		m_gridView.setFocusPainted(false);
		m_gridView.setPreferredSize(new Dimension(50, 50));
		m_gridView.setSelected(true);	
		m_gridView.setEnabled(false);

		try {
            img = ImageIO.read(new File("list.png"));
        } catch (IOException e) {}
        newimg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

		m_listView = new JButton(new ImageIcon(newimg));
        m_listView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				m_listView.setEnabled(false);
				m_model.enableListView();
				m_gridView.setEnabled(true);
            }
        });
		m_listView.setBackground(Color.WHITE);
		m_listView.setFocusPainted(false);
		m_listView.setPreferredSize(new Dimension(50, 50));
        
		ActionListener loadPictureListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = m_chooser.showOpenDialog(m_model.getFrame());
				File[] files = m_chooser.getSelectedFiles();
				if (files.length == 0 || returnVal > 0) {
					return;
				}
				
				BufferedImage img = null;
		        Image newimg = null;
				for (File file : files) {
					try {
            			img = ImageIO.read(file);
        			} catch (Exception ex) {}
				
					if (img == null) {
						JOptionPane.showMessageDialog(m_model.getFrame(), "You must only select photos.");
                    	return;
					}
				}	
	
				for (File file : files) {
					m_model.addImage(file.getAbsolutePath());
				}
			}
		};

		try {
            img = ImageIO.read(new File("loadicon.png"));
        } catch (IOException e) {}
        newimg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);

		JButton loadButton = new JButton(new ImageIcon(newimg));
        loadButton.setBackground(Color.WHITE);
        loadButton.setFocusPainted(false);
        loadButton.setPreferredSize(new Dimension(35, 35));
		loadButton.addActionListener(loadPictureListener);
	
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBackground(Color.WHITE);
		panel.add(m_gridView);
		panel.add(m_listView);
		
		JLabel label = new JLabel();
		label.setFont(new Font("Courier New", Font.BOLD, 60));
		label.setText(" Fotag!");

		JPanel spare1 = new JPanel();
		JPanel spare2 = new JPanel();
		spare1.setBackground(Color.WHITE);
		spare2.setBackground(Color.WHITE);

        try {
            img = ImageIO.read(new File("reset.png"));
        } catch (IOException e) {}
        newimg = img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);

		JButton resetButton = new JButton(new ImageIcon(newimg));
		resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m_model.setRatingFilter(0);
				m_ratingBar.updateRatingBar(0);
                //updateView();
            }
        });
		resetButton.setPreferredSize(new Dimension(30, 30));
		resetButton.setFocusPainted(false);
		resetButton.setBackground(Color.WHITE);

		JPanel theOne = new JPanel(new FlowLayout(FlowLayout.LEFT));
		theOne.setBackground(Color.WHITE);

		JPanel panel3 = new JPanel();
		panel3.setBackground(Color.WHITE);
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
		panel3.add(spare1);

		JLabel filter = new JLabel();
        filter.setFont(new Font("Courier New", Font.BOLD, 12));
        filter.setText(" Filter by:");
		
		theOne.add(loadButton);
		theOne.add(filter);
		theOne.add(m_ratingBar);
		theOne.add(resetButton);
		panel3.add(theOne);
		panel3.add(spare2);

		add(panel, BorderLayout.WEST);
		add(panel3, BorderLayout.EAST);
		add(label, BorderLayout.CENTER);			
    }
	
	private class FullStar extends JLabel {
		private int m_index;

		public FullStar(int index) {
			setOpaque(true);
			setBackground(Color.WHITE);
			m_index = index;
			setIcon(new ImageIcon(STAR));
			addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    m_model.setRatingFilter(m_index);
					m_ratingBar.updateRatingBar(m_index);
                }
            });
		}
	}

	private class EmptyStar extends JLabel {
        private int m_index;

        public EmptyStar(int index) {
			setOpaque(true);
			setBackground(Color.WHITE);
			m_index = index;
            setIcon(new ImageIcon(EMPTY_STAR));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
					m_model.setRatingFilter(m_index);
					m_ratingBar.updateRatingBar(m_index);
                }
            });
        }
    }

	
	private class RatingBar extends JPanel {

		public RatingBar(int rating) {
			setBackground(Color.WHITE);
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
    	return new Dimension(100, 80);
    }

	@Override
	public void notifyView() {}
}
