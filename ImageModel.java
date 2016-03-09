import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ImageModel {
	private String m_path;
	private int m_rating;
	private ImageCollectionModel m_model;

   	public ImageModel(String path, ImageCollectionModel model) {
		m_model = model;
		m_path = path;
		m_rating = 0;
    }

	public ImageModel(String path, int rating, ImageCollectionModel model) {
		m_model = model;
		m_path = path;
		m_rating = rating;
	}

	public int getRating() {
		return m_rating;
	}

	public void setRating(int rating) {
		m_rating = rating;
		m_model.notifyViews();
	}

	public String getPath() {
		return m_path;
	}

	public JFrame getFrame() {
		return m_model.getFrame();
	}
}
