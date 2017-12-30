package raptor.engine.logical.entity.order.handler;

import java.util.LinkedList;
import java.util.Queue;

import raptor.engine.logical.entity.order.api.IOrder;
import raptor.engine.logical.entity.order.api.IOrderHandler;
import raptor.engine.logical.entity.order.api.IOrderQueue;
import raptor.engine.logical.entity.order.orders.MoveOrder;
import raptor.engine.logical.nav.api.INavAgent;

public class DefaultOrderPackage implements IOrderQueue, IOrderHandler {
	private final Queue<IOrder> orderQueue;

	public DefaultOrderPackage() {
		orderQueue = new LinkedList<IOrder>();
	}

	@Override
	public void addOrder(IOrder o) {
		orderQueue.add(o);
	}

	@Override
	public boolean readyForOrder() {
		return !orderQueue.isEmpty();
	}

	@Override
	public IOrder getNewOrder() {
		if (!readyForOrder())
			throw new RuntimeException("No order was found when attempting to retrieve order. Make sure to call readyForOrder before querying.");
		return orderQueue.poll();
	}

	@Override
	public IOrder getCurrentOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleMoveOrder(final MoveOrder o, final INavAgent nav) {
		nav.setDestination(o.getTarget());
	}

	@Override
	public void handleStopOrder(final INavAgent nav) {
		nav.setDestination(nav.getPosition());
	}
}