package raptor.engine.logical.entity.order.orders;

import raptor.engine.logical.entity.order.api.IOrder;

public class StopOrder implements IOrder {
	@Override
	public OrderType getOrderType() {
		return OrderType.StopOrder;
	}
}
