package raptor.engine.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import raptor.engine.collision.geometry.CollisionCircle;
import raptor.engine.collision.geometry.CollisionTriangle;
import raptor.engine.display.render.IDrawable;
import raptor.engine.event.EventBroker;
import raptor.engine.event.IEventBroker;
import raptor.engine.game.entity.IEntity;
import raptor.engine.nav.api.INavigator;
import raptor.engine.util.IIdProvider;
import raptor.engine.util.IdProvider;

public abstract class Level implements IDrawable {
	private final IEventBroker eventBroker;
	private final List<IEntity> entities;
	private final IIdProvider idProvider;
	private INavigator navigator;

	public Level() {
		this.eventBroker = new EventBroker();
		this.entities = new ArrayList<IEntity>();
		this.idProvider = new IdProvider();
		this.navigator = null;
	}

	public void init() {
		// Let implementing classes override if wanted
	}

	public void advanceFrame() {
		eventBroker.distribute();

		checkCollisions();

		for (final IEntity e : entities)
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
		for (int i = 0; i < entities.size(); i++) {
			final IEntity source = entities.get(i);

			if (i+1 >= entities.size())
				break;

			for (int j = i+1; j < entities.size(); j++) {
				final IEntity target = entities.get(j);

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

	public Iterator<IDrawable> getDrawables() {
		return new InsertingDrawableIteratorWrapper(this, entities.iterator());
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
