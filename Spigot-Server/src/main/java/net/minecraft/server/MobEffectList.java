package net.minecraft.server;

import com.google.common.collect.Maps;

import net.mcavenue.redspigot.registries.MobEffectRegistry;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;

// CraftBukkit start
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
// CraftBukkit end
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MobEffectList {
	@Autowired
	public MobEffectRegistry REGISTRY;
	private final Map<IAttribute, AttributeModifier> a = Maps.newHashMap();
	private final boolean c;
	private final int d;
	private String e = "";
	private int f = -1;
	public double durationModifier;
	private boolean h;

	@Nullable
	public MobEffectList fromId(int i) {
		return (MobEffectList) REGISTRY.getId(i);
	}

	public int getId(MobEffectList mobeffectlist) {
		return REGISTRY.a(mobeffectlist); // CraftBukkit -
											// decompile error
	}

	@Nullable
	public MobEffectList getByName(String s) {
		return (MobEffectList) REGISTRY.get(new MinecraftKey(s));
	}

	public MobEffectList(boolean flag, int i) {
		this.c = flag;
		if (flag) {
			this.durationModifier = 0.5D;
		} else {
			this.durationModifier = 1.0D;
		}

		this.d = i;
	}

	public MobEffectList b(int i, int j) {
		this.f = i + j * 8;
		return this;
	}

	public void tick(EntityLiving entityliving, int i) {
		if (this == MobEffects.REGENERATION) {
			if (entityliving.getHealth() < entityliving.getMaxHealth()) {
				entityliving.heal(1.0F, RegainReason.MAGIC_REGEN); // CraftBukkit
			}
		} else if (this == MobEffects.POISON) {
			if (entityliving.getHealth() > 1.0F) {
				entityliving.damageEntity(CraftEventFactory.POISON, 1.0F); // CraftBukkit
																			// -
																			// DamageSource.MAGIC
																			// ->
																			// CraftEventFactory.POISON
			}
		} else if (this == MobEffects.WITHER) {
			entityliving.damageEntity(DamageSource.WITHER, 1.0F);
		} else if (this == MobEffects.HUNGER && entityliving instanceof EntityHuman) {
			((EntityHuman) entityliving).applyExhaustion(0.005F * (float) (i + 1));
		} else if (this == MobEffects.SATURATION && entityliving instanceof EntityHuman) {
			if (!entityliving.world.isClientSide) {
				// CraftBukkit start
				EntityHuman entityhuman = (EntityHuman) entityliving;
				int oldFoodLevel = entityhuman.getFoodData().foodLevel;

				org.bukkit.event.entity.FoodLevelChangeEvent event = CraftEventFactory.callFoodLevelChangeEvent(entityhuman, i + 1 + oldFoodLevel);

				if (!event.isCancelled()) {
					entityhuman.getFoodData().eat(event.getFoodLevel() - oldFoodLevel, 1.0F);
				}

				((EntityPlayer) entityhuman).playerConnection
						.sendPacket(new PacketPlayOutUpdateHealth(((EntityPlayer) entityhuman).getBukkitEntity().getScaledHealth(),
								entityhuman.getFoodData().foodLevel, entityhuman.getFoodData().saturationLevel));
				// CraftBukkit end
			}
		} else if ((this != MobEffects.HEAL || entityliving.cc()) && (this != MobEffects.HARM || !entityliving.cc())) {
			if (this == MobEffects.HARM && !entityliving.cc() || this == MobEffects.HEAL && entityliving.cc()) {
				entityliving.damageEntity(DamageSource.MAGIC, (float) (6 << i));
			}
		} else {
			entityliving.heal((float) Math.max(4 << i, 0), RegainReason.MAGIC); // CraftBukkit
		}

	}

	public void applyInstantEffect(@Nullable Entity entity, @Nullable Entity entity1, EntityLiving entityliving, int i, double d0) {
		int j;

		if ((this != MobEffects.HEAL || entityliving.cc()) && (this != MobEffects.HARM || !entityliving.cc())) {
			if (this == MobEffects.HARM && !entityliving.cc() || this == MobEffects.HEAL && entityliving.cc()) {
				j = (int) (d0 * (double) (6 << i) + 0.5D);
				if (entity == null) {
					entityliving.damageEntity(DamageSource.MAGIC, (float) j);
				} else {
					entityliving.damageEntity(DamageSource.b(entity, entity1), (float) j);
				}
			}
		} else {
			j = (int) (d0 * (double) (4 << i) + 0.5D);
			entityliving.heal((float) j, RegainReason.MAGIC); // CraftBukkit
		}

	}

	public boolean a(int i, int j) {
		int k;

		if (this == MobEffects.REGENERATION) {
			k = 50 >> j;
			return k > 0 ? i % k == 0 : true;
		} else if (this == MobEffects.POISON) {
			k = 25 >> j;
			return k > 0 ? i % k == 0 : true;
		} else if (this == MobEffects.WITHER) {
			k = 40 >> j;
			return k > 0 ? i % k == 0 : true;
		} else {
			return this == MobEffects.HUNGER;
		}
	}

	public boolean isInstant() {
		return false;
	}

	public MobEffectList c(String s) {
		this.e = s;
		return this;
	}

	public String a() {
		return this.e;
	}

	public MobEffectList a(double d0) {
		this.durationModifier = d0;
		return this;
	}

	public int getColor() {
		return this.d;
	}

	public MobEffectList a(IAttribute iattribute, String s, double d0, int i) {
		AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(s), this.a(), d0, i);

		this.a.put(iattribute, attributemodifier);
		return this;
	}

	public void a(EntityLiving entityliving, AttributeMapBase attributemapbase, int i) {
		Iterator iterator = this.a.entrySet().iterator();

		while (iterator.hasNext()) {
			Entry entry = (Entry) iterator.next();
			AttributeInstance attributeinstance = attributemapbase.a((IAttribute) entry.getKey());

			if (attributeinstance != null) {
				attributeinstance.c((AttributeModifier) entry.getValue());
			}
		}

	}

	public void b(EntityLiving entityliving, AttributeMapBase attributemapbase, int i) {
		Iterator iterator = this.a.entrySet().iterator();

		while (iterator.hasNext()) {
			Entry entry = (Entry) iterator.next();
			AttributeInstance attributeinstance = attributemapbase.a((IAttribute) entry.getKey());

			if (attributeinstance != null) {
				AttributeModifier attributemodifier = (AttributeModifier) entry.getValue();

				attributeinstance.c(attributemodifier);
				attributeinstance
						.b(new AttributeModifier(attributemodifier.a(), this.a() + " " + i, this.a(i, attributemodifier), attributemodifier.c()));
			}
		}

	}

	public double a(int i, AttributeModifier attributemodifier) {
		return attributemodifier.d() * (double) (i + 1);
	}

	public MobEffectList j() {
		this.h = true;
		return this;
	}

}
