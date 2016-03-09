import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ImageCollectionView extends JPanel implements ViewInterface {

	//private Model m_model;
	//private boolean m_fullView;
	private ImageCollectionModel m_model;
	private ArrayList<ImageView> m_imageViews = new ArrayList<ImageView>();

   	public ImageCollectionView(ImageCollectionModel model) {
		m_model = model;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));//FlowLayout(FlowLayout.LEFT));
		addComponentListener(new ComponentAdapter() {
			@Override
    		public void componentResized(ComponentEvent e) {
    			resetLayout();
			}
		});
//		setMaximumSize(new Dimension(400, 400));
//		setBorder(BorderFactory.createLineBorder(Color.black, 5));
//		setBackground(Color.BLACK);
    }

	public void resetLayout() {
		removeAll();
		int width = getWidth();
		//If the component has not filled its parent container yet, the layout is undefined.
		if (width == 0) return;
//		System.out.println(width);
		//We're only adding the images that are shown via the rating system.
		int numImages = m_model.getVisibleImages();
		int columns = width/(ImageView.WIDTH + 10);
		//int rows = numImages/columns + (numImages % columns == 0 ? 0 : 1);
		int addedToRow = 0;
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, columns));
		for (int i = 0; i < m_model.getImages().size(); i++) {
			if (m_model.getImages().get(i).getRating() < m_model.getRatingFilter()) {
				continue;
			}
			if (addedToRow >= columns) {
				add(panel);
                addedToRow = 0;
                panel = new JPanel();
                panel.setLayout(new GridLayout(0, columns));
			}
			JPanel panel1 = new JPanel();
            panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));

            JPanel panel2 = new JPanel();
            panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

            panel1.add(new JPanel());
            panel1.add(panel2);
            panel1.add(new JPanel());

            panel2.add(new JPanel());
            panel2.add(m_imageViews.get(i));
            panel2.add(new JPanel());

			panel.add(panel1);
			addedToRow++;
		}
		if (addedToRow > 0) {
			add(panel);
		}


	/*	for(int i = 0; i < m_model.getImages().size(); i+=columns) {
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(0, columns));//new BoxLayout(panel, BoxLayout.X_AXIS));
			for(int j = 0; j < columns; j++) {
				if (i + j >= m_imageViews.size()) break;
//				System.out.println(m_model.getRatingFilter());
				if (m_imageViews.get(i + j).getRating() >= m_model.getRatingFilter()) {
					JPanel panel1 = new JPanel();
					panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));

					JPanel panel2 = new JPanel();
					panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

					panel1.add(new JPanel());
					panel1.add(panel2);
					panel1.add(new JPanel());

					panel2.add(new JPanel());
					panel2.add(m_imageViews.get(i + j));
					panel2.add(new JPanel());

					//JPanel finalPanel = new JPanel(new BorderLayout());
					//finalPanel.add(panel1, BorderLayout.CENTER);

					panel.add(panel1);
				}
			}
			add(panel);
		}*/
		//for(int i = numImages; i < rows*columns; i++) {
		//  add(new JPanel());
		//}
		revalidate();
		repaint();
	}


	@Override
	public void notifyView() {
		//for(ImageView view : m_imageViews) {
		//	remove(view);
		//}
		//removeAll();
		for (ImageView view : m_imageViews) {
            view.notifyView();
        }
		if (m_imageViews.size() != m_model.getImages().size()) {
		
			m_imageViews = new ArrayList<ImageView>();
			for(int i = 0; i < m_model.getImages().size(); i++) {
				m_imageViews.add(new ImageView(m_model.getImages().get(i)));
			//add(m_imageViews.get(i));
//			add(new JPanel());
			}
		}
		//removeAll();
		//revalidate();
		//repaint();
		resetLayout();
	}

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getParent().getWidth(), (int)super.getPreferredSize().getHeight());
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
