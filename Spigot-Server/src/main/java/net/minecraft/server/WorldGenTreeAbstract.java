package net.minecraft.server;

import java.util.Random;

import org.bukkit.craftbukkit.potion.CraftPotionUtil;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.springframework.beans.factory.annotation.Autowired;

import net.mcavenue.redspigot.registries.BlockRegistry;
import net.mcavenue.redspigot.registries.ItemRegistry;
import net.mcavenue.redspigot.registries.MobEffectRegistry;
import net.minecraft.server.BlockPosition;
import net.minecraft.server.Material;
import net.minecraft.server.WorldGenerator;

public abstract class WorldGenTreeAbstract extends WorldGenerator {
	@Autowired
	protected GenericAttributes attr;
	@Autowired
	protected MobEffectRegistry effectRegistry;
	@Autowired
	protected CraftPotionUtil potions;
	@Autowired
	protected ItemRegistry items;
	@Autowired
	protected BlockRegistry blockRegistry;
	@Autowired
	protected Blocks blocks;
	@Autowired
	protected CraftMagicNumbers magicNumbers;

	public WorldGenTreeAbstract(boolean flag) {
		super(flag);
	}

	protected boolean a(Block block) {
		Material material = block.getBlockData().getMaterial();

		return material == Material.AIR || material == Material.LEAVES || block == blocks.GRASS || block == blocks.DIRT || block == blocks.LOG
				|| block == blocks.LOG2 || block == blocks.SAPLING || block == blocks.VINE;
	}

	protected void a(World world, BlockPosition blockposition) {
		if (world.getType(blockposition).getBlock() != blocks.DIRT) {
			this.a(world, blockposition, blocks.DIRT.getBlockData());
		}

	}
}
