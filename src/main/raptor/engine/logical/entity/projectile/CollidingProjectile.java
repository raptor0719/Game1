package raptor.engine.logical.entity.projectile;

import raptor.engine.display.api.IDrawer;
import raptor.engine.logical.entity.api.IAffectable;
import raptor.engine.logical.entity.projectile.data.ProjectileData;
import raptor.engine.logical.event.api.IEvent;
import raptor.engine.logical.event.events.EmptyEvent;
import raptor.engine.logical.map.api.IMapContext;
import raptor.engine.util.geometry.Point;
import raptor.engine.util.geometry.Vector;

public class CollidingProjectile implements IAffectable {
	private final IMapContext mapContext;
	private final ProjectileData data;
	private final Vector direction;

	private long remainingLifetime;

	private int posX;
	private int posY;

	private long timeOfLastTick;

	public CollidingProjectile(final Point pos, final Vector direction, final ProjectileData data, final IMapContext mapContext) {
		remainingLifetime = data.getLifetime();

		posX = pos.getX();
		posY = pos.getY();

		this.data = data;
		this.mapContext = mapContext;
		this.direction = direction;

		timeOfLastTick = mapContext.getCurrentTime();
	}

	@Override
	public int getId() {
		return data.getId();
	}

	@Override
	public Point getPosition() {
		return new Point(posX, posY);
	}

	@Override
	public void act() {
		// Check if expired
		remainingLifetime -= getElapsedTimeSinceLastTick();
		if (remainingLifetime <= 0)
			mapContext.noticeOfDeath(this);

		// Move
		final Point newPos = data.getMovement().calculateNewPosition(getPosition(), direction, getMovementThisTick());
		posX = newPos.getX();
		posY = newPos.getY();

		// Check collision
		if (data.getCollision().collision(data.getHitbox(), data.onHit(), mapContext))
			mapContext.noticeOfDeath(this);
	}

	@Override
	public void draw(final IDrawer drawer) {
		drawer.drawOval(getPosition().getX()-2, getPosition().getY()-2, 4, 4);
	}

	@Override
	public void affect(final IEvent e) {
		// Swallow
	}

	@Override
	public IEvent onDeath() {
		return new EmptyEvent();
	}

	private double getMovementThisTick() {
		return data.getMoveRate() * getTimeScale();
	}

	private float getTimeScale() {
		return getElapsedTimeSinceLastTick() / (float)1000;
	}

	private long getElapsedTimeSinceLastTick() {
		return mapContext.getCurrentTime() - timeOfLastTick;
	}
}
