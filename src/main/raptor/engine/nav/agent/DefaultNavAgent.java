package raptor.engine.nav.agent;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import raptor.engine.nav.api.INavAgent;
import raptor.engine.nav.api.INavigator;
import raptor.engine.util.geometry.DoubleVector;
import raptor.engine.util.geometry.Point;
import raptor.engine.util.geometry.Vector;

public class DefaultNavAgent implements INavAgent {
	private final INavigator navigator;

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

	public DefaultNavAgent(final Point startingPoint, final INavigator navigator, final int speed) {
		this.navigator = navigator;
		this.speed = speed;

		posX = startingPoint.getX();
		posY = startingPoint.getY();

		destX = 0;
		destY = 0;

		isMoving = false;

		path = new LinkedList<Point>();

		millisSinceLastTick = System.currentTimeMillis();

		fracX = 0;
		fracY = 0;
	}

	@Override
	public void setSpeed(final int speed) {
		this.speed = speed;
	}

	@Override
	public void setDestination(final Point dest) {
		setPath(navigator.findPath(getPosition(), dest));
	}

	private void setPath(final List<Point> newPath) {
		if (newPath == null || newPath.isEmpty())
			return;

		path.clear();
		path.addAll(newPath);

		final Point start = path.poll();
		destX = start.getX();
		destY = start.getY();

		isMoving = true;
	}

	@Override
	public void move() {
		calculate();
	}

	@Override
	public Point getPosition() {
		return new Point(posX, posY);
	}

	private void calculate() {
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
}
