import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;

public class ImageCollectionModel {

	private ArrayList<ViewInterface> m_observers = new ArrayList<ViewInterface>();
	private ArrayList<ImageModel> m_images = new ArrayList<ImageModel>();

   	public ImageCollectionModel() {
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
					m_images.add(new ImageModel(path, rating));
				}
			} finally {
				bufferedReader.close();
			}
		} catch(FileNotFoundException ex) {
        } catch(IOException ex) {
        }		
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
