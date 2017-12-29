package raptor.engine.logical.entity.api;

import raptor.engine.logical.entity.order.api.IOrder;

public interface IOrderable extends ICollideable {
	public void order(final IOrder o);
}
