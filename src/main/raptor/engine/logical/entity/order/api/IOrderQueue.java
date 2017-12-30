package raptor.engine.logical.entity.order.api;

public interface IOrderQueue {
	public void addOrder(final IOrder o);
	public boolean readyForOrder();
	public IOrder getNewOrder();
	public IOrder getCurrentOrder();
}
