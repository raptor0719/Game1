package raptor.engine.game.entity.unit;

import java.awt.Image;

import raptor.engine.display.api.IDrawable;
import raptor.engine.test.main.TestModelFactory;
import raptor.engine.util.geometry.Vector;
import raptor.modelLibrary.model.AttachableWiredModel;
import raptor.modelLibrary.model.WiredModel;
import raptor.modelLibrary.model.util.point.IRotatedPoint;
import raptor.modelLibrary.model.util.point.Point;

public class Unit implements IOrderable, IDrawable {
	public Point position;
	public Vector velocity;

	public int hp;
	public int moveSpeed;

	public AttachableWiredModel model;

	public Unit(final int x, final int y, final int hp, final int moveSpeed) {
		position = new Point(x, y);
		velocity = new Vector(0, 0);
		this.hp = hp;
		this.moveSpeed = moveSpeed;

		model = TestModelFactory.getModel1(position);
	}

	public WiredModel getModel() {
		return model;
	}

	@Override
	public void move(int xScale, int yScale) {
		velocity.setX(moveSpeed * xScale);
		velocity.setY(moveSpeed * yScale);
	}

	@Override
	public boolean doDraw() {
		return true;
	}

	@Override
	public Image getImage() {
		return model.getCurrentSprite().getImage();
	}

	@Override
	public IRotatedPoint getPosition() {
		return position;
	}

	@Override
	public int getWidth() {
		return model.getWidth();
	}

	@Override
	public int getHeight() {
		return model.getWidth();
	}
}
