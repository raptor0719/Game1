package raptor.engine.collision;

import java.util.ArrayList;
import java.util.List;

import raptor.engine.collision.api.ICollisionPlaneHandler;
import raptor.engine.collision.api.ICollisionShape;
import raptor.engine.collision.geometry.CollisionCircle;
import raptor.engine.collision.geometry.CollisionRectangle;
import raptor.engine.collision.geometry.CollisionTriangle;
import raptor.engine.game.Terrain;
import raptor.engine.game.entity.IEntity;

public class CollisionPlane {
	private final int id;
	private final String name;
	private final List<Terrain> terrains;
	private final List<IEntity> entities;
	private final ICollisionPlaneHandler collisionHandler;

	public CollisionPlane(final int id, final String name, final ICollisionPlaneHandler collisionHandler) {
		this.id = id;
		this.name = name;
		this.terrains = new ArrayList<>();
		this.entities = new ArrayList<>();
		this.collisionHandler = collisionHandler;
	}

	public void detectCollisions() {
		// TODO: Ideally we have local collision detection rather than global
		for (int i = 0; i < entities.size(); i++) {
			final IEntity entity = entities.get(i);
			final ICollisionShape collision = entity.getCollision(id);

			if (collision == null)
				continue;

			for (final Terrain terrain : terrains) {
				if (collision instanceof CollisionTriangle) {
					if (terrain.collidesWithTriangle((CollisionTriangle) collision)) {
						collisionHandler.handleTerrainCollision(id, entity, terrain);
					}
				} else if (collision instanceof CollisionCircle) {
					if (terrain.collidesWithCircle((CollisionCircle) collision)) {
						collisionHandler.handleTerrainCollision(id, entity, terrain);
					}
				} else if (collision instanceof CollisionRectangle) {
					if (terrain.collidesWithRectangle((CollisionRectangle) collision)) {
						collisionHandler.handleTerrainCollision(id, entity, terrain);
					}
				}
			}

			if (i + 1 >= entities.size())
				break;

			for (int j = i + 1; j < entities.size(); j++) {
				final IEntity entityOther = entities.get(j);
				final ICollisionShape entityOtherCollision = entityOther.getCollision(id);

				if (entityOtherCollision == null)
					continue;

				if (collision instanceof CollisionTriangle) {
					if (entityOtherCollision.collidesWithTriangle((CollisionTriangle) collision)) {
						collisionHandler.handleEntityCollision(id, entity, entityOther);
					}
				} else if (collision instanceof CollisionCircle) {
					if (entityOtherCollision.collidesWithCircle((CollisionCircle) collision)) {
						collisionHandler.handleEntityCollision(id, entity, entityOther);
					}
				} else if (collision instanceof CollisionRectangle) {
					if (entityOtherCollision.collidesWithRectangle((CollisionRectangle) collision)) {
						collisionHandler.handleEntityCollision(id, entity, entityOther);
					}
				}
			}
		}
	}

	public void registerEntity(final IEntity entity) {
		if (entities.contains(entity))
			return;
		entities.add(entity);
	}

	public void unregisterEntity(final IEntity entity) {
		entities.remove(entity);
	}

	public void registerTerrain(final Terrain terrain) {
		if (terrains.contains(terrain))
			return;
		terrains.add(terrain);
	}

	public void unregisterTerrain(final Terrain terrain) {
		terrains.remove(terrain);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
