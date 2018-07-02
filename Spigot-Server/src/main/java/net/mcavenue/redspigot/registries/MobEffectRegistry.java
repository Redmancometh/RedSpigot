package net.mcavenue.redspigot.registries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.minecraft.server.GenericAttributes;
import net.minecraft.server.InstantMobEffect;
import net.minecraft.server.MinecraftKey;
import net.minecraft.server.MobEffectAbsorption;
import net.minecraft.server.MobEffectAttackDamage;
import net.minecraft.server.MobEffectHealthBoost;
import net.minecraft.server.MobEffectList;

@Component
public class MobEffectRegistry extends RegistryMaterials<MinecraftKey, MobEffectList> {

	public MobEffectRegistry(@Autowired GenericAttributes attr) {
		a(1, new MinecraftKey("speed"), (new MobEffectList(false, 8171462)).c("effect.moveSpeed").b(0, 0)
				.a(attr.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", 0.20000000298023224D, 2).j());
		a(2, new MinecraftKey("slowness"), (new MobEffectList(true, 5926017)).c("effect.moveSlowdown").b(1, 0).a(attr.MOVEMENT_SPEED,
				"7107DE5E-7CE8-4030-940E-514C1F160890", -0.15000000596046448D, 2));
		a(3, new MinecraftKey("haste"), (new MobEffectList(false, 14270531)).c("effect.digSpeed").b(2, 0).a(1.5D).j().a(attr.g,
				"AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", 0.10000000149011612D, 2));
		a(4, new MinecraftKey("mining_fatigue"), (new MobEffectList(true, 4866583)).c("effect.digSlowDown").b(3, 0).a(attr.g,
				"55FCED67-E92A-486E-9800-B47F202C4386", -0.10000000149011612D, 2));
		a(5, new MinecraftKey("strength"), (new MobEffectAttackDamage(false, 9643043, 3.0D)).c("effect.damageBoost").b(4, 0)
				.a(attr.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 0.0D, 0).j());
		a(6, new MinecraftKey("instant_health"), (new InstantMobEffect(false, 16262179)).c("effect.heal").j());
		a(7, new MinecraftKey("instant_damage"), (new InstantMobEffect(true, 4393481)).c("effect.harm").j());
		a(8, new MinecraftKey("jump_boost"), (new MobEffectList(false, 2293580)).c("effect.jump").b(2, 1).j());
		a(9, new MinecraftKey("nausea"), (new MobEffectList(true, 5578058)).c("effect.confusion").b(3, 1).a(0.25D));
		a(10, new MinecraftKey("regeneration"), (new MobEffectList(false, 13458603)).c("effect.regeneration").b(7, 0).a(0.25D).j());
		a(11, new MinecraftKey("resistance"), (new MobEffectList(false, 10044730)).c("effect.resistance").b(6, 1).j());
		a(12, new MinecraftKey("fire_resistance"), (new MobEffectList(false, 14981690)).c("effect.fireResistance").b(7, 1).j());
		a(13, new MinecraftKey("water_breathing"), (new MobEffectList(false, 3035801)).c("effect.waterBreathing").b(0, 2).j());
		a(14, new MinecraftKey("invisibility"), (new MobEffectList(false, 8356754)).c("effect.invisibility").b(0, 1).j());
		a(15, new MinecraftKey("blindness"), (new MobEffectList(true, 2039587)).c("effect.blindness").b(5, 1).a(0.25D));
		a(16, new MinecraftKey("night_vision"), (new MobEffectList(false, 2039713)).c("effect.nightVision").b(4, 1).j());
		a(17, new MinecraftKey("hunger"), (new MobEffectList(true, 5797459)).c("effect.hunger").b(1, 1));
		a(18, new MinecraftKey("weakness"), (new MobEffectAttackDamage(true, 4738376, -4.0D)).c("effect.weakness").b(5, 0).a(attr.ATTACK_DAMAGE,
				"22653B89-116E-49DC-9B6B-9971489B5BE5", 0.0D, 0));
		a(19, new MinecraftKey("poison"), (new MobEffectList(true, 5149489)).c("effect.poison").b(6, 0).a(0.25D));
		a(20, new MinecraftKey("wither"), (new MobEffectList(true, 3484199)).c("effect.wither").b(1, 2).a(0.25D));
		a(21, new MinecraftKey("health_boost"), (new MobEffectHealthBoost(false, 16284963)).c("effect.healthBoost").b(7, 2)
				.a(attr.maxHealth, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0D, 0).j());
		a(22, new MinecraftKey("absorption"), (new MobEffectAbsorption(false, 2445989)).c("effect.absorption").b(2, 2).j());
		a(23, new MinecraftKey("saturation"), (new InstantMobEffect(false, 16262179)).c("effect.saturation").j());
		a(24, new MinecraftKey("glowing"), (new MobEffectList(false, 9740385)).c("effect.glowing").b(4, 2));
		a(25, new MinecraftKey("levitation"), (new MobEffectList(true, 13565951)).c("effect.levitation").b(3, 2));
		a(26, new MinecraftKey("luck"),
				(new MobEffectList(false, 3381504)).c("effect.luck").b(5, 2).j().a(attr.j, "03C3C89D-7037-4B42-869F-B146BCB64D2E", 1.0D, 0));
		a(27, new MinecraftKey("unluck"),
				(new MobEffectList(true, 12624973)).c("effect.unluck").b(6, 2).a(attr.j, "CC5AF142-2BD2-4215-B636-2605AED11727", -1.0D, 0));

	}
	public void registerPotionEffects() {
		for (Object effect : this) {
			org.bukkit.potion.PotionEffectType
					.registerPotionEffectType(new org.bukkit.craftbukkit.potion.CraftPotionEffectType((MobEffectList) effect, this));
		}
	}
}
