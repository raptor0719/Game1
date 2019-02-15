package raptor.engine.game.entity.unit;

import raptor.engine.test.main.TestModelFactory;
import raptor.engine.util.geometry.Point;
import raptor.engine.util.geometry.Vector;
import raptor.modelLibrary.model.Model;

public class Unit {
	public Point position;
	public Vector velocity;

	public int hp;
	public int moveSpeed;

	public Model model;

	public Unit(final int x, final int y, final int hp, final int moveSpeed) {
		position = new Point(x, y);
		velocity = new Vector(0, 0);
		this.hp = hp;
		this.moveSpeed = moveSpeed;

		model = TestModelFactory.getModel2(position);
	}

	public Model getModel() {
		return model;
	}
}
