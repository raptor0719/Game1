package raptor.engine.game.entity.unit;

import raptor.engine.display.api.IDrawable;
import raptor.engine.display.api.IDrawer;
import raptor.engine.util.geometry.Point;
import raptor.engine.util.geometry.Vector;

public class Unit implements IDrawable {
	public Point position;
	public Vector velocity;

	public int hp;
	public int moveSpeed;

	public Unit(final int x, final int y, final int hp, final int moveSpeed) {
		position = new Point(x, y);
		velocity = new Vector(0, 0);
		this.hp = hp;
		this.moveSpeed = moveSpeed;
	}

	@Override
	public void draw(final IDrawer drawer) {
		drawer.drawOval(position.getX(), position.getY(), 10, 10);
	}
}
