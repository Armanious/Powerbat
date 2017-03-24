package org.powerbat.projects;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;

import org.powerbat.configuration.Global.Paths;

/**
 * Loads locally found runners for the updater and for the loader. This will
 * retrieve all runners found in the respective categories.
 * 
 * @author Legend
 * @since 1.0
 */

public class ProjectData {

	public static HashMap<String, ArrayList<Project>> DATA;

	private static final FileFilter CLASS_FILES = new FileFilter() {
		public boolean accept(File file) {
			return file.getName().toLowerCase().endsWith(".class");
		}
	};

	private ProjectData() {
	}

	/**
	 * This does a sweep of the source folder to get all 5 categories and
	 * respective Runners inside of them. This is should only be called to check
	 * for currently loaded Runners, then to add all the updated versions of
	 * Runners, if any. The key for the HashMap is the category, with the value
	 * being a list of runners.
	 * 
	 * @author Legend
	 * @since 1.0
	 */

	public static void loadCurrent() {
		final HashMap<String, ArrayList<Project>> map = new HashMap<String, ArrayList<Project>>();
		final File root = new File(Paths.SOURCE);
		if (!root.exists()) {
			return;
		}
		for (final File parent : root.listFiles()) {
			if (parent.isDirectory()) {
				final ArrayList<Project> projects = new ArrayList<Project>();
				for (final File child : parent.listFiles(CLASS_FILES)) {
					final String name = child.getName();
					final int idx = name.indexOf("Runner.class");
					if (idx == -1) {
						continue;
					}
					final Project p = new Project(name.substring(0, idx), child);
					projects.add(p);
				}
				map.put(parent.getName(), projects);
			}
		}
		DATA = map;
	}

}
