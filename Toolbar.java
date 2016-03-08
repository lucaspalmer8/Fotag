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

   	public Toolbar(ImageCollectionModel model) {
		m_model = model;
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
//				final JFileChooser chooser = new JFileChooser();
				/*if (m_model.getStrokeList().size() != 0) {
					Object[] options = {"Yes, please", "No, thanks"};
                	int n = JOptionPane.showOptionDialog(m_model.getFrame(),
                    	"Are you sure you want to load a new Doodle\nwithout saving this one?",
                    	"Save Document?",
                    	JOptionPane.YES_NO_OPTION,
                    	JOptionPane.QUESTION_MESSAGE,
                    	null,
                    	options,
                    	options[1]);
				
					if (n == 1) {
						return;
					}
				}*/


                int returnVal = m_chooser.showOpenDialog(m_model.getFrame());
				if (m_chooser.getSelectedFile() == null) {
					return;
				}
                String path = m_chooser.getSelectedFile().getAbsolutePath();
                String fileName = m_chooser.getSelectedFile().getName();

				BufferedImage img = null;
		        Image newimg = null;
				try {
            		img = ImageIO.read(new File(path));
        		} catch (IOException ex) {}

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
		
		JLabel label = new JLabel();
		label.setFont(new Font("Courier New", Font.BOLD, 60));
		label.setText(" Fotag!");
		panel.add(label);

		add(panel, BorderLayout.WEST);
		add(loadButton, BorderLayout.EAST);
			
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
