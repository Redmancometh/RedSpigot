package net.mcavenue.redspigot.configuration.pojo.spigot;

import lombok.Data;

@Data
public class AttributeConfig {
	private int maxHealth = 2048, movementSpeed = 2048, attackDamage = 2048;

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getMovementSpeed() {
		return movementSpeed;
	}

	public void setMovementSpeed(int movementSpeed) {
		this.movementSpeed = movementSpeed;
	}

	public int getAttackDamage() {
		return attackDamage;
	}

	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}

}
