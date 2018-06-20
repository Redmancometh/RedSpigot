package net.mcavenue.redspigot.event;

import java.util.function.Consumer;

import org.bukkit.event.Event;

public interface EventConsumer<T extends Event> extends Consumer<T> {

}
