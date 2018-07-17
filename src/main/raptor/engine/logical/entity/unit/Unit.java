package raptor.engine.logical.entity.unit;

import raptor.engine.display.api.IDrawer;
import raptor.engine.logical.entity.api.IOrderable;
import raptor.engine.logical.entity.order.api.IOrder;
import raptor.engine.logical.entity.order.api.IOrderHandler;
import raptor.engine.logical.entity.order.api.IOrderQueue;
import raptor.engine.logical.entity.order.orders.MoveOrder;
import raptor.engine.logical.nav.api.INavAgent;
import raptor.engine.util.geometry.Point;

public class Unit implements IOrderable {
	private final INavAgent navAgent;

	private final IOrderQueue orderQueue;
	private final IOrderHandler orderHandler;

	public Unit(final INavAgent navAgent, final IOrderQueue orderQueue, final IOrderHandler orderHandler) {
		this.navAgent = navAgent;

		this.orderQueue = orderQueue;
		this.orderHandler = orderHandler;
	}

	@Override
	public int getLocalId() {
		// TODO: need a unit data object
		return 0;
	}

	@Override
	public Point getPosition() {
		return navAgent.getPosition();
	}

	@Override
	public void act() {
		// Handle orders
		if (orderQueue.readyForOrder())
			applyOrder(orderQueue.getNewOrder());

		// Move
		navAgent.move();
	}

	@Override
	public void draw(final IDrawer drawer) {
		drawer.drawOval(getPosition().getX()-5, getPosition().getY()-5, 10, 10);
	}

	@Override
	public void order(final IOrder o) {
		orderQueue.addOrder(o);
	}

	private void applyOrder(final IOrder o) {
		switch (o.getOrderType()) {
			case MoveOrder:
				orderHandler.handleMoveOrder((MoveOrder)o, navAgent);
				break;
			case StopOrder:
				orderHandler.handleStopOrder(navAgent);
				break;
			default:
		}
	}
}
