package raptor.engine.game;

import java.util.Collection;

import raptor.engine.collision.api.ICollisionShape;
import raptor.engine.collision.geometry.CollisionCircle;
import raptor.engine.collision.geometry.CollisionRectangle;
import raptor.engine.collision.geometry.CollisionTriangle;

public class Terrain implements ICollisionShape {
	private final int id;
	private final Collection<ICollisionShape> terrain;

	public Terrain(final int id, final Collection<ICollisionShape> terrain) {
		this.id = id;
		this.terrain = terrain;
	}

	public int getId() {
		return id;
	}

	@Override
	public boolean collidesWithTriangle(final CollisionTriangle t) {
		for (final ICollisionShape terrainSegment : terrain)
			if (terrainSegment.collidesWithTriangle(t))
				return true;
		return false;
	}

	@Override
	public boolean collidesWithCircle(final CollisionCircle c) {
		for (final ICollisionShape terrainSegment : terrain)
			if (terrainSegment.collidesWithCircle(c))
				return true;
		return false;
	}

	@Override
	public boolean collidesWithRectangle(final CollisionRectangle r) {
		for (final ICollisionShape terrainSegment : terrain)
			if (terrainSegment.collidesWithRectangle(r))
				return true;
		return false;
	}
}
