package net.mcavenue.redspigot.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;

import net.mcavenue.redspigot.util.Defaultable;

@Entity
public class RedPlayer implements Serializable, Defaultable {

	private static final long serialVersionUID = -5653731552221349210L;
	@Column
	@Id
	@Type(type = "uuid-char")
	private UUID uuid;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "uuid")
	private PlayerPermissions permissions;

	public RedPlayer() {

	}

	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	public PlayerPermissions getPermissions() {
		return permissions;
	}
	public void setPermissions(PlayerPermissions permissions) {
		this.permissions = permissions;
	}

}
