package test.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

import logical.nav.api.INavigator;
import util.geometry.Point;

public class TestDisplay extends JFrame {
	private static final long serialVersionUID = 3754897033232484338L;

	private final MyPanel panel;
	private final TestAgent player;

	public TestDisplay() {
		player = new TestAgent(100, 100, 80);
		panel = new MyPanel(player);

		this.setSize(800, 800);
		this.add(panel);
		setTitle("RaptorEngine");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(500, 300);

		setVisible(true);
	}

	private static class MyPanel extends JPanel {
		private static final long serialVersionUID = 1703223292209679374L;
		private final TestAgent player;
		private final LogicalTimer timer;
		private final GraphicsTimer gtimer;
		private final MyMouseListener mml;

		public MyPanel(final TestAgent p) {
			player = p;

			timer = new LogicalTimer(p);
			timer.start();

			gtimer = new GraphicsTimer(this);
			gtimer.start();

			this.setSize(800, 800);
			setVisible(true);

			mml = new MyMouseListener(p, TestMapFactory.getMap1());
			addMouseListener(mml);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2d = (Graphics2D)g;

			g2d.drawRect(300, 300, 200, 200);

			for (final Point p : mml.previousPath)
				g2d.fillOval(p.getX()-5, p.getY()-5, 10, 10);

			g2d.setColor(Color.BLUE);
			player.draw(new TestDrawer(g2d));
			g2d.setColor(Color.BLACK);

		}
	}

	private static class LogicalTimer extends Timer {
		private static final long serialVersionUID = 7336213152017273761L;

		public LogicalTimer(final TestAgent p) {
			super(0, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					p.move();
				}
			});

			setDelay(10);
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

			setDelay(17);
		}
	}

	private static class MyMouseListener implements MouseInputListener {
		private final TestAgent player;
		private final INavigator nav;
		public List<Point> previousPath = new LinkedList<Point>();

		public MyMouseListener(final TestAgent p, final INavigator nav) {
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
			previousPath.clear();

			final List<Point> path = nav.findPath(player.getPosition(), new Point(arg0.getX(), arg0.getY()));
			if (path == null) {
				return;
			}
			player.setPath(path);

			for (Point p : path)
				previousPath.add(p);
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
