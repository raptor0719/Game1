package raptor.engine.event;

import java.util.Iterator;

public interface IEventSource {
	Iterator<IEvent> getEvents();
}
