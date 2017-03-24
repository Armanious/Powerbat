package org.powerbat.methods;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.powerbat.configuration.Global;
import org.powerbat.configuration.Global.Paths;
import org.powerbat.configuration.Global.URLs;
import org.powerbat.gui.PowerbatSplash;
import org.powerbat.interfaces.Manifest;
import org.powerbat.projects.Project;
import org.powerbat.projects.ProjectData;

/**
 * Updates all Runner information and program data. This is used in conjunction
 * with the boot class and only should be used in there. This class handles most
 * client data and also calls upon the other loaders.
 * 
 * @author Legend
 * @since 1.0
 */
public class Updater {

	private static final String SUB_UPDATE = "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	private static final String MAIN_UPDATE = "<br>";
	private static final String OPEN_HTML = "<html>";
	private static final String CLOSE_HTML = "</html>";

	private static double updatedClientVersion = 0.0;
	private static HashMap<String, Double> updatedRunnersList = new HashMap<String, Double>();

	private static double currentClientVersion = 1.02;
	private static HashMap<String, Double> currentRunnersList = new HashMap<String, Double>();

	/**
	 * Gets the update logs for the info pane. Uses HTML styling to make updates
	 * logs legible.
	 * 
	 * @author Legend
	 * @since 1.0
	 * @return A string built for keeping track of updates.
	 */

	public static String getUpdateLogs() {
		final StringBuilder sb = new StringBuilder();
		sb.append(OPEN_HTML);
		sb.append(MAIN_UPDATE);
		sb.append("Powerbat v" + currentClientVersion);
		sb.append(SUB_UPDATE);
		sb.append("Fixed instruction issues.");
		sb.append(CLOSE_HTML);
		return sb.toString();
	}

	private Updater() {
	}

	/**
	 * Returns the current client version. This is hard-coded every update.
	 * 
	 * @author Legend
	 * @since 1.0
	 * @return Client version.
	 */

	public static double clientVersion() {
		return currentClientVersion;
	}

	/**
	 * Updates the client and all public Runners. No alpha implementation yet.
	 * Should only ever be ran once.
	 * 
	 * @author Legend
	 * @since 1.0
	 */

	public static void update() {
		if (!isInternetReachable()) {
			return;
		}
		String[] sources;
		try {
			final File sourceFile = new File(Paths.SETTINGS, "sources.txt");
			if (!sourceFile.exists()) {
				sourceFile.createNewFile();
			}
			final BufferedReader br = new BufferedReader(new FileReader(sourceFile));
			final ArrayList<String> src = new ArrayList<String>();
			src.add(URLs.BIN);
			String next;
			while ((next = br.readLine()) != null) {
				if (!next.contains("#")) {
					src.add(next);
				}
			}
			sources = src.toArray(new String[src.size()]);
			br.close();
		} catch (Exception e) {
			sources = new String[] { URLs.BIN };
		}
		for (final String src : sources) {
			byte[] updatedClientInfo = downloadCurrentClientInfo(src);
			if (updatedClientInfo == null) {
				return;
			}
			parseUpdated(updatedClientInfo, src);
			if (updatedClientVersion > currentClientVersion) {
				JOptionPane.showMessageDialog(null, "Update available - Please download from powerbot.org again.", "Update",
						JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
				return;
			}
			ProjectData.loadCurrent();
			final HashMap<String, ArrayList<Project>> data = ProjectData.DATA;
			final String runner = "Runner";
			for (final String key : data.keySet()) {
				for (final Project p : data.get(key)) {
					currentRunnersList.put(p.getName() + runner, p.getVersion());
				}
			}
			for (final String key : updatedRunnersList.keySet()) {
				if (!currentRunnersList.containsKey(key)) {
					System.out.println("Did not contain: " + key);
					download(key, src);
					continue;
				}
				final double current = currentRunnersList.get(key);
				final double updated = updatedRunnersList.get(key);
				if (updated > current) {
					download(key, src);
				}
			}
		}
		ProjectData.loadCurrent();
	}

	/**
	 * Downloads a runner name from a URL. The URL is constructed from the
	 * <tt>src</tt> followed by the <tt>runnerName</tt>.
	 * 
	 * @param runnerName
	 *            The name of the Runner, in pure form such as
	 *            <tt>SimpleAdditionRunner</tt>
	 * @param src
	 *            The depot of the Runner.
	 * @author Legend
	 * @since 1.0
	 */

	private static void download(String runnerName, String src) {
		try {
			PowerbatSplash.setStatus("Downloading " + runnerName);
			final byte[] data = IOUtils.download(new URL(src + "java/" + runnerName + ".class"));
			final String category = CustomClassLoader.loadClassFromData(runnerName, data).getAnnotation(Manifest.class).category();
			final File out = new File(Global.Paths.SOURCE + File.separator + category, runnerName + ".class");
			IOUtils.write(out, data);
		} catch (IOException e) {
			System.err.println("Unable to donwload " + runnerName);
		}

	}

	/**
	 * Reads the update information and runner information from a source. Source
	 * specific arguments do matter.
	 * 
	 * @param data
	 *            The byte data downloaded from the source.txt file.
	 * @param src
	 *            The URL directory for special loads.
	 * @author Legend
	 * @since 1.0
	 */

	private static void parseUpdated(byte[] data, String src) {
		try {
			final BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data)));
			if (src.equals(URLs.BIN)) {
				updatedClientVersion = Double.parseDouble(in.readLine());
			}
			String s;
			while ((s = in.readLine()) != null) {
				String[] split = s.split("-");
				updatedRunnersList.put(split[0], Double.parseDouble(split[1]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads information from a source. This will automatically be called for
	 * the default directory, as implemented by myself.
	 * 
	 * @param src
	 *            The URL for the bin.
	 * @return The byte data to be read.
	 * @author Legend
	 * @since 1.0
	 * @see {@link Updater#parseUpdated(byte[], String)}
	 */

	private static byte[] downloadCurrentClientInfo(String src) {
		try {
			return IOUtils.download(new URL(src + "version.txt"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Unable to connect to internet; unable to check versions.", "Update", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	/**
	 * Ping common sites to check Internet availability.
	 * 
	 * @return a boolean to check if Internet is available. Used before
	 *         attempting to update.
	 * @author Legend
	 * @since 1.0
	 * @since {@link Updater#update()}
	 */

	public static boolean isInternetReachable() {
		PowerbatSplash.setStatus("Checking connection");
		try {
			((HttpURLConnection) new URL(URLs.HOME).openConnection()).getContent();
		} catch (Exception e) {
			try {
				((HttpURLConnection) new URL("http://www.google.com").openConnection()).getContent();
			} catch (Exception e1) {
				return false;
			}
		}
		return true;
	}
}
