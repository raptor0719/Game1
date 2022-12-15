package raptor.engine.event;

import java.util.Iterator;

public interface IEventBus {
	void emit(IEvent event);
	Iterator<IEvent> getCurrentEvents();
	Iterator<IEvent> getCurrentEvents(IEventFilter filter);
}
