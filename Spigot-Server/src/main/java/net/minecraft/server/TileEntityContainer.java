package net.minecraft.server;

import org.bukkit.craftbukkit.potion.CraftPotionUtil;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class TileEntityContainer extends TileEntity implements ITileInventory {

	private ChestLock a;
	@Autowired
	protected CraftPotionUtil potions;

	public TileEntityContainer() {
		this.a = ChestLock.a;
	}

	public void load(NBTTagCompound nbttagcompound) {
		super.load(nbttagcompound);
		this.a = ChestLock.b(nbttagcompound);
	}

	public NBTTagCompound save(NBTTagCompound nbttagcompound) {
		super.save(nbttagcompound);
		if (this.a != null) {
			this.a.a(nbttagcompound);
		}

		return nbttagcompound;
	}

	public boolean isLocked() {
		return this.a != null && !this.a.a();
	}

	public ChestLock getLock() {
		return this.a;
	}

	public void setLock(ChestLock chestlock) {
		this.a = chestlock;
	}

	public IChatBaseComponent getScoreboardDisplayName() {
		return (IChatBaseComponent) (this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatMessage(this.getName(), new Object[0]));
	}

	// CraftBukkit start
	@Override
	public org.bukkit.Location getLocation() {
		if (world == null)
			return null;
		return new org.bukkit.Location(world.getWorld(), position.getX(), position.getY(), position.getZ());
	}
	// CraftBukkit end
}
