package net.mcavenue.redspigot.registries;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;

import net.minecraft.server.Block;
import net.minecraft.server.Item;
import net.minecraft.server.Items;
import net.minecraft.server.MinecraftKey;
import net.minecraft.server.RegistryMaterials;

public class ItemRegistry extends RegistryMaterials<MinecraftKey, Item> {
	@Autowired
	private BlockItemRegistry blockItems;
	public int getId(Item item) {
		return item == null ? 0 : a(item);
	}

	public Item getById(int i) {
		return (Item) getId(i);
	}

	public Item getItemOf(Block block) {
		Item item = (Item) blockItems.get(block);
		return item == null ? Items.a : item;
	}

	@Nullable
	public Item b(String s) {
		Item item = (Item) get(new MinecraftKey(s));

		if (item == null) {
			try {
				return getById(Integer.parseInt(s));
			} catch (NumberFormatException numberformatexception) {
				;
			}
		}

		return item;
	}
}
