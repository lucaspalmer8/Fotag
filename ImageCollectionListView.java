import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ImageCollectionListView extends JPanel implements ViewInterface {

	//private Model m_model;
	//private boolean m_fullView;
	private ImageCollectionModel m_model;
	private ArrayList<ImageView> m_imageViews = new ArrayList<ImageView>();

   	public ImageCollectionListView(ImageCollectionModel model) {
		m_model = model;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));//FlowLayout(FlowLayout.LEFT));
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
			//m_imageViews.add(new ImageView(m_model.getImages().get(m_model.getImages().size() - 1)));
			//JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			//panel.add(m_imageViews.get(m_imageViews.size() - 1));
			//add(panel);
			//revalidate();
			//repaint()
			m_imageViews = new ArrayList<ImageView>();
			for(int i = 0; i < m_model.getImages().size(); i++) {
				m_imageViews.add(new ImageView(m_model.getImages().get(i)));
			}
			//add(m_imageViews.get(i));
//			add(new JPanel());
			//}
		}

		removeAll();
		for (ImageView view : m_imageViews) {
			if (view.getRating() < m_model.getRatingFilter()) {
				continue;
			}
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			panel.add(view);
			add(panel);
		}
		revalidate();
		repaint();
		//resetLayout();
	}

    //@Override
    //public Dimension getPreferredSize() {
    //    return new Dimension(getParent().getWidth(), (int)super.getPreferredSize().getHeight());
    //}


	@Override    
   	public void paintComponent(Graphics g) {
		super.paintComponent(g);
       	Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
       	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
           	    RenderingHints.VALUE_ANTIALIAS_ON);
       	//for (Model.Stroke stroke : m_model.getStrokeList()) {
  	}
}
