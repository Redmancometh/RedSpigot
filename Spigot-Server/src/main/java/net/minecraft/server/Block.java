package net.minecraft.server;

import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;

import net.mcavenue.redspigot.registries.BlockItemRegistry;
import net.mcavenue.redspigot.registries.BlockRegistry;
import net.mcavenue.redspigot.registries.ItemRegistry;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
public class Block {

	private static final MinecraftKey a = new MinecraftKey("air");
	@Autowired
	protected BlockRegistry REGISTRY;
	@Autowired
	protected Blocks blocks;
	@Autowired
	protected ItemRegistry items;
	@Autowired
	protected BlockItemRegistry blockItems;
	public static final RegistryBlockID<IBlockData> REGISTRY_ID = new RegistryBlockID();
	public static final AxisAlignedBB j = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	@Nullable
	public static final AxisAlignedBB k = null;
	private CreativeModeTab creativeTab;
	protected boolean l;
	protected int m;
	protected boolean n;
	protected int o;
	protected boolean p;
	protected float strength;
	protected float durability;
	protected boolean s;
	protected boolean t;
	protected boolean isTileEntity;
	protected SoundEffectType stepSound;
	public float w;
	protected final Material material;
	protected final MaterialMapColor y;
	public float frictionFactor;
	protected final BlockStateList blockStateList;
	private IBlockData blockData;
	private String name;

	public Block asBlock(@Nullable Item item) {
		return item instanceof ItemBlock ? ((ItemBlock) item).getBlock() : blocks.AIR;
	}

	@Deprecated
	public boolean k(IBlockData iblockdata) {
		return iblockdata.getMaterial().k() && iblockdata.g();
	}

	@Deprecated
	public boolean l(IBlockData iblockdata) {
		return this.l;
	}

	@Deprecated
	public boolean a(IBlockData iblockdata, Entity entity) {
		return true;
	}

	@Deprecated
	public int m(IBlockData iblockdata) {
		return this.m;
	}

	@Deprecated
	public int o(IBlockData iblockdata) {
		return this.o;
	}

	@Deprecated
	public boolean p(IBlockData iblockdata) {
		return this.p;
	}

	@Deprecated
	public Material q(IBlockData iblockdata) {
		return this.material;
	}

	@Deprecated
	public MaterialMapColor c(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
		return this.y;
	}

	@Deprecated
	public IBlockData fromLegacyData(int i) {
		return this.getBlockData();
	}

	public int toLegacyData(IBlockData iblockdata) {
		if (iblockdata.s().isEmpty()) {
			return 0;
		} else {
			throw new IllegalArgumentException("Don\'t know how to convert " + iblockdata + " back into data...");
		}
	}

	@Deprecated
	public IBlockData updateState(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
		return iblockdata;
	}

	@Deprecated
	public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
		return iblockdata;
	}

	@Deprecated
	public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
		return iblockdata;
	}

	public Block(Material material, MaterialMapColor materialmapcolor) {
		this.s = true;
		this.stepSound = SoundEffectType.d;
		this.w = 1.0F;
		this.frictionFactor = 0.6F;
		this.material = material;
		this.y = materialmapcolor;
		this.blockStateList = this.getStateList();
		this.w(this.blockStateList.getBlockData());
		this.l = this.getBlockData().p();
		this.m = this.l ? 255 : 0;
		this.n = !material.blocksLight();
	}

	protected Block(Material material) {
		this(material, material.r());
	}

	protected Block a(SoundEffectType soundeffecttype) {
		this.stepSound = soundeffecttype;
		return this;
	}

	protected Block e(int i) {
		this.m = i;
		return this;
	}

	protected Block a(float f) {
		this.o = (int) (15.0F * f);
		return this;
	}

	protected Block b(float f) {
		this.durability = f * 3.0F;
		return this;
	}

	protected boolean b(Block block) {
		return block instanceof BlockShulkerBox || block instanceof BlockLeaves || block instanceof BlockTrapdoor || block == blocks.BEACON
				|| block == blocks.cauldron || block == blocks.GLASS || block == blocks.GLOWSTONE || block == blocks.ICE
				|| block == blocks.SEA_LANTERN || block == blocks.STAINED_GLASS;
	}

	protected boolean c(Block block) {
		return b(block) || block == blocks.PISTON || block == blocks.STICKY_PISTON || block == blocks.PISTON_HEAD;
	}

	@Deprecated
	public boolean r(IBlockData iblockdata) {
		return iblockdata.getMaterial().isSolid() && iblockdata.g();
	}

	@Deprecated
	public boolean isOccluding(IBlockData iblockdata) {
		return iblockdata.getMaterial().k() && iblockdata.g() && !iblockdata.m();
	}

	@Deprecated
	public boolean t(IBlockData iblockdata) {
		return this.material.isSolid() && this.getBlockData().g();
	}

	@Deprecated
	public boolean c(IBlockData iblockdata) {
		return true;
	}

	public boolean b(IBlockAccess iblockaccess, BlockPosition blockposition) {
		return !this.material.isSolid();
	}

	@Deprecated
	public EnumRenderType a(IBlockData iblockdata) {
		return EnumRenderType.MODEL;
	}

	public boolean a(IBlockAccess iblockaccess, BlockPosition blockposition) {
		return false;
	}

	protected Block c(float f) {
		this.strength = f;
		if (this.durability < f * 5.0F) {
			this.durability = f * 5.0F;
		}

		return this;
	}

	protected Block j() {
		this.c(-1.0F);
		return this;
	}

	@Deprecated
	public float a(IBlockData iblockdata, World world, BlockPosition blockposition) {
		return this.strength;
	}

	protected Block a(boolean flag) {
		this.t = flag;
		return this;
	}

	public boolean isTicking() {
		return this.t;
	}

	public boolean isTileEntity() {
		return this.isTileEntity;
	}

	@Deprecated
	public AxisAlignedBB b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
		return Block.j;
	}

	@Deprecated
	public EnumBlockFaceShape a(IBlockAccess iblockaccess, IBlockData iblockdata, BlockPosition blockposition, EnumDirection enumdirection) {
		return EnumBlockFaceShape.SOLID;
	}

	@Deprecated
	public void a(IBlockData iblockdata, World world, BlockPosition blockposition, AxisAlignedBB axisalignedbb, List<AxisAlignedBB> list,
			@Nullable Entity entity, boolean flag) {
		a(blockposition, axisalignedbb, list, iblockdata.d(world, blockposition));
	}

	protected static void a(BlockPosition blockposition, AxisAlignedBB axisalignedbb, List<AxisAlignedBB> list,
			@Nullable AxisAlignedBB axisalignedbb1) {
		if (axisalignedbb1 != Block.k) {
			AxisAlignedBB axisalignedbb2 = axisalignedbb1.a(blockposition);

			if (axisalignedbb.c(axisalignedbb2)) {
				list.add(axisalignedbb2);
			}
		}

	}

	@Deprecated
	@Nullable
	public AxisAlignedBB a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
		return iblockdata.e(iblockaccess, blockposition);
	}

	@Deprecated
	public boolean b(IBlockData iblockdata) {
		return true;
	}

	public boolean a(IBlockData iblockdata, boolean flag) {
		return this.m();
	}

	public boolean m() {
		return true;
	}

	public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
		this.b(world, blockposition, iblockdata, random);
	}

	public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
	}

	public void postBreak(World world, BlockPosition blockposition, IBlockData iblockdata) {
	}

	@Deprecated
	public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block, BlockPosition blockposition1) {
	}

	public int a(World world) {
		return 10;
	}

	public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata) {
		org.spigotmc.AsyncCatcher.catchOp("block onPlace"); // Spigot
	}

	public void remove(World world, BlockPosition blockposition, IBlockData iblockdata) {
		org.spigotmc.AsyncCatcher.catchOp("block remove"); // Spigot
	}

	public int a(Random random) {
		return 1;
	}

	public Item getDropType(IBlockData iblockdata, Random random, int i) {
		return blockItems.get(this);
	}

	@Deprecated
	public float getDamage(IBlockData iblockdata, EntityHuman entityhuman, World world, BlockPosition blockposition) {
		float f = iblockdata.b(world, blockposition);

		return f < 0.0F ? 0.0F : (!entityhuman.hasBlock(iblockdata) ? entityhuman.b(iblockdata) / f / 100.0F : entityhuman.b(iblockdata) / f / 30.0F);
	}

	public final void b(World world, BlockPosition blockposition, IBlockData iblockdata, int i) {
		this.dropNaturally(world, blockposition, iblockdata, 1.0F, i);
	}

	public void dropNaturally(World world, BlockPosition blockposition, IBlockData iblockdata, float f, int i) {
		if (!world.isClientSide) {
			int j = this.getDropCount(i, world.random);

			for (int k = 0; k < j; ++k) {
				// CraftBukkit - <= to < to allow for plugins to completely
				// disable block drops from explosions
				if (world.random.nextFloat() < f) {
					Item item = this.getDropType(iblockdata, world.random, i);

					if (item != Items.a) {
						a(world, blockposition, new ItemStack(item, 1, this.getDropData(iblockdata)));
					}
				}
			}

		}
	}

	public static void a(World world, BlockPosition blockposition, ItemStack itemstack) {
		if (!world.isClientSide && !itemstack.isEmpty() && world.getGameRules().getBoolean("doTileDrops")) {
			float f = 0.5F;
			double d0 = (double) (world.random.nextFloat() * 0.5F) + 0.25D;
			double d1 = (double) (world.random.nextFloat() * 0.5F) + 0.25D;
			double d2 = (double) (world.random.nextFloat() * 0.5F) + 0.25D;
			EntityItem entityitem = new EntityItem(world, (double) blockposition.getX() + d0, (double) blockposition.getY() + d1,
					(double) blockposition.getZ() + d2, itemstack);

			entityitem.q();
			// CraftBukkit start
			if (world.captureDrops != null) {
				world.captureDrops.add(entityitem);
			} else {
				world.addEntity(entityitem);
			}
			// CraftBukkit end
		}
	}

	protected void dropExperience(World world, BlockPosition blockposition, int i) {
		if (!world.isClientSide && world.getGameRules().getBoolean("doTileDrops")) {
			while (i > 0) {
				int j = EntityExperienceOrb.getOrbValue(i);

				i -= j;
				world.addEntity(new EntityExperienceOrb(world, (double) blockposition.getX() + 0.5D, (double) blockposition.getY() + 0.5D,
						(double) blockposition.getZ() + 0.5D, j));
			}
		}

	}

	public int getDropData(IBlockData iblockdata) {
		return 0;
	}

	public float a(Entity entity) {
		return this.durability / 5.0F;
	}

	@Deprecated
	@Nullable
	public MovingObjectPosition a(IBlockData iblockdata, World world, BlockPosition blockposition, Vec3D vec3d, Vec3D vec3d1) {
		return this.a(blockposition, vec3d, vec3d1, iblockdata.e(world, blockposition));
	}

	@Nullable
	protected MovingObjectPosition a(BlockPosition blockposition, Vec3D vec3d, Vec3D vec3d1, AxisAlignedBB axisalignedbb) {
		Vec3D vec3d2 = vec3d.a((double) blockposition.getX(), (double) blockposition.getY(), (double) blockposition.getZ());
		Vec3D vec3d3 = vec3d1.a((double) blockposition.getX(), (double) blockposition.getY(), (double) blockposition.getZ());
		MovingObjectPosition movingobjectposition = axisalignedbb.b(vec3d2, vec3d3);

		return movingobjectposition == null
				? null
				: new MovingObjectPosition(
						movingobjectposition.pos.add((double) blockposition.getX(), (double) blockposition.getY(), (double) blockposition.getZ()),
						movingobjectposition.direction, blockposition);
	}

	public void wasExploded(World world, BlockPosition blockposition, Explosion explosion) {
	}

	public boolean canPlace(World world, BlockPosition blockposition, EnumDirection enumdirection) {
		return this.canPlace(world, blockposition);
	}

	public boolean canPlace(World world, BlockPosition blockposition) {
		return world.getType(blockposition).getBlock().material.isReplaceable();
	}

	public boolean interact(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman, EnumHand enumhand,
			EnumDirection enumdirection, float f, float f1, float f2) {
		return false;
	}

	public void stepOn(World world, BlockPosition blockposition, Entity entity) {
	}

	public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i,
			EntityLiving entityliving) {
		return this.fromLegacyData(i);
	}

	public void attack(World world, BlockPosition blockposition, EntityHuman entityhuman) {
	}

	public Vec3D a(World world, BlockPosition blockposition, Entity entity, Vec3D vec3d) {
		return vec3d;
	}

	@Deprecated
	public int b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
		return 0;
	}

	@Deprecated
	public boolean isPowerSource(IBlockData iblockdata) {
		return false;
	}

	public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Entity entity) {
	}

	@Deprecated
	public int c(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
		return 0;
	}

	public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, @Nullable TileEntity tileentity,
			ItemStack itemstack) {
		entityhuman.b(StatisticList.a(this));
		entityhuman.applyExhaustion(0.005F);
		if (this.n() && EnchantmentManager.getEnchantmentLevel(Enchantments.SILK_TOUCH, itemstack) > 0) {
			ItemStack itemstack1 = this.u(iblockdata);

			a(world, blockposition, itemstack1);
		} else {
			int i = EnchantmentManager.getEnchantmentLevel(Enchantments.LOOT_BONUS_BLOCKS, itemstack);

			this.b(world, blockposition, iblockdata, i);
		}

	}

	protected boolean n() {
		return this.getBlockData().g() && !this.isTileEntity;
	}

	protected ItemStack u(IBlockData iblockdata) {
		Item item = blockItems.get(this);
		int i = 0;

		if (item.k()) {
			i = this.toLegacyData(iblockdata);
		}

		return new ItemStack(item, 1, i);
	}

	public int getDropCount(int i, Random random) {
		return this.a(random);
	}

	public void postPlace(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving entityliving, ItemStack itemstack) {
	}

	public boolean d() {
		return !this.material.isBuildable() && !this.material.isLiquid();
	}

	public Block c(String s) {
		this.name = s;
		return this;
	}

	public String getName() {
		return LocaleI18n.get(this.a() + ".name");
	}

	public String a() {
		return "tile." + this.name;
	}

	@Deprecated
	public boolean a(IBlockData iblockdata, World world, BlockPosition blockposition, int i, int j) {
		return false;
	}

	public boolean o() {
		return this.s;
	}

	protected Block p() {
		this.s = false;
		return this;
	}

	@Deprecated
	public EnumPistonReaction h(IBlockData iblockdata) {
		return this.material.getPushReaction();
	}

	public void fallOn(World world, BlockPosition blockposition, Entity entity, float f) {
		entity.e(f, 1.0F);
	}

	public void a(World world, Entity entity) {
		entity.motY = 0.0D;
	}

	public ItemStack a(World world, BlockPosition blockposition, IBlockData iblockdata) {
		return new ItemStack(blockItems.get(this), 1, this.getDropData(iblockdata));
	}

	public void a(CreativeModeTab creativemodetab, NonNullList<ItemStack> nonnulllist) {
		nonnulllist.add(new ItemStack(this));
	}

	public CreativeModeTab q() {
		return this.creativeTab;
	}

	public Block a(CreativeModeTab creativemodetab) {
		this.creativeTab = creativemodetab;
		return this;
	}

	public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman) {
	}

	public void h(World world, BlockPosition blockposition) {
	}

	public boolean r() {
		return true;
	}

	public boolean a(Explosion explosion) {
		return true;
	}

	public boolean d(Block block) {
		return this == block;
	}

	public static boolean a(Block block, Block block1) {
		return block != null && block1 != null ? (block == block1 ? true : block.d(block1)) : false;
	}

	@Deprecated
	public boolean isComplexRedstone(IBlockData iblockdata) {
		return false;
	}

	@Deprecated
	public int c(IBlockData iblockdata, World world, BlockPosition blockposition) {
		return 0;
	}

	protected BlockStateList getStateList() {
		return new BlockStateList(this, new IBlockState[0]);
	}

	public BlockStateList s() {
		return this.blockStateList;
	}

	protected final void w(IBlockData iblockdata) {
		this.blockData = iblockdata;
	}

	public final IBlockData getBlockData() {
		return this.blockData;
	}

	public Block.EnumRandomOffset u() {
		return Block.EnumRandomOffset.NONE;
	}

	@Deprecated
	public Vec3D f(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
		Block.EnumRandomOffset block_enumrandomoffset = this.u();

		if (block_enumrandomoffset == Block.EnumRandomOffset.NONE) {
			return Vec3D.a;
		} else {
			long i = MathHelper.c(blockposition.getX(), 0, blockposition.getZ());

			return new Vec3D(((double) ((float) (i >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D,
					block_enumrandomoffset == Block.EnumRandomOffset.XYZ ? ((double) ((float) (i >> 20 & 15L) / 15.0F) - 1.0D) * 0.2D : 0.0D,
					((double) ((float) (i >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D);
		}
	}

	public SoundEffectType getStepSound() {
		return this.stepSound;
	}

	public String toString() {
		return "Block{" + REGISTRY.b(this) + "}";
	}

	public void w() {
		REGISTRY.a(0, Block.a, (new BlockAir()).c("air"));
		REGISTRY.a(1, "stone", (new BlockStone()).c(1.5F).b(10.0F).a(SoundEffectType.d).c("stone"));
		REGISTRY.a(2, "grass", (new BlockGrass()).c(0.6F).a(SoundEffectType.c).c("grass"));
		REGISTRY.a(3, "dirt", (new BlockDirt()).c(0.5F).a(SoundEffectType.b).c("dirt"));
		Block block = (new Block(Material.STONE)).c(2.0F).b(10.0F).a(SoundEffectType.d).c("stonebrick").a(CreativeModeTab.b);
		REGISTRY.a(4, "cobblestone", block);
		Block block1 = (new BlockWood()).c(2.0F).b(5.0F).a(SoundEffectType.a).c("wood");
		REGISTRY.a(5, "planks", block1);
		REGISTRY.a(6, "sapling", (new BlockSapling()).c(0.0F).a(SoundEffectType.c).c("sapling"));
		REGISTRY.a(7, "bedrock", (new BlockNoDrop(Material.STONE)).j().b(6000000.0F).a(SoundEffectType.d).c("bedrock").p().a(CreativeModeTab.b));
		REGISTRY.a(8, "flowing_water", (new BlockFlowing(Material.WATER)).c(100.0F).e(3).c("water").p());
		REGISTRY.a(9, "water", (new BlockStationary(Material.WATER)).c(100.0F).e(3).c("water").p());
		REGISTRY.a(10, "flowing_lava", (new BlockFlowing(Material.LAVA)).c(100.0F).a(1.0F).c("lava").p());
		REGISTRY.a(11, "lava", (new BlockStationary(Material.LAVA)).c(100.0F).a(1.0F).c("lava").p());
		REGISTRY.a(12, "sand", (new BlockSand()).c(0.5F).a(SoundEffectType.h).c("sand"));
		REGISTRY.a(13, "gravel", (new BlockGravel()).c(0.6F).a(SoundEffectType.b).c("gravel"));
		REGISTRY.a(14, "gold_ore", (new BlockOre()).c(3.0F).b(5.0F).a(SoundEffectType.d).c("oreGold"));
		REGISTRY.a(15, "iron_ore", (new BlockOre()).c(3.0F).b(5.0F).a(SoundEffectType.d).c("oreIron"));
		REGISTRY.a(16, "coal_ore", (new BlockOre()).c(3.0F).b(5.0F).a(SoundEffectType.d).c("oreCoal"));
		REGISTRY.a(17, "log", (new BlockLog1()).c("log"));
		REGISTRY.a(18, "leaves", (new BlockLeaves1()).c("leaves"));
		REGISTRY.a(19, "sponge", (new BlockSponge()).c(0.6F).a(SoundEffectType.c).c("sponge"));
		REGISTRY.a(20, "glass", (new BlockGlass(Material.SHATTERABLE, false)).c(0.3F).a(SoundEffectType.f).c("glass"));
		REGISTRY.a(21, "lapis_ore", (new BlockOre()).c(3.0F).b(5.0F).a(SoundEffectType.d).c("oreLapis"));
		REGISTRY.a(22, "lapis_block",
				(new Block(Material.ORE, MaterialMapColor.I)).c(3.0F).b(5.0F).a(SoundEffectType.d).c("blockLapis").a(CreativeModeTab.b));
		REGISTRY.a(23, "dispenser", (new BlockDispenser()).c(3.5F).a(SoundEffectType.d).c("dispenser"));
		Block block2 = (new BlockSandStone()).a(SoundEffectType.d).c(0.8F).c("sandStone");
		REGISTRY.a(24, "sandstone", block2);
		REGISTRY.a(25, "noteblock", (new BlockNote()).a(SoundEffectType.a).c(0.8F).c("musicBlock"));
		REGISTRY.a(26, "bed", (new BlockBed()).a(SoundEffectType.a).c(0.2F).c("bed").p());
		REGISTRY.a(27, "golden_rail", (new BlockPoweredRail()).c(0.7F).a(SoundEffectType.e).c("goldenRail"));
		REGISTRY.a(28, "detector_rail", (new BlockMinecartDetector()).c(0.7F).a(SoundEffectType.e).c("detectorRail"));
		REGISTRY.a(29, "sticky_piston", (new BlockPiston(true)).c("pistonStickyBase"));
		REGISTRY.a(30, "web", (new BlockWeb()).e(1).c(4.0F).c("web"));
		REGISTRY.a(31, "tallgrass", (new BlockLongGrass()).c(0.0F).a(SoundEffectType.c).c("tallgrass"));
		REGISTRY.a(32, "deadbush", (new BlockDeadBush()).c(0.0F).a(SoundEffectType.c).c("deadbush"));
		REGISTRY.a(33, "piston", (new BlockPiston(false)).c("pistonBase"));
		REGISTRY.a(34, "piston_head", (new BlockPistonExtension()).c("pistonBase"));
		REGISTRY.a(35, "wool", (new BlockCloth(Material.CLOTH)).c(0.8F).a(SoundEffectType.g).c("cloth"));
		REGISTRY.a(36, "piston_extension", new BlockPistonMoving());
		REGISTRY.a(37, "yellow_flower", (new BlockYellowFlowers()).c(0.0F).a(SoundEffectType.c).c("flower1"));
		REGISTRY.a(38, "red_flower", (new BlockRedFlowers()).c(0.0F).a(SoundEffectType.c).c("flower2"));
		Block block3 = (new BlockMushroom()).c(0.0F).a(SoundEffectType.c).a(0.125F).c("mushroom");
		REGISTRY.a(39, "brown_mushroom", block3);
		Block block4 = (new BlockMushroom()).c(0.0F).a(SoundEffectType.c).c("mushroom");
		REGISTRY.a(40, "red_mushroom", block4);
		REGISTRY.a(41, "gold_block",
				(new Block(Material.ORE, MaterialMapColor.G)).c(3.0F).b(10.0F).a(SoundEffectType.e).c("blockGold").a(CreativeModeTab.b));
		REGISTRY.a(42, "iron_block",
				(new Block(Material.ORE, MaterialMapColor.i)).c(5.0F).b(10.0F).a(SoundEffectType.e).c("blockIron").a(CreativeModeTab.b));
		REGISTRY.a(43, "double_stone_slab", (new BlockDoubleStep()).c(2.0F).b(10.0F).a(SoundEffectType.d).c("stoneSlab"));
		REGISTRY.a(44, "stone_slab", (new BlockStep()).c(2.0F).b(10.0F).a(SoundEffectType.d).c("stoneSlab"));
		Block block5 = (new Block(Material.STONE, MaterialMapColor.E)).c(2.0F).b(10.0F).a(SoundEffectType.d).c("brick").a(CreativeModeTab.b);
		REGISTRY.a(45, "brick_block", block5);
		REGISTRY.a(46, "tnt", (new BlockTNT()).c(0.0F).a(SoundEffectType.c).c("tnt"));
		REGISTRY.a(47, "bookshelf", (new BlockBookshelf()).c(1.5F).a(SoundEffectType.a).c("bookshelf"));
		REGISTRY.a(48, "mossy_cobblestone", (new Block(Material.STONE)).c(2.0F).b(10.0F).a(SoundEffectType.d).c("stoneMoss").a(CreativeModeTab.b));
		REGISTRY.a(49, "obsidian", (new BlockObsidian()).c(50.0F).b(2000.0F).a(SoundEffectType.d).c("obsidian"));
		REGISTRY.a(50, "torch", (new BlockTorch()).c(0.0F).a(0.9375F).a(SoundEffectType.a).c("torch"));
		REGISTRY.a(51, "fire", (new BlockFire()).c(0.0F).a(1.0F).a(SoundEffectType.g).c("fire").p());
		REGISTRY.a(52, "mob_spawner", (new BlockMobSpawner()).c(5.0F).a(SoundEffectType.e).c("mobSpawner").p());
		REGISTRY.a(53, "oak_stairs", (new BlockStairs(block1.getBlockData().set(BlockWood.VARIANT, BlockWood.EnumLogVariant.OAK))).c("stairsWood"));
		REGISTRY.a(54, "chest", (new BlockChest(BlockChest.Type.BASIC)).c(2.5F).a(SoundEffectType.a).c("chest"));
		REGISTRY.a(55, "redstone_wire", (new BlockRedstoneWire()).c(0.0F).a(SoundEffectType.d).c("redstoneDust").p());
		REGISTRY.a(56, "diamond_ore", (new BlockOre()).c(3.0F).b(5.0F).a(SoundEffectType.d).c("oreDiamond"));
		REGISTRY.a(57, "diamond_block",
				(new Block(Material.ORE, MaterialMapColor.H)).c(5.0F).b(10.0F).a(SoundEffectType.e).c("blockDiamond").a(CreativeModeTab.b));
		REGISTRY.a(58, "crafting_table", (new BlockWorkbench()).c(2.5F).a(SoundEffectType.a).c("workbench"));
		REGISTRY.a(59, "wheat", (new BlockCrops()).c("crops"));
		Block block6 = (new BlockSoil()).c(0.6F).a(SoundEffectType.b).c("farmland");
		REGISTRY.a(60, "farmland", block6);
		REGISTRY.a(61, "furnace", (new BlockFurnace(false)).c(3.5F).a(SoundEffectType.d).c("furnace").a(CreativeModeTab.c));
		REGISTRY.a(62, "lit_furnace", (new BlockFurnace(true)).c(3.5F).a(SoundEffectType.d).a(0.875F).c("furnace"));
		REGISTRY.a(63, "standing_sign", (new BlockFloorSign()).c(1.0F).a(SoundEffectType.a).c("sign").p());
		REGISTRY.a(64, "wooden_door", (new BlockDoor(Material.WOOD)).c(3.0F).a(SoundEffectType.a).c("doorOak").p());
		REGISTRY.a(65, "ladder", (new BlockLadder()).c(0.4F).a(SoundEffectType.j).c("ladder"));
		REGISTRY.a(66, "rail", (new BlockMinecartTrack()).c(0.7F).a(SoundEffectType.e).c("rail"));
		REGISTRY.a(67, "stone_stairs", (new BlockStairs(block.getBlockData())).c("stairsStone"));
		REGISTRY.a(68, "wall_sign", (new BlockWallSign()).c(1.0F).a(SoundEffectType.a).c("sign").p());
		REGISTRY.a(69, "lever", (new BlockLever()).c(0.5F).a(SoundEffectType.a).c("lever"));
		REGISTRY.a(70, "stone_pressure_plate", (new BlockPressurePlateBinary(Material.STONE, BlockPressurePlateBinary.EnumMobType.MOBS)).c(0.5F)
				.a(SoundEffectType.d).c("pressurePlateStone"));
		REGISTRY.a(71, "iron_door", (new BlockDoor(Material.ORE)).c(5.0F).a(SoundEffectType.e).c("doorIron").p());
		REGISTRY.a(72, "wooden_pressure_plate", (new BlockPressurePlateBinary(Material.WOOD, BlockPressurePlateBinary.EnumMobType.EVERYTHING)).c(0.5F)
				.a(SoundEffectType.a).c("pressurePlateWood"));
		REGISTRY.a(73, "redstone_ore", (new BlockRedstoneOre(false)).c(3.0F).b(5.0F).a(SoundEffectType.d).c("oreRedstone").a(CreativeModeTab.b));
		REGISTRY.a(74, "lit_redstone_ore", (new BlockRedstoneOre(true)).a(0.625F).c(3.0F).b(5.0F).a(SoundEffectType.d).c("oreRedstone"));
		REGISTRY.a(75, "unlit_redstone_torch", (new BlockRedstoneTorch(false)).c(0.0F).a(SoundEffectType.a).c("notGate"));
		REGISTRY.a(76, "redstone_torch", (new BlockRedstoneTorch(true)).c(0.0F).a(0.5F).a(SoundEffectType.a).c("notGate").a(CreativeModeTab.d));
		REGISTRY.a(77, "stone_button", (new BlockStoneButton()).c(0.5F).a(SoundEffectType.d).c("button"));
		REGISTRY.a(78, "snow_layer", (new BlockSnow()).c(0.1F).a(SoundEffectType.i).c("snow").e(0));
		REGISTRY.a(79, "ice", (new BlockIce()).c(0.5F).e(3).a(SoundEffectType.f).c("ice"));
		REGISTRY.a(80, "snow", (new BlockSnowBlock()).c(0.2F).a(SoundEffectType.i).c("snow"));
		REGISTRY.a(81, "cactus", (new BlockCactus()).c(0.4F).a(SoundEffectType.g).c("cactus"));
		REGISTRY.a(82, "clay", (new BlockClay()).c(0.6F).a(SoundEffectType.b).c("clay"));
		REGISTRY.a(83, "reeds", (new BlockReed()).c(0.0F).a(SoundEffectType.c).c("reeds").p());
		REGISTRY.a(84, "jukebox", (new BlockJukeBox()).c(2.0F).b(10.0F).a(SoundEffectType.d).c("jukebox"));
		REGISTRY.a(85, "fence", (new BlockFence(Material.WOOD, BlockWood.EnumLogVariant.OAK.c())).c(2.0F).b(5.0F).a(SoundEffectType.a).c("fence"));
		Block block7 = (new BlockPumpkin()).c(1.0F).a(SoundEffectType.a).c("pumpkin");
		REGISTRY.a(86, "pumpkin", block7);
		REGISTRY.a(87, "netherrack", (new BlockBloodStone()).c(0.4F).a(SoundEffectType.d).c("hellrock"));
		REGISTRY.a(88, "soul_sand", (new BlockSlowSand()).c(0.5F).a(SoundEffectType.h).c("hellsand"));
		REGISTRY.a(89, "glowstone", (new BlockLightStone(Material.SHATTERABLE)).c(0.3F).a(SoundEffectType.f).a(1.0F).c("lightgem"));
		REGISTRY.a(90, "portal", (new BlockPortal()).c(-1.0F).a(SoundEffectType.f).a(0.75F).c("portal"));
		REGISTRY.a(91, "lit_pumpkin", (new BlockPumpkin()).c(1.0F).a(SoundEffectType.a).a(1.0F).c("litpumpkin"));
		REGISTRY.a(92, "cake", (new BlockCake()).c(0.5F).a(SoundEffectType.g).c("cake").p());
		REGISTRY.a(93, "unpowered_repeater", (new BlockRepeater(false)).c(0.0F).a(SoundEffectType.a).c("diode").p());
		REGISTRY.a(94, "powered_repeater", (new BlockRepeater(true)).c(0.0F).a(SoundEffectType.a).c("diode").p());
		REGISTRY.a(95, "stained_glass", (new BlockStainedGlass(Material.SHATTERABLE)).c(0.3F).a(SoundEffectType.f).c("stainedGlass"));
		REGISTRY.a(96, "trapdoor", (new BlockTrapdoor(Material.WOOD)).c(3.0F).a(SoundEffectType.a).c("trapdoor").p());
		REGISTRY.a(97, "monster_egg", (new BlockMonsterEggs()).c(0.75F).c("monsterStoneEgg"));
		Block block8 = (new BlockSmoothBrick()).c(1.5F).b(10.0F).a(SoundEffectType.d).c("stonebricksmooth");
		REGISTRY.a(98, "stonebrick", block8);
		REGISTRY.a(99, "brown_mushroom_block",
				(new BlockHugeMushroom(Material.WOOD, MaterialMapColor.m, block3)).c(0.2F).a(SoundEffectType.a).c("mushroom"));
		REGISTRY.a(100, "red_mushroom_block",
				(new BlockHugeMushroom(Material.WOOD, MaterialMapColor.E, block4)).c(0.2F).a(SoundEffectType.a).c("mushroom"));
		REGISTRY.a(101, "iron_bars", (new BlockThin(Material.ORE, true)).c(5.0F).b(10.0F).a(SoundEffectType.e).c("fenceIron"));
		REGISTRY.a(102, "glass_pane", (new BlockThin(Material.SHATTERABLE, false)).c(0.3F).a(SoundEffectType.f).c("thinGlass"));
		Block block9 = (new BlockMelon()).c(1.0F).a(SoundEffectType.a).c("melon");
		REGISTRY.a(103, "melon_block", block9);
		REGISTRY.a(104, "pumpkin_stem", (new BlockStem(block7)).c(0.0F).a(SoundEffectType.a).c("pumpkinStem"));
		REGISTRY.a(105, "melon_stem", (new BlockStem(block9)).c(0.0F).a(SoundEffectType.a).c("pumpkinStem"));
		REGISTRY.a(106, "vine", (new BlockVine()).c(0.2F).a(SoundEffectType.c).c("vine"));
		REGISTRY.a(107, "fence_gate", (new BlockFenceGate(BlockWood.EnumLogVariant.OAK)).c(2.0F).b(5.0F).a(SoundEffectType.a).c("fenceGate"));
		REGISTRY.a(108, "brick_stairs", (new BlockStairs(block5.getBlockData())).c("stairsBrick"));
		REGISTRY.a(109, "stone_brick_stairs",
				(new BlockStairs(block8.getBlockData().set(BlockSmoothBrick.VARIANT, BlockSmoothBrick.EnumStonebrickType.DEFAULT)))
						.c("stairsStoneBrickSmooth"));
		REGISTRY.a(110, "mycelium", (new BlockMycel()).c(0.6F).a(SoundEffectType.c).c("mycel"));
		REGISTRY.a(111, "waterlily", (new BlockWaterLily()).c(0.0F).a(SoundEffectType.c).c("waterlily"));
		Block block10 = (new BlockNetherbrick()).c(2.0F).b(10.0F).a(SoundEffectType.d).c("netherBrick").a(CreativeModeTab.b);
		REGISTRY.a(112, "nether_brick", block10);
		REGISTRY.a(113, "nether_brick_fence",
				(new BlockFence(Material.STONE, MaterialMapColor.L)).c(2.0F).b(10.0F).a(SoundEffectType.d).c("netherFence"));
		REGISTRY.a(114, "nether_brick_stairs", (new BlockStairs(block10.getBlockData())).c("stairsNetherBrick"));
		REGISTRY.a(115, "nether_wart", (new BlockNetherWart()).c("netherStalk"));
		REGISTRY.a(116, "enchanting_table", (new BlockEnchantmentTable()).c(5.0F).b(2000.0F).c("enchantmentTable"));
		REGISTRY.a(117, "brewing_stand", (new BlockBrewingStand()).c(0.5F).a(0.125F).c("brewingStand"));
		REGISTRY.a(118, "cauldron", (new BlockCauldron()).c(2.0F).c("cauldron"));
		REGISTRY.a(119, "end_portal", (new BlockEnderPortal(Material.PORTAL)).c(-1.0F).b(6000000.0F));
		REGISTRY.a(120, "end_portal_frame",
				(new BlockEnderPortalFrame()).a(SoundEffectType.f).a(0.125F).c(-1.0F).c("endPortalFrame").b(6000000.0F).a(CreativeModeTab.c));
		REGISTRY.a(121, "end_stone",
				(new Block(Material.STONE, MaterialMapColor.e)).c(3.0F).b(15.0F).a(SoundEffectType.d).c("whiteStone").a(CreativeModeTab.b));
		REGISTRY.a(122, "dragon_egg", (new BlockDragonEgg()).c(3.0F).b(15.0F).a(SoundEffectType.d).a(0.125F).c("dragonEgg"));
		REGISTRY.a(123, "redstone_lamp", (new BlockRedstoneLamp(false)).c(0.3F).a(SoundEffectType.f).c("redstoneLight").a(CreativeModeTab.d));
		REGISTRY.a(124, "lit_redstone_lamp", (new BlockRedstoneLamp(true)).c(0.3F).a(SoundEffectType.f).c("redstoneLight"));
		REGISTRY.a(125, "double_wooden_slab", (new BlockDoubleWoodStep()).c(2.0F).b(5.0F).a(SoundEffectType.a).c("woodSlab"));
		REGISTRY.a(126, "wooden_slab", (new BlockWoodStep()).c(2.0F).b(5.0F).a(SoundEffectType.a).c("woodSlab"));
		REGISTRY.a(127, "cocoa", (new BlockCocoa()).c(0.2F).b(5.0F).a(SoundEffectType.a).c("cocoa"));
		REGISTRY.a(128, "sandstone_stairs",
				(new BlockStairs(block2.getBlockData().set(BlockSandStone.TYPE, BlockSandStone.EnumSandstoneVariant.SMOOTH))).c("stairsSandStone"));
		REGISTRY.a(129, "emerald_ore", (new BlockOre()).c(3.0F).b(5.0F).a(SoundEffectType.d).c("oreEmerald"));
		REGISTRY.a(130, "ender_chest", (new BlockEnderChest()).c(22.5F).b(1000.0F).a(SoundEffectType.d).c("enderChest").a(0.5F));
		REGISTRY.a(131, "tripwire_hook", (new BlockTripwireHook()).c("tripWireSource"));
		REGISTRY.a(132, "tripwire", (new BlockTripwire()).c("tripWire"));
		REGISTRY.a(133, "emerald_block",
				(new Block(Material.ORE, MaterialMapColor.J)).c(5.0F).b(10.0F).a(SoundEffectType.e).c("blockEmerald").a(CreativeModeTab.b));
		REGISTRY.a(134, "spruce_stairs",
				(new BlockStairs(block1.getBlockData().set(BlockWood.VARIANT, BlockWood.EnumLogVariant.SPRUCE))).c("stairsWoodSpruce"));
		REGISTRY.a(135, "birch_stairs",
				(new BlockStairs(block1.getBlockData().set(BlockWood.VARIANT, BlockWood.EnumLogVariant.BIRCH))).c("stairsWoodBirch"));
		REGISTRY.a(136, "jungle_stairs",
				(new BlockStairs(block1.getBlockData().set(BlockWood.VARIANT, BlockWood.EnumLogVariant.JUNGLE))).c("stairsWoodJungle"));
		REGISTRY.a(137, "command_block", (new BlockCommand(MaterialMapColor.C)).j().b(6000000.0F).c("commandBlock"));
		REGISTRY.a(138, "beacon", (new BlockBeacon()).c("beacon").a(1.0F));
		REGISTRY.a(139, "cobblestone_wall", (new BlockCobbleWall(block)).c("cobbleWall"));
		REGISTRY.a(140, "flower_pot", (new BlockFlowerPot()).c(0.0F).a(SoundEffectType.d).c("flowerPot"));
		REGISTRY.a(141, "carrots", (new BlockCarrots()).c("carrots"));
		REGISTRY.a(142, "potatoes", (new BlockPotatoes()).c("potatoes"));
		REGISTRY.a(143, "wooden_button", (new BlockWoodButton()).c(0.5F).a(SoundEffectType.a).c("button"));
		REGISTRY.a(144, "skull", (new BlockSkull()).c(1.0F).a(SoundEffectType.d).c("skull"));
		REGISTRY.a(145, "anvil", (new BlockAnvil()).c(5.0F).a(SoundEffectType.k).b(2000.0F).c("anvil"));
		REGISTRY.a(146, "trapped_chest", (new BlockChest(BlockChest.Type.TRAP)).c(2.5F).a(SoundEffectType.a).c("chestTrap"));
		REGISTRY.a(147, "light_weighted_pressure_plate",
				(new BlockPressurePlateWeighted(Material.ORE, 15, MaterialMapColor.G)).c(0.5F).a(SoundEffectType.a).c("weightedPlate_light"));
		REGISTRY.a(148, "heavy_weighted_pressure_plate",
				(new BlockPressurePlateWeighted(Material.ORE, 150)).c(0.5F).a(SoundEffectType.a).c("weightedPlate_heavy"));
		REGISTRY.a(149, "unpowered_comparator", (new BlockRedstoneComparator(false)).c(0.0F).a(SoundEffectType.a).c("comparator").p());
		REGISTRY.a(150, "powered_comparator", (new BlockRedstoneComparator(true)).c(0.0F).a(0.625F).a(SoundEffectType.a).c("comparator").p());
		REGISTRY.a(151, "daylight_detector", new BlockDaylightDetector(false));
		REGISTRY.a(152, "redstone_block",
				(new BlockPowered(Material.ORE, MaterialMapColor.g)).c(5.0F).b(10.0F).a(SoundEffectType.e).c("blockRedstone").a(CreativeModeTab.d));
		REGISTRY.a(153, "quartz_ore", (new BlockOre(MaterialMapColor.L)).c(3.0F).b(5.0F).a(SoundEffectType.d).c("netherquartz"));
		REGISTRY.a(154, "hopper", (new BlockHopper()).c(3.0F).b(8.0F).a(SoundEffectType.e).c("hopper"));
		Block block11 = (new BlockQuartz()).a(SoundEffectType.d).c(0.8F).c("quartzBlock");
		REGISTRY.a(155, "quartz_block", block11);
		REGISTRY.a(156, "quartz_stairs",
				(new BlockStairs(block11.getBlockData().set(BlockQuartz.VARIANT, BlockQuartz.EnumQuartzVariant.DEFAULT))).c("stairsQuartz"));
		REGISTRY.a(157, "activator_rail", (new BlockPoweredRail()).c(0.7F).a(SoundEffectType.e).c("activatorRail"));
		REGISTRY.a(158, "dropper", (new BlockDropper()).c(3.5F).a(SoundEffectType.d).c("dropper"));
		REGISTRY.a(159, "stained_hardened_clay", (new BlockStainedHardenedClay()).c(1.25F).b(7.0F).a(SoundEffectType.d).c("clayHardenedStained"));
		REGISTRY.a(160, "stained_glass_pane", (new BlockStainedGlassPane()).c(0.3F).a(SoundEffectType.f).c("thinStainedGlass"));
		REGISTRY.a(161, "leaves2", (new BlockLeaves2()).c("leaves"));
		REGISTRY.a(162, "log2", (new BlockLog2()).c("log"));
		REGISTRY.a(163, "acacia_stairs",
				(new BlockStairs(block1.getBlockData().set(BlockWood.VARIANT, BlockWood.EnumLogVariant.ACACIA))).c("stairsWoodAcacia"));
		REGISTRY.a(164, "dark_oak_stairs",
				(new BlockStairs(block1.getBlockData().set(BlockWood.VARIANT, BlockWood.EnumLogVariant.DARK_OAK))).c("stairsWoodDarkOak"));
		REGISTRY.a(165, "slime", (new BlockSlime()).c("slime").a(SoundEffectType.l));
		REGISTRY.a(166, "barrier", (new BlockBarrier()).c("barrier"));
		REGISTRY.a(167, "iron_trapdoor", (new BlockTrapdoor(Material.ORE)).c(5.0F).a(SoundEffectType.e).c("ironTrapdoor").p());
		REGISTRY.a(168, "prismarine", (new BlockPrismarine()).c(1.5F).b(10.0F).a(SoundEffectType.d).c("prismarine"));
		REGISTRY.a(169, "sea_lantern", (new BlockSeaLantern(Material.SHATTERABLE)).c(0.3F).a(SoundEffectType.f).a(1.0F).c("seaLantern"));
		REGISTRY.a(170, "hay_block", (new BlockHay()).c(0.5F).a(SoundEffectType.c).c("hayBlock").a(CreativeModeTab.b));
		REGISTRY.a(171, "carpet", (new BlockCarpet()).c(0.1F).a(SoundEffectType.g).c("woolCarpet").e(0));
		REGISTRY.a(172, "hardened_clay", (new BlockHardenedClay()).c(1.25F).b(7.0F).a(SoundEffectType.d).c("clayHardened"));
		REGISTRY.a(173, "coal_block",
				(new Block(Material.STONE, MaterialMapColor.F)).c(5.0F).b(10.0F).a(SoundEffectType.d).c("blockCoal").a(CreativeModeTab.b));
		REGISTRY.a(174, "packed_ice", (new BlockPackedIce()).c(0.5F).a(SoundEffectType.f).c("icePacked"));
		REGISTRY.a(175, "double_plant", new BlockTallPlant());
		REGISTRY.a(176, "standing_banner", (new BlockBanner.BlockStandingBanner()).c(1.0F).a(SoundEffectType.a).c("banner").p());
		REGISTRY.a(177, "wall_banner", (new BlockBanner.BlockWallBanner()).c(1.0F).a(SoundEffectType.a).c("banner").p());
		REGISTRY.a(178, "daylight_detector_inverted", new BlockDaylightDetector(true));
		Block block12 = (new BlockRedSandstone()).a(SoundEffectType.d).c(0.8F).c("redSandStone");
		REGISTRY.a(179, "red_sandstone", block12);
		REGISTRY.a(180, "red_sandstone_stairs",
				(new BlockStairs(block12.getBlockData().set(BlockRedSandstone.TYPE, BlockRedSandstone.EnumRedSandstoneVariant.SMOOTH)))
						.c("stairsRedSandStone"));
		REGISTRY.a(181, "double_stone_slab2", (new BlockDoubleStoneStep2()).c(2.0F).b(10.0F).a(SoundEffectType.d).c("stoneSlab2"));
		REGISTRY.a(182, "stone_slab2", (new BlockStoneStep2()).c(2.0F).b(10.0F).a(SoundEffectType.d).c("stoneSlab2"));
		REGISTRY.a(183, "spruce_fence_gate",
				(new BlockFenceGate(BlockWood.EnumLogVariant.SPRUCE)).c(2.0F).b(5.0F).a(SoundEffectType.a).c("spruceFenceGate"));
		REGISTRY.a(184, "birch_fence_gate",
				(new BlockFenceGate(BlockWood.EnumLogVariant.BIRCH)).c(2.0F).b(5.0F).a(SoundEffectType.a).c("birchFenceGate"));
		REGISTRY.a(185, "jungle_fence_gate",
				(new BlockFenceGate(BlockWood.EnumLogVariant.JUNGLE)).c(2.0F).b(5.0F).a(SoundEffectType.a).c("jungleFenceGate"));
		REGISTRY.a(186, "dark_oak_fence_gate",
				(new BlockFenceGate(BlockWood.EnumLogVariant.DARK_OAK)).c(2.0F).b(5.0F).a(SoundEffectType.a).c("darkOakFenceGate"));
		REGISTRY.a(187, "acacia_fence_gate",
				(new BlockFenceGate(BlockWood.EnumLogVariant.ACACIA)).c(2.0F).b(5.0F).a(SoundEffectType.a).c("acaciaFenceGate"));
		REGISTRY.a(188, "spruce_fence",
				(new BlockFence(Material.WOOD, BlockWood.EnumLogVariant.SPRUCE.c())).c(2.0F).b(5.0F).a(SoundEffectType.a).c("spruceFence"));
		REGISTRY.a(189, "birch_fence",
				(new BlockFence(Material.WOOD, BlockWood.EnumLogVariant.BIRCH.c())).c(2.0F).b(5.0F).a(SoundEffectType.a).c("birchFence"));
		REGISTRY.a(190, "jungle_fence",
				(new BlockFence(Material.WOOD, BlockWood.EnumLogVariant.JUNGLE.c())).c(2.0F).b(5.0F).a(SoundEffectType.a).c("jungleFence"));
		REGISTRY.a(191, "dark_oak_fence",
				(new BlockFence(Material.WOOD, BlockWood.EnumLogVariant.DARK_OAK.c())).c(2.0F).b(5.0F).a(SoundEffectType.a).c("darkOakFence"));
		REGISTRY.a(192, "acacia_fence",
				(new BlockFence(Material.WOOD, BlockWood.EnumLogVariant.ACACIA.c())).c(2.0F).b(5.0F).a(SoundEffectType.a).c("acaciaFence"));
		REGISTRY.a(193, "spruce_door", (new BlockDoor(Material.WOOD)).c(3.0F).a(SoundEffectType.a).c("doorSpruce").p());
		REGISTRY.a(194, "birch_door", (new BlockDoor(Material.WOOD)).c(3.0F).a(SoundEffectType.a).c("doorBirch").p());
		REGISTRY.a(195, "jungle_door", (new BlockDoor(Material.WOOD)).c(3.0F).a(SoundEffectType.a).c("doorJungle").p());
		REGISTRY.a(196, "acacia_door", (new BlockDoor(Material.WOOD)).c(3.0F).a(SoundEffectType.a).c("doorAcacia").p());
		REGISTRY.a(197, "dark_oak_door", (new BlockDoor(Material.WOOD)).c(3.0F).a(SoundEffectType.a).c("doorDarkOak").p());
		REGISTRY.a(198, "end_rod", (new BlockEndRod()).c(0.0F).a(0.9375F).a(SoundEffectType.a).c("endRod"));
		REGISTRY.a(199, "chorus_plant", (new BlockChorusFruit()).c(0.4F).a(SoundEffectType.a).c("chorusPlant"));
		REGISTRY.a(200, "chorus_flower", (new BlockChorusFlower()).c(0.4F).a(SoundEffectType.a).c("chorusFlower"));
		Block block13 = (new Block(Material.STONE, MaterialMapColor.s)).c(1.5F).b(10.0F).a(SoundEffectType.d).a(CreativeModeTab.b).c("purpurBlock");
		REGISTRY.a(201, "purpur_block", block13);
		REGISTRY.a(202, "purpur_pillar", (new BlockRotatable(Material.STONE, MaterialMapColor.s)).c(1.5F).b(10.0F).a(SoundEffectType.d)
				.a(CreativeModeTab.b).c("purpurPillar"));
		REGISTRY.a(203, "purpur_stairs", (new BlockStairs(block13.getBlockData())).c("stairsPurpur"));
		REGISTRY.a(204, "purpur_double_slab", (new BlockPurpurSlab.Default()).c(2.0F).b(10.0F).a(SoundEffectType.d).c("purpurSlab"));
		REGISTRY.a(205, "purpur_slab", (new BlockPurpurSlab.Half()).c(2.0F).b(10.0F).a(SoundEffectType.d).c("purpurSlab"));
		REGISTRY.a(206, "end_bricks",
				(new Block(Material.STONE, MaterialMapColor.e)).a(SoundEffectType.d).c(0.8F).a(CreativeModeTab.b).c("endBricks"));
		REGISTRY.a(207, "beetroots", (new BlockBeetroot()).c("beetroots"));
		Block block14 = (new BlockGrassPath()).c(0.65F).a(SoundEffectType.c).c("grassPath").p();
		REGISTRY.a(208, "grass_path", block14);
		REGISTRY.a(209, "end_gateway", (new BlockEndGateway(Material.PORTAL)).c(-1.0F).b(6000000.0F));
		REGISTRY.a(210, "repeating_command_block", (new BlockCommand(MaterialMapColor.A)).j().b(6000000.0F).c("repeatingCommandBlock"));
		REGISTRY.a(211, "chain_command_block", (new BlockCommand(MaterialMapColor.D)).j().b(6000000.0F).c("chainCommandBlock"));
		REGISTRY.a(212, "frosted_ice", (new BlockIceFrost()).c(0.5F).e(3).a(SoundEffectType.f).c("frostedIce"));
		REGISTRY.a(213, "magma", (new BlockMagma()).c(0.5F).a(SoundEffectType.d).c("magma"));
		REGISTRY.a(214, "nether_wart_block",
				(new Block(Material.GRASS, MaterialMapColor.E)).a(CreativeModeTab.b).c(1.0F).a(SoundEffectType.a).c("netherWartBlock"));
		REGISTRY.a(215, "red_nether_brick", (new BlockNetherbrick()).c(2.0F).b(10.0F).a(SoundEffectType.d).c("redNetherBrick").a(CreativeModeTab.b));
		REGISTRY.a(216, "bone_block", (new BlockBone()).c("boneBlock"));
		REGISTRY.a(217, "structure_void", (new BlockStructureVoid()).c("structureVoid"));
		REGISTRY.a(218, "observer", (new BlockObserver()).c(3.0F).c("observer"));
		REGISTRY.a(219, "white_shulker_box", (new BlockShulkerBox(EnumColor.WHITE)).c(2.0F).a(SoundEffectType.d).c("shulkerBoxWhite"));
		REGISTRY.a(220, "orange_shulker_box", (new BlockShulkerBox(EnumColor.ORANGE)).c(2.0F).a(SoundEffectType.d).c("shulkerBoxOrange"));
		REGISTRY.a(221, "magenta_shulker_box", (new BlockShulkerBox(EnumColor.MAGENTA)).c(2.0F).a(SoundEffectType.d).c("shulkerBoxMagenta"));
		REGISTRY.a(222, "light_blue_shulker_box", (new BlockShulkerBox(EnumColor.LIGHT_BLUE)).c(2.0F).a(SoundEffectType.d).c("shulkerBoxLightBlue"));
		REGISTRY.a(223, "yellow_shulker_box", (new BlockShulkerBox(EnumColor.YELLOW)).c(2.0F).a(SoundEffectType.d).c("shulkerBoxYellow"));
		REGISTRY.a(224, "lime_shulker_box", (new BlockShulkerBox(EnumColor.LIME)).c(2.0F).a(SoundEffectType.d).c("shulkerBoxLime"));
		REGISTRY.a(225, "pink_shulker_box", (new BlockShulkerBox(EnumColor.PINK)).c(2.0F).a(SoundEffectType.d).c("shulkerBoxPink"));
		REGISTRY.a(226, "gray_shulker_box", (new BlockShulkerBox(EnumColor.GRAY)).c(2.0F).a(SoundEffectType.d).c("shulkerBoxGray"));
		REGISTRY.a(227, "silver_shulker_box", (new BlockShulkerBox(EnumColor.SILVER)).c(2.0F).a(SoundEffectType.d).c("shulkerBoxSilver"));
		REGISTRY.a(228, "cyan_shulker_box", (new BlockShulkerBox(EnumColor.CYAN)).c(2.0F).a(SoundEffectType.d).c("shulkerBoxCyan"));
		REGISTRY.a(229, "purple_shulker_box", (new BlockShulkerBox(EnumColor.PURPLE)).c(2.0F).a(SoundEffectType.d).c("shulkerBoxPurple"));
		REGISTRY.a(230, "blue_shulker_box", (new BlockShulkerBox(EnumColor.BLUE)).c(2.0F).a(SoundEffectType.d).c("shulkerBoxBlue"));
		REGISTRY.a(231, "brown_shulker_box", (new BlockShulkerBox(EnumColor.BROWN)).c(2.0F).a(SoundEffectType.d).c("shulkerBoxBrown"));
		REGISTRY.a(232, "green_shulker_box", (new BlockShulkerBox(EnumColor.GREEN)).c(2.0F).a(SoundEffectType.d).c("shulkerBoxGreen"));
		REGISTRY.a(233, "red_shulker_box", (new BlockShulkerBox(EnumColor.RED)).c(2.0F).a(SoundEffectType.d).c("shulkerBoxRed"));
		REGISTRY.a(234, "black_shulker_box", (new BlockShulkerBox(EnumColor.BLACK)).c(2.0F).a(SoundEffectType.d).c("shulkerBoxBlack"));
		REGISTRY.a(235, "white_glazed_terracotta", new BlockGlazedTerracotta(EnumColor.WHITE));
		REGISTRY.a(236, "orange_glazed_terracotta", new BlockGlazedTerracotta(EnumColor.ORANGE));
		REGISTRY.a(237, "magenta_glazed_terracotta", new BlockGlazedTerracotta(EnumColor.MAGENTA));
		REGISTRY.a(238, "light_blue_glazed_terracotta", new BlockGlazedTerracotta(EnumColor.LIGHT_BLUE));
		REGISTRY.a(239, "yellow_glazed_terracotta", new BlockGlazedTerracotta(EnumColor.YELLOW));
		REGISTRY.a(240, "lime_glazed_terracotta", new BlockGlazedTerracotta(EnumColor.LIME));
		REGISTRY.a(241, "pink_glazed_terracotta", new BlockGlazedTerracotta(EnumColor.PINK));
		REGISTRY.a(242, "gray_glazed_terracotta", new BlockGlazedTerracotta(EnumColor.GRAY));
		REGISTRY.a(243, "silver_glazed_terracotta", new BlockGlazedTerracotta(EnumColor.SILVER));
		REGISTRY.a(244, "cyan_glazed_terracotta", new BlockGlazedTerracotta(EnumColor.CYAN));
		REGISTRY.a(245, "purple_glazed_terracotta", new BlockGlazedTerracotta(EnumColor.PURPLE));
		REGISTRY.a(246, "blue_glazed_terracotta", new BlockGlazedTerracotta(EnumColor.BLUE));
		REGISTRY.a(247, "brown_glazed_terracotta", new BlockGlazedTerracotta(EnumColor.BROWN));
		REGISTRY.a(248, "green_glazed_terracotta", new BlockGlazedTerracotta(EnumColor.GREEN));
		REGISTRY.a(249, "red_glazed_terracotta", new BlockGlazedTerracotta(EnumColor.RED));
		REGISTRY.a(250, "black_glazed_terracotta", new BlockGlazedTerracotta(EnumColor.BLACK));
		REGISTRY.a(251, "concrete", (new BlockCloth(Material.STONE)).c(1.8F).a(SoundEffectType.d).c("concrete"));
		REGISTRY.a(252, "concrete_powder", (new BlockConcretePowder()).c(0.5F).a(SoundEffectType.h).c("concretePowder"));
		REGISTRY.a(255, "structure_block", (new BlockStructure()).j().b(6000000.0F).c("structureBlock"));
		REGISTRY.a();
		Iterator iterator = REGISTRY.iterator();

		while (iterator.hasNext()) {
			Block block15 = (Block) iterator.next();

			if (block15.material == Material.AIR) {
				block15.p = false;
			} else {
				boolean flag = false;
				boolean flag1 = block15 instanceof BlockStairs;
				boolean flag2 = block15 instanceof BlockStepAbstract;
				boolean flag3 = block15 == block6 || block15 == block14;
				boolean flag4 = block15.n;
				boolean flag5 = block15.m == 0;

				if (flag1 || flag2 || flag3 || flag4 || flag5) {
					flag = true;
				}

				block15.p = flag;
			}
		}

		HashSet hashset = Sets.newHashSet(new Block[]{(Block) REGISTRY.get(new MinecraftKey("tripwire"))});
		Iterator iterator1 = REGISTRY.iterator();

		while (iterator1.hasNext()) {
			Block block16 = (Block) iterator1.next();

			if (hashset.contains(block16)) {
				for (int i = 0; i < 15; ++i) {
					int j = REGISTRY.a(block16) << 4 | i; // CraftBukkit -
															// decompile
															// error

					Block.REGISTRY_ID.a(block16.fromLegacyData(i), j);
				}
			} else {
				UnmodifiableIterator unmodifiableiterator = block16.s().a().iterator();

				while (unmodifiableiterator.hasNext()) {
					IBlockData iblockdata = (IBlockData) unmodifiableiterator.next();
					int k = REGISTRY.a(block16) << 4 | block16.toLegacyData(iblockdata); // CraftBukkit
																							// -
																							// decompile
																							// error

					Block.REGISTRY_ID.a(iblockdata, k);
				}
			}
		}

	}

	// CraftBukkit start
	public int getExpDrop(World world, IBlockData data, int enchantmentLevel) {
		return 0;
	}
	// CraftBukkit end

	// Spigot start
	public static float range(float min, float value, float max) {
		if (value < min) {
			return min;
		}
		if (value > max) {
			return max;
		}
		return value;
	}
	// Spigot end

	public static enum EnumRandomOffset {

		NONE, XZ, XYZ;

		private EnumRandomOffset() {
		}
	}
}
