package raptor.engine.test.main;

import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestFrame {
	private final JFrame frame;
	private final JPanel panel;

	public TestFrame(final int width, final int height, final String title) {
		frame = new JFrame();
		panel = new JPanel();

		panel.setSize(width, height);
		panel.setVisible(true);
		panel.setDoubleBuffered(true);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setTitle(title);
		frame.add(panel);
		frame.setVisible(true);
	}

	public Graphics2D getGraphics() {
		return (Graphics2D)panel.getGraphics();
	}
}
