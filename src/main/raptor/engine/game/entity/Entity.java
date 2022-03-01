package raptor.engine.game.entity;

import java.util.Map;

import raptor.engine.collision.api.ICollisionShape;
import raptor.engine.display.render.IGraphics;
import raptor.engine.model.Direction;
import raptor.engine.model.Model;
import raptor.engine.util.geometry.Point;
import raptor.engine.util.geometry.api.IPoint;

public abstract class Entity implements IEntity {
	private final long id;
	private final String name;
	private final Point position;
	private final Model model;

	private Map<Long, ICollisionShape> collisions;
	private int facingInDegrees;

	public Entity(final long id, final String name, final Model model) {
		this.id = id;
		this.name = name;
		this.position = new Point(0, 0);
		this.model = model;

		if (model != null)
			model.setPosition(position);

		facingInDegrees = 0;
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
	public int getFacingInDegrees() {
		return facingInDegrees;
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
	public void setFacingInDegrees(int degrees) {
		this.facingInDegrees = degrees;
	}

	@Override
	public boolean hasCollision(final long planeId) {
		return collisions.containsKey(id);
	}

	@Override
	public ICollisionShape getCollision(final long planeId) {
		return collisions.get(id);
	}

	@Override
	public void draw(final IGraphics graphics) {
		if (model != null) {
			model.setDirection(Direction.calculateDirection(facingInDegrees));
			model.draw(graphics);
		}
	}

	public Model getModel() {
		return model;
	}

	public IPoint getPosition() {
		return position;
	}

	public void removeCollision(final long planeId) {
		collisions.remove(planeId);
	}

	public void setCollision(final long planeId, final ICollisionShape collisionShape) {
		collisions.put(planeId, collisionShape);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
