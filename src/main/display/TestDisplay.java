package display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

public class TestDisplay extends JFrame {
	private static final long serialVersionUID = 3754897033232484338L;

	private final MyPanel panel;
	private final Player player;

	public TestDisplay() {
		player = new Player(400,300);
		panel = new MyPanel(player);

		this.setSize(800, 600);
		this.add(panel);
		this.setTitle("RaptorEngine");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(500, 300);

		this.setVisible(true);
	}

	private static class MyPanel extends JPanel {
		private static final long serialVersionUID = 1703223292209679374L;
		private final Player player;
		private final LogicalTimer timer;
		private final GraphicsTimer gtimer;

		public MyPanel(final Player p) {
			player = p;

			timer = new LogicalTimer(p);
			timer.start();

			gtimer = new GraphicsTimer(this);
			gtimer.start();

			this.setSize(800, 600);
			this.setVisible(true);
			this.addMouseListener(new MyMouseListener(p));
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2d = (Graphics2D)g;

			player.draw(g2d);
		}
	}

	private static class LogicalTimer extends Timer {
		private static final long serialVersionUID = 7336213152017273761L;

		public LogicalTimer(final Player p) {
			super(0, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					p.update();
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

	private static class Player {
		public int posX;
		public int posY;

		public int dx;
		public int dy;

		public int destX;
		public int destY;

		public boolean isMoving;

		public Player(final int x, final int y) {
			posX = x;
			posY = y;

			dx = 1;
			dy = 1;

			destX = 0;
			destY = 0;

			isMoving = false;
		}

		public void draw(Graphics2D drawer) {
			drawer.fillOval(posX, posY, 10, 10);
		}

		public void update() {
			if (!isMoving)
				return;

			if (destX > posX) {
				if (posX+dx > destX)
					posX = destX;
				else
					posX += dx;
			} else if (destX < posX)
				if (posX-dx < destX)
					posX = destX;
				else
					posX -= dx;

			if (destY > posY) {
				if (posY+dy > destY)
					posY = destY;
				else
					posY += dy;
			} else if (destY < posY)
				if (posY-dy < destY)
					posY = destY;
				else
					posY -= dy;


			if (destX == posX && destY == posY)
				isMoving = false;
		}
	}

	private static class MyMouseListener implements MouseInputListener {
		private final Player player;

		public MyMouseListener(final Player p) {
			player = p;
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
			player.destX = arg0.getX();
			player.destY = arg0.getY();
			player.isMoving = true;
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
