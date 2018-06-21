package net.mcavenue.redspigot.db;

import org.bukkit.craftbukkit.util.SpecialFuture;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import net.mcavenue.redspigot.exception.ObjectNotPresentException;
import net.mcavenue.redspigot.util.Defaultable;

import java.util.List;
import java.util.UUID;

public class ObjectManager<T extends Defaultable<?>> implements BaseObjectManager<T> {
	private final Class<T> type;

	@Autowired
	private MasterDatabase masterDB;
	private String name;
	public ObjectManager(Class<T> type, String name) {
		this.type = type;
		this.name = name;
	}

	/**
	 * Deletes a record
	 * 
	 * @param e
	 */
	public void delete(T e) {
		getSubDB().deleteObject(e);
	}

	/**
	 * Returns the top X players based on the property given
	 * 
	 * @param x
	 *            This is how many records to return
	 * @param onProperty
	 *            This is the column name. When you use @Column(name="name")
	 *            it's called "name." Always use
	 * @Column and explicitly set the name if you intend on using this.
	 * @return
	 */
	public SpecialFuture<List<T>> topX(int x, String onProperty) {
		return getSubDB().topX(x, onProperty);
	}

	/**
	 * Don't use this if you can avoid.
	 *
	 * @return
	 */
	public SubDatabase<UUID, T> getSubDB() {
		return masterDB.getSubDBForType(type);
	}

	@Override
	public ObjectManager<T> getThis() {
		return this;
	}

	/**
	 * Get the type that's being mapped by this ObjectManager
	 */
	public Class<T> getType() {
		return type;
	}

	/**
	 * Wait for completion of the SpecialFuture Synchronously
	 * 
	 * @param key
	 * @return
	 */
	public T getBlocking(UUID key) {
		return getSubDB().get(key);
	}

	/**
	 * Get a record for the player with this uuid
	 * 
	 * @param uuid
	 * @return
	 */
	public SpecialFuture<T> getRecord(UUID uuid) {
		return getSubDB().getObject(uuid);
	}

	/**
	 * Insert an object into the subDatabase for future persistence
	 * 
	 * @param key
	 * @param value
	 */
	public void insertObject(UUID key, T value) {
		getSubDB().insertObject(key, value);
	}

	/**
	 * Save without purging
	 * 
	 * @param e
	 * @return
	 */
	public SpecialFuture<Void> save(T e) {
		return masterDB.getSubDBForType(type).saveObject(e);
	}

	/**
	 * Save without purging
	 * 
	 * @param p
	 * @return
	 */
	public SpecialFuture<T> save(Player p) {
		return save(p.getUniqueId());
	}

	/**
	 * Save without purging
	 * 
	 * @param uuid
	 * @return
	 */
	public SpecialFuture<T> save(UUID uuid) {
		return getSubDB().getObject(uuid).thenAccept((record) -> getSubDB().saveObject(record));
	}

	/**
	 * Save an object and purge it from the cache
	 * 
	 * @param e
	 * @param uuid
	 * @return
	 */
	public SpecialFuture<T> saveAndPurge(Player p) {
		UUID uuid = p.getUniqueId();
		return getSubDB().getObject(uuid).thenAccept((record) -> {
			try {
				getSubDB().saveAndPurge(record, uuid);
			} catch (ObjectNotPresentException e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Save an object and purge it from the cache
	 * 
	 * @param e
	 * @param uuid
	 * @return
	 */
	public SpecialFuture<?> saveAndPurge(T e, UUID uuid) {
		try {
			return getSubDB().saveAndPurge(e, uuid);
		} catch (ObjectNotPresentException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public MasterDatabase getMasterDB() {
		return masterDB;
	}

	public String getName() {
		return name;
	}

	public void setMasterDB(MasterDatabase masterDB) {
		this.masterDB = masterDB;
	}

	public void setName(String name) {
		this.name = name;
	}
}
