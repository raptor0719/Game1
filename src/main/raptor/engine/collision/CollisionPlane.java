package raptor.engine.collision;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import raptor.engine.collision.api.ICollisionShape;
import raptor.engine.collision.geometry.CollisionCircle;
import raptor.engine.collision.geometry.CollisionRectangle;
import raptor.engine.collision.geometry.CollisionTriangle;
import raptor.engine.game.Terrain;
import raptor.engine.game.entity.IEntity;

public class CollisionPlane {
	private final int id;
	private final String name;
	private final Map<Integer, Terrain> terrains;
	private final Map<Long, IEntity> entities;
	private final CollisionPlaneHandler collisionHandler;

	public CollisionPlane(final int id, final String name, final CollisionPlaneHandler collisionHandler) {
		this.id = id;
		this.name = name;
		this.terrains = new HashMap<>();
		this.entities = new HashMap<>();
		this.collisionHandler = collisionHandler;
	}

	public void detectCollisions() {
		// TODO: Ideally we have local collision detection rather than global
		final Collection<IEntity> entityCollection = entities.values();
		final Collection<Terrain> terrainCollection = terrains.values();

		final IEntity[] rawEntities = entityCollection.toArray(new IEntity[entityCollection.size()]);
		final Terrain[] rawTerrains = terrainCollection.toArray(new Terrain[terrainCollection.size()]);

		for (int i = 0; i < rawEntities.length; i++) {
			final IEntity entity = rawEntities[i];
			final ICollisionShape collision = entity.getCollision(id);

			for (final Terrain terrain : rawTerrains) {

				if (collision instanceof CollisionTriangle) {
					if (terrain.collidesWithTriangle((CollisionTriangle) collision)) {
						collisionHandler.handleTerrainCollision(entity, terrain);
					}
				} else if (collision instanceof CollisionCircle) {
					if (terrain.collidesWithCircle((CollisionCircle) collision)) {
						collisionHandler.handleTerrainCollision(entity, terrain);
					}
				} else if (collision instanceof CollisionRectangle) {
					if (terrain.collidesWithRectangle((CollisionRectangle) collision)) {
						collisionHandler.handleTerrainCollision(entity, terrain);
					}
				}
			}

			if (i + 1 >= rawEntities.length)
				break;

			for (int j = i + 1; j < rawEntities.length; i++) {
				final IEntity entityOther = rawEntities[j];
				final ICollisionShape entityOtherCollision = entityOther.getCollision(id);

				if (collision instanceof CollisionTriangle) {
					if (entityOtherCollision.collidesWithTriangle((CollisionTriangle) collision)) {
						collisionHandler.handleEntityCollision(entity, entityOther);
					}
				} else if (collision instanceof CollisionCircle) {
					if (entityOtherCollision.collidesWithCircle((CollisionCircle) collision)) {
						collisionHandler.handleEntityCollision(entity, entityOther);
					}
				} else if (collision instanceof CollisionRectangle) {
					if (entityOtherCollision.collidesWithRectangle((CollisionRectangle) collision)) {
						collisionHandler.handleEntityCollision(entity, entityOther);
					}
				}
			}
		}
	}

	public void registerEntity(final IEntity entity) {
		entities.put(entity.getId(), entity);
	}

	public void unregisterEntity(final long id) {
		entities.remove(id);
	}

	public void registerTerrain(final Terrain terrain) {
		terrains.put(terrain.getId(), terrain);
	}

	public void unregisterTerrain(final int id) {
		terrains.remove(id);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
