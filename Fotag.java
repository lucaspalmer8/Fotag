import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class Fotag {

    public JFrame frame;

	public static void main(String[] args) {
		Fotag fotag = new Fotag();
	}

    public Fotag() {
		//Create and initialize model.
        ImageCollectionModel model = new ImageCollectionModel();

		frame = new JFrame("Fotag");
		frame.addWindowListener(new WindowAdapter() {
			@Override
		 	public void windowClosing(WindowEvent e) {
				System.out.println("Hello!!!");
				model.saveState();
			}
		});

        //Create view/controller and tell it about model.
        ImageCollectionView view = new ImageCollectionView(model);
        model.addObserver(view);

		//Notify the views of the model.
		model.notifyViews();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(900, 600);
        frame.setMinimumSize(new Dimension(300, 330));
        frame.add(view);
        frame.setVisible(true);
    }
}
