package net.mcavenue.redspigot.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class PermissionPlayer {
	@Column
	@Id
	private UUID uuid;
}
