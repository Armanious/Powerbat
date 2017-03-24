package org.powerbat.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.powerbat.projects.Project;

/**
 * This is the upper level part of every Project. This will contain data as to
 * what the project entitles. All parts must be added for a successful Runner.
 * 
 * @author Legend
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Manifest {

	/**
	 * Version is used to keep track of what the program needs to be download
	 * and what doesn't. Under <tt>1.0</tt> is considered alpha. No carrying
	 * mark of an alpha project has yet been implemented.
	 * 
	 * @return The version as a <tt>double</tt> value.
	 * 
	 * @since 1.0
	 * @author Legend
	 */

	public double version();

	/**
	 * Instructions are necessary for the users to understand <i>what</i> you
	 * are trying to make them do. The purpose of this program is not to leave
	 * them in the dark, but bring them to the light.
	 * 
	 * @return <tt>String</tt> representation of the instructions. HTML styling
	 *         is not supported.
	 * @since 1.0
	 * @author Legend
	 */

	public String instructions();

	/**
	 * Category for directory storage. The standard categories are: AP,
	 * Recursion, Logic, Array and String. Please use proper discretion when
	 * picking this.
	 * 
	 * @return Category for files.
	 * @since 1.0
	 * @author Legend
	 */

	public String category();

	/**
	 * Used primarily in the skeleton, as for now all classes must be registered
	 * as the Runner's class name without the Runner keyword.
	 * 
	 * @return
	 * @since 1.0
	 * @author Legend
	 */

	public String className();

	/**
	 * Used in the skeleton. A proper method instance would be as follows:
	 * {@code "int add(int a, int b)"}
	 * 
	 * Which will return the following in the skeleton:
	 * 
	 * {@code "public int add(int a, int b)"}
	 * 
	 * @return
	 */

	public String method();

	/**
	 * This is used for categorizing the projects based off of difficulty.
	 * 
	 * @return Integer for the level, ranging from 1-5.
	 * @see {@link Project#DIFFICULTY}
	 * @author Legend
	 * @since 1.0
	 */

	public int level();

}
