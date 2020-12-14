package raptor.engine.nav.agent;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import raptor.engine.nav.api.INavAgent;
import raptor.engine.nav.api.INavigator;
import raptor.engine.util.geometry.DoubleVector;
import raptor.engine.util.geometry.Point;

public class DefaultNavAgent implements INavAgent {
	private final INavigator navigator;

	private int positionX;
	private int positionY;
	private int currentWaypointX;
	private int currentWaypointY;

	private boolean isMoving;
	private Queue<Point> path;

	public DefaultNavAgent(final INavigator navigator) {
		this.navigator = navigator;

		positionX = 0;
		positionY = 0;
		currentWaypointX = 0;
		currentWaypointY = 0;

		isMoving = false;

		path = new LinkedList<Point>();
	}

	@Override
	public void setPosition(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public Point getPosition() {
		return new Point(positionX, positionY);
	}

	@Override
	public void setDestination(final int x, final int y) {
		setPath(navigator.findPath(getPosition(), new Point(x, y)));
	}

	@Override
	public void move(final int unitsToMove) {
		if (!isMoving || unitsToMove < 1)
			return;

		DoubleVector totalMovementVector = DoubleVector.unitVectorTowardPoint(
				new Point(positionX, positionY), new Point(currentWaypointX, currentWaypointY)).scale(unitsToMove);
		DoubleVector movementToWaypointVector = new DoubleVector(currentWaypointX - positionX, currentWaypointY - positionY);

		// TODO: Finish this
	}

	/* INTERNAL */

	private void setPath(final List<Point> newPath) {
		if (newPath == null || newPath.isEmpty())
			return;

		path.clear();
		path.addAll(newPath);

		final Point start = path.poll();
		currentWaypointX = start.getX();
		currentWaypointY = start.getY();

		isMoving = true;
	}

// FIXME: Here as a reference
//	private void calculate() {
//		final long currentTime = System.currentTimeMillis();
//		final double F = (currentTime - millisSinceLastTick)/(float)1000;
//		millisSinceLastTick = currentTime;
//
//		if (!isMoving)
//			return;
//
//		if (posX == destX && posY == destY) {
//			if (path.isEmpty()) {
//				isMoving = false;
//				return;
//			}
//			final Point nextPoint = path.poll();
//			destX = nextPoint.getX();
//			destY = nextPoint.getY();
//		}
//
//		final double MS = speed * F;
//		DoubleVector V = DoubleVector.unitVectorTowardPoint(getPosition(), new Point(destX, destY)).scale(MS);
//		DoubleVector VplusFrac = new DoubleVector(V.getX() + fracX, V.getY() + fracY);
//
//		fracX = VplusFrac.getX() % 1;
//		fracY = VplusFrac.getY() % 1;
//
//		Vector MOVE = new Vector((int)VplusFrac.getX(), (int)VplusFrac.getY());
//		Vector toDest = new Vector(destX - posX, destY - posY);
//
//		while (MOVE.getMagnitude() > toDest.getMagnitude()) {
//			posX = destX;
//			posY = destY;
//
//			if (path.isEmpty()) {
//				isMoving = false;
//				return;
//			}
//
//			final Point nextPoint = path.poll();
//			destX = nextPoint.getX();
//			destY = nextPoint.getY();
//
//			final double Leftover = MOVE.getMagnitude() - toDest.getMagnitude();
//			V = DoubleVector.unitVectorTowardPoint(getPosition(), new Point(destX, destY)).scale(Leftover);
//			VplusFrac = new DoubleVector(V.getX() + fracX, V.getY() + fracY);
//
//			fracX = VplusFrac.getX() % 1;
//			fracY = VplusFrac.getY() % 1;
//
//			MOVE = new Vector((int)VplusFrac.getX(), (int)VplusFrac.getY());
//			toDest = new Vector(destX - posX, destY - posY);
//		}
//
//		posX = MOVE.getX() + posX;
//		posY = MOVE.getY() + posY;
//
//		if (posX == destX && posY == destY) {
//			if (path.isEmpty()) {
//				isMoving = false;
//				return;
//			}
//
//			final Point nextPoint = path.poll();
//			destX = nextPoint.getX();
//			destY = nextPoint.getY();
//		}
//	}
}
