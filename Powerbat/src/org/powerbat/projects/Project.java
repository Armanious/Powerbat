package org.powerbat.projects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.powerbat.configuration.Global.Paths;
import org.powerbat.gui.PowerbatProjectPanel;
import org.powerbat.gui.PowerbatProjectSelector;
import org.powerbat.interfaces.Manifest;
import org.powerbat.methods.CustomClassLoader;
import org.powerbat.methods.IOUtils;

public class Project {

	public static final String[] DIFFICULTY = new String[] { "Beginner", "Intermediate", "Advanced", "Challenging", "Legendary" };

	private final String name;
	private final File file;
	private final String instructions;
	private final String category;
	private final String className;
	private final String method;
	private final Class<?> runner;
	private final double version;
	private final int level;
	private boolean complete;

	public Project(String name, File runnerFile) {
		try {
			final BufferedReader br = new BufferedReader(new FileReader(new File(Paths.SETTINGS + File.separator + "data.dat")));
			boolean b;
			try {
				final String in = br.readLine();
				b = (in.contains("|" + name + "|"));
			} catch (Exception e) {
				b = false;
			}
			complete = b;
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error getting instructions for " + name);
		}
		file = new File(Paths.JAVA + File.separator + name + ".java");
		runner = CustomClassLoader.loadClassFromFile(runnerFile);
		final Manifest manifest = runner.getAnnotation(Manifest.class);
		instructions = manifest.instructions();
		category = manifest.category();
		version = manifest.version();
		className = manifest.className();
		method = manifest.method();
		level = Math.min(5, Math.max(1, manifest.level()));
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getSortName() {
		return level + getName();
	}

	public String toString() {
		return getName();
	}

	public int getLevel(){
		return level;
	}
	
	public String getCurrentCode() {
		try {
			if (file.exists()) {
				final byte[] data = IOUtils.readData(file);
				return new String(IOUtils.readData(file), 0, data.length);
			}

			return getSkeleton();
		} catch (Exception e) {
			if (e instanceof NoSuchMethodException) {
				return "";
			}
			e.printStackTrace();
		}
		return "";
	}

	public String getInstructions() {
		return instructions;
	}

	public File getFile() {
		return file;
	}

	public double getVersion() {
		return version;
	}

	public String getCategory() {
		return category;
	}

	public Class<?> getRunner() {
		return runner;
	}

	public int hashCode() {
		return name.hashCode() * 31 + file.hashCode() * 17 - instructions.hashCode() * 3;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
		for(final PowerbatProjectPanel panel : PowerbatProjectSelector.getProjectList()){
			if(panel.getProject().equals(this)){
				panel.setComplete(complete);
				return;
			}
		}
	}

	public boolean equals(Object o) {
		if (!(o instanceof Project)) {
			return false;
		}
		return this.hashCode() == ((Project) o).hashCode();
	}

	public boolean save(String code) {
		try {
			IOUtils.write(getFile(), code.getBytes());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getSkeleton() {
		return "public class " + className + " {\n\t\n\tpublic " + method + "{\n\t\n\t}\n\n}";
	}

}