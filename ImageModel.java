import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ImageModel {

	private String m_path;
	private int m_rating;

   	public ImageModel(String path) {
		m_path = path;
		m_rating = 0;
    }

	public ImageModel(String path, int rating) {
		m_path = path;
		m_rating = rating;
	}

	public int getRating() {
		return m_rating;
	}

	public void setRating(int rating) {
		m_rating = rating;
	}

	public String getPath() {
		return m_path;
	}
}
