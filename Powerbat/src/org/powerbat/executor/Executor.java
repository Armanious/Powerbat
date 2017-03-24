package org.powerbat.executor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Date;

import org.powerbat.configuration.Global;
import org.powerbat.gui.PowerbatGUI;
import org.powerbat.gui.PowerbatJavaEditor;
import org.powerbat.methods.CustomClassLoader;
import org.powerbat.projects.Project;

/**
 * Source code compiler and executor. This is used internally during the run of
 * the input code.
 * 
 * @author Legend
 * @since 1.0
 */

public class Executor {

	/**
	 * Runs the compiled code from the {@link Executor#compileClass(Project)
	 * compileClass(Project)} method. This returns a list of <tt>Result</tt>s
	 * dependent on the users input and the provided answer set.
	 * 
	 * @param project
	 *            The specific project to compile and run to provide results
	 *            for.
	 * @return An array of type {@link Result Result}.
	 * @author Legend
	 * @since 1.0
	 * @see Executor#compileClass(Project)
	 */

	public static Result[] runAndGetResults(Project project) {
		if (project != null) {
			if (compileClass(project)) {
				try {
					final Class<?> ref = project.getRunner();
					final Method m = ref.getMethod("getResults", Class.class);
					final Result[] results = (Result[]) m.invoke(ref.newInstance(),
							CustomClassLoader.loadClassFromFile(Global.Paths.JAVA + File.separator + project.getName() + ".class"));
					return results;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * This will use the located JDK directory during initialization and compile
	 * the code provided by the user. This file is then handed off to the
	 * {@link Executor#runAndGetResults(Project) runAndGetResults(Project)}
	 * method where it is processed. This method will only return true should
	 * the code be successfully compiled.
	 * 
	 * @param project
	 *            The project source you wish to compile, as provided by the
	 *            user.
	 * @return <tt>true</tt> should the class successfully compile and write to
	 *         a file.
	 * @author Legend
	 * @since 1.0
	 * @see Executor#runAndGetResults(Project)
	 */

	private static boolean compileClass(Project project) {
		InputStream err = null;
		try {
			final String classPath = System.getProperty("java.class.path");
			final String projectParent = project.getFile().getParent();
			final String projectFile = project.getFile().getPath();
			final String cmd = "javac -g -cp " + classPath + " -d " + projectParent
					+ " " + projectFile;
			final Process p = Runtime.getRuntime().exec(cmd, null, null);
			err = p.getErrorStream();
			p.waitFor();
			final BufferedReader error = new BufferedReader(new InputStreamReader(err));
			String line;
			boolean failed = false;
			final String format = "[" + new Date(System.currentTimeMillis()).toString() + "]_>";
			final PowerbatJavaEditor editor = PowerbatGUI.tabByName(project.getName());
			while ((line = error.readLine()) != null) {
				if (!failed) {
					editor.setInstructionsText("");
					failed = true;
				}
				System.err.print(format);
				final String build = line.concat("\n").substring(Math.max(line.indexOf("java:"), 0)).replace("java:", "line ");
				System.err.print(build);
				editor.append(build);
			}
			return !failed;
		} catch (Exception e) {
			if (err != null) {
				final BufferedReader in = new BufferedReader(new InputStreamReader(err));
				String s;
				try {
					while ((s = in.readLine()) != null) {
						System.err.println(s);
					}
				} catch (IOException e1) {
				}
			}
			e.printStackTrace();
			return false;
		}
	}

}
