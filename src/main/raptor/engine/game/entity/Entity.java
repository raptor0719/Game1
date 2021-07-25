package raptor.engine.game.entity;

import java.util.Map;

import raptor.engine.collision.api.ICollisionShape;
import raptor.engine.event.IEventSource;
import raptor.engine.util.geometry.Point;
import raptor.engine.util.geometry.api.IPoint;

public abstract class Entity implements IEntity {
	private final long id;
	private final String name;
	private final IEventSource eventSource;
	private final Point position;

	private Map<Long, ICollisionShape> collisions;

	public Entity(final long id, final String name, final IEventSource eventSource) {
		this.id = id;
		this.name = name;
		this.eventSource = eventSource;
		this.position = new Point(0, 0);
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getX() {
		return position.getX();
	}

	@Override
	public int getY() {
		return position.getY();
	}

	@Override
	public void setX(final int x) {
		position.setX(x);
	}

	@Override
	public void setY(final int y) {
		position.setY(y);
	}

	@Override
	public boolean hasCollision(final long planeId) {
		return collisions.containsKey(id);
	}

	@Override
	public ICollisionShape getCollision(final long planeId) {
		return collisions.get(id);
	}

	public IPoint getPosition() {
		return position;
	}

	public IEventSource getEventSource() {
		return eventSource;
	}

	public void removeCollision(final long planeId) {
		collisions.remove(planeId);
	}

	public void setCollision(final long planeId, final ICollisionShape collisionShape) {
		collisions.put(planeId, collisionShape);
	}
}
