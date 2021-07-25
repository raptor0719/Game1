package raptor.engine.event.events;

import raptor.engine.event.Event;
import raptor.engine.game.entity.IEntity;

public class EntityCollisionEvent extends Event {
	public EntityCollisionEvent(final IEntity collidingEntity, final IEntity collidingEntityOther) {
		super(Event.ENTITY_COLLISION);
		this.setProperty(Event.COLLIDING_ENTITY, collidingEntity);
		this.setProperty(Event.COLLIDING_ENTITY_OTHER, collidingEntityOther);
	}
}
