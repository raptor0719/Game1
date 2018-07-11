package raptor.engine.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import raptor.engine.util.geometry.LineSegment;
import raptor.engine.util.geometry.Point;

public class Main {

	public static void main(final String[] args) {
		final Player player = new Player(300, 300, 10, 5);
		final MyKeyListener listener = new MyKeyListener();
		final MainPanel panel = new MainPanel(listener, player);

		final MainFrame frame = new MainFrame(panel);

		long lastCompute = System.currentTimeMillis();
		while (true) {
			final long currentTime = System.currentTimeMillis();
			final long timeSinceLastCompute = currentTime - lastCompute;

			if (timeSinceLastCompute >= 50) {
				lastCompute = currentTime;
				if (listener.up)
					player.y -= player.moveRate;
				if (listener.down)
					player.y += player.moveRate;
				if (listener.left)
					player.x -= player.moveRate;
				if (listener.right)
					player.x += player.moveRate;
				final java.awt.Point mouseLocation = frame.getMousePosition(true);
				if (mouseLocation != null) {
					final LineSegment playerToMouse = new LineSegment(new Point(player.x, player.y), new Point(mouseLocation.x, mouseLocation.y));
					player.faceDegrees = convertAngle(playerToMouse.getAngleBetween(player.horizontalLine)*180/Math.PI, mouseLocation.x < player.x);
				}
			}

			panel.repaint();
		}
	}

	private static int convertAngle(final double angle, final boolean mouseXltplayerX) {
		if (mouseXltplayerX)
			return (int)(360 - angle);
		return (int)angle;
	}

	private static class MainFrame extends JFrame {
		private static final long serialVersionUID = 1L;

		public MainFrame(final JPanel panel) {
			this.setSize(800, 800);
			this.add(panel);
			setTitle("RaptorEngine");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.setLocation(500, 300);

			setVisible(true);
		}
	}

	private static class MainPanel extends JPanel {
		final MyKeyListener input;
		final Player player;

		public MainPanel(final MyKeyListener keyListener, final Player player) {
			input = keyListener;
			this.player = player;

			this.setSize(800, 800);
			setVisible(true);

			setFocusable(true);
			addKeyListener(input);
		}

		@Override
		public void paintComponent(final Graphics g) {
			super.paintComponent(g);

			Graphics2D g2d = (Graphics2D)g;

			g2d.setColor(Color.BLUE);
			g2d.drawOval(player.x-(int)(0.5*player.width), player.y-(int)(0.5*player.height), player.width, player.height);
			final LineSegment faceLine = getRotatedLineSegment(player.horizontalLine, player.faceDegrees);
			final LineSegment translatedFaceLine = translateLineSegment(faceLine, player.x, player.y);
			drawLineSegment(translatedFaceLine, g2d);
		}
	}

	private static void drawLineSegment(final LineSegment ls, final Graphics2D g) {
		final Point p1 = ls.getPoints().getValue1();
		final Point p2 = ls.getPoints().getValue2();

		g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

	private static LineSegment translateLineSegment(final LineSegment ls, final int x, final int y) {
		final Point a = new Point(ls.getPoints().getValue1().getX()+x, ls.getPoints().getValue1().getY()+y);
		final Point b = new Point(ls.getPoints().getValue2().getX()+x, ls.getPoints().getValue2().getY()+y);
		return new LineSegment(a, b);
	}

	private static LineSegment getRotatedLineSegment(final LineSegment ls, final int angle) {
		final Point p1Rotated = rotatePointAboutOrigin(ls.getPoints().getValue1(), angle);
		final Point p2Rotated = rotatePointAboutOrigin(ls.getPoints().getValue2(), angle);

		return new LineSegment(p1Rotated, p2Rotated);
	}

	private static Point rotatePointAboutOrigin(final Point p, final int angle) {
		final double radians = Math.toRadians(angle);
		final int newX = (int)(p.getX() * Math.cos(radians) - p.getY() * Math.sin(radians));
		final int newY = (int)(p.getY() * Math.cos(radians) + p.getX() * Math.sin(radians));
		final Point newPoint = new Point(newX, newY);
		return newPoint;
	}

	private static class Player {
		public int x;
		public int y;
		public int width;
		public int height;
		public int faceDegrees;

		public int moveRate;

		public LineSegment horizontalLine;
		public LineSegment lineStart;

		public Player(final int startX, final int startY, final int diameter, final int moveRate) {
			x = startX;
			y = startY;
			width = diameter;
			height = diameter;
			faceDegrees = 0;
			this.moveRate = moveRate;
			horizontalLine = new LineSegment(new Point(0, 0), new Point(0, -10));
			lineStart = null;
		}
	}

	private static class MyKeyListener implements KeyListener {
		public boolean up;
		public boolean down;
		public boolean left;
		public boolean right;

		public MyKeyListener() {
			up = false;
			down = false;
			left = false;
			right = false;
		}

		@Override
		public void keyPressed(KeyEvent e) {
			setDirection(true, e.getKeyCode());
		}

		@Override
		public void keyReleased(KeyEvent e) {
			setDirection(false, e.getKeyCode());
		}

		@Override
		public void keyTyped(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_G) {
				System.out.println("Up: " + up + "\nDown: " + down + "\nLeft: " + left + "\nRight: " + right);
			}
		}

		private void setDirection(final boolean on, final int keyCode) {
			switch (keyCode) {
				case KeyEvent.VK_W:
					up = on;
					break;
				case KeyEvent.VK_S:
					down = on;
					break;
				case KeyEvent.VK_A:
					left = on;
					break;
				case KeyEvent.VK_D:
					right = on;
					break;
			}
		}
	}
}
