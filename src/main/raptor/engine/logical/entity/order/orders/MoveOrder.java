package raptor.engine.logical.entity.order.orders;

import raptor.engine.logical.entity.order.api.IOrder;
import raptor.engine.util.geometry.Point;

public class MoveOrder implements IOrder {
	private final Point target;

	public MoveOrder(final Point target) {
		this.target = target;
	}

	public Point getTarget() {
		return target;
	}

	@Override
	public OrderType getOrderType() {
		return OrderType.MoveOrder;
	}
}
