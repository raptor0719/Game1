package raptor.engine.event.events;

import raptor.engine.event.Event;
import raptor.engine.game.entity.IEntity;

public class EntityCollisionEvent extends Event {
	public EntityCollisionEvent(final IEntity collidingEntity) {
		super(Event.ENTITY_COLLISION);
		this.setProperty(Event.COLLIDING_ENTITY, collidingEntity);
	}
}
