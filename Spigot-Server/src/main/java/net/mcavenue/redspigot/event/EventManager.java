package net.mcavenue.redspigot.event;
import org.bukkit.event.Event;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Component;
/**
 * TODO: Async event manager
 * 
 * @author Redmancometh
 *
 */
@Component
public class EventManager {
	private Multimap<Class<? extends Event>, EventConsumer> listeners = HashMultimap.create();

	public Multimap<Class<? extends Event>, EventConsumer> consumers() {
		return listeners;
	}

	public <T extends Event> void registerListener(Class<T> clazz, EventConsumer<T> consumer) {
		listeners.put(clazz, consumer);
	}

	public void callEvent(Event e) {
		listeners.get(e.getClass()).forEach((listener) -> listener.accept(e));
	}

	public void removeListener(Event event, Class<? extends EventConsumer> listenerClass) {
		listeners.get(event.getClass()).stream().filter((listener) -> listener.getClass() == listenerClass).forEach((listener) -> {
			listeners.remove(event.getClass(), listener);
		});
	}

	public void registerListener(Event event, EventConsumer eventConsumer) {
		listeners.put(event.getClass(), eventConsumer);
	}
}
