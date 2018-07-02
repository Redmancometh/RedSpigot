package net.mcavenue.redspigot.registries;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;

import net.minecraft.server.*;
import net.minecraft.server.RegistryMaterials;

public class ItemRegistry extends RegistryMaterials<MinecraftKey, Item> {
	@Autowired
	private BlockItemRegistry blockItems;
	@Autowired
	private Blocks blocks;
	@Autowired
	private BlockRegistry blockRegistry;
	// TODON: Autowire/Bean
	private final IRegistry<MinecraftKey, IDynamicTexture> textureMap = new RegistrySimple();

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

	public void t() {
		a(blocks.AIR, (Item) (new ItemAir(blocks.AIR)));
		a(blocks.STONE, (new ItemMultiTexture(blocks.STONE, blocks.STONE, new ItemMultiTexture.a() {
			public String a(ItemStack itemstack) {
				return BlockStone.EnumStoneVariant.a(itemstack.getData()).d();
			}
		})).c("stone"));
		a((Block) blocks.GRASS, (Item) (new ItemWithAuxData(blocks.GRASS, false)));
		a(blocks.DIRT, (new ItemMultiTexture(blocks.DIRT, blocks.DIRT, new ItemMultiTexture.a() {
			public String a(ItemStack itemstack) {
				return BlockDirt.EnumDirtVariant.a(itemstack.getData()).c();
			}
		})).c("dirt"));
		b(blocks.COBBLESTONE);
		a(blocks.PLANKS, (new ItemMultiTexture(blocks.PLANKS, blocks.PLANKS, new ItemMultiTexture.a() {
			public String a(ItemStack itemstack) {
				return BlockWood.EnumLogVariant.a(itemstack.getData()).d();
			}
		})).c("wood"));
		a(blocks.SAPLING, (new ItemMultiTexture(blocks.SAPLING, blocks.SAPLING, new ItemMultiTexture.a() {
			public String a(ItemStack itemstack) {
				return BlockWood.EnumLogVariant.a(itemstack.getData()).d();
			}
		})).c("sapling"));
		b(blocks.BEDROCK);
		a((Block) blocks.SAND, (new ItemMultiTexture(blocks.SAND, blocks.SAND, new ItemMultiTexture.a() {
			public String a(ItemStack itemstack) {
				return BlockSand.EnumSandVariant.a(itemstack.getData()).e();
			}
		})).c("sand"));
		b(blocks.GRAVEL);
		b(blocks.GOLD_ORE);
		b(blocks.IRON_ORE);
		b(blocks.COAL_ORE);
		a(blocks.LOG, (new ItemMultiTexture(blocks.LOG, blocks.LOG, new ItemMultiTexture.a() {
			public String a(ItemStack itemstack) {
				return BlockWood.EnumLogVariant.a(itemstack.getData()).d();
			}
		})).c("log"));
		a(blocks.LOG2, (new ItemMultiTexture(blocks.LOG2, blocks.LOG2, new ItemMultiTexture.a() {
			public String a(ItemStack itemstack) {
				return BlockWood.EnumLogVariant.a(itemstack.getData() + 4).d();
			}
		})).c("log"));
		a((Block) blocks.LEAVES, (new ItemLeaves(blocks.LEAVES)).c("leaves"));
		a((Block) blocks.LEAVES2, (new ItemLeaves(blocks.LEAVES2)).c("leaves"));
		a(blocks.SPONGE, (new ItemMultiTexture(blocks.SPONGE, blocks.SPONGE, new ItemMultiTexture.a() {
			public String a(ItemStack itemstack) {
				return (itemstack.getData() & 1) == 1 ? "wet" : "dry";
			}
		})).c("sponge"));
		b(blocks.GLASS);
		b(blocks.LAPIS_ORE);
		b(blocks.LAPIS_BLOCK);
		b(blocks.DISPENSER);
		a(blocks.SANDSTONE, (new ItemMultiTexture(blocks.SANDSTONE, blocks.SANDSTONE, new ItemMultiTexture.a() {
			public String a(ItemStack itemstack) {
				return BlockSandStone.EnumSandstoneVariant.a(itemstack.getData()).c();
			}
		})).c("sandStone"));
		b(blocks.NOTEBLOCK);
		b(blocks.GOLDEN_RAIL);
		b(blocks.DETECTOR_RAIL);
		a((Block) blocks.STICKY_PISTON, (Item) (new ItemPiston(blocks.STICKY_PISTON)));
		b(blocks.WEB);
		a((Block) blocks.TALLGRASS, (Item) (new ItemWithAuxData(blocks.TALLGRASS, true)).a(new String[]{"shrub", "grass", "fern"}));
		b((Block) blocks.DEADBUSH);
		a((Block) blocks.PISTON, (Item) (new ItemPiston(blocks.PISTON)));
		a(blocks.WOOL, (new ItemCloth(blocks.WOOL)).c("cloth"));
		a((Block) blocks.YELLOW_FLOWER, (new ItemMultiTexture(blocks.YELLOW_FLOWER, blocks.YELLOW_FLOWER, new ItemMultiTexture.a() {
			public String a(ItemStack itemstack) {
				return BlockFlowers.EnumFlowerVarient.a(BlockFlowers.EnumFlowerType.YELLOW, itemstack.getData()).d();
			}
		})).c("flower"));
		a((Block) blocks.RED_FLOWER, (new ItemMultiTexture(blocks.RED_FLOWER, blocks.RED_FLOWER, new ItemMultiTexture.a() {
			public String a(ItemStack itemstack) {
				return BlockFlowers.EnumFlowerVarient.a(BlockFlowers.EnumFlowerType.RED, itemstack.getData()).d();
			}
		})).c("rose"));
		b((Block) blocks.BROWN_MUSHROOM);
		b((Block) blocks.RED_MUSHROOM);
		b(blocks.GOLD_BLOCK);
		b(blocks.IRON_BLOCK);
		a((Block) blocks.STONE_SLAB, (new ItemStep(blocks.STONE_SLAB, blocks.STONE_SLAB, blocks.DOUBLE_STONE_SLAB)).c("stoneSlab"));
		b(blocks.BRICK_BLOCK);
		b(blocks.TNT);
		b(blocks.BOOKSHELF);
		b(blocks.MOSSY_COBBLESTONE);
		b(blocks.OBSIDIAN);
		b(blocks.TORCH);
		b(blocks.END_ROD);
		b(blocks.CHORUS_PLANT);
		b(blocks.CHORUS_FLOWER);
		b(blocks.PURPUR_BLOCK);
		b(blocks.PURPUR_PILLAR);
		b(blocks.PURPUR_STAIRS);
		a((Block) blocks.PURPUR_SLAB, (new ItemStep(blocks.PURPUR_SLAB, blocks.PURPUR_SLAB, blocks.PURPUR_DOUBLE_SLAB)).c("purpurSlab"));
		b(blocks.MOB_SPAWNER);
		b(blocks.OAK_STAIRS);
		b((Block) blocks.CHEST);
		b(blocks.DIAMOND_ORE);
		b(blocks.DIAMOND_BLOCK);
		b(blocks.CRAFTING_TABLE);
		b(blocks.FARMLAND);
		b(blocks.FURNACE);
		b(blocks.LADDER);
		b(blocks.RAIL);
		b(blocks.STONE_STAIRS);
		b(blocks.LEVER);
		b(blocks.STONE_PRESSURE_PLATE);
		b(blocks.WOODEN_PRESSURE_PLATE);
		b(blocks.REDSTONE_ORE);
		b(blocks.REDSTONE_TORCH);
		b(blocks.STONE_BUTTON);
		a(blocks.SNOW_LAYER, (Item) (new ItemSnow(blocks.SNOW_LAYER)));
		b(blocks.ICE);
		b(blocks.SNOW);
		b((Block) blocks.CACTUS);
		b(blocks.CLAY);
		b(blocks.JUKEBOX);
		b(blocks.FENCE);
		b(blocks.SPRUCE_FENCE);
		b(blocks.BIRCH_FENCE);
		b(blocks.JUNGLE_FENCE);
		b(blocks.DARK_OAK_FENCE);
		b(blocks.ACACIA_FENCE);
		b(blocks.PUMPKIN);
		b(blocks.NETHERRACK);
		b(blocks.SOUL_SAND);
		b(blocks.GLOWSTONE);
		b(blocks.LIT_PUMPKIN);
		b(blocks.TRAPDOOR);
		a(blocks.MONSTER_EGG, (new ItemMultiTexture(blocks.MONSTER_EGG, blocks.MONSTER_EGG, new ItemMultiTexture.a() {
			public String a(ItemStack itemstack) {
				return BlockMonsterEggs.EnumMonsterEggVarient.a(itemstack.getData()).c();
			}
		})).c("monsterStoneEgg"));
		a(blocks.STONEBRICK, (new ItemMultiTexture(blocks.STONEBRICK, blocks.STONEBRICK, new ItemMultiTexture.a() {
			public String a(ItemStack itemstack) {
				return BlockSmoothBrick.EnumStonebrickType.a(itemstack.getData()).c();
			}
		})).c("stonebricksmooth"));
		b(blocks.BROWN_MUSHROOM_BLOCK);
		b(blocks.RED_MUSHROOM_BLOCK);
		b(blocks.IRON_BARS);
		b(blocks.GLASS_PANE);
		b(blocks.MELON_BLOCK);
		a(blocks.VINE, (Item) (new ItemWithAuxData(blocks.VINE, false)));
		b(blocks.FENCE_GATE);
		b(blocks.SPRUCE_FENCE_GATE);
		b(blocks.BIRCH_FENCE_GATE);
		b(blocks.JUNGLE_FENCE_GATE);
		b(blocks.DARK_OAK_FENCE_GATE);
		b(blocks.ACACIA_FENCE_GATE);
		b(blocks.BRICK_STAIRS);
		b(blocks.STONE_BRICK_STAIRS);
		b((Block) blocks.MYCELIUM);
		a(blocks.WATERLILY, (Item) (new ItemWaterLily(blocks.WATERLILY)));
		b(blocks.NETHER_BRICK);
		b(blocks.NETHER_BRICK_FENCE);
		b(blocks.NETHER_BRICK_STAIRS);
		b(blocks.ENCHANTING_TABLE);
		b(blocks.END_PORTAL_FRAME);
		b(blocks.END_STONE);
		b(blocks.END_BRICKS);
		b(blocks.DRAGON_EGG);
		b(blocks.REDSTONE_LAMP);
		a((Block) blocks.WOODEN_SLAB, (new ItemStep(blocks.WOODEN_SLAB, blocks.WOODEN_SLAB, blocks.DOUBLE_WOODEN_SLAB)).c("woodSlab"));
		b(blocks.SANDSTONE_STAIRS);
		b(blocks.EMERALD_ORE);
		b(blocks.ENDER_CHEST);
		b((Block) blocks.TRIPWIRE_HOOK);
		b(blocks.EMERALD_BLOCK);
		b(blocks.SPRUCE_STAIRS);
		b(blocks.BIRCH_STAIRS);
		b(blocks.JUNGLE_STAIRS);
		b(blocks.COMMAND_BLOCK);
		b((Block) blocks.BEACON);
		a(blocks.COBBLESTONE_WALL, (new ItemMultiTexture(blocks.COBBLESTONE_WALL, blocks.COBBLESTONE_WALL, new ItemMultiTexture.a() {
			public String a(ItemStack itemstack) {
				return BlockCobbleWall.EnumCobbleVariant.a(itemstack.getData()).c();
			}
		})).c("cobbleWall"));
		b(blocks.WOODEN_BUTTON);
		a(blocks.ANVIL, (new ItemAnvil(blocks.ANVIL)).c("anvil"));
		b(blocks.TRAPPED_CHEST);
		b(blocks.LIGHT_WEIGHTED_PRESSURE_PLATE);
		b(blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);
		b((Block) blocks.DAYLIGHT_DETECTOR);
		b(blocks.REDSTONE_BLOCK);
		b(blocks.QUARTZ_ORE);
		b((Block) blocks.HOPPER);
		a(blocks.QUARTZ_BLOCK,
				(new ItemMultiTexture(blocks.QUARTZ_BLOCK, blocks.QUARTZ_BLOCK, new String[]{"default", "chiseled", "lines"})).c("quartzBlock"));
		b(blocks.QUARTZ_STAIRS);
		b(blocks.ACTIVATOR_RAIL);
		b(blocks.DROPPER);
		a(blocks.STAINED_HARDENED_CLAY, (new ItemCloth(blocks.STAINED_HARDENED_CLAY)).c("clayHardenedStained"));
		b(blocks.BARRIER);
		b(blocks.IRON_TRAPDOOR);
		b(blocks.HAY_BLOCK);
		a(blocks.CARPET, (new ItemCloth(blocks.CARPET)).c("woolCarpet"));
		b(blocks.HARDENED_CLAY);
		b(blocks.COAL_BLOCK);
		b(blocks.PACKED_ICE);
		b(blocks.ACACIA_STAIRS);
		b(blocks.DARK_OAK_STAIRS);
		b(blocks.SLIME);
		b(blocks.GRASS_PATH);
		a((Block) blocks.DOUBLE_PLANT, (new ItemMultiTexture(blocks.DOUBLE_PLANT, blocks.DOUBLE_PLANT, new ItemMultiTexture.a() {
			public String a(ItemStack itemstack) {
				return BlockTallPlant.EnumTallFlowerVariants.a(itemstack.getData()).c();
			}
		})).c("doublePlant"));
		a((Block) blocks.STAINED_GLASS, (new ItemCloth(blocks.STAINED_GLASS)).c("stainedGlass"));
		a((Block) blocks.STAINED_GLASS_PANE, (new ItemCloth(blocks.STAINED_GLASS_PANE)).c("stainedGlassPane"));
		a(blocks.PRISMARINE, (new ItemMultiTexture(blocks.PRISMARINE, blocks.PRISMARINE, new ItemMultiTexture.a() {
			public String a(ItemStack itemstack) {
				return BlockPrismarine.EnumPrismarineVariant.a(itemstack.getData()).c();
			}
		})).c("prismarine"));
		b(blocks.SEA_LANTERN);
		a(blocks.RED_SANDSTONE, (new ItemMultiTexture(blocks.RED_SANDSTONE, blocks.RED_SANDSTONE, new ItemMultiTexture.a() {
			public String a(ItemStack itemstack) {
				return BlockRedSandstone.EnumRedSandstoneVariant.a(itemstack.getData()).c();
			}
		})).c("redSandStone"));
		b(blocks.RED_SANDSTONE_STAIRS);
		a((Block) blocks.STONE_SLAB2, (new ItemStep(blocks.STONE_SLAB2, blocks.STONE_SLAB2, blocks.DOUBLE_STONE_SLAB2)).c("stoneSlab2"));
		b(blocks.dc);
		b(blocks.dd);
		b(blocks.df);
		b(blocks.dg);
		b(blocks.dh);
		b(blocks.di);
		b(blocks.dj);
		b(blocks.dk);
		a(blocks.WHITE_SHULKER_BOX, (Item) (new ItemShulkerBox(blocks.WHITE_SHULKER_BOX)));
		a(blocks.dm, (Item) (new ItemShulkerBox(blocks.dm)));
		a(blocks.dn, (Item) (new ItemShulkerBox(blocks.dn)));
		a(blocks.LIGHT_BLUE_SHULKER_BOX, (Item) (new ItemShulkerBox(blocks.LIGHT_BLUE_SHULKER_BOX)));
		a(blocks.dp, (Item) (new ItemShulkerBox(blocks.dp)));
		a(blocks.dq, (Item) (new ItemShulkerBox(blocks.dq)));
		a(blocks.dr, (Item) (new ItemShulkerBox(blocks.dr)));
		a(blocks.ds, (Item) (new ItemShulkerBox(blocks.ds)));
		a(blocks.dt, (Item) (new ItemShulkerBox(blocks.dt)));
		a(blocks.du, (Item) (new ItemShulkerBox(blocks.du)));
		a(blocks.dv, (Item) (new ItemShulkerBox(blocks.dv)));
		a(blocks.dw, (Item) (new ItemShulkerBox(blocks.dw)));
		a(blocks.dx, (Item) (new ItemShulkerBox(blocks.dx)));
		a(blocks.dy, (Item) (new ItemShulkerBox(blocks.dy)));
		a(blocks.dz, (Item) (new ItemShulkerBox(blocks.dz)));
		a(blocks.dA, (Item) (new ItemShulkerBox(blocks.dA)));
		b(blocks.dB);
		b(blocks.dC);
		b(blocks.dD);
		b(blocks.dE);
		b(blocks.dF);
		b(blocks.dG);
		b(blocks.dH);
		b(blocks.dI);
		b(blocks.dJ);
		b(blocks.dK);
		b(blocks.dL);
		b(blocks.dM);
		b(blocks.dN);
		b(blocks.dO);
		b(blocks.dP);
		b(blocks.dQ);
		a(blocks.dR, (new ItemCloth(blocks.dR)).c("concrete"));
		a(blocks.dS, (new ItemCloth(blocks.dS)).c("concrete_powder"));
		b(blocks.STRUCTURE_BLOCK);
		a(256, "iron_shovel", (new ItemSpade(Item.EnumToolMaterial.IRON)).c("shovelIron"));
		a(257, "iron_pickaxe", (new ItemPickaxe(Item.EnumToolMaterial.IRON)).c("pickaxeIron"));
		a(258, "iron_axe", (new ItemAxe(Item.EnumToolMaterial.IRON)).c("hatchetIron"));
		a(259, "flint_and_steel", (new ItemFlintAndSteel()).c("flintAndSteel"));
		a(260, "apple", (new ItemFood(4, 0.3F, false)).c("apple"));
		a(261, "bow", (new ItemBow()).c("bow"));
		a(262, "arrow", (new ItemArrow()).c("arrow"));
		a(263, "coal", (new ItemCoal()).c("coal"));
		a(264, "diamond", (new Item()).c("diamond").b(CreativeModeTab.l));
		a(265, "iron_ingot", (new Item()).c("ingotIron").b(CreativeModeTab.l));
		a(266, "gold_ingot", (new Item()).c("ingotGold").b(CreativeModeTab.l));
		a(267, "iron_sword", (new ItemSword(Item.EnumToolMaterial.IRON)).c("swordIron"));
		a(268, "wooden_sword", (new ItemSword(Item.EnumToolMaterial.WOOD)).c("swordWood"));
		a(269, "wooden_shovel", (new ItemSpade(Item.EnumToolMaterial.WOOD)).c("shovelWood"));
		a(270, "wooden_pickaxe", (new ItemPickaxe(Item.EnumToolMaterial.WOOD)).c("pickaxeWood"));
		a(271, "wooden_axe", (new ItemAxe(Item.EnumToolMaterial.WOOD)).c("hatchetWood"));
		a(272, "stone_sword", (new ItemSword(Item.EnumToolMaterial.STONE)).c("swordStone"));
		a(273, "stone_shovel", (new ItemSpade(Item.EnumToolMaterial.STONE)).c("shovelStone"));
		a(274, "stone_pickaxe", (new ItemPickaxe(Item.EnumToolMaterial.STONE)).c("pickaxeStone"));
		a(275, "stone_axe", (new ItemAxe(Item.EnumToolMaterial.STONE)).c("hatchetStone"));
		a(276, "diamond_sword", (new ItemSword(Item.EnumToolMaterial.DIAMOND)).c("swordDiamond"));
		a(277, "diamond_shovel", (new ItemSpade(Item.EnumToolMaterial.DIAMOND)).c("shovelDiamond"));
		a(278, "diamond_pickaxe", (new ItemPickaxe(Item.EnumToolMaterial.DIAMOND)).c("pickaxeDiamond"));
		a(279, "diamond_axe", (new ItemAxe(Item.EnumToolMaterial.DIAMOND)).c("hatchetDiamond"));
		a(280, "stick", (new Item()).n().c("stick").b(CreativeModeTab.l));
		a(281, "bowl", (new Item()).c("bowl").b(CreativeModeTab.l));
		a(282, "mushroom_stew", (new ItemSoup(6)).c("mushroomStew"));
		a(283, "golden_sword", (new ItemSword(Item.EnumToolMaterial.GOLD)).c("swordGold"));
		a(284, "golden_shovel", (new ItemSpade(Item.EnumToolMaterial.GOLD)).c("shovelGold"));
		a(285, "golden_pickaxe", (new ItemPickaxe(Item.EnumToolMaterial.GOLD)).c("pickaxeGold"));
		a(286, "golden_axe", (new ItemAxe(Item.EnumToolMaterial.GOLD)).c("hatchetGold"));
		a(287, "string", (new ItemReed(blocks.TRIPWIRE)).c("string").b(CreativeModeTab.l));
		a(288, "feather", (new Item()).c("feather").b(CreativeModeTab.l));
		a(289, "gunpowder", (new Item()).c("sulphur").b(CreativeModeTab.l));
		a(290, "wooden_hoe", (new ItemHoe(Item.EnumToolMaterial.WOOD)).c("hoeWood"));
		a(291, "stone_hoe", (new ItemHoe(Item.EnumToolMaterial.STONE)).c("hoeStone"));
		a(292, "iron_hoe", (new ItemHoe(Item.EnumToolMaterial.IRON)).c("hoeIron"));
		a(293, "diamond_hoe", (new ItemHoe(Item.EnumToolMaterial.DIAMOND)).c("hoeDiamond"));
		a(294, "golden_hoe", (new ItemHoe(Item.EnumToolMaterial.GOLD)).c("hoeGold"));
		a(295, "wheat_seeds", (new ItemSeeds(blocks.WHEAT, blocks.FARMLAND)).c("seeds"));
		a(296, "wheat", (new Item()).c("wheat").b(CreativeModeTab.l));
		a(297, "bread", (new ItemFood(5, 0.6F, false)).c("bread"));
		a(298, "leather_helmet", (new ItemArmor(ItemArmor.EnumArmorMaterial.LEATHER, 0, EnumItemSlot.HEAD)).c("helmetCloth"));
		a(299, "leather_chestplate", (new ItemArmor(ItemArmor.EnumArmorMaterial.LEATHER, 0, EnumItemSlot.CHEST)).c("chestplateCloth"));
		a(300, "leather_leggings", (new ItemArmor(ItemArmor.EnumArmorMaterial.LEATHER, 0, EnumItemSlot.LEGS)).c("leggingsCloth"));
		a(301, "leather_boots", (new ItemArmor(ItemArmor.EnumArmorMaterial.LEATHER, 0, EnumItemSlot.FEET)).c("bootsCloth"));
		a(302, "chainmail_helmet", (new ItemArmor(ItemArmor.EnumArmorMaterial.CHAIN, 1, EnumItemSlot.HEAD)).c("helmetChain"));
		a(303, "chainmail_chestplate", (new ItemArmor(ItemArmor.EnumArmorMaterial.CHAIN, 1, EnumItemSlot.CHEST)).c("chestplateChain"));
		a(304, "chainmail_leggings", (new ItemArmor(ItemArmor.EnumArmorMaterial.CHAIN, 1, EnumItemSlot.LEGS)).c("leggingsChain"));
		a(305, "chainmail_boots", (new ItemArmor(ItemArmor.EnumArmorMaterial.CHAIN, 1, EnumItemSlot.FEET)).c("bootsChain"));
		a(306, "iron_helmet", (new ItemArmor(ItemArmor.EnumArmorMaterial.IRON, 2, EnumItemSlot.HEAD)).c("helmetIron"));
		a(307, "iron_chestplate", (new ItemArmor(ItemArmor.EnumArmorMaterial.IRON, 2, EnumItemSlot.CHEST)).c("chestplateIron"));
		a(308, "iron_leggings", (new ItemArmor(ItemArmor.EnumArmorMaterial.IRON, 2, EnumItemSlot.LEGS)).c("leggingsIron"));
		a(309, "iron_boots", (new ItemArmor(ItemArmor.EnumArmorMaterial.IRON, 2, EnumItemSlot.FEET)).c("bootsIron"));
		a(310, "diamond_helmet", (new ItemArmor(ItemArmor.EnumArmorMaterial.DIAMOND, 3, EnumItemSlot.HEAD)).c("helmetDiamond"));
		a(311, "diamond_chestplate", (new ItemArmor(ItemArmor.EnumArmorMaterial.DIAMOND, 3, EnumItemSlot.CHEST)).c("chestplateDiamond"));
		a(312, "diamond_leggings", (new ItemArmor(ItemArmor.EnumArmorMaterial.DIAMOND, 3, EnumItemSlot.LEGS)).c("leggingsDiamond"));
		a(313, "diamond_boots", (new ItemArmor(ItemArmor.EnumArmorMaterial.DIAMOND, 3, EnumItemSlot.FEET)).c("bootsDiamond"));
		a(314, "golden_helmet", (new ItemArmor(ItemArmor.EnumArmorMaterial.GOLD, 4, EnumItemSlot.HEAD)).c("helmetGold"));
		a(315, "golden_chestplate", (new ItemArmor(ItemArmor.EnumArmorMaterial.GOLD, 4, EnumItemSlot.CHEST)).c("chestplateGold"));
		a(316, "golden_leggings", (new ItemArmor(ItemArmor.EnumArmorMaterial.GOLD, 4, EnumItemSlot.LEGS)).c("leggingsGold"));
		a(317, "golden_boots", (new ItemArmor(ItemArmor.EnumArmorMaterial.GOLD, 4, EnumItemSlot.FEET)).c("bootsGold"));
		a(318, "flint", (new Item()).c("flint").b(CreativeModeTab.l));
		a(319, "porkchop", (new ItemFood(3, 0.3F, true)).c("porkchopRaw"));
		a(320, "cooked_porkchop", (new ItemFood(8, 0.8F, true)).c("porkchopCooked"));
		a(321, "painting", (new ItemHanging(EntityPainting.class)).c("painting"));
		a(322, "golden_apple", (new ItemGoldenApple(4, 1.2F, false)).h().c("appleGold"));
		a(323, "sign", (new ItemSign()).c("sign"));
		a(324, "wooden_door", (new ItemDoor(blocks.WOODEN_DOOR)).c("doorOak"));
		Item item = (new ItemBucket(blocks.AIR)).c("bucket").d(16);

		a(325, "bucket", item);
		a(326, "water_bucket", (new ItemBucket(blocks.FLOWING_WATER)).c("bucketWater").b(item));
		a(327, "lava_bucket", (new ItemBucket(blocks.FLOWING_LAVA)).c("bucketLava").b(item));
		a(328, "minecart", (new ItemMinecart(EntityMinecartAbstract.EnumMinecartType.RIDEABLE)).c("minecart"));
		a(329, "saddle", (new ItemSaddle()).c("saddle"));
		a(330, "iron_door", (new ItemDoor(blocks.IRON_DOOR)).c("doorIron"));
		a(331, "redstone", (new ItemRedstone()).c("redstone"));
		a(332, "snowball", (new ItemSnowball()).c("snowball"));
		a(333, "boat", new ItemBoat(EntityBoat.EnumBoatType.OAK));
		a(334, "leather", (new Item()).c("leather").b(CreativeModeTab.l));
		a(335, "milk_bucket", (new ItemMilkBucket()).c("milk").b(item));
		a(336, "brick", (new Item()).c("brick").b(CreativeModeTab.l));
		a(337, "clay_ball", (new Item()).c("clay").b(CreativeModeTab.l));
		a(338, "reeds", (new ItemReed(blocks.REEDS)).c("reeds").b(CreativeModeTab.l));
		a(339, "paper", (new Item()).c("paper").b(CreativeModeTab.f));
		a(340, "book", (new ItemBook()).c("book").b(CreativeModeTab.f));
		a(341, "slime_ball", (new Item()).c("slimeball").b(CreativeModeTab.f));
		a(342, "chest_minecart", (new ItemMinecart(EntityMinecartAbstract.EnumMinecartType.CHEST)).c("minecartChest"));
		a(343, "furnace_minecart", (new ItemMinecart(EntityMinecartAbstract.EnumMinecartType.FURNACE)).c("minecartFurnace"));
		a(344, "egg", (new ItemEgg()).c("egg"));
		a(345, "compass", (new ItemCompass()).c("compass").b(CreativeModeTab.i));
		a(346, "fishing_rod", (new ItemFishingRod()).c("fishingRod"));
		a(347, "clock", (new ItemClock()).c("clock").b(CreativeModeTab.i));
		a(348, "glowstone_dust", (new Item()).c("yellowDust").b(CreativeModeTab.l));
		a(349, "fish", (new ItemFish(false)).c("fish").a(true));
		a(350, "cooked_fish", (new ItemFish(true)).c("fish").a(true));
		a(351, "dye", (new ItemDye()).c("dyePowder"));
		a(352, "bone", (new Item()).c("bone").n().b(CreativeModeTab.f));
		a(353, "sugar", (new Item()).c("sugar").b(CreativeModeTab.l));
		a(354, "cake", (new ItemReed(blocks.CAKE)).d(1).c("cake").b(CreativeModeTab.h));
		a(355, "bed", (new ItemBed()).d(1).c("bed"));
		a(356, "repeater", (new ItemReed(blocks.UNPOWERED_REPEATER)).c("diode").b(CreativeModeTab.d));
		a(357, "cookie", (new ItemFood(2, 0.1F, false)).c("cookie"));
		a(358, "filled_map", (new ItemWorldMap()).c("map"));
		a(359, "shears", (new ItemShears()).c("shears"));
		a(360, "melon", (new ItemFood(2, 0.3F, false)).c("melon"));
		a(361, "pumpkin_seeds", (new ItemSeeds(blocks.PUMPKIN_STEM, blocks.FARMLAND)).c("seeds_pumpkin"));
		a(362, "melon_seeds", (new ItemSeeds(blocks.MELON_STEM, blocks.FARMLAND)).c("seeds_melon"));
		a(363, "beef", (new ItemFood(3, 0.3F, true)).c("beefRaw"));
		a(364, "cooked_beef", (new ItemFood(8, 0.8F, true)).c("beefCooked"));
		a(365, "chicken", (new ItemFood(2, 0.3F, true)).a(new MobEffect(MobEffects.HUNGER, 600, 0), 0.3F).c("chickenRaw"));
		a(366, "cooked_chicken", (new ItemFood(6, 0.6F, true)).c("chickenCooked"));
		a(367, "rotten_flesh", (new ItemFood(4, 0.1F, true)).a(new MobEffect(MobEffects.HUNGER, 600, 0), 0.8F).c("rottenFlesh"));
		a(368, "ender_pearl", (new ItemEnderPearl()).c("enderPearl"));
		a(369, "blaze_rod", (new Item()).c("blazeRod").b(CreativeModeTab.l).n());
		a(370, "ghast_tear", (new Item()).c("ghastTear").b(CreativeModeTab.k));
		a(371, "gold_nugget", (new Item()).c("goldNugget").b(CreativeModeTab.l));
		a(372, "nether_wart", (new ItemSeeds(blocks.NETHER_WART, blocks.SOUL_SAND)).c("netherStalkSeeds"));
		a(373, "potion", (new ItemPotion()).c("potion"));
		Item item1 = (new ItemGlassBottle()).c("glassBottle");

		a(374, "glass_bottle", item1);
		a(375, "spider_eye", (new ItemFood(2, 0.8F, false)).a(new MobEffect(MobEffects.POISON, 100, 0), 1.0F).c("spiderEye"));
		a(376, "fermented_spider_eye", (new Item()).c("fermentedSpiderEye").b(CreativeModeTab.k));
		a(377, "blaze_powder", (new Item()).c("blazePowder").b(CreativeModeTab.k));
		a(378, "magma_cream", (new Item()).c("magmaCream").b(CreativeModeTab.k));
		a(379, "brewing_stand", (new ItemReed(blocks.BREWING_STAND)).c("brewingStand").b(CreativeModeTab.k));
		a(380, "cauldron", (new ItemReed(blocks.cauldron)).c("cauldron").b(CreativeModeTab.k));
		a(381, "ender_eye", (new ItemEnderEye()).c("eyeOfEnder"));
		a(382, "speckled_melon", (new Item()).c("speckledMelon").b(CreativeModeTab.k));
		a(383, "spawn_egg", (new ItemMonsterEgg()).c("monsterPlacer"));
		a(384, "experience_bottle", (new ItemExpBottle()).c("expBottle"));
		a(385, "fire_charge", (new ItemFireball()).c("fireball"));
		a(386, "writable_book", (new ItemBookAndQuill()).c("writingBook").b(CreativeModeTab.f));
		a(387, "written_book", (new ItemWrittenBook()).c("writtenBook").d(16));
		a(388, "emerald", (new Item()).c("emerald").b(CreativeModeTab.l));
		a(389, "item_frame", (new ItemHanging(EntityItemFrame.class)).c("frame"));
		a(390, "flower_pot", (new ItemReed(blocks.FLOWER_POT)).c("flowerPot").b(CreativeModeTab.c));
		a(391, "carrot", (new ItemSeedFood(3, 0.6F, blocks.CARROTS, blocks.FARMLAND)).c("carrots"));
		a(392, "potato", (new ItemSeedFood(1, 0.3F, blocks.POTATOES, blocks.FARMLAND)).c("potato"));
		a(393, "baked_potato", (new ItemFood(5, 0.6F, false)).c("potatoBaked"));
		a(394, "poisonous_potato", (new ItemFood(2, 0.3F, false)).a(new MobEffect(MobEffects.POISON, 100, 0), 0.6F).c("potatoPoisonous"));
		a(395, "map", (new ItemMapEmpty()).c("emptyMap"));
		a(396, "golden_carrot", (new ItemFood(6, 1.2F, false)).c("carrotGolden").b(CreativeModeTab.k));
		a(397, "skull", (new ItemSkull()).c("skull"));
		a(398, "carrot_on_a_stick", (new ItemCarrotStick()).c("carrotOnAStick"));
		a(399, "nether_star", (new ItemNetherStar()).c("netherStar").b(CreativeModeTab.l));
		a(400, "pumpkin_pie", (new ItemFood(8, 0.3F, false)).c("pumpkinPie").b(CreativeModeTab.h));
		a(401, "fireworks", (new ItemFireworks()).c("fireworks"));
		a(402, "firework_charge", (new ItemFireworksCharge()).c("fireworksCharge").b(CreativeModeTab.f));
		a(403, "enchanted_book", (new ItemEnchantedBook()).d(1).c("enchantedBook"));
		a(404, "comparator", (new ItemReed(blocks.UNPOWERED_COMPARATOR)).c("comparator").b(CreativeModeTab.d));
		a(405, "netherbrick", (new Item()).c("netherbrick").b(CreativeModeTab.l));
		a(406, "quartz", (new Item()).c("netherquartz").b(CreativeModeTab.l));
		a(407, "tnt_minecart", (new ItemMinecart(EntityMinecartAbstract.EnumMinecartType.TNT)).c("minecartTnt"));
		a(408, "hopper_minecart", (new ItemMinecart(EntityMinecartAbstract.EnumMinecartType.HOPPER)).c("minecartHopper"));
		a(409, "prismarine_shard", (new Item()).c("prismarineShard").b(CreativeModeTab.l));
		a(410, "prismarine_crystals", (new Item()).c("prismarineCrystals").b(CreativeModeTab.l));
		a(411, "rabbit", (new ItemFood(3, 0.3F, true)).c("rabbitRaw"));
		a(412, "cooked_rabbit", (new ItemFood(5, 0.6F, true)).c("rabbitCooked"));
		a(413, "rabbit_stew", (new ItemSoup(10)).c("rabbitStew"));
		a(414, "rabbit_foot", (new Item()).c("rabbitFoot").b(CreativeModeTab.k));
		a(415, "rabbit_hide", (new Item()).c("rabbitHide").b(CreativeModeTab.l));
		a(416, "armor_stand", (new ItemArmorStand()).c("armorStand").d(16));
		a(417, "iron_horse_armor", (new Item()).c("horsearmormetal").d(1).b(CreativeModeTab.f));
		a(418, "golden_horse_armor", (new Item()).c("horsearmorgold").d(1).b(CreativeModeTab.f));
		a(419, "diamond_horse_armor", (new Item()).c("horsearmordiamond").d(1).b(CreativeModeTab.f));
		a(420, "lead", (new ItemLeash()).c("leash"));
		a(421, "name_tag", (new ItemNameTag()).c("nameTag"));
		a(422, "command_block_minecart",
				(new ItemMinecart(EntityMinecartAbstract.EnumMinecartType.COMMAND_BLOCK)).c("minecartCommandBlock").b((CreativeModeTab) null));
		a(423, "mutton", (new ItemFood(2, 0.3F, true)).c("muttonRaw"));
		a(424, "cooked_mutton", (new ItemFood(6, 0.8F, true)).c("muttonCooked"));
		a(425, "banner", (new ItemBanner()).c("banner"));
		a(426, "end_crystal", new ItemEndCrystal());
		a(427, "spruce_door", (new ItemDoor(blocks.SPRUCE_DOOR)).c("doorSpruce"));
		a(428, "birch_door", (new ItemDoor(blocks.BIRCH_DOOR)).c("doorBirch"));
		a(429, "jungle_door", (new ItemDoor(blocks.JUNGLE_DOOR)).c("doorJungle"));
		a(430, "acacia_door", (new ItemDoor(blocks.ACACIA_DOOR)).c("doorAcacia"));
		a(431, "dark_oak_door", (new ItemDoor(blocks.DARK_OAK_DOOR)).c("doorDarkOak"));
		a(432, "chorus_fruit", (new ItemChorusFruit(4, 0.3F)).h().c("chorusFruit").b(CreativeModeTab.l));
		a(433, "chorus_fruit_popped", (new Item()).c("chorusFruitPopped").b(CreativeModeTab.l));
		a(434, "beetroot", (new ItemFood(1, 0.6F, false)).c("beetroot"));
		a(435, "beetroot_seeds", (new ItemSeeds(blocks.BEETROOT, blocks.FARMLAND)).c("beetroot_seeds"));
		a(436, "beetroot_soup", (new ItemSoup(6)).c("beetroot_soup"));
		a(437, "dragon_breath", (new Item()).b(CreativeModeTab.k).c("dragon_breath").b(item1));
		a(438, "splash_potion", (new ItemSplashPotion()).c("splash_potion"));
		a(439, "spectral_arrow", (new ItemSpectralArrow()).c("spectral_arrow"));
		a(440, "tipped_arrow", (new ItemTippedArrow()).c("tipped_arrow"));
		a(441, "lingering_potion", (new ItemLingeringPotion()).c("lingering_potion"));
		a(442, "shield", (new ItemShield()).c("shield"));
		a(443, "elytra", (new ItemElytra()).c("elytra"));
		a(444, "spruce_boat", new ItemBoat(EntityBoat.EnumBoatType.SPRUCE));
		a(445, "birch_boat", new ItemBoat(EntityBoat.EnumBoatType.BIRCH));
		a(446, "jungle_boat", new ItemBoat(EntityBoat.EnumBoatType.JUNGLE));
		a(447, "acacia_boat", new ItemBoat(EntityBoat.EnumBoatType.ACACIA));
		a(448, "dark_oak_boat", new ItemBoat(EntityBoat.EnumBoatType.DARK_OAK));
		a(449, "totem_of_undying", (new Item()).c("totem").d(1).b(CreativeModeTab.j));
		a(450, "shulker_shell", (new Item()).c("shulkerShell").b(CreativeModeTab.l));
		a(452, "iron_nugget", (new Item()).c("ironNugget").b(CreativeModeTab.l));
		a(453, "knowledge_book", (new ItemKnowledgeBook()).c("knowledgeBook"));
		a(2256, "record_13", (new ItemRecord("13", SoundEffects.gb)).c("record"));
		a(2257, "record_cat", (new ItemRecord("cat", SoundEffects.gd)).c("record"));
		a(2258, "record_blocks", (new ItemRecord("blocks", SoundEffects.gc)).c("record"));
		a(2259, "record_chirp", (new ItemRecord("chirp", SoundEffects.ge)).c("record"));
		a(2260, "record_far", (new ItemRecord("far", SoundEffects.gf)).c("record"));
		a(2261, "record_mall", (new ItemRecord("mall", SoundEffects.gg)).c("record"));
		a(2262, "record_mellohi", (new ItemRecord("mellohi", SoundEffects.gh)).c("record"));
		a(2263, "record_stal", (new ItemRecord("stal", SoundEffects.gi)).c("record"));
		a(2264, "record_strad", (new ItemRecord("strad", SoundEffects.gj)).c("record"));
		a(2265, "record_ward", (new ItemRecord("ward", SoundEffects.gl)).c("record"));
		a(2266, "record_11", (new ItemRecord("11", SoundEffects.ga)).c("record"));
		a(2267, "record_wait", (new ItemRecord("wait", SoundEffects.gk)).c("record"));
	}

	private void a(int i, String s, Item item) {
		a(i, new MinecraftKey(s), item);
	}

	public void a(int i, MinecraftKey minecraftkey, Item item) {
		a(i, minecraftkey, item);
	}

	private void b(Block block) {
		a(block, (Item) (new ItemBlock(block)));
	}

	public final void a(MinecraftKey minecraftkey, IDynamicTexture idynamictexture) {
		this.textureMap.a(minecraftkey, idynamictexture);
	}

	protected void a(Block block, Item item) {
		a(blockRegistry.a(block), (MinecraftKey) blockRegistry.b(block), item);
		blockItems.put(block, item);
	}
}
