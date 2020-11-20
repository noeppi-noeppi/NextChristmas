package io.github.noeppi_noeppi.mods.nextchristmas.mill;

import io.github.noeppi_noeppi.libx.crafting.recipe.RecipeHelper;
import io.github.noeppi_noeppi.libx.inventory.BaseItemStackHandler;
import io.github.noeppi_noeppi.libx.inventory.ItemStackHandlerWrapper;
import io.github.noeppi_noeppi.libx.mod.registration.TileEntityBase;
import io.github.noeppi_noeppi.mods.nextchristmas.ModRecipes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;

public class TileMill extends TileEntityBase implements ITickableTileEntity {

    public static final int MILL_TICKS_MAX = 40;

    public static final int SLOT_IN = 0;
    public static final int SLOT_OUT = 1;

    private final BaseItemStackHandler handler;
    private final LazyOptional<IItemHandlerModifiable> cap;

    private int millClicks = 0;
    private int millTicks = 0;
    private transient MillRecipe recipe = null;

    public TileMill(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        this.handler = new BaseItemStackHandler(2, (slot) -> {
            this.updateRecipes();
            this.markDirty();
            this.markDispatchable();
        }, (slot, stack) -> {
            if (slot == SLOT_IN) {
                return this.world != null && RecipeHelper.isItemValidInput(this.world.getRecipeManager(), ModRecipes.MILL, stack);
            }
            return false;
        });

        this.handler.addSlotLimit(SLOT_IN, 1);
        this.handler.addSlotLimit(SLOT_OUT, 1);

        this.handler.setInputSlots(SLOT_IN);

        this.cap = ItemStackHandlerWrapper.create(this.handler, slot -> slot == SLOT_OUT, (slot, stack) -> slot == SLOT_IN && this.handler.getStackInSlot(SLOT_OUT).isEmpty());
    }

    @Override
    public void tick() {
        if (this.millTicks > 0 && this.millTicks < MILL_TICKS_MAX) {
            this.millTicks += 1;
        }

        if (this.world != null && !this.world.isRemote) {
            if (this.recipe == null) {
                this.millClicks = 0;
                this.millTicks = 0;
            } else if (this.millTicks >= MILL_TICKS_MAX) {
                this.millTicks = 0;
                this.millClicks += 1;
                if (this.millClicks >= this.recipe.getMillClicks()) {
                    ItemStack output = this.recipe.getRecipeOutput().copy();
                    this.handler.setStackInSlot(SLOT_IN, ItemStack.EMPTY);
                    this.handler.setStackInSlot(SLOT_OUT, output);
                    this.millClicks = 0;
                    this.millTicks = 0;
                    this.updateRecipes();
                }
            }
        }
    }

    private void updateRecipes() {
        if (this.world != null) {
            if (this.handler.getStackInSlot(SLOT_IN).isEmpty() || !this.handler.getStackInSlot(SLOT_OUT).isEmpty()) {
                this.recipe = null;
            } else {
                this.recipe = this.world.getRecipeManager().getRecipesForType(ModRecipes.MILL).stream().filter(r -> RecipeHelper.matches(r, Collections.singletonList(this.handler.getStackInSlot(SLOT_IN)), true)).findFirst().orElse(null);
            }
        }
    }

    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return this.cap.cast();
        } else {
            return super.getCapability(cap, side);
        }
    }

    @Override
    public void read(@Nonnull BlockState state, @Nonnull CompoundNBT nbt) {
        super.read(state, nbt);
        if (nbt.contains("inventory", Constants.NBT.TAG_COMPOUND)) {
            this.handler.deserializeNBT(nbt.getCompound("inventory"));
        }
        this.millClicks = nbt.getInt("millClicks");
        this.millTicks = nbt.getInt("millTicks");
        this.updateRecipes();
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT nbt) {
        nbt.put("inventory", this.handler.serializeNBT());
        nbt.putInt("millClicks", this.millClicks);
        nbt.putInt("millTicks", this.millTicks);
        return super.write(nbt);
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = super.getUpdateTag();
        nbt.put("inventory", this.handler.serializeNBT());
        nbt.putInt("millClicks", this.millClicks);
        nbt.putInt("millTicks", this.millTicks);
        return nbt;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
        super.handleUpdateTag(state, nbt);
        if (this.world != null && this.world.isRemote && nbt.contains("inventory", Constants.NBT.TAG_COMPOUND)) {
            this.handler.deserializeNBT(nbt.getCompound("inventory"));
            this.millClicks = nbt.getInt("millClicks");
            this.millTicks = nbt.getInt("millTicks");
            this.updateRecipes();
        }
    }

    public int getMillTicks() {
        return this.millTicks;
    }

    public boolean startMilling(PlayerEntity player, Hand hand) {
        if (this.millTicks <= 0 && this.recipe != null) {
            this.millTicks = 1;
            this.markDirty();
            this.markDispatchable();
            player.swing(hand, true);
            return true;
        }
        return false;
    }

    public IItemHandlerModifiable getInventory() {
        return this.handler;
    }

    public float getProgress() {
        if (this.recipe == null) {
            return 0;
        } else {
            return MathHelper.clamp((this.millClicks + ((this.millTicks / (float) MILL_TICKS_MAX) / (float) this.recipe.getMillClicks())) / (float) this.recipe.getMillClicks(), 0, 1);
        }
    }

    public MillRecipe getRecipe() {
        return this.recipe;
    }

    public boolean itemClick(PlayerEntity player, Hand hand, int slot) {
        if (!this.handler.getStackInSlot(slot).isEmpty()) {
            ItemStack held = player.getHeldItem(hand);
            ItemStack inv = this.handler.getStackInSlot(slot).copy();
            if (held.isEmpty() || (ItemStack.areItemsEqual(held, inv) && ItemStack.areItemStackTagsEqual(held, inv) && held.getCount() + inv.getCount() <= held.getMaxStackSize())) {
                if (held.isEmpty()) {
                    held = inv;
                } else {
                    held.grow(inv.getCount());
                }
                player.setHeldItem(hand, held.copy());
                this.handler.setStackInSlot(slot, ItemStack.EMPTY);
                player.swing(hand, true);
                return true;
            }
            return false;
        } else if (slot == SLOT_IN) {
            ItemStack held = player.getHeldItem(hand);
            if (!held.isEmpty() && this.handler.isItemValid(slot, held)) {
                this.handler.setStackInSlot(slot, held.split(1));
                player.setHeldItem(hand, held);
                player.swing(hand, true);
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.updateRecipes();
    }
}
