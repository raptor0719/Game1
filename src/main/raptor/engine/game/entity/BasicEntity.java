package raptor.engine.game.entity;

import raptor.engine.util.geometry.Point;

public abstract class BasicEntity implements IEntity {
	private final Point position;

	public BasicEntity() {
		this.position = new Point(0, 0);
	}

	@Override
	public int getX() {
		return position.getX();
	}

	@Override
	public int getY() {
		return position.getY();
	}
}
