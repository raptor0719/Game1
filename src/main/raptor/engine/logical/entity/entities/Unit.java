package raptor.engine.logical.entity.entities;

import java.util.LinkedList;
import java.util.Queue;

import raptor.engine.display.api.IDrawable;
import raptor.engine.display.api.IDrawer;
import raptor.engine.logical.entity.api.IOrderable;
import raptor.engine.logical.entity.order.api.IOrder;
import raptor.engine.logical.entity.statblock.api.IStatBlock;
import raptor.engine.logical.event.api.IEvent;
import raptor.engine.util.geometry.Point;

public class Unit implements IOrderable {

	private final IDrawable visual;
	private final IStatBlock statBlock;

	private final Queue<IOrder> orderQueue;

	public Unit(final IDrawable visual, final IStatBlock statBlock) {
		this.visual = visual;
		this.statBlock = statBlock;

		orderQueue = new LinkedList<IOrder>();
	}

	@Override
	public boolean colliding(final Point p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void affect(final IEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public Point getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(final IDrawer drawer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void order(final IOrder o) {
		// TODO Auto-generated method stub

	}
}
