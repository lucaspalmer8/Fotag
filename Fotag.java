import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class Fotag {

	private JFrame m_frame;
	private JPanel m_panel;
	private JScrollPane m_gridView;
	private JScrollPane m_listView; 

	public static void main(String[] args) {
		Fotag fotag = new Fotag();
	}

	public Fotag() {
		//Create and initialize model.
		ImageCollectionModel model = new ImageCollectionModel(this);

		//Create view/controller and tell it about model.
		ImageCollectionView view = new ImageCollectionView(model);
		model.addObserver(view);

		//Create view/controller and tell it about model.
		ImageCollectionListView listView = new ImageCollectionListView(model);
		model.addObserver(listView);

		//Create view/controller and tell it about model.
		Toolbar toolbar = new Toolbar(model);
		model.addObserver(toolbar);

		//Notify the views of the model.
		model.notifyViews();

		m_frame = new JFrame("Fotag");
		m_frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				model.saveState();
			}
		});

		m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_frame.setResizable(true);
		m_frame.setSize(900, 600);
		m_frame.setMinimumSize(new Dimension(400, 330));
		
		m_panel = new JPanel(new BorderLayout());
		m_gridView = new JScrollPane(view, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		m_listView = new JScrollPane(listView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		m_panel.add(m_gridView, BorderLayout.CENTER);
		m_panel.add(toolbar, BorderLayout.NORTH);
		
		m_frame.add(m_panel);
		m_frame.setVisible(true);
	}

	public JFrame getFrame() {
		return m_frame;
	}

	public JPanel getPanel() {
		return m_panel;
	}

	public JScrollPane getGridView() {
		return m_gridView;
	}

	public JScrollPane getListView() {
		return m_listView;
	}
}
