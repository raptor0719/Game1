package raptor.engine.logical.entity.order.api;

import raptor.engine.logical.entity.order.orders.MoveOrder;
import raptor.engine.logical.nav.api.INavAgent;

// TODO: This should be split into an order queue and an order handler
public interface IOrderHandler {
	public void handleMoveOrder(final MoveOrder o, final INavAgent nav);
	public void handleStopOrder(final INavAgent nav);
}
