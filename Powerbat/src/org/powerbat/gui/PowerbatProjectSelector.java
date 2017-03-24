package org.powerbat.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.powerbat.projects.Project;
import org.powerbat.projects.ProjectData;

/**
 * The selector is used to display the list of available projects and allow the
 * user to open them. This will display all correctly published Runners.
 * 
 * @author Legend
 * @since 1.0
 */

public class PowerbatProjectSelector extends JPanel {

	private static final long serialVersionUID = 4869219241938861949L;

	private static final ArrayList<PowerbatProjectPanel> PROJECTS = new ArrayList<PowerbatProjectPanel>();
	private final JPanel selector;

	/**
	 * Constructs a new project selector. Only to be used inside of the
	 * {@link PowerbatGUI#openProject(Project)}.
	 * 
	 * @author Legend
	 * @since 1.0
	 */

	public PowerbatProjectSelector() {
		super(new BorderLayout());
		final String toolTip = " information. Press 'Open' to start it.";
		selector = new JPanel(new GridLayout(0, 2));
		for (final String category : ProjectData.DATA.keySet()) {
			for (final Project project : ProjectData.DATA.get(category)) {
				final PowerbatProjectPanel temp = new PowerbatProjectPanel(project);
				PROJECTS.add(temp);
				temp.setToolTipText(project.getName() + toolTip);
			}
		}
		Collections.sort(PROJECTS, new Comparator<PowerbatProjectPanel>() {

			@Override
			public int compare(PowerbatProjectPanel o1, PowerbatProjectPanel o2) {
				return o1.compareTo(o2);
			}

		});
		for (final PowerbatProjectPanel pane : PROJECTS) {
			selector.add(pane);
		}
		add(new JScrollPane(selector), BorderLayout.CENTER);
	}

	/**
	 * Gets the list of all added projects to the selector pane.
	 * 
	 * @return The list of available projects.
	 * @author Legend
	 * @since 1.0
	 */

	public static ArrayList<PowerbatProjectPanel> getProjectList() {
		return PROJECTS;
	}
}
