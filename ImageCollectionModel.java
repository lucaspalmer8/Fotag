import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;

public class ImageCollectionModel {

	private ArrayList<ViewInterface> m_observers = new ArrayList<ViewInterface>();
	private ArrayList<ImageModel> m_images = new ArrayList<ImageModel>();
	private Fotag m_fotag;
	private int m_ratingFilter = 0;

   	public ImageCollectionModel(Fotag fotag) {
		m_fotag = fotag;
		try {
			FileReader fileReader = new FileReader("laststate.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String line = null;

			try {
				line = bufferedReader.readLine();
				int size = Integer.parseInt(line);
				for (int i = 0; i < size; i++) {					
					String path = bufferedReader.readLine();
					line = bufferedReader.readLine();
					int rating = Integer.parseInt(line);
					m_images.add(new ImageModel(path, rating, this));
				}
			} finally {
				bufferedReader.close();
			}
		} catch(FileNotFoundException ex) {
        } catch(IOException ex) {
        }		
	}

	public void setRatingFilter(int filter) {
		m_ratingFilter = filter;
		notifyViews();
	}

	public int getRatingFilter() {
		return m_ratingFilter;
	}

	public int getVisibleImages() {
		int counter = 0;
		for (ImageModel model : m_images) {
			if (model.getRating() >= m_ratingFilter) {
				counter++;
			}
		}
		return counter;
	}

	public JFrame getFrame() {
		return m_fotag.getFrame();
	}

	public void addImage(String path) {
		for (ImageModel model : m_images) {
			if (path.equals(model.getPath())) {
				return;
			}
		}
		m_images.add(new ImageModel(path, 0, this));
		notifyViews();
	}

	public void enableGridView() {
		m_fotag.getPanel().remove(m_fotag.getListView());
		m_fotag.getPanel().add(m_fotag.getGridView(), BorderLayout.CENTER);
		m_fotag.getPanel().revalidate();
		m_fotag.getPanel().repaint();
		notifyViews();
	}

	public void enableListView() {
        m_fotag.getPanel().remove(m_fotag.getGridView());
        m_fotag.getPanel().add(m_fotag.getListView(), BorderLayout.CENTER);
        m_fotag.getPanel().revalidate();
        m_fotag.getPanel().repaint();
		notifyViews();
    }

	public void addObserver(ViewInterface view) {
		m_observers.add(view);
	}

	public void notifyViews() {
		for (ViewInterface view : m_observers) {
			view.notifyView();
		}
	}

	public ArrayList<ImageModel> getImages() {
		return m_images;
	}

	public void saveState() {
		try {
			FileWriter fileWriter = new FileWriter("laststate.txt");
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			// Note that write() does not automatically
			// append a newline character.
			try {
				bufferedWriter.write(String.valueOf(m_images.size()));
				bufferedWriter.newLine();
				for (int i = 0; i < m_images.size(); i++) {
					ImageModel imageModel = m_images.get(i);
					bufferedWriter.write(imageModel.getPath());
					bufferedWriter.newLine();
					bufferedWriter.write(String.valueOf(imageModel.getRating()));
					bufferedWriter.newLine();
				}
			} finally { 
				bufferedWriter.close();
			}					
		} catch(IOException ex) {}
	}
}
