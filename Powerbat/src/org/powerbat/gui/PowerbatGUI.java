package org.powerbat.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.metal.MetalButtonUI;

import org.powerbat.configuration.Global;
import org.powerbat.methods.Updater;
import org.powerbat.projects.Project;

/**
 * Only for use in the boot and the setting and getting of Projects.
 * 
 * @author Legend
 * @since 1.0
 * @see PowerbatGUI#setProject(Project)
 * @see PowerbatGUI#removeTab(String)
 * @see PowerbatGUI#tabByName(String)
 */
public class PowerbatGUI extends JFrame {

	private static final long serialVersionUID = 4204914118301301061L;

	private static JTabbedPane tabs;
	private static PowerbatProjectSelector selecter;

	/**
	 * Creates a new PowerbatGUI instance. Should only be done once per
	 * <tt>Runtime</tt>. This will set all listeners and handle the
	 * initialization of the static arguments to be used later.
	 * 
	 * @author Legend
	 * @since 1.0
	 */
	public PowerbatGUI() {
		super("Powerbat");
		final JPanel main = new JPanel(new BorderLayout());
		final JPanel content = new JPanel(new BorderLayout());
		final JPanel mainpane = new JPanel();
		final JPanel infopane = new JPanel();
		final JPanel info = new JPanel(new BorderLayout());
		final JLabel logs = new JLabel();

		logs.setText(Updater.getUpdateLogs());
		logs.setVerticalAlignment(SwingConstants.TOP);
		logs.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		info.add(new JScrollPane(logs));

		tabs = new JTabbedPane();
		selecter = new PowerbatProjectSelector();

		setContentPane(main);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1000, 600));
		setMinimumSize(getPreferredSize());
		setLocationRelativeTo(getParent());

		try {
			setIconImage(Global.getImage(Global.ICON_IMAGE));
		} catch (Exception e) {
			PowerbatSplash.setStatus("Downloading icon failed");
		}

		content.add(selecter, BorderLayout.CENTER);

		mainpane.setOpaque(false);
		mainpane.add(new JLabel(
				"<html><head><style>p.padding {padding-left:50px; padding-right:50px;}</style></head><body><p class=\"padding\">Home</p></body></html>"));
		infopane.setOpaque(false);
		infopane.add(new JLabel(
				"<html><head><style>p.padding {padding-left:50px; padding-right:50px;}</style></head><body><p class=\"padding\">Info</p></body></html>"));
		tabs.setTabPlacement(SwingConstants.LEFT);
		tabs.add(content, tabs.getTabCount());
		tabs.setTabComponentAt(tabs.getTabCount() - 1, mainpane);
		tabs.add(info, tabs.getTabCount());
		tabs.setTabComponentAt(tabs.getTabCount() - 1, infopane);

		main.add(tabs);

		setVisible(true);
		PowerbatSplash.setStatus("Complete");
	}

	/**
	 * Adds a project selected from the PowerbatProjectSelector. This can also
	 * be accessed through the {@link PowerbatGUI#getOpenButton()} instance.
	 * 
	 * @param project
	 *            The selected Project you wish to add.
	 * @author Legend
	 * @since 1.0
	 */

	protected synchronized static void openProject(final Project project) {
		if (project == null) {
			return;
		}
		final PowerbatJavaEditor temp = new PowerbatJavaEditor(project);
		temp.setInstructionsText(project.getInstructions());
		if (tabByName(project.getName()) == null) {
			final JButton button = new JButton(new ImageIcon(Global.getImage(Global.CLOSE_IMAGE)));
			final JLabel label = new JLabel(project.getName());
			final JPanel pane = new JPanel(new BorderLayout());
			final JPanel main = new JPanel(new BorderLayout());

			pane.setName(project.getName());
			pane.setPreferredSize(new Dimension(140, 30));
			pane.setOpaque(false);
			pane.add(label, BorderLayout.CENTER);

			tabs.add(temp, tabs.getTabCount() - 1);
			tabs.setTabComponentAt(tabs.getTabCount() - 2, main);

			main.add(button, BorderLayout.EAST);
			main.add(pane, BorderLayout.CENTER);
			main.setOpaque(false);
			main.setPreferredSize(new Dimension(170, 30));

			label.setLocation(pane.getWidth() - label.getWidth(), pane.getY());

			button.setToolTipText("Close Project");
			button.setBorder(new BevelBorder(BevelBorder.RAISED));
			button.setPreferredSize(new Dimension(30, 30));
			button.setUI(new MetalButtonUI());
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					removeTab(project.getName());
				}
			});

		}
		tabs.setSelectedComponent(tabByName(project.getName()));
	}

	/**
	 * This will return the <tt>PowerbatJavaEditor</tt> that corresponds to the
	 * given name.
	 * 
	 * @param name
	 *            The name of the tab, always portrayed through
	 *            {@link Runner#getName()}
	 * @return The tab instance of the Java editor.
	 * @author Legend
	 * @since 1.0
	 */

	public synchronized static PowerbatJavaEditor tabByName(String name) {
		for (final Component c : tabs.getComponents()) {
			if (c != null && c instanceof PowerbatJavaEditor) {
				final PowerbatJavaEditor c1 = (PowerbatJavaEditor) c;
				if (name.equals(c1.getName())) {
					return c1;
				}
			}
		}
		return null;
	}

	/**
	 * Removes the tab from the tabbed pane. Loads the tab by name.
	 * 
	 * @param name
	 *            Always represented through {@link Runner#getName()}
	 * @author Legend
	 * @since 1.0
	 * @see PowerbatGUI#tabByName(String)
	 */

	public synchronized static void removeTab(String name) {
		final PowerbatJavaEditor cur = tabByName(name);
		if (cur != null) {
			tabs.remove(cur);
			return;
		}
		System.err.println("Failed to close tab " + name);
	}

}