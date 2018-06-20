package net.mcavenue.redspigot.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "player_permissions")
public class PlayerPermissions {

	@Id
	@Column(name = "player_id")
	@Type(type = "uuid-char")
	private UUID playerId;

	/**
	 * Last KNOWN name.
	 */
	@Column
	private String name;
	public PlayerPermissions() {

	}

	public PlayerPermissions(UUID playerId) {
		this.playerId = playerId;
	}

	public UUID getPlayerId() {
		return playerId;
	}

	public void setPlayerId(UUID playerId) {
		this.playerId = playerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
