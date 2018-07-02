package net.minecraft.server;

import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.mcavenue.redspigot.configuration.pojo.spigot.SpigotConfig;
import net.minecraft.server.AttributeInstance;
import net.minecraft.server.AttributeMapBase;
import net.minecraft.server.AttributeModifier;
import net.minecraft.server.IAttribute;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;

@Component
public class GenericAttributes {

	private final Logger k = LogManager.getLogger();
	@Autowired
	SpigotConfig spigotCfg;

	// Spigot start
	public IAttribute maxHealth;
	public IAttribute FOLLOW_RANGE;
	public IAttribute c;
	public IAttribute MOVEMENT_SPEED;
	public IAttribute e;
	public IAttribute ATTACK_DAMAGE;
	public IAttribute g;
	public IAttribute h;
	public IAttribute i;
	public IAttribute j;
	// Spigot end
	@PostConstruct
	public void postConstruct() {
		this.maxHealth = (new AttributeRanged((IAttribute) null, "generic.maxHealth", 20.0D, 0.0D, spigotCfg.getAttributes().getMaxHealth()))
				.a("Max Health").a(true);
		this.FOLLOW_RANGE = (new AttributeRanged((IAttribute) null, "generic.followRange", 32.0D, 0.0D, 2048.0D)).a("Follow Range");
		this.c = (new AttributeRanged((IAttribute) null, "generic.knockbackResistance", 0.0D, 0.0D, 1.0D)).a("Knockback Resistance");
		this.MOVEMENT_SPEED = (new AttributeRanged((IAttribute) null, "generic.movementSpeed", 0.699999988079071D, 0.0D,
				spigotCfg.getAttributes().getMovementSpeed())).a("Movement Speed").a(true);
		this.e = (new AttributeRanged((IAttribute) null, "generic.flyingSpeed", 0.4000000059604645D, 0.0D, 1024.0D)).a("Flying Speed").a(true);
		this.ATTACK_DAMAGE = new AttributeRanged((IAttribute) null, "generic.attackDamage", 2.0D, 0.0D, spigotCfg.getAttributes().getAttackDamage());
		this.g = (new AttributeRanged((IAttribute) null, "generic.attackSpeed", 4.0D, 0.0D, 1024.0D)).a(true);
		this.h = (new AttributeRanged((IAttribute) null, "generic.armor", 0.0D, 0.0D, 30.0D)).a(true);
		this.i = (new AttributeRanged((IAttribute) null, "generic.armorToughness", 0.0D, 0.0D, 20.0D)).a(true);
		this.j = (new AttributeRanged((IAttribute) null, "generic.luck", 0.0D, -1024.0D, 1024.0D)).a(true);

	}
	public NBTTagList a(AttributeMapBase attributemapbase) {
		NBTTagList nbttaglist = new NBTTagList();
		Iterator iterator = attributemapbase.a().iterator();

		while (iterator.hasNext()) {
			AttributeInstance attributeinstance = (AttributeInstance) iterator.next();

			nbttaglist.add(a(attributeinstance));
		}

		return nbttaglist;
	}

	private NBTTagCompound a(AttributeInstance attributeinstance) {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		IAttribute iattribute = attributeinstance.getAttribute();

		nbttagcompound.setString("Name", iattribute.getName());
		nbttagcompound.setDouble("Base", attributeinstance.b());
		Collection collection = attributeinstance.c();

		if (collection != null && !collection.isEmpty()) {
			NBTTagList nbttaglist = new NBTTagList();
			Iterator iterator = collection.iterator();

			while (iterator.hasNext()) {
				AttributeModifier attributemodifier = (AttributeModifier) iterator.next();

				if (attributemodifier.e()) {
					nbttaglist.add(a(attributemodifier));
				}
			}

			nbttagcompound.set("Modifiers", nbttaglist);
		}

		return nbttagcompound;
	}

	public NBTTagCompound a(AttributeModifier attributemodifier) {
		NBTTagCompound nbttagcompound = new NBTTagCompound();

		nbttagcompound.setString("Name", attributemodifier.b());
		nbttagcompound.setDouble("Amount", attributemodifier.d());
		nbttagcompound.setInt("Operation", attributemodifier.c());
		nbttagcompound.a("UUID", attributemodifier.a());
		return nbttagcompound;
	}

	public void a(AttributeMapBase attributemapbase, NBTTagList nbttaglist) {
		for (int i = 0; i < nbttaglist.size(); ++i) {
			NBTTagCompound nbttagcompound = nbttaglist.get(i);
			AttributeInstance attributeinstance = attributemapbase.a(nbttagcompound.getString("Name"));

			if (attributeinstance == null) {
				k.warn("Ignoring unknown attribute \'{}\'", nbttagcompound.getString("Name"));
			} else {
				a(attributeinstance, nbttagcompound);
			}
		}

	}

	private void a(AttributeInstance attributeinstance, NBTTagCompound nbttagcompound) {
		attributeinstance.setValue(nbttagcompound.getDouble("Base"));
		if (nbttagcompound.hasKeyOfType("Modifiers", 9)) {
			NBTTagList nbttaglist = nbttagcompound.getList("Modifiers", 10);

			for (int i = 0; i < nbttaglist.size(); ++i) {
				AttributeModifier attributemodifier = a(nbttaglist.get(i));

				if (attributemodifier != null) {
					AttributeModifier attributemodifier1 = attributeinstance.a(attributemodifier.a());

					if (attributemodifier1 != null) {
						attributeinstance.c(attributemodifier1);
					}

					attributeinstance.b(attributemodifier);
				}
			}
		}

	}

	@Nullable
	public AttributeModifier a(NBTTagCompound nbttagcompound) {
		UUID uuid = nbttagcompound.a("UUID");

		try {
			return new AttributeModifier(uuid, nbttagcompound.getString("Name"), nbttagcompound.getDouble("Amount"),
					nbttagcompound.getInt("Operation"));
		} catch (Exception exception) {
			k.warn("Unable to create attribute: {}", exception.getMessage());
			return null;
		}
	}
}
