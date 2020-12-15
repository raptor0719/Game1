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

	private Queue<Point> path;

	public DefaultNavAgent(final INavigator navigator) {
		this.navigator = navigator;

		positionX = 0;
		positionY = 0;
		currentWaypointX = 0;
		currentWaypointY = 0;

		path = new LinkedList<Point>();
	}

	@Override
	public void setPosition(int x, int y) {
		positionX = x;
		positionY = y;
		currentWaypointX = x;
		currentWaypointY = y;

		path.clear();
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
		if ((path.isEmpty() && atWaypoint()) || unitsToMove < 1)
			return;

		DoubleVector totalMovementVector = DoubleVector.unitVectorTowardPoint(positionX, positionY, currentWaypointX, currentWaypointY).scale(unitsToMove);
		DoubleVector movementToWaypointVector = new DoubleVector(currentWaypointX - positionX, currentWaypointY - positionY);

		while (totalMovementVector.getMagnitude() > movementToWaypointVector.getMagnitude()) {
			final double movementToGo = totalMovementVector.getMagnitude() - movementToWaypointVector.getMagnitude();

			positionX = currentWaypointX;
			positionY = currentWaypointY;

			// If we have more total movement than we have distance to go
			if (path.isEmpty())
				return;

			final Point nextWaypoint = path.poll();
			currentWaypointX = nextWaypoint.getX();
			currentWaypointY = nextWaypoint.getY();

			totalMovementVector = DoubleVector.unitVectorTowardPoint(positionX, positionY, currentWaypointX, currentWaypointY).scale(movementToGo);
			movementToWaypointVector = new DoubleVector(currentWaypointX - positionX, currentWaypointY - positionY);
		}

		positionX = positionX + round(totalMovementVector.getX());
		positionY = positionY + round(totalMovementVector.getY());

		if (atWaypoint() && !path.isEmpty()) {
			final Point nextWaypoint = path.poll();
			currentWaypointX = nextWaypoint.getX();
			currentWaypointY = nextWaypoint.getY();
		}
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
	}

	private boolean atWaypoint() {
		return positionX == currentWaypointX && positionY == currentWaypointY;
	}

	// FIXME: Seems a bit weird to lose precision here. But this is probably because we don't have a float vector.
	private int round(final double val) {
		return (int) Math.round(val);
	}
}
