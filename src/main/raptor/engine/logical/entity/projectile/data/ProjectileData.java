package raptor.engine.logical.entity.projectile.data;

import raptor.engine.logical.entity.hitbox.api.IHitbox;
import raptor.engine.logical.entity.projectile.collision.api.IProjectileCollision;
import raptor.engine.logical.entity.projectile.movement.api.IProjectileMovement;
import raptor.engine.logical.event.api.IEvent;

public class ProjectileData {
	private final int id;
	private final ProjectileType type;
	private final IProjectileMovement movement;
	private final IProjectileCollision collision;

	private final IEvent onDeath;
	private final IEvent onHit;

	private final int moveRate;
	private final long lifetime;
	private final IHitbox hitbox;

	public ProjectileData(final int id, final ProjectileType type, final IProjectileMovement movement, final IProjectileCollision collision, final IEvent onDeath, final IEvent onHit, final int moveRate, final long lifetime, final IHitbox hitbox) {
		this.id = id;
		this.type = type;
		this.movement = movement;
		this.collision = collision;
		this.onDeath = onDeath;
		this.onHit = onHit;
		this.moveRate = moveRate;
		this.lifetime = lifetime;
		this.hitbox = hitbox;
	}

	public int getId() {
		return id;
	}

	public ProjectileType getType() {
		return type;
	}

	public IProjectileMovement getMovement() {
		return movement;
	}

	public IProjectileCollision getCollision() {
		return collision;
	}

	public IEvent onDeath() {
		return onDeath;
	}

	public IEvent onHit() {
		return onHit;
	}

	public int getMoveRate() {
		return moveRate;
	}

	public long getLifetime() {
		return lifetime;
	}

	public IHitbox getHitbox() {
		return hitbox;
	}
}
