package guiMummy;

import MummyMaze.MummyMazeAgent;
import MummyMaze.MummyMazeState;
import showSolution.SolutionPanel;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

	public Main() {

		MainFrame frame = new MainFrame();

		/*MummyMazeAgent agent = new MummyMazeAgent(environment);

		File file = new File("nivel_so_com_heroi_e_paredes.txt");

		agent.readInitialStateFromFile(file);*/

		// Center the window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		frame.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);

		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException |
						InstantiationException |
						IllegalAccessException |
						UnsupportedLookAndFeelException exception) {
					exception.printStackTrace(System.err);
				}
				new Main();
			}
		});

	}
}
