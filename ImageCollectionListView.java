import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ImageCollectionListView extends JPanel implements ViewInterface {

	private ImageCollectionModel m_model;
	private ArrayList<ImageListView> m_imageViews = new ArrayList<ImageListView>();

	public ImageCollectionListView(ImageCollectionModel model) {
		m_model = model;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	@Override
	public void notifyView() {
		for (ImageListView view : m_imageViews) {
			view.notifyView();
		}

		if (m_imageViews.size() != m_model.getImages().size()) {
			m_imageViews = new ArrayList<ImageListView>();
			for(int i = 0; i < m_model.getImages().size(); i++) {
				m_imageViews.add(new ImageListView(m_model.getImages().get(i)));
			}
		}

		removeAll();
		for (ImageListView view : m_imageViews) {
			if (view.getRating() < m_model.getRatingFilter()) {
				continue;
			}
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			panel.add(view);
			add(panel);
		}
		revalidate();
		repaint();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(getParent().getWidth(), (int)super.getPreferredSize().getHeight());
	}
}
