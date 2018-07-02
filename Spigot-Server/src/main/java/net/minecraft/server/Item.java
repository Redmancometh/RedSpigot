package net.minecraft.server;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.mcavenue.redspigot.registries.BlockItemRegistry;
import net.mcavenue.redspigot.registries.BlockRegistry;
import net.mcavenue.redspigot.registries.ItemRegistry;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;

public class Item {

	@Autowired
	protected ItemRegistry items;
	@Autowired
	protected Blocks blocks;
	@Autowired
	protected BlockRegistry blockRegistry;
	@Autowired
	protected BlockItemRegistry blockItems;
	private static final IDynamicTexture b = new IDynamicTexture() {
	};
	private static final IDynamicTexture c = new IDynamicTexture() {
	};
	private static final IDynamicTexture d = new IDynamicTexture() {
	};
	private static final IDynamicTexture e = new IDynamicTexture() {
	};
	protected static final UUID h = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
	protected static final UUID i = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
	private CreativeModeTab n;
	protected static Random j = new Random();
	protected int maxStackSize = 64;
	private int durability;
	protected boolean l;
	protected boolean m;
	private Item craftingResult;
	private String name;

	public boolean a(NBTTagCompound nbttagcompound) {
		return false;
	}

	public Item() {
		items.a(new MinecraftKey("lefthanded"), Item.d);
		items.a(new MinecraftKey("cooldown"), Item.e);
	}

	public Item d(int i) {
		this.maxStackSize = i;
		return this;
	}

	public EnumInteractionResult a(EntityHuman entityhuman, World world, BlockPosition blockposition, EnumHand enumhand, EnumDirection enumdirection,
			float f, float f1, float f2) {
		return EnumInteractionResult.PASS;
	}

	public float getDestroySpeed(ItemStack itemstack, IBlockData iblockdata) {
		return 1.0F;
	}

	public InteractionResultWrapper<ItemStack> a(World world, EntityHuman entityhuman, EnumHand enumhand) {
		return new InteractionResultWrapper(EnumInteractionResult.PASS, entityhuman.b(enumhand));
	}

	public ItemStack a(ItemStack itemstack, World world, EntityLiving entityliving) {
		return itemstack;
	}

	public int getMaxStackSize() {
		return this.maxStackSize;
	}

	public int filterData(int i) {
		return 0;
	}

	public boolean k() {
		return this.m;
	}

	protected Item a(boolean flag) {
		this.m = flag;
		return this;
	}

	public int getMaxDurability() {
		return this.durability;
	}

	protected Item setMaxDurability(int i) {
		this.durability = i;
		if (i > 0) {
			items.a(new MinecraftKey("damaged"), Item.b);
			items.a(new MinecraftKey("damage"), Item.c);
		}

		return this;
	}

	public boolean usesDurability() {
		return this.durability > 0 && (!this.m || this.maxStackSize == 1);
	}

	public boolean a(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
		return false;
	}

	public boolean a(ItemStack itemstack, World world, IBlockData iblockdata, BlockPosition blockposition, EntityLiving entityliving) {
		return false;
	}

	public boolean canDestroySpecialBlock(IBlockData iblockdata) {
		return false;
	}

	public boolean a(ItemStack itemstack, EntityHuman entityhuman, EntityLiving entityliving, EnumHand enumhand) {
		return false;
	}

	public Item n() {
		this.l = true;
		return this;
	}

	public Item c(String s) {
		this.name = s;
		return this;
	}

	public String j(ItemStack itemstack) {
		return LocaleI18n.get(this.a(itemstack));
	}

	public String getName() {
		return "item." + this.name;
	}

	public String a(ItemStack itemstack) {
		return "item." + this.name;
	}

	public Item b(Item item) {
		this.craftingResult = item;
		return this;
	}

	public boolean p() {
		return true;
	}

	@Nullable
	public Item q() {
		return this.craftingResult;
	}

	public boolean r() {
		return this.craftingResult != null;
	}

	public void a(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
	}

	public void b(ItemStack itemstack, World world, EntityHuman entityhuman) {
	}

	public boolean f() {
		return false;
	}

	public EnumAnimation f(ItemStack itemstack) {
		return EnumAnimation.NONE;
	}

	public int e(ItemStack itemstack) {
		return 0;
	}

	public void a(ItemStack itemstack, World world, EntityLiving entityliving, int i) {
	}

	public String b(ItemStack itemstack) {
		return LocaleI18n.get(this.j(itemstack) + ".name").trim();
	}

	public EnumItemRarity g(ItemStack itemstack) {
		return itemstack.hasEnchantments() ? EnumItemRarity.RARE : EnumItemRarity.COMMON;
	}

	public boolean g_(ItemStack itemstack) {
		return this.getMaxStackSize() == 1 && this.usesDurability();
	}

	protected MovingObjectPosition a(World world, EntityHuman entityhuman, boolean flag) {
		float f = entityhuman.pitch;
		float f1 = entityhuman.yaw;
		double d0 = entityhuman.locX;
		double d1 = entityhuman.locY + (double) entityhuman.getHeadHeight();
		double d2 = entityhuman.locZ;
		Vec3D vec3d = new Vec3D(d0, d1, d2);
		float f2 = MathHelper.cos(-f1 * 0.017453292F - 3.1415927F);
		float f3 = MathHelper.sin(-f1 * 0.017453292F - 3.1415927F);
		float f4 = -MathHelper.cos(-f * 0.017453292F);
		float f5 = MathHelper.sin(-f * 0.017453292F);
		float f6 = f3 * f4;
		float f7 = f2 * f4;
		double d3 = 5.0D;
		Vec3D vec3d1 = vec3d.add((double) f6 * 5.0D, (double) f5 * 5.0D, (double) f7 * 5.0D);

		return world.rayTrace(vec3d, vec3d1, flag, !flag, false);
	}

	public int c() {
		return 0;
	}

	public void a(CreativeModeTab creativemodetab, NonNullList<ItemStack> nonnulllist) {
		if (this.a(creativemodetab)) {
			nonnulllist.add(new ItemStack(this));
		}

	}

	protected boolean a(CreativeModeTab creativemodetab) {
		CreativeModeTab creativemodetab1 = this.b();

		return creativemodetab1 != null && (creativemodetab == CreativeModeTab.g || creativemodetab == creativemodetab1);
	}

	@Nullable
	public CreativeModeTab b() {
		return this.n;
	}

	public Item b(CreativeModeTab creativemodetab) {
		this.n = creativemodetab;
		return this;
	}

	public boolean s() {
		return false;
	}

	public boolean a(ItemStack itemstack, ItemStack itemstack1) {
		return false;
	}

	public Multimap<String, AttributeModifier> a(EnumItemSlot enumitemslot) {
		return HashMultimap.create();
	}

	public static class EnumToolMaterial {
		public static final EnumToolMaterial DIAMOND = new EnumToolMaterial(3, 1561, 8.0F, 3.0F, 10);
		public static final EnumToolMaterial WOOD = new EnumToolMaterial(0, 59, 2.0F, 0.0F, 15);
		public static final EnumToolMaterial STONE = new EnumToolMaterial(1, 131, 4.0F, 1.0F, 5);
		public static final EnumToolMaterial IRON = new EnumToolMaterial(2, 250, 6.0F, 2.0F, 14);
		public static final EnumToolMaterial GOLD = new EnumToolMaterial(0, 32, 12.0F, 0.0F, 22);

		private final int f;
		private final int g;
		private final float h;
		private final float i;
		private final int j;

		private EnumToolMaterial(int i, int j, float f, float f1, int k) {
			this.f = i;
			this.g = j;
			this.h = f;
			this.i = f1;
			this.j = k;
		}

		public int a() {
			return this.g;
		}

		public float b() {
			return this.h;
		}

		public float c() {
			return this.i;
		}

		public int d() {
			return this.f;
		}

		public int e() {
			return this.j;
		}
		// TODON: Make this work again
		/*
		 * public Item f() { return this == Item.EnumToolMaterial.WOOD ?
		 * getItemOf(blocks.PLANKS) : (this == Item.EnumToolMaterial.STONE ?
		 * getItemOf(blocks.COBBLESTONE) : (this == Item.EnumToolMaterial.GOLD ?
		 * Items.GOLD_INGOT : (this == Item.EnumToolMaterial.IRON ?
		 * Items.IRON_INGOT : (this == Item.EnumToolMaterial.DIAMOND ?
		 * Items.DIAMOND : null)))); }
		 */
	}
}
