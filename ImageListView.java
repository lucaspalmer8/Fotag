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

public class ImageListView extends AbstractImageView {

	public static int WIDTH = 360;
	public static int HEIGHT = 240;

   	public ImageListView(ImageModel model) {
		super(model);
		BufferedImage img = null;
        Image newimg = null;

		Path file = Paths.get(m_model.getPath());
		BasicFileAttributes attrs = null;
		try {
    		attrs = Files.readAttributes(file, BasicFileAttributes.class);
		} catch (IOException ex) {
			System.out.println(ex);
		}
		JPanel m_rightPanel = new JPanel();
		m_rightPanel.setLayout(new BoxLayout(m_rightPanel, BoxLayout.Y_AXIS));
		m_rightPanel.setBackground(Color.WHITE);
		
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

		//m_rightPanel.add(name);
		//m_rightPanel.add(date);

		//m_rightPanel.add(m_ratingBar);

		JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bar.setBackground(Color.WHITE);
		bar.add(m_ratingBar);

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

		bar.add(reset);

		m_rightPanel.add(name);
		m_rightPanel.add(date);
		m_rightPanel.add(bar);
		m_rightPanel.setPreferredSize(new Dimension((int)m_rightPanel.getPreferredSize().getWidth(), 120));
		m_rightPanel.setMaximumSize(new Dimension((int)m_rightPanel.getMaximumSize().getWidth(), 120));

		JPanel spare = new JPanel();
		JPanel spare2 = new JPanel();
		spare2.setBackground(Color.WHITE);
		spare.setBackground(Color.WHITE);
		JPanel format = new JPanel();
		format.setLayout(new BoxLayout(format, BoxLayout.Y_AXIS));
		format.setBackground(Color.WHITE);
		format.add(spare);
		format.add(m_rightPanel);
		format.add(spare2);
		
		add(format, BorderLayout.EAST);
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
