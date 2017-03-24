package org.powerbat.configuration;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.powerbat.gui.PowerbatSplash;
import org.powerbat.methods.Updater;

/**
 * 
 * Used for file, directory and image creations. Contains information regarding
 * URLs, operating system information and file directories.
 * 
 * @author Legend
 * @since 1.0
 * @see URLs
 * @see Paths
 */
public class Global {

	private static BufferedImage[] imgs;

	public static final int SPLASH_IMAGE = 0;
	public static final int ICON_IMAGE = 1;
	public static final int CLOSE_IMAGE = 2;
	public static final int COMPLETE_IMAGE = 3;

	/**
	 * Loads the images. Preferably only ran once in the boot to avoid
	 * unnecessary usage.
	 * 
	 * You can load these images through {@link Global#getImage(int)
	 * getImage(int)}
	 * 
	 * @author Legend
	 * @since 1.0
	 * @see Global#getImage(int)
	 */

	public static void loadImages() {
		imgs = new BufferedImage[URLs.IMAGES.length];
		for (int i = 0; i < URLs.IMAGES.length; i++) {
			try {
				final File f = new File(Paths.SETTINGS, URLs.IMAGE_NAMES[i] + ".png");
				if (f.exists()) {
					imgs[i] = ImageIO.read(f);
				} else {
					if (Updater.isInternetReachable()) {
						System.out.println("Downloading images");
						imgs[i] = ImageIO.read(new URL(URLs.IMAGES[i]));
						ImageIO.write(imgs[i], "png", f);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 
	 * Used to return the loaded images. These are primarily for internal use
	 * only.
	 * 
	 * @param num
	 *            The image instance to load.
	 * @author Legend
	 * @since 1.0
	 * @return Image instance from pre-loaded or defined images.
	 * @see Global#CLOSE_IMAGE
	 * @see Global#ICON_IMAGE
	 * @see Global#SPLASH_IMAGE
	 */

	public static Image getImage(int num) {
		if (num >= 0 && num <= 3) {
			return imgs[num];
		}
		throw new IndexOutOfBoundsException("Invalid input");
	}

	/**
	 * For path configurations. Depends on {@link OS} for certain dependencies.
	 * 
	 * @author Legend
	 * @since 1.0
	 */

	public static class Paths {

		public static final String APPDATA = getAppData();
		public static final String HOME = APPDATA + File.separator + "Powerbat";
		public static final String SOURCE = HOME + File.separator + "src";
		public static final String ARRAY = SOURCE + File.separator + "array";
		public static final String STRING = SOURCE + File.separator + "string";
		public static final String LOGIC = SOURCE + File.separator + "logic";
		public static final String AP = SOURCE + File.separator + "AP";
		public static final String RECURSIVE = SOURCE + File.separator + "recursive";
		public static final String SETTINGS = HOME + File.separator + "data";
		public static final String JAVA = SETTINGS + File.separator + "java";
		public static final String[] PATHS = new String[] { APPDATA, HOME, SOURCE, ARRAY, STRING, LOGIC, AP, RECURSIVE, SETTINGS, JAVA };

		/**
		 * Used to build external folder sets. Internal use only.
		 * 
		 * @author Legend
		 * @since 1.0
		 */

		public static void build() {
			for (final String s : PATHS) {
				final File f = new File(s);
				if (!f.exists()) {
					PowerbatSplash.setStatus("Creating Dirctory: " + s);
					f.mkdir();
				}
			}
			final File complete = new File(SETTINGS + File.separator + "data.dat");
			if (!complete.exists()) {
				try {
					complete.createNewFile();
				} catch (IOException e) {
					System.err.print("Failed to create data file.");
					System.err.println("Completion progress will not be saved, code still will be.");
				}
			}
		}
	}

	/**
	 * The default URL's for all of Powerbat's necessities such as images,
	 * version information and packaged Runners.
	 * 
	 * @author Legend
	 * @since 1.0
	 * @see {@link Updater#update()}
	 * 
	 */

	public static class URLs {
		public static final String HOME = "http://powerbat.webs.com/org/powerbat/";
		public static final String SPLASH = "http://bit.ly/P0UNyH";
		public static final String BIN = HOME + "bin/";
		public static final String ICON = BIN + "icon.png";
		public static final String CLOSE = BIN + "close.png";
		public static final String COMPLETE = BIN + "complete.png";
		public static final String JAVA = BIN + "java/";
		public static final String[] IMAGES = new String[] { SPLASH, ICON, CLOSE, COMPLETE };
		public static final String[] IMAGE_NAMES = new String[] { "splash", "icon", "close", "complete" };
	}

	/**
	 * To switch and find the current Operating system.
	 * 
	 * @author Legend
	 * @since 1.0
	 * @see Global#getAppData()
	 * @see Global#getOS()
	 */
	public enum OS {
		WINDOWS, MAC, LINUX, OTHER
	}

	/**
	 * Returns the location of the parent storage directory.
	 * 
	 * @return <tt>String</tt> representation of the <tt>AppData</tt> or
	 *         <tt>user.home</tt> directory
	 * @see Global#getOS()
	 * @author Legend
	 * @since 1.0
	 */
	public static String getAppData() {
		return getOS() == OS.WINDOWS ? System.getenv("APPDATA") : System.getProperty("user.home");
	}

	/**
	 * Returns the OS system for basic configuration.
	 * 
	 * @return Returns the <tt>enumerator</tt> of type {@link OS}
	 * @see Global#getAppData()
	 * @author Legend
	 * @since 1.0
	 */
	public static OS getOS() {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("windows")) {
			return OS.WINDOWS;
		}
		if (os.contains("mac")) {
			return OS.MAC;
		}
		if (os.contains("linux")) {
			return OS.LINUX;
		}
		return OS.OTHER;
	}
}
