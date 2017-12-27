package test.main;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import display.api.IDrawable;
import display.api.IDrawer;
import util.geometry.DoubleVector;
import util.geometry.Point;
import util.geometry.Vector;

public class TestAgent implements IDrawable {
	private int dx;
	private int dy;
	private int speed;

	private int posX;
	private int posY;

	private int destX;
	private int destY;

	private boolean isMoving;

	private Queue<Point> path;

	private long millisSinceLastTick;

	private double fracX;
	private double fracY;

	public TestAgent(final int xPos, final int yPos, final int speed) {
		dx = speed;
		dy = speed;
		this.speed = speed;

		posX = xPos;
		posY = yPos;

		destX = 0;
		destY = 0;

		isMoving = false;

		path = new LinkedList<Point>();

		millisSinceLastTick = System.currentTimeMillis();

		fracX = 0;
		fracY = 0;
	}

	public Point getPosition() {
		return new Point(posX, posY);
	}

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

	public void move() {
		tempMove();
	}

	private void currentMove() {
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

	private void tempMove() {
		final long currentTime = System.currentTimeMillis();
		final double F = (currentTime - millisSinceLastTick)/(float)1000;
		millisSinceLastTick = currentTime;

		if (!isMoving)
			return;

		if (posX == destX && posY == destY) {
			if (path.isEmpty()) {
				isMoving = false;
				return;
			}
			final Point nextPoint = path.poll();
			destX = nextPoint.getX();
			destY = nextPoint.getY();
		}

		final double MS = speed * F;
		DoubleVector V = DoubleVector.unitVectorTowardPoint(getPosition(), new Point(destX, destY)).scale(MS);
		DoubleVector VplusFrac = new DoubleVector(V.getX() + fracX, V.getY() + fracY);

		fracX = VplusFrac.getX() % 1;
		fracY = VplusFrac.getY() % 1;

		Vector MOVE = new Vector((int)VplusFrac.getX(), (int)VplusFrac.getY());
		Vector toDest = new Vector(destX - posX, destY - posY);

		while (MOVE.getMagnitude() > toDest.getMagnitude()) {
			posX = destX;
			posY = destY;

			if (path.isEmpty()) {
				isMoving = false;
				return;
			}

			final Point nextPoint = path.poll();
			destX = nextPoint.getX();
			destY = nextPoint.getY();

			final double Leftover = MOVE.getMagnitude() - toDest.getMagnitude();
			V = DoubleVector.unitVectorTowardPoint(getPosition(), new Point(destX, destY)).scale(Leftover);
			VplusFrac = new DoubleVector(V.getX() + fracX, V.getY() + fracY);

			fracX = VplusFrac.getX() % 1;
			fracY = VplusFrac.getY() % 1;

			MOVE = new Vector((int)VplusFrac.getX(), (int)VplusFrac.getY());
			toDest = new Vector(destX - posX, destY - posY);
		}

		posX = MOVE.getX() + posX;
		posY = MOVE.getY() + posY;

		if (posX == destX && posY == destY) {
			if (path.isEmpty()) {
				isMoving = false;
				return;
			}

			final Point nextPoint = path.poll();
			destX = nextPoint.getX();
			destY = nextPoint.getY();
		}
	}

	@Override
	public void draw(final IDrawer drawer) {
		drawer.drawOval(posX-5, posY-5, 10, 10);
	}
}
