package pt.iul.ista.poo.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author POO 2016
 * 
 *         ImageMatrixGUI, manages and displays in its main window a grid of
 *         square images (implementations of ImageTile) of the same size. The
 *         default image-size is 48x48 and the default grid-size is 10x10.
 * 
 *         This class also provides a status-bar (also composed of square images
 *         of the same size) that will be displayed above the grid.
 * 
 *         This class is observable and will send an update message everytime a
 *         key is pressed.
 * 
 *         The images that ImageTiles refer to MUST be in a folder called
 *         "images" directly under the project folder.
 * 
 *         ImageTile is required to provide the name of the image (e.g.
 *         "XxxX") and its position (in tile coordinates, with 0,0 in the top
 *         left corner and increasing to the right in the horizontal axis and
 *         downwards in the vertical axis). ImageMatrixGUI will look for an
 *         image with that name in the "images" folder (e.g. "XxxX.png") and draw
 *         that image in the window.
 * 
 *         ImageMatrixGUI implements the Singleton pattern.
 *
 */

/**
 * @author lmmn
 *
 */
public class ImageMatrixGUI extends Observable {

	private static final ImageMatrixGUI INSTANCE = new ImageMatrixGUI();

	private final String IMAGE_DIR = "images";
	private final int LABEL_HEIGHT = 20;
	private final int SQUARE_SIZE;
	private final int N_SQUARES_WIDTH;
	private final int N_SQUARES_HEIGHT;

	private JFrame frame;
	private JPanel panel;
	private JLabel info;

	private Map<String, ImageIcon> imageDB = new HashMap<String, ImageIcon>();

	private List<ImageTile> images = new ArrayList<ImageTile>();

	private int lastKeyPressed;
	private boolean keyPressed;

	private int maxLevel;

	private Thread keyW;

	private ImageMatrixGUI() {
		SQUARE_SIZE = 50;
		N_SQUARES_WIDTH = 10;
		N_SQUARES_HEIGHT = 10;
		init();
	}

	/**
	 * @return Access to the Singleton instance of ImageMatrixGUI
	 */
	public static ImageMatrixGUI getInstance() {
		return INSTANCE;
	}

	/**
	 * 
	 * Setter for the name of the frame
	 * 
	 * @param name
	 *            Name of application (will be displayed as a frame title in the
	 *            top left corner)
	 * 
	 */

	public void setName(final String name) {
		frame.setTitle(name); // Corrected 2-Mar-2016
//		Image image = new ImageIcon("images/Empilhadora_D.png").getImage();
//		frame.setIconImage(image); // testando a frame
	}

	private void init() {
		frame = new JFrame();
		panel = new DisplayWindow();
		info = new JLabel();

		panel.setPreferredSize(new Dimension(N_SQUARES_WIDTH * SQUARE_SIZE, N_SQUARES_HEIGHT * SQUARE_SIZE));
		info.setPreferredSize(new Dimension(N_SQUARES_WIDTH * SQUARE_SIZE, LABEL_HEIGHT));
		info.setBackground(Color.BLACK);
		frame.add(panel);
		frame.add(info, BorderLayout.NORTH);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initImages();

		keyW = new KeyWatcher();
		keyW.start();

		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				lastKeyPressed = e.getKeyCode();
				keyPressed = true;
				releaseObserver();
			}
		});
	}

	synchronized void releaseObserver() {
		notify();
	}

	synchronized void waitForKey() throws InterruptedException {
		while (!keyPressed) {
			wait();
		}
		setChanged();
		notifyObservers(lastKeyPressed);
		keyPressed = false;
	}

	private void initImages() {
		File dir = new File(IMAGE_DIR);
		if (!dir.isDirectory())
			throw new IllegalStateException();
		for (File f : dir.listFiles()) {
			assert (f.getName().lastIndexOf('.') != -1);
			imageDB.put(f.getName().substring(0, f.getName().lastIndexOf('.')),
					new ImageIcon(IMAGE_DIR + "/" + f.getName()));
		}
	}

	/**
	 * 
	 * Make the window visible.
	 * 
	 */
	public void go() {
		frame.setVisible(true);
	}

	/**
	 * 
	 * Add a new set of images to the main window.
	 * 
	 * @param newImages
	 *            images to be added to main window
	 * 
	 * @throws IllegalArgumentException
	 *             if no image with that name (and a suitable extension) is
	 *             found the images folder
	 * 
	 */

	public void addImages(final List<ImageTile> newImages) {
		synchronized (images) { // Added 16-Mar-2016
			if (newImages == null)
				throw new IllegalArgumentException("Null list");
			if (newImages.size() == 0)
				return;
			for (ImageTile i : newImages) {
				if (i == null)
					throw new IllegalArgumentException("Null image");
				if (!imageDB.containsKey(i.getName())) {
					throw new IllegalArgumentException("No such image in DB " + i.getName());
				}
				addImage(i);
			}
		}
	}

	// Added 2-Mar-2016
	/**
	 * Removes the image given as a parameter.
	 * 
	 * Does nothing if there is no match.
	 * 
	 * @param image
	 *            to be removed (must be the exact same Object and not a copy)
	 * 
	 */

	public void removeImage(final ImageTile image) {
		synchronized (images) { // Added 16-Mar-2016
			images.remove(image);
		}
	}

	// Added 2-Mar-2016
	/**
	 * Adds image to main window
	 * 
	 * @param image
	 *            to be added
	 */
	public void addImage(final ImageTile image) {
		synchronized (images) { // Added 16-Mar-2016
			if (image == null)
				throw new IllegalArgumentException("Null image");
			if (image.getName() == null)
				throw new IllegalArgumentException("Null image name");
			if (image.getPosition() == null)
				throw new IllegalArgumentException("Null image position");
			if (image.getLevel() >= maxLevel)
				maxLevel = image.getLevel() + 1; 
			if (image.getPosition().getX() < 0 || image.getPosition().getX() > N_SQUARES_WIDTH)
				throw new IllegalArgumentException("Invalid image position " + image.getName() + ": " + image.getPosition());
			if (image.getPosition().getY() < 0 || image.getPosition().getY() > N_SQUARES_HEIGHT)
				throw new IllegalArgumentException("Invalid image position " + image.getName() + ": " + image.getPosition());
			if (!imageDB.containsKey(image.getName())) {
				throw new IllegalArgumentException("No such image in DB " + image.getName());
			}
			images.add(image);
		}
	}

	/**
	 * Clear all images displayed in main window.
	 */
	public void clearImages() {
		synchronized (images) { // Added 16-Mar-2016
			images.clear();
		}
	}

	/**
	 * 
	 * Add a new set of images to the status window.
	 * 
	 * @param newImages
	 *            images to be added to status bar
	 *
	 * @throws IllegalArgumentException
	 *             if no image with that name (and a suitable extension) is
	 *             found the images folder
	 *
	 * 
	 */

	public void setStatusMessage(String message) {
		info.setText(message);
	}


	@SuppressWarnings("serial") // Added 2-Mar-2016
	private class DisplayWindow extends JPanel {
		@Override
		public void paintComponent(Graphics g) {
			// System.out.println("Thread " + Thread.currentThread() + "
			// repainting");
			synchronized (images) { // Added 16-Mar-2016
				for (int j = 0; j != maxLevel; j++) 
				for (ImageTile i : images) {
					if (i.getLevel() == j) {
						if (i.getPosition().getX() < 0 || i.getPosition().getX() > N_SQUARES_WIDTH)
							throw new IllegalStateException("Invalid image position " + i.getName() + ": " + i.getPosition());
						if (i.getPosition().getY() < 0 || i.getPosition().getY() > N_SQUARES_HEIGHT)
							throw new IllegalStateException("Invalid image position " + i.getName() + ": " + i.getPosition());
						g.drawImage(imageDB.get(i.getName()).getImage(), i.getPosition().getX() * SQUARE_SIZE,
								i.getPosition().getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, frame);
					}
				}
			}
		}
	}

	private class KeyWatcher extends Thread {
		public void run() {
			try {
				while (true)
					waitForKey();
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * Force scheduling of a new window paint (this may take a while, it does
	 * not necessarily happen immediately after this instruction is issued)
	 */
	public void update() {
		frame.repaint();
	}

	/**
	 * Terminate window GUI
	 */
	public void dispose() {
		images.clear();
		imageDB.clear();
		frame.dispose();
		keyW.interrupt();
	}

	/**
	 * 
	 * Grid dimensions
	 * 
	 * @return the width and height of the image grid
	 * 
	 */
	public Dimension getGridDimension() {
		return new Dimension(N_SQUARES_WIDTH, N_SQUARES_HEIGHT);
	}

}
