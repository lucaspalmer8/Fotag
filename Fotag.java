import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class Fotag {

    public JFrame m_frame;
	public JPanel m_panel;
	public JScrollPane m_gridView;
	public JScrollPane m_listView; 

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
                System.out.println("Hello!!!");
                model.saveState();
            }
        });

        m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m_frame.setResizable(true);
        m_frame.setSize(900, 600);
        m_frame.setMinimumSize(new Dimension(390, 330));
		//JPanel theOne = new JPanel();
		//theOne.add(new JScrollPane(view));
		m_panel = new JPanel(new BorderLayout());
       	m_gridView = new JScrollPane(view, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		m_panel.add(m_gridView, BorderLayout.CENTER);
		m_listView = new JScrollPane(listView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		m_panel.add(toolbar, BorderLayout.NORTH);
		m_frame.add(m_panel);
		//model.notifyViews();
        m_frame.setVisible(true);
    }
}
