package io.github.noeppi_noeppi.mods.nextchristmas.biscuit;

import io.github.noeppi_noeppi.libx.block.DirectionShape;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.BlockTE;
import io.github.noeppi_noeppi.libx.mod.registration.TileEntityBase;
import io.github.noeppi_noeppi.mods.nextchristmas.ModModels;
import io.github.noeppi_noeppi.mods.nextchristmas.util.ItemStackRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockBakingSheet extends BlockTE<TileEntityBase> {

    private final ItemStack fillStack;
    private final ResourceLocation fillModel;
    private final boolean big;

    private static final DirectionShape shape = new DirectionShape(VoxelShapes.or(
            makeCuboidShape(2.5, 0, 4, 13.5, 0.25, 13.5),
            makeCuboidShape(2, 0, 13.5, 14, 0.75, 14),
            makeCuboidShape(2, 0, 4, 2.5, 0.75, 13.5),
            makeCuboidShape(13.5, 0, 4, 14, 0.75, 13.5)
    ));

    public BlockBakingSheet(ModX mod, IItemProvider fillStack, boolean big, Properties properties) {
        this(mod, new ItemStack(fillStack), big, properties);
    }

    public BlockBakingSheet(ModX mod, ItemStack fillStack, boolean big, Properties properties) {
        this(mod, fillStack, big, properties, new Item.Properties());
    }

    public BlockBakingSheet(ModX mod, IItemProvider fillStack, boolean big, Properties properties, Item.Properties itemProperties) {
        this(mod, new ItemStack(fillStack), big, properties, itemProperties);
    }

    public BlockBakingSheet(ModX mod, ItemStack fillStack, boolean big, Properties properties, Item.Properties itemProperties) {
        super(mod, TileEntityBase.class, properties, itemProperties.setISTER(() -> () -> ItemStackRenderer.INSTANCE).maxStackSize(1));
        this.fillStack = fillStack;
        this.fillModel = null;
        this.big = big;
    }

    public BlockBakingSheet(ModX mod, ResourceLocation fillModel, boolean big, Properties properties) {
        this(mod, fillModel, big, properties, new Item.Properties());
    }

    public BlockBakingSheet(ModX mod, ResourceLocation fillModel, boolean big, Properties properties, Item.Properties itemProperties) {
        super(mod, TileEntityBase.class, properties, itemProperties.setISTER(() -> () -> ItemStackRenderer.INSTANCE).maxStackSize(1));
        this.fillStack = new ItemStack(Items.BARRIER);
        this.fillModel = fillModel;
        this.big = big;

        DistExecutor.unsafeRunForDist(() -> () -> {
            ModModels.registerModel(this.fillModel);
            return null;
        }, () -> () -> null);
    }

    @Override
    public void registerClient(ResourceLocation id) {
        RenderTypeLookup.setRenderLayer(this, RenderType.getCutout());
        if (this.fillModel != null || !this.fillStack.isEmpty()) {
            ClientRegistry.bindTileEntityRenderer(this.getTileType(), dispatcher -> new RenderBakingSheet(dispatcher, this));
            ItemStackRenderer.addRenderTile(this.getTileType());
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return shape.getShape(state.get(BlockStateProperties.HORIZONTAL_FACING));
    }

    public ItemStack getFillStack() {
        return this.fillStack;
    }

    public IBakedModel getFillModel(@Nullable World world) {
        return this.fillModel != null ? ModModels.getModel(this.fillModel) : Minecraft.getInstance().getItemRenderer().getItemModelWithOverrides(this.fillStack, world, null);
    }

    public boolean isBig() {
        return this.big;
    }
}