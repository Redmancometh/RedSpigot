package net.mcavenue.redspigot.configuration.context;

import java.util.Collections;
import java.util.List;

import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import net.mcavenue.redspigot.util.BooleanWrapper;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.PlayerList;

@Configuration
public class StateContext {
	@Bean
	@Qualifier("online")
	public BooleanWrapper online() {
		return new BooleanWrapper();
	}

	@Bean
	@Qualifier("player-view")
	public List<CraftPlayer> playerView(PlayerList playerList) {
		return Collections.unmodifiableList(Lists.transform(playerList.players, new Function<EntityPlayer, CraftPlayer>() {
			@Override
			public CraftPlayer apply(EntityPlayer player) {
				return player.getBukkitEntity();
			}
		}));
	}

}
