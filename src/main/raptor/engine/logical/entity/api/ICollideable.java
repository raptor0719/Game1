package raptor.engine.logical.entity.api;

import raptor.engine.logical.entity.hitbox.api.IHitbox;

public interface ICollideable extends IAffectable {
	public IHitbox getHitbox();
}
