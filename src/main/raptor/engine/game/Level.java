package raptor.engine.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import raptor.engine.collision.geometry.CollisionCircle;
import raptor.engine.collision.geometry.CollisionTriangle;
import raptor.engine.display.render.IDrawable;
import raptor.engine.event.EventBroker;
import raptor.engine.event.IEventBroker;
import raptor.engine.event.events.EntityCollisionEvent;
import raptor.engine.event.events.TerrainCollisionEvent;
import raptor.engine.game.entity.DrawDepthEntityComparator;
import raptor.engine.game.entity.IEntity;
import raptor.engine.nav.api.INavigator;
import raptor.engine.ui.UserInterface;
import raptor.engine.util.IIdProvider;
import raptor.engine.util.IdProvider;
import raptor.engine.util.ListSortingIterator;

public abstract class Level implements IDrawable {
	private static final Comparator<IEntity> DRAW_DEPTH_COMPARE = new DrawDepthEntityComparator();

	private final IEventBroker eventBroker;
	private final Map<Long, IEntity> entities;
	private final IIdProvider entityIdProvider;
	private final Map<Integer, INavigator> navigators;
	private final Map<Integer, Terrain> terrains;

	private UserInterface userInterface;

	public Level() {
		this.eventBroker = new EventBroker();
		this.entities = new HashMap<Long, IEntity>();
		this.entityIdProvider = new IdProvider();
		this.navigators = new HashMap<Integer, INavigator>();
		this.terrains = new HashMap<Integer, Terrain>();
	}

	public void init() {
		// Let implementing classes override if wanted
	}

	public void tick() {
		checkCollisions();

		eventBroker.distribute();

		for (final IEntity e : entities.values())
			e.update();
	}

	/* FIXME
	* This isn't the best because it's checking collisions of ALL
	* entities in the level vs all other entities. Ideally we filter
	* by proximity. I think we could do this by having the level
	* space sectioned in a grid pattern and putting entities in a
	* bucket corresponding to what part of the grid they are in.
	* We can then only check collisions for an entity with other
	* entities either in the same grid-square along with surrounding
	* grid-squares.
	*/
	private void checkCollisions() {
		final List<IEntity> rawEntities = new ArrayList<>(entities.values());

		for (int i = 0; i < rawEntities.size(); i++) {
			final IEntity source = rawEntities.get(i);

			if (!source.hasCollision())
				continue;

			for (final Terrain terrain : terrains.values()) {
				if (source.getCollision() instanceof CollisionTriangle) {
					final CollisionTriangle sourceTriangle = (CollisionTriangle) source.getCollision();
					if (terrain.collidesWithTriangle(sourceTriangle))
						eventBroker.trigger(source.getId(), new TerrainCollisionEvent(terrain));
				} else if (source.getCollision() instanceof CollisionCircle) {
					final CollisionCircle sourceCircle = (CollisionCircle) source.getCollision();
					if (terrain.collidesWithCircle(sourceCircle))
						eventBroker.trigger(source.getId(), new TerrainCollisionEvent(terrain));
				}
			}

			if (i+1 >= rawEntities.size())
				break;

			for (int j = i+1; j < rawEntities.size(); j++) {
				final IEntity target = rawEntities.get(j);

				if (target.getCollision() instanceof CollisionTriangle) {
					final CollisionTriangle triangle = (CollisionTriangle) target.getCollision();
					if (source.getCollision().collidesWithTriangle(triangle)) {
						eventBroker.trigger(source.getId(), new EntityCollisionEvent(target));
						eventBroker.trigger(target.getId(), new EntityCollisionEvent(source));
					}
				} else if (target.getCollision() instanceof CollisionCircle) {
					final CollisionCircle circle = (CollisionCircle) target.getCollision();
					if (source.getCollision().collidesWithCircle(circle)) {
						eventBroker.trigger(source.getId(), new EntityCollisionEvent(target));
						eventBroker.trigger(target.getId(), new EntityCollisionEvent(source));
					}
				}
			}
		}
	}

	public Iterator<IDrawable> getDrawables() {
		return new InsertingDrawableIteratorWrapper(this, new ListSortingIterator<>(entities.values(), DRAW_DEPTH_COMPARE), userInterface);
	}

	public IEventBroker getEventBroker() {
		return eventBroker;
	}

	public IIdProvider getEntityIdProvider() {
		return entityIdProvider;
	}

	public void addNavigator(final int id, final INavigator newNavigator) {
		navigators.put(id, newNavigator);
	}

	public void removeNavigator(final int id) {
		navigators.remove(id);
	}

	public INavigator getNavigator(final int id) {
		return navigators.get(id);
	}

	public INavigator getNavigator(final int x, final int y) {
		for (final INavigator n : navigators.values())
			if (n.containsPoint(x, y))
				return n;
		return null;
	}

	public void addTerrain(final Terrain terrain) {
		terrains.put(terrain.getId(), terrain);
	}

	public void removeTerrain(final int id) {
		terrains.remove(id);
	}

	public Terrain getTerrain(final int id) {
		return terrains.get(id);
	}

	public void addEntity(final IEntity entity) throws IllegalArgumentException {
		if (entities.containsKey(entity.getId()))
			throw new IllegalArgumentException("Cannot add an entity if another has the same id. id=" + entity.getId());

		entities.put(entity.getId(), entity);
	}

	public void removeEntity(final long id) {
		entities.remove(id);
	}

	public IEntity getEntity(final long id) {
		return entities.get(id);
	}

	public List<IEntity> getAllEntities() {
		return new ArrayList<IEntity>(entities.values());
	}

	public UserInterface getUserInterface() {
		return userInterface;
	}

	public void setUserInterface(final UserInterface newUserInterface) {
		this.userInterface = newUserInterface;
	}

	private static class InsertingDrawableIteratorWrapper implements Iterator<IDrawable> {
		private final IDrawable insertedBefore;
		private final IDrawable insertedAfter;
		private final Iterator<IEntity> wrapped;
		private boolean insertedBeforeHasBeenServed;
		private boolean insertedAfterHasBeenServed;

		public InsertingDrawableIteratorWrapper(final IDrawable insertedBefore, final Iterator<IEntity> wrapped, final IDrawable insertedAfter) {
			this.insertedBefore = insertedBefore;
			this.insertedAfter = insertedAfter;
			this.wrapped = wrapped;
			insertedBeforeHasBeenServed = false;
			insertedAfterHasBeenServed = false;
		}

		@Override
		public boolean hasNext() {
			return !insertedBeforeHasBeenServed || wrapped.hasNext() || !insertedAfterHasBeenServed;
		}

		@Override
		public IDrawable next() {
			if (hasBefore()) {
				insertedBeforeHasBeenServed = true;
				return insertedBefore;
			} else if (hasAfter() && !wrapped.hasNext()) {
				insertedAfterHasBeenServed = true;
				return insertedAfter;
			}

			return wrapped.next();
		}

		private boolean hasBefore() {
			return insertedBefore != null && !insertedBeforeHasBeenServed;
		}

		private boolean hasAfter() {
			return insertedAfter != null && !insertedAfterHasBeenServed;
		}
	}
}
