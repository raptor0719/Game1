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
import raptor.engine.game.entity.DrawDepthEntityComparator;
import raptor.engine.game.entity.IEntity;
import raptor.engine.nav.api.INavigator;
import raptor.engine.util.IIdProvider;
import raptor.engine.util.IdProvider;
import raptor.engine.util.ListSortingIterator;

public abstract class Level implements IDrawable {
	private static final Comparator<IEntity> DRAW_DEPTH_COMPARE = new DrawDepthEntityComparator();

	private final IEventBroker eventBroker;
	private final Map<Long, IEntity> entities;
	private final Map<Long, IEntity> physicsEnabledEntities;
	private final IIdProvider idProvider;
	private INavigator navigator;

	public Level() {
		this.eventBroker = new EventBroker();
		this.entities = new HashMap<Long, IEntity>();
		this.physicsEnabledEntities = new HashMap<Long, IEntity>();
		this.idProvider = new IdProvider();
		this.navigator = null;
	}

	public void init() {
		// Let implementing classes override if wanted
	}

	public void advanceFrame() {
		eventBroker.distribute();

		for (final IEntity e : entities.values())
			e.update();

		checkCollisions();
		resolvePhysicsCollisions();
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

			if (i+1 >= rawEntities.size())
				break;

			for (int j = i+1; j < rawEntities.size(); j++) {
				final IEntity target = rawEntities.get(j);

				if (target.getCollision() instanceof CollisionTriangle) {
					final CollisionTriangle triangle = (CollisionTriangle) target.getCollision();
					if (source.getCollision().collidesWithTriangle(triangle)) {
						source.onCollision(target);
						target.onCollision(source);
					}
				} else if (target.getCollision() instanceof CollisionCircle) {
					final CollisionCircle circle = (CollisionCircle) target.getCollision();
					if (source.getCollision().collidesWithCircle(circle)) {
						source.onCollision(target);
						target.onCollision(source);
					}
				}
			}
		}
	}

	private void resolvePhysicsCollisions() {
		// TODO: Implement this
		/*
		* 1. Iterate through physics enabled entities
		* 2. Go through area bounds and check for collisions
		* 3. If colliding then:
		* 	a) Get normal N from center of entity to segment
		* 	b) Take radius of collision as R
		* 	c) Move entity R - magnitude(N) away from the segment in the opposite direction of the normal
		* 4. Go through phyics enabled entities
		* 5. Compare each to eachother and check for collisions
		* 6. If they collide then:
		* 	a) Get the distance from the center of each entity as distTo
		* 	b) Add entity radius together as minValidDist
		* 	c) Compare each entity's weight to get weight ratios as r1 and r2 for each entity respectively
		* 	d) Calculate total amount to move to reach minValidDist as toMove using minValidDist - (radius of 1 + radius of 2)
		* 	e) Calculate magnitude of resultant move vectors using toMove scaled by weight ratios
		* 	f) Calculate move vectors which are unit vectors pointing away from the other entity scaled by the calculated magnitudes
		* 	b) Apply move vectors
		*/
	}

	public Iterator<IDrawable> getDrawables() {
		return new InsertingDrawableIteratorWrapper(this, new ListSortingIterator<>(entities.values(), DRAW_DEPTH_COMPARE));
	}

	public IEventBroker getEventBroker() {
		return eventBroker;
	}

	public IIdProvider getIdProvider() {
		return idProvider;
	}

	public void setNavigator(final INavigator navigator) {
		this.navigator = navigator;
	}

	public INavigator getNavigator() {
		return navigator;
	}

	public void addEntity(final IEntity entity) throws IllegalArgumentException {
		if (entities.containsKey(entity.getId()))
			throw new IllegalArgumentException("Cannot add an entity if another has the same id. id=" + entity.getId());

		entities.put(entity.getId(), entity);

		if (entity.isPhysicsEnabled())
			physicsEnabledEntities.put(entity.getId(), entity);
	}

	public void removeEntity(final long id) {
		entities.remove(id);
		physicsEnabledEntities.remove(id);
	}

	public IEntity getEntity(final long id) {
		return entities.get(id);
	}

	public List<IEntity> getAllEntities() {
		return new ArrayList<IEntity>(entities.values());
	}

	private static class InsertingDrawableIteratorWrapper implements Iterator<IDrawable> {
		private final IDrawable inserted;
		private final Iterator<IEntity> wrapped;
		private boolean insertedHasBeenServed;

		public InsertingDrawableIteratorWrapper(final IDrawable inserted, final Iterator<IEntity> wrapped) {
			this.inserted = inserted;
			this.wrapped = wrapped;
			insertedHasBeenServed = false;
		}

		@Override
		public boolean hasNext() {
			return !insertedHasBeenServed || wrapped.hasNext();
		}

		@Override
		public IDrawable next() {
			if (insertedHasBeenServed)
				return wrapped.next();
			insertedHasBeenServed = true;
			return inserted;
		}
	}
}
