package net.mcavenue.redspigot.db;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import lombok.Getter;
import net.mcavenue.redspigot.exception.ObjectNotPresentException;
import net.mcavenue.redspigot.util.Defaultable;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.util.SpecialFuture;
import org.bukkit.plugin.java.JavaPlugin;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @param <V>
 *            This is the type of object persisted
 * @param <K>
 *            This is the type for the key which is used to lookup the object in
 *            both the cache and database.
 * @author Redmancometh
 */
public class SubDatabase<K extends Serializable, V extends Defaultable> {
	private final Class<V> type;
	public Function<K, V> defaultObjectBuilder;
	@Getter
	private String name;
	@Autowired
	private ScheduledExecutorService exec;
	private SessionFactory factory;
	JSONParser parser = new JSONParser();

	LoadingCache<K, SpecialFuture<V>> cache = CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.MINUTES)
			.build(new CacheLoader<K, SpecialFuture<V>>() {
				@Override
				public SpecialFuture<V> load(K key) {
					return SpecialFuture.supplyAsync(() -> {
						try (Session session = factory.openSession()) {
							V result = session.get(type, key);
							if (result == null) {
								System.out.println("RESULT IS NULL");
								return defaultObjectBuilder.apply(key);

							}
							return result;
						} catch (SecurityException | IllegalArgumentException e) {
							SpecialFuture.runSync(() -> Bukkit.getLogger().log(Level.SEVERE, "Failed to get database object", e));
							throw new RuntimeException(e);
						}
					});
				}
			});

	public SubDatabase(Class<V> type, String name) {
		super();
		this.name = name;
		this.type = type;
		this.factory = factory();
		this.defaultObjectBuilder = (key) -> {
			try {
				V v = type.newInstance();
				v.setDefaults(key);
				return v;
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;
		};
	}

	public SessionFactory factory() {
		File hibernateConfig = new File("db" + File.separator + name + File.separator + "hibernate.cfg.xml");
		System.out.println("HB FILE: " + hibernateConfig.getAbsolutePath());
		if (!hibernateConfig.exists())
			throw new IllegalStateException("Configuration not initialized properly. Either config.json is missing, corrupted, or ill-formatted");
		Configuration config = new Configuration().configure(hibernateConfig);
		JSONObject jsonConfig = buildJsonConfig();
		JSONObject dbConfig = (JSONObject) jsonConfig.get("DB");
		config.setProperty("hibernate.hikari.dataSource.user", dbConfig.get("user").toString());
		config.setProperty("hibernate.hikari.dataSource.password", dbConfig.get("password").toString());
		config.setProperty("hibernate.hikari.dataSource.url", dbConfig.get("url").toString());
		SessionFactory sessionFactory = config.buildSessionFactory();
		return sessionFactory;
	}

	public JSONObject buildJsonConfig() {
		File hibernateConfig = new File("db" + File.separator + name + File.separator + "config.json");
		if (!hibernateConfig.exists())
			throw new IllegalStateException("Configuration not initialized properly. Either config.json is missing, corrupted, or ill-formatted");
		try (FileReader scanner = new FileReader(hibernateConfig)) {
			return (JSONObject) parser.parse(scanner);
		} catch (Exception e) {
			throw new IllegalStateException("Configuration not initialized properly. Either config.json is missing, corrupted, or ill-formatted");
		}
	}

	public void deleteObject(V e) {
		try (Session s = factory.openSession()) {
			s.delete(e);
		}
	}

	public V getDirectlyFromDB(K e) {
		try (Session session = factory.openSession()) {
			V result = session.get(type, e);
			if (result == null) {
				System.out.println("RESULT IS NULL");
				return defaultObjectBuilder.apply(e);
			}
			return result;
		}

	}

	public SpecialFuture<V> getFromCache(K e) {
		try {
			return cache.get(e);
		} catch (ExecutionException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * This method is an insta-return which assumes the value is already loaded
	 * into the cache.
	 *
	 * @param e
	 * @return
	 */
	public V get(K e) {
		SpecialFuture<V> future = cache.asMap().get(e);
		if (future == null)
			System.out.println("DAFUQ");
		return future.get();
	}

	public SpecialFuture<List<V>> topX(int x, String onProperty) {
		return queryWithCriteria((criteria) -> {
			criteria.addOrder(Order.desc(onProperty));
			criteria.setFirstResult(0);
			criteria.setMaxResults(50);
		});
	}

	public SpecialFuture<List<V>> queryWithCriteria(Consumer<Criteria> criteriaCallback) {
		return SpecialFuture.supplyAsync(() -> {
			List<V> list;
			try (Session session = factory.openSession()) {
				Criteria c = session.createCriteria(type);
				criteriaCallback.accept(c);
				list = c.list();
			}
			return list;
		});
	}

	public SessionFactory getFactory() {
		return factory;
	}

	public Class<V> getMyType() {
		return this.type;
	}

	/**
	 * Get an object from the db async using CompletableFuture. Call this with
	 * .thenAccept or something.
	 *
	 * @param e
	 * @return
	 */
	public SpecialFuture<V> getObject(K e) {
		try {
			return cache.get(e);
		} catch (ExecutionException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public SpecialFuture<V> getWithCriteria(K e, Criteria... criteria) {
		try {
			return cache.get(e);
		} catch (ExecutionException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public void insertObject(K key, V value) {
		System.out.println("Inserting: " + value + " AT: " + key);
		cache.asMap().put(key, SpecialFuture.supplyAsync(() -> value));
	}

	public Session newSession() {
		return factory.openSession();
	}

	/**
	 * Purge an object by using it's KEY object. Warning: This is unchecked, and
	 * the object will not be saved.
	 *
	 * @param e
	 */
	public void purgeObject(K e) {
		cache.asMap().remove(e);
	}

	/**
	 * Save a value and purge it from the cache.
	 *
	 * @param e
	 * @return
	 * @throws ObjectNotPresentException
	 */
	public SpecialFuture<?> saveAndPurge(V e, UUID uuid) throws ObjectNotPresentException {
		return saveObject(e).thenRun(() -> cache.asMap().remove(uuid));
	}

	/**
	 * Save an object without purging it
	 *
	 * @param e
	 * @return
	 */
	public SpecialFuture<?> saveObject(V e) {
		return SpecialFuture.runAsync(() -> {
			try (Session session = factory.openSession()) {
				session.beginTransaction();
				session.saveOrUpdate(e);
				session.getTransaction().commit();
			}
		});
	}

}
