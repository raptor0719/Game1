package raptor.engine.game.entity;

import raptor.engine.collision.api.ICollisionShape;
import raptor.engine.event.IEventSource;
import raptor.engine.util.geometry.Point;
import raptor.engine.util.geometry.api.ICircle;

public abstract class Entity implements IEntity {
	private final long id;
	private final IEventSource eventSource;
	private final ICollisionShape collision;

	private final Point position;

	public Entity(final long id, final IEventSource eventSource, final ICollisionShape collision) {
		this(id, eventSource, collision, null, -1);
	}

	public Entity(final long id, final IEventSource eventSource, final ICollisionShape collision, final ICircle physicsCollision, final int weight) {
		this.id = id;
		this.eventSource = eventSource;
		this.collision = collision;

		this.position = new Point(0, 0);
	}

	@Override
	public long getId() {
		return id;
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
	public ICollisionShape getCollision() {
		return collision;
	}

	protected IEventSource getEventSource() {
		return eventSource;
	}
}
