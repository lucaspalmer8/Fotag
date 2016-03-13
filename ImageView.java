import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.nio.file.*;
import java.nio.*;

import java.nio.file.*;
import java.nio.file.Path;
import java.nio.file.attribute.*;
import java.io.IOException;

import java.util.Date;
import java.text.SimpleDateFormat;

public class ImageView extends AbstractImageView {

	public static int WIDTH = 225;
	public static int HEIGHT = 300;

   	public ImageView(ImageModel model) {
		super(model);

		Path file = Paths.get(m_model.getPath());
		BasicFileAttributes attrs = null;
		try {
    		attrs = Files.readAttributes(file, BasicFileAttributes.class);
		} catch (IOException ex) {
			System.out.println(ex);
		}
		JPanel m_bottomPanel = new JPanel();
		m_bottomPanel.setLayout(new BoxLayout(m_bottomPanel, BoxLayout.Y_AXIS));
		m_bottomPanel.setBackground(Color.WHITE);
		
		JPanel date = new JPanel(new FlowLayout(FlowLayout.LEFT));
		date.setBackground(Color.WHITE);
		JLabel dateCreated = new JLabel();
		dateCreated.setBackground(Color.WHITE);
	
		String DATE_FORMAT = "MM/dd/yyyy";
    	SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
    	//System.out.println("Formated Date " + sdf.format(date));

		dateCreated.setText(sdf.format(new Date(attrs.creationTime().toMillis())).toString());
		date.add(dateCreated);
		date.setBackground(Color.WHITE);
		JPanel name = new JPanel(new FlowLayout(FlowLayout.LEFT));
		name.setBackground(Color.WHITE);
		JLabel fileName = new JLabel(new File(m_model.getPath()).getName());
		fileName.setBackground(Color.WHITE);
		name.add(fileName);

		date.add(m_ratingBar);

		BufferedImage img = null;
        Image newimg = null;
		try {
            img = ImageIO.read(new File("reset.png"));
        } catch (IOException e) {}
        newimg = img.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		JButton reset = new JButton(new ImageIcon(newimg));
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m_model.setRating(0);
                notifyView();
			}
		});
		reset.setPreferredSize(new Dimension(20, 20));
		reset.setBackground(Color.WHITE);
        reset.setFocusPainted(false);

		date.add(reset);

		m_bottomPanel.add(name);
		m_bottomPanel.add(date);
		//m_bottomPanel.add(m_ratingBar);

		add(m_bottomPanel, BorderLayout.SOUTH);
    }

	@Override
    public Dimension getPreferredSize() {
   		return new Dimension(WIDTH, HEIGHT);
    }

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

	@Override
	public Dimension getMinimumSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
}
