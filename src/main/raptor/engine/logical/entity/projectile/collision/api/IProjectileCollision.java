package raptor.engine.logical.entity.projectile.collision.api;

import raptor.engine.logical.entity.hitbox.api.IHitbox;
import raptor.engine.logical.event.api.IEvent;
import raptor.engine.logical.map.api.IMapContext;

public interface IProjectileCollision {
	public boolean collision(final IHitbox hitbox, final IEvent onHit, final IMapContext map);
}
