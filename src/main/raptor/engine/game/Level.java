package raptor.engine.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import raptor.engine.collision.CollisionPlane;
import raptor.engine.collision.api.ICollisionPlaneHandler;
import raptor.engine.display.render.IDrawable;
import raptor.engine.event.EventBus;
import raptor.engine.event.IEventBus;
import raptor.engine.game.entity.DrawDepthEntityComparator;
import raptor.engine.game.entity.IEntity;
import raptor.engine.nav.api.INavigator;
import raptor.engine.util.IIdProvider;
import raptor.engine.util.IdProvider;
import raptor.engine.util.ListSortingIterator;

public abstract class Level implements IDrawable {
	private static final Comparator<IEntity> DRAW_DEPTH_COMPARE = new DrawDepthEntityComparator();

	private final EventBus eventBus;
	private final Map<Long, IEntity> entities;
	private final IIdProvider entityIdProvider;
	private final Map<Integer, INavigator> navigators;
	private final Map<Integer, CollisionPlane> collisionPlanes;

	public Level() {
		this.eventBus = new EventBus();
		this.entities = new HashMap<Long, IEntity>();
		this.entityIdProvider = new IdProvider();
		this.navigators = new HashMap<Integer, INavigator>();
		this.collisionPlanes = new HashMap<Integer, CollisionPlane>();
	}

	public void init() {
		// Let implementing classes override if wanted
	}

	public void cleanup() {
		// Let implementing classes override if wanted
	}

	protected void update(final long millisSinceLastFrame) {
		// Let implementing classes override if wanted
	}

	public void tick(final long millisSinceLastFrame) {
		eventBus.update();

		for (final IEntity e : entities.values())
			e.update(millisSinceLastFrame);

		update(millisSinceLastFrame);

		checkCollisions();
	}

	private void checkCollisions() {
		for (final CollisionPlane collisionPlane : collisionPlanes.values())
			collisionPlane.detectCollisions();
	}

	public Iterator<IDrawable> getDrawables() {
		return new InsertingDrawableIteratorWrapper(this, new ListSortingIterator<>(entities.values(), DRAW_DEPTH_COMPARE), null);
	}

	public IEventBus getEventBus() {
		return eventBus;
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

	public CollisionPlane getCollisionPlane(final int id) {
		return collisionPlanes.get(id);
	}

	public CollisionPlane addCollisionPlane(final int id, final String name, final ICollisionPlaneHandler collisionPlaneHandler) {
		final CollisionPlane newCollisionPlane = new CollisionPlane(id, name, collisionPlaneHandler);

		collisionPlanes.put(id, newCollisionPlane);

		return newCollisionPlane;
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
			insertedBeforeHasBeenServed = insertedBefore == null;
			insertedAfterHasBeenServed = insertedAfter == null;
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
