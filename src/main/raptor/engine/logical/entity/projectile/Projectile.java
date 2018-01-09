package raptor.engine.logical.entity.projectile;

import raptor.engine.display.api.IDrawer;
import raptor.engine.logical.effect.api.IEffect;
import raptor.engine.logical.entity.api.IAffectable;
import raptor.engine.logical.entity.projectile.collision.api.IProjectileCollision;
import raptor.engine.logical.entity.projectile.lifecycle.api.IProjectileLifecycle;
import raptor.engine.logical.entity.projectile.movement.api.IProjectileMovement;
import raptor.engine.logical.event.api.IEvent;
import raptor.engine.logical.event.events.EmptyEvent;
import raptor.engine.logical.map.api.IMapContext;
import raptor.engine.util.geometry.Point;

public class Projectile implements IAffectable {
	private final int typeId;
	private final IProjectileCollision collision;
	private final IProjectileMovement movement;
	private final IProjectileLifecycle lifecycle;
	private final IMapContext mapContext;

	private final int localId;

	private int posX;
	private int posY;

	public Projectile(final int typeId, final IProjectileCollision collision, final IProjectileMovement movement, final IProjectileLifecycle lifecycle, final IMapContext mapContext, final int localId) {
		this.typeId = typeId;
		this.collision = collision;
		this.movement = movement;
		this.lifecycle = lifecycle;
		this.mapContext = mapContext;
		this.localId = localId;
	}

	@Override
	public int getLocalId() {
		return localId;
	}

	@Override
	public Point getPosition() {
		return new Point(posX, posY);
	}

	@Override
	public void act() {
		collision.collide(getPosition());

		movement.calculateNewPosition(getPosition());

		if (lifecycle.isExpired(collision.getCollisionCount()))
			mapContext.noticeOfDeath(this);
	}

	@Override
	public void draw(final IDrawer drawer) {
		drawer.drawOval(getPosition().getX()-2, getPosition().getY()-2, 4, 4);
	}

	@Override
	public void affect(final IEffect e) {
		// Swallow
	}

	@Override
	public IEvent onDeath() {
		return new EmptyEvent();
	}

	public int getTypeId() {
		return typeId;
	}
}
