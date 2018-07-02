package net.mcavenue.redspigot.registries;

import net.minecraft.server.Block;
import net.minecraft.server.IBlockData;
import net.minecraft.server.MinecraftKey;
import net.minecraft.server.RegistryBlocks;

public class BlockRegistry extends RegistryBlocks<MinecraftKey, Block> {

	public BlockRegistry(MinecraftKey arg0) {
		super(arg0);
	}

	public void registerBlock(int id, String key, Block block) {
		a(id, key, block);
	}

	public int getId(Block block) {
		return a(block); // CraftBukkit - decompile error
	}

	@SuppressWarnings("deprecation")
	public IBlockData getByCombinedId(int i) {
		int j = i & 4095;
		int k = i >> 12 & 15;
		return getById(j).fromLegacyData(k);
	}

	public int getCombinedId(IBlockData iblockdata) {
		Block block = iblockdata.getBlock();
		return getId(block) + (block.toLegacyData(iblockdata) << 12);
	}

	public Block getById(int i) {
		return (Block) getId(i);
	}

	public void a(int i, MinecraftKey minecraftkey, Block block) {
		a(i, minecraftkey, block);
	}

	public void a(int i, String s, Block block) {
		a(i, new MinecraftKey(s), block);
	}

}
