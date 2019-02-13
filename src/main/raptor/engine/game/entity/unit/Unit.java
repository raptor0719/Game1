package raptor.engine.game.entity.unit;

import java.util.ArrayList;
import java.util.List;

import raptor.engine.display.api.IDrawable;
import raptor.engine.test.main.TestModelFactory;
import raptor.engine.util.geometry.Point;
import raptor.engine.util.geometry.Vector;
import raptor.modelLibrary.model.IHardpointPosition;
import raptor.modelLibrary.model.Model;

public class Unit implements IDrawable {
	public Point position;
	public Vector velocity;

	public int hp;
	public int moveSpeed;

	public Model model;
	public List<IDrawable> limbs;

	public Unit(final int x, final int y, final int hp, final int moveSpeed) {
		position = new Point(x, y);
		velocity = new Vector(0, 0);
		this.hp = hp;
		this.moveSpeed = moveSpeed;

		model = TestModelFactory.getModel2(position);

		limbs = new ArrayList<>();
		limbs.add(new Limb(model.getHardpointPosition(0), new Point(15, 15)));
		limbs.add(new Limb(model.getHardpointPosition(1)));
		limbs.add(new Limb(model.getHardpointPosition(2), new Point(10, 10)));
		limbs.add(new Limb(model.getHardpointPosition(3)));
		limbs.add(new Limb(model.getHardpointPosition(4), new Point(7, 7)));
		limbs.add(new Limb(model.getHardpointPosition(5), new Point(10, 10)));
		limbs.add(new Limb(model.getHardpointPosition(6)));
		limbs.add(new Limb(model.getHardpointPosition(7), new Point(7, 7)));
		limbs.add(new Limb(model.getHardpointPosition(8)));
		limbs.add(new Limb(model.getHardpointPosition(9)));
		limbs.add(new Limb(model.getHardpointPosition(10)));
		limbs.add(new Limb(model.getHardpointPosition(11)));
		limbs.add(new Limb(model.getHardpointPosition(12), new Point(7, 7)));
		limbs.add(new Limb(model.getHardpointPosition(13)));
		limbs.add(new Limb(model.getHardpointPosition(14)));
		limbs.add(new Limb(model.getHardpointPosition(15), new Point(7, 7)));
	}

	@Override
	public Point getPosition() {
		return position;
	}

	@Override
	public Point getDimensions() {
		return new Point(0, 0);
	}

	@Override
	public List<IDrawable> getDrawables() {
		return limbs;
	}

	private static class Limb implements IDrawable {
		private IHardpointPosition ref;
		private Point dim;

		public Limb(final IHardpointPosition ref) {
			this(ref, new Point(4, 4));
		}

		public Limb(final IHardpointPosition ref, final Point dim) {
			this.ref = ref;
			this.dim = dim;
		}

		@Override
		public Point getPosition() {
			return new Point(ref.getX(), ref.getY());
		}

		@Override
		public Point getDimensions() {
			return dim;
		}

		@Override
		public List<IDrawable> getDrawables() {
			return null;
		}
	}
}
