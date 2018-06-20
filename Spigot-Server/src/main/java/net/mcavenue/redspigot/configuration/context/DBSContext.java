package net.mcavenue.redspigot.configuration.context;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.mcavenue.redspigot.db.BaseObjectManager;
import net.mcavenue.redspigot.db.MasterDatabase;
import net.mcavenue.redspigot.db.ObjectManager;
import net.mcavenue.redspigot.model.RedPlayer;

/**
 * Context for the Database System previously known as "RedCore" migrated to a
 * Spring system.
 * 
 * @author Redmancometh
 *
 */
@Configuration
public class DBSContext {

	@Bean
	public MasterDatabase masterDB() {
		return new MasterDatabase();
	}

	@Bean
	public ObjectManager<RedPlayer> players(MasterDatabase masterDB) {
		ObjectManager objMon = new ObjectManager(RedPlayer.class, "permissions");
		masterDB.registerDatabase(objMon.getType(), objMon.getName());
		return objMon;
	}

}
