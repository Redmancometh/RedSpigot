package net.mcavenue.redspigot.db;

import java.util.UUID;

import net.mcavenue.redspigot.util.Defaultable;

public interface BaseObjectManager<T extends Defaultable<?>> {
	SubDatabase<UUID, T> getSubDB();

	ObjectManager<T> getThis();

	Class<T> getType();
}
