package org.powerbat;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import org.powerbat.configuration.Global;
import org.powerbat.configuration.Global.Paths;
import org.powerbat.gui.PowerbatGUI;
import org.powerbat.gui.PowerbatSplash;
import org.powerbat.methods.Updater;

/**
 * The boot class is responsible for basic loading for the client. Bringing all
 * the classes into unison, it effectively creates what is known as Powerbat.
 * Advanced technology to help you learn Java and fulfill what I like to know as
 * 'Good standing'. Helping others for free. Give what you can and take what you
 * must. From everything to the CustomClassLoader class to the Project class,
 * everything here was made for you, the user. I hope you have a great time
 * running this application. <br>
 * <br>
 * {@code
 * Powerbat - Program to help teach users Java. Copyright (C) 2012 Legend
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version. <br>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details. <br>
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see http://opensource.org/licenses/gpl-3.0.html.}
 * 
 * @author Legend
 * @since 1.0
 * @version 1.0
 */

public class Boot {
	/**
	 * Nothing truly big to see here. Runs the application. Really should be
	 * monitored but it isn't.
	 * 
	 * @param args
	 *            ignored. Or is it?
	 * @since 1.0
	 * @author Legend
	 */

	public static void main(String[] args) {
		Paths.build();
		Global.loadImages();
		PowerbatSplash.setStatus("Loading");
		final PowerbatSplash splash = new PowerbatSplash();
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					splash.setVisible(true);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		Updater.update();
		PowerbatSplash.setStatus("Loading framework");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(new MetalLookAndFeel());
					PowerbatSplash.setStatus("Building GUI");
					new PowerbatGUI();
					splash.shouldDispose(true);
					splash.dispose();
					PowerbatSplash.setStatus(null);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
		});
	}
}
