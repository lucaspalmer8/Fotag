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
		setLayout(new FlowLayout(FlowLayout.LEFT));
//		setMaximumSize(new Dimension(400, 400));
//		setBorder(BorderFactory.createLineBorder(Color.black, 5));
//		setBackground(Color.BLACK);
    }

	@Override
	public void notifyView() {
		for(ImageView view : m_imageViews) {
			remove(view);
		}
		m_imageViews = new ArrayList<ImageView>();
		for(int i = 0; i < m_model.getImages().size(); i++) {
			m_imageViews.add(new ImageView(m_model.getImages().get(i)));
			add(m_imageViews.get(i));
		}
		revalidate();
		repaint();
	}

    //@Override
    //public Dimension getMaximumSize() {
    //    return new Dimension(100, 100);
   // }


	@Override    
   	public void paintComponent(Graphics g) {
		super.paintComponent(g);
       	Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
       	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
           	    RenderingHints.VALUE_ANTIALIAS_ON);
       	//for (Model.Stroke stroke : m_model.getStrokeList()) {
  	}
}
