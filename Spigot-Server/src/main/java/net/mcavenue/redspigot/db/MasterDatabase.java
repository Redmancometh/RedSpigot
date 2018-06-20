package net.mcavenue.redspigot.db;

import lombok.Getter;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.hibernate.SessionFactory;

public class MasterDatabase implements Iterable<SubDatabase> {
	@Getter
	private Map<Class, SubDatabase> subDBMap = new ConcurrentHashMap();

	public SubDatabase getSubDBForType(Class clazz) {
		return subDBMap.get(clazz);
	}

	@Override
	public Iterator<SubDatabase> iterator() {
		return subDBMap.values().iterator();
	}

	@Override
	public void forEach(Consumer<? super SubDatabase> action) {
		subDBMap.values().forEach(action);
	}

	public void registerDatabase(Class ofType, String name) {
		this.subDBMap.put(ofType, new SubDatabase(ofType, name));
	}

}
