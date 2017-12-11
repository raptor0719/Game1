package test.main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

import logical.nav.api.INavAgent;
import logical.nav.path.api.IPathFinder;
import util.geometry.Point;

public class TestDisplay extends JFrame {
	private static final long serialVersionUID = 3754897033232484338L;

	private final MyPanel panel;
	private final TestAgent player;

	public TestDisplay() {
		player = new TestAgent(100, 100, 10);
		panel = new MyPanel(player);

		this.setSize(800, 800);
		this.add(panel);
		this.setTitle("RaptorEngine");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(500, 300);

		this.setVisible(true);
	}

	private static class MyPanel extends JPanel {
		private static final long serialVersionUID = 1703223292209679374L;
		private final TestAgent player;
		private final LogicalTimer timer;
		private final GraphicsTimer gtimer;

		public MyPanel(final TestAgent p) {
			player = p;

			timer = new LogicalTimer(p);
			timer.start();

			gtimer = new GraphicsTimer(this);
			gtimer.start();

			this.setSize(800, 800);
			this.setVisible(true);
// TODO: MAKE SURE TO ADD THIS BACK IN
//			this.addMouseListener(new MyMouseListener(p));
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2d = (Graphics2D)g;

			player.draw(new TestDrawer(g2d));
		}
	}

	private static class LogicalTimer extends Timer {
		private static final long serialVersionUID = 7336213152017273761L;

		public LogicalTimer(final INavAgent p) {
			super(0, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					p.move();
				}
			});

			this.setDelay(1);
		}
	}

	private static class GraphicsTimer extends Timer {
		private static final long serialVersionUID = 2230571834869249207L;

		public GraphicsTimer(final MyPanel p) {
			super(0, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					p.repaint();
				}
			});

			this.setDelay(17);
		}
	}

	private static class MyMouseListener implements MouseInputListener {
		private final INavAgent player;
		private final IPathFinder nav;

		public MyMouseListener(final INavAgent p, final IPathFinder nav) {
			player = p;
			this.nav = nav;
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			player.setPath(nav.findPath(player.getPosition(), new Point(arg0.getX(), arg0.getY())));
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
	}
}
