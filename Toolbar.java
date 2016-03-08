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
            STAR = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        }

        if (EMPTY_STAR == null) {
            BufferedImage img = null;
            Image newimg = null;
            try {
                img = ImageIO.read(new File("emptystar.png"));
            } catch (IOException e) {}
            EMPTY_STAR = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        }

		m_model = model;
		m_ratingBar = new RatingBar(0);
		setBorder(BorderFactory.createLineBorder(Color.black, 5));
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
	
		BufferedImage img = null;
        Image newimg = null;
        try {
            img = ImageIO.read(new File("grid.png"));
        } catch (IOException e) {}
        newimg = img.getScaledInstance(70, 90, Image.SCALE_SMOOTH);

		m_gridView = new JButton(new ImageIcon(newimg));
		m_gridView.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//m_listView.setEnabled(true);
				//m_listView.setSelected(false);
				m_gridView.setEnabled(false);
				m_model.enableGridView();
				//setEnabled(false);
				m_listView.setEnabled(true);
			}
		});
		m_gridView.setBackground(Color.WHITE);
//		m_gridView.setFocusPainted(false);
//		m_gridView.setBorderPainted(false);
		m_gridView.setPreferredSize(new Dimension(70, 90));
		m_gridView.setSelected(true);	
		m_gridView.setEnabled(false);

		try {
            img = ImageIO.read(new File("list.png"));
        } catch (IOException e) {}
        newimg = img.getScaledInstance(70, 90, Image.SCALE_SMOOTH);

		m_listView = new JButton(new ImageIcon(newimg));
        m_listView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				//m_gridView.setEnabled(true);
				//m_gridView.setSelected(false);
				m_listView.setEnabled(false);
				m_model.enableListView();
				//setEnabled(false);
				m_gridView.setEnabled(true);
            }
        });
		m_listView.setBackground(Color.WHITE);
//		m_listView.setFocusPainted(false);
//		m_listView.setBorderPainted(false);
		m_listView.setPreferredSize(new Dimension(70, 90));
        
		ActionListener loadPictureListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = m_chooser.showOpenDialog(m_model.getFrame());
				if (m_chooser.getSelectedFile() == null || returnVal > 0) {
					return;
				}
				System.out.println(returnVal);
                String path = m_chooser.getSelectedFile().getAbsolutePath();
                String fileName = m_chooser.getSelectedFile().getName();

				BufferedImage img = null;
		        Image newimg = null;
				try {
            		img = ImageIO.read(new File(path));
        		} catch (Exception ex) {}
				
				if (img == null) {
					JOptionPane.showMessageDialog(m_model.getFrame(), "You must select a photo.");
                    return;
				}	

				m_model.addImage(path);
			}
		};

		try {
            img = ImageIO.read(new File("loadicon.png"));
        } catch (IOException e) {}
        newimg = img.getScaledInstance(70, 90, Image.SCALE_SMOOTH);

		JButton loadButton = new JButton(new ImageIcon(newimg));
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.out.println("Ehhhh");
			}
        });
        loadButton.setBackground(Color.WHITE);
        //m_list.setFocusPainted(false);
        //m_listView.setBorderPainted(false);
        loadButton.setPreferredSize(new Dimension(70, 90));
		loadButton.addActionListener(loadPictureListener);
	
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBackground(Color.WHITE);
		panel.add(m_gridView);
		panel.add(m_listView);
		
//		JPanel s3 = new JPanel();
//		JPanel s4 = new JPanel();
//		s3.setBackground(Color.WHITE);
//		s4.setBackground(Color.WHITE);
//		JPanel center = new JPanel();
//		center.setBackground(Color.WHITE);
		JLabel label = new JLabel();
		label.setFont(new Font("Courier New", Font.BOLD, 60));
		label.setText(" Fotag!");
		label.setPreferredSize(new Dimension(300, 100));
//		center.add(s3);
//		center.add(label);
//		center.add(s4);
//		panel.add(label);

		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		panel2.setBackground(Color.WHITE);
		panel2.add(loadButton);

		JPanel spare1 = new JPanel();
		JPanel spare2 = new JPanel();
		spare1.setBackground(Color.WHITE);
		spare2.setBackground(Color.WHITE);

		JButton resetButton = new JButton("0");
		resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m_model.setRatingFilter(0);
				m_ratingBar.updateRatingBar(0);
                //updateView();
            }
        });

		JPanel theOne = new JPanel(new FlowLayout(FlowLayout.LEFT));
		theOne.setBackground(Color.WHITE);

		JPanel panel3 = new JPanel();
		panel3.setBackground(Color.WHITE);
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
		panel3.add(spare1);
		
		theOne.add(m_ratingBar);
		theOne.add(resetButton);
		panel3.add(theOne);

		panel3.add(spare2);
		panel2.add(panel3);

		add(panel, BorderLayout.WEST);
		add(panel2, BorderLayout.EAST);
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
    	return new Dimension(100, 100);
    }

	@Override
	public void notifyView() {

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
