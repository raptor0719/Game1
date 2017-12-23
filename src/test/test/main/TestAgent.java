package test.main;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import display.api.IDrawable;
import display.api.IDrawer;
import logical.nav.api.INavAgent;
import util.geometry.Point;

public class TestAgent implements INavAgent, IDrawable {
	private int dx;
	private int dy;

	private int posX;
	private int posY;

	private int destX;
	private int destY;

	private boolean isMoving;

	private Queue<Point> path;

	public TestAgent(final int xPos, final int yPos, final int speed) {
		this.dx = speed;
		this.dy = speed;

		this.posX = xPos;
		this.posY = yPos;

		this.destX = 0;
		this.destY = 0;

		this.isMoving = false;

		path = new LinkedList<Point>();
	}

	@Override
	public Point getPosition() {
		return new Point(posX, posY);
	}

	@Override
	public boolean setPath(final List<Point> newPath) {
		if (newPath == null || newPath.isEmpty())
			return isMoving;

		path.clear();
		path.addAll(newPath);

		final Point start = path.poll();
		destX = start.getX();
		destY = start.getY();

		isMoving = true;

		return true;
	}

	public Queue<Point> getPath() {
		return path;
	}

	@Override
	public void move() {
		if (!isMoving)
			return;

		if (destX > posX) {
			if (posX+dx > destX)
				posX = destX;
			else
				posX += dx;
		} else if (destX < posX) {
			if (posX-dx < destX)
				posX = destX;
			else
				posX -= dx;
		}

		if (destY > posY) {
			if (posY+dy > destY)
				posY = destY;
			else
				posY += dy;
		} else if (destY < posY) {
			if (posY-dy < destY)
				posY = destY;
			else
				posY -= dy;
		}


		if (destX == posX && destY == posY) {
			if (!path.isEmpty()) {
				final Point start = path.poll();
				destX = start.getX();
				destY = start.getY();
			} else {
				isMoving = false;
			}
		}
	}

	@Override
	public void draw(final IDrawer drawer) {
		drawer.drawOval(posX-5, posY-5, 10, 10);
	}
}
