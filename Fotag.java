import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Fotag {

    public JFrame frame;
	public JPanel panel;
	public JScrollPane fullView;
	public JPanel fitView;

	public static void main(String[] args) {
		Fotag fotag = new Fotag();
	}

    public Fotag() {
		frame = new JFrame("Fotag");
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		for (int i = 0; i < 10; i++) {
			ImageModel model = new ImageModel("/home/lucas/Desktop/dolphin.jpg");
			panel.add(new ImageView(model));
			//panel.add(new DrawingCanvas());
			//panel.add(new DrawingCanvas());
		}

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(900, 600);
        frame.setMinimumSize(new Dimension(300, 330));
        frame.add(panel);
        frame.setVisible(true);
    }
}
