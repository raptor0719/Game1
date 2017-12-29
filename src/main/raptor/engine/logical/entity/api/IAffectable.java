package raptor.engine.logical.entity.api;

import raptor.engine.logical.event.api.IEvent;

public interface IAffectable extends IEntity {
	public void affect(final IEvent e);
}
