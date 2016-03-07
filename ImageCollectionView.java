import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DrawingCanvas extends JPanel {//implements ViewInterface {

	//private Model m_model;
	//private boolean m_fullView;

   	public DrawingCanvas() {
//		setMaximumSize(new Dimension(400, 400));
		setBorder(BorderFactory.createLineBorder(Color.black, 5));
		setBackground(Color.BLACK);
    }

	@Override
    public Dimension getPreferredSize() {
    	return new Dimension(200, 200);
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
