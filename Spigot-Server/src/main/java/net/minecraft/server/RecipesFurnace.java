package net.minecraft.server;

import com.google.common.collect.Maps;

import net.mcavenue.redspigot.registries.ItemRegistry;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

public class RecipesFurnace {
	@Autowired
	private Blocks blocks;
	@Autowired
	private ItemRegistry items;
	private static final RecipesFurnace a = new RecipesFurnace();
	public Map<ItemStack, ItemStack> recipes = Maps.newHashMap();
	private final Map<ItemStack, Float> experience = Maps.newHashMap();
	public Map<ItemStack, ItemStack> customRecipes = Maps.newHashMap(); // CraftBukkit
																		// - add
																		// field
	public Map<ItemStack, Float> customExperience = Maps.newHashMap(); // CraftBukkit
																		// - add
																		// field

	public static RecipesFurnace getInstance() {
		return RecipesFurnace.a;
	}

	public RecipesFurnace() {
		this.registerRecipe(blocks.IRON_ORE, new ItemStack(Items.IRON_INGOT), 0.7F);
		this.registerRecipe(blocks.GOLD_ORE, new ItemStack(Items.GOLD_INGOT), 1.0F);
		this.registerRecipe(blocks.DIAMOND_ORE, new ItemStack(Items.DIAMOND), 1.0F);
		this.registerRecipe(blocks.SAND, new ItemStack(blocks.GLASS), 0.1F);
		this.a(Items.PORKCHOP, new ItemStack(Items.COOKED_PORKCHOP), 0.35F);
		this.a(Items.BEEF, new ItemStack(Items.COOKED_BEEF), 0.35F);
		this.a(Items.CHICKEN, new ItemStack(Items.COOKED_CHICKEN), 0.35F);
		this.a(Items.RABBIT, new ItemStack(Items.COOKED_RABBIT), 0.35F);
		this.a(Items.MUTTON, new ItemStack(Items.COOKED_MUTTON), 0.35F);
		this.registerRecipe(blocks.COBBLESTONE, new ItemStack(blocks.STONE), 0.1F);
		this.a(new ItemStack(blocks.STONEBRICK, 1, BlockSmoothBrick.b), new ItemStack(blocks.STONEBRICK, 1, BlockSmoothBrick.d), 0.1F);
		this.a(Items.CLAY_BALL, new ItemStack(Items.BRICK), 0.3F);
		this.registerRecipe(blocks.CLAY, new ItemStack(blocks.HARDENED_CLAY), 0.35F);
		this.registerRecipe(blocks.CACTUS, new ItemStack(Items.DYE, 1, EnumColor.GREEN.getInvColorIndex()), 0.2F);
		this.registerRecipe(blocks.LOG, new ItemStack(Items.COAL, 1, 1), 0.15F);
		this.registerRecipe(blocks.LOG2, new ItemStack(Items.COAL, 1, 1), 0.15F);
		this.registerRecipe(blocks.EMERALD_ORE, new ItemStack(Items.EMERALD), 1.0F);
		this.a(Items.POTATO, new ItemStack(Items.BAKED_POTATO), 0.35F);
		this.registerRecipe(blocks.NETHERRACK, new ItemStack(Items.NETHERBRICK), 0.1F);
		this.a(new ItemStack(blocks.SPONGE, 1, 1), new ItemStack(blocks.SPONGE, 1, 0), 0.15F);
		this.a(Items.CHORUS_FRUIT, new ItemStack(Items.CHORUS_FRUIT_POPPED), 0.1F);
		ItemFish.EnumFish[] aitemfish_enumfish = ItemFish.EnumFish.values();
		int i = aitemfish_enumfish.length;

		for (int j = 0; j < i; ++j) {
			ItemFish.EnumFish itemfish_enumfish = aitemfish_enumfish[j];

			if (itemfish_enumfish.g()) {
				this.a(new ItemStack(Items.FISH, 1, itemfish_enumfish.a()), new ItemStack(Items.COOKED_FISH, 1, itemfish_enumfish.a()), 0.35F);
			}
		}

		this.registerRecipe(blocks.COAL_ORE, new ItemStack(Items.COAL), 0.1F);
		this.registerRecipe(blocks.REDSTONE_ORE, new ItemStack(Items.REDSTONE), 0.7F);
		this.registerRecipe(blocks.LAPIS_ORE, new ItemStack(Items.DYE, 1, EnumColor.BLUE.getInvColorIndex()), 0.2F);
		this.registerRecipe(blocks.QUARTZ_ORE, new ItemStack(Items.QUARTZ), 0.2F);
		this.a((Item) Items.CHAINMAIL_HELMET, new ItemStack(Items.da), 0.1F);
		this.a((Item) Items.CHAINMAIL_CHESTPLATE, new ItemStack(Items.da), 0.1F);
		this.a((Item) Items.CHAINMAIL_LEGGINGS, new ItemStack(Items.da), 0.1F);
		this.a((Item) Items.CHAINMAIL_BOOTS, new ItemStack(Items.da), 0.1F);
		this.a(Items.IRON_PICKAXE, new ItemStack(Items.da), 0.1F);
		this.a(Items.IRON_SHOVEL, new ItemStack(Items.da), 0.1F);
		this.a(Items.IRON_AXE, new ItemStack(Items.da), 0.1F);
		this.a(Items.IRON_HOE, new ItemStack(Items.da), 0.1F);
		this.a(Items.IRON_SWORD, new ItemStack(Items.da), 0.1F);
		this.a((Item) Items.IRON_HELMET, new ItemStack(Items.da), 0.1F);
		this.a((Item) Items.IRON_CHESTPLATE, new ItemStack(Items.da), 0.1F);
		this.a((Item) Items.IRON_LEGGINGS, new ItemStack(Items.da), 0.1F);
		this.a((Item) Items.IRON_BOOTS, new ItemStack(Items.da), 0.1F);
		this.a(Items.IRON_HORSE_ARMOR, new ItemStack(Items.da), 0.1F);
		this.a(Items.GOLDEN_PICKAXE, new ItemStack(Items.GOLD_NUGGET), 0.1F);
		this.a(Items.GOLDEN_SHOVEL, new ItemStack(Items.GOLD_NUGGET), 0.1F);
		this.a(Items.GOLDEN_AXE, new ItemStack(Items.GOLD_NUGGET), 0.1F);
		this.a(Items.GOLDEN_HOE, new ItemStack(Items.GOLD_NUGGET), 0.1F);
		this.a(Items.GOLDEN_SWORD, new ItemStack(Items.GOLD_NUGGET), 0.1F);
		this.a((Item) Items.GOLDEN_HELMET, new ItemStack(Items.GOLD_NUGGET), 0.1F);
		this.a((Item) Items.GOLDEN_CHESTPLATE, new ItemStack(Items.GOLD_NUGGET), 0.1F);
		this.a((Item) Items.GOLDEN_LEGGINGS, new ItemStack(Items.GOLD_NUGGET), 0.1F);
		this.a((Item) Items.GOLDEN_BOOTS, new ItemStack(Items.GOLD_NUGGET), 0.1F);
		this.a(Items.GOLDEN_HORSE_ARMOR, new ItemStack(Items.GOLD_NUGGET), 0.1F);
		this.a(new ItemStack(blocks.STAINED_HARDENED_CLAY, 1, EnumColor.WHITE.getColorIndex()), new ItemStack(blocks.dB), 0.1F);
		this.a(new ItemStack(blocks.STAINED_HARDENED_CLAY, 1, EnumColor.ORANGE.getColorIndex()), new ItemStack(blocks.dC), 0.1F);
		this.a(new ItemStack(blocks.STAINED_HARDENED_CLAY, 1, EnumColor.MAGENTA.getColorIndex()), new ItemStack(blocks.dD), 0.1F);
		this.a(new ItemStack(blocks.STAINED_HARDENED_CLAY, 1, EnumColor.LIGHT_BLUE.getColorIndex()), new ItemStack(blocks.dE), 0.1F);
		this.a(new ItemStack(blocks.STAINED_HARDENED_CLAY, 1, EnumColor.YELLOW.getColorIndex()), new ItemStack(blocks.dF), 0.1F);
		this.a(new ItemStack(blocks.STAINED_HARDENED_CLAY, 1, EnumColor.LIME.getColorIndex()), new ItemStack(blocks.dG), 0.1F);
		this.a(new ItemStack(blocks.STAINED_HARDENED_CLAY, 1, EnumColor.PINK.getColorIndex()), new ItemStack(blocks.dH), 0.1F);
		this.a(new ItemStack(blocks.STAINED_HARDENED_CLAY, 1, EnumColor.GRAY.getColorIndex()), new ItemStack(blocks.dI), 0.1F);
		this.a(new ItemStack(blocks.STAINED_HARDENED_CLAY, 1, EnumColor.SILVER.getColorIndex()), new ItemStack(blocks.dJ), 0.1F);
		this.a(new ItemStack(blocks.STAINED_HARDENED_CLAY, 1, EnumColor.CYAN.getColorIndex()), new ItemStack(blocks.dK), 0.1F);
		this.a(new ItemStack(blocks.STAINED_HARDENED_CLAY, 1, EnumColor.PURPLE.getColorIndex()), new ItemStack(blocks.dL), 0.1F);
		this.a(new ItemStack(blocks.STAINED_HARDENED_CLAY, 1, EnumColor.BLUE.getColorIndex()), new ItemStack(blocks.dM), 0.1F);
		this.a(new ItemStack(blocks.STAINED_HARDENED_CLAY, 1, EnumColor.BROWN.getColorIndex()), new ItemStack(blocks.dN), 0.1F);
		this.a(new ItemStack(blocks.STAINED_HARDENED_CLAY, 1, EnumColor.GREEN.getColorIndex()), new ItemStack(blocks.dO), 0.1F);
		this.a(new ItemStack(blocks.STAINED_HARDENED_CLAY, 1, EnumColor.RED.getColorIndex()), new ItemStack(blocks.dP), 0.1F);
		this.a(new ItemStack(blocks.STAINED_HARDENED_CLAY, 1, EnumColor.BLACK.getColorIndex()), new ItemStack(blocks.dQ), 0.1F);
	}

	// CraftBukkit start - add method
	public void registerRecipe(ItemStack itemstack, ItemStack itemstack1, float f) {
		this.customRecipes.put(itemstack, itemstack1);
		this.customExperience.put(itemstack, f);
	}
	// CraftBukkit end

	public void registerRecipe(Block block, ItemStack itemstack, float f) {
		this.a(items.getItemOf(block), itemstack, f);
	}

	public void a(Item item, ItemStack itemstack, float f) {
		this.a(new ItemStack(item, 1, 32767), itemstack, f);
	}

	public void a(ItemStack itemstack, ItemStack itemstack1, float f) {
		this.recipes.put(itemstack, itemstack1);
		this.experience.put(itemstack1, Float.valueOf(f));
	}

	public ItemStack getResult(ItemStack itemstack) {
		// CraftBukkit start - initialize to customRecipes
		boolean vanilla = false;
		Iterator<Entry<ItemStack, ItemStack>> iterator = this.customRecipes.entrySet().iterator();
		// CraftBukkit end

		Entry entry;

		do {
			if (!iterator.hasNext()) {
				// CraftBukkit start - fall back to vanilla recipes
				if (!vanilla && !this.recipes.isEmpty()) {
					iterator = this.recipes.entrySet().iterator();
					vanilla = true;
				} else {
					return ItemStack.a;
				}
				// CraftBukkit end
			}

			entry = (Entry) iterator.next();
		} while (!this.a(itemstack, (ItemStack) entry.getKey()));

		return (ItemStack) entry.getValue();
	}

	private boolean a(ItemStack itemstack, ItemStack itemstack1) {
		return itemstack1.getItem() == itemstack.getItem() && (itemstack1.getData() == 32767 || itemstack1.getData() == itemstack.getData());
	}

	public Map<ItemStack, ItemStack> getRecipes() {
		return this.recipes;
	}

	public float b(ItemStack itemstack) {
		// CraftBukkit start - initialize to customRecipes
		boolean vanilla = false;
		Iterator<Entry<ItemStack, Float>> iterator = this.customExperience.entrySet().iterator();
		// CraftBukkit end

		Entry entry;

		do {
			if (!iterator.hasNext()) {
				// CraftBukkit start - fall back to vanilla recipes
				if (!vanilla && !this.experience.isEmpty()) {
					iterator = this.experience.entrySet().iterator();
					vanilla = true;
				} else {
					return 0.0F;
				}
				// CraftBukkit end
			}

			entry = (Entry) iterator.next();
		} while (!this.a(itemstack, (ItemStack) entry.getKey()));

		return ((Float) entry.getValue()).floatValue();
	}
}
