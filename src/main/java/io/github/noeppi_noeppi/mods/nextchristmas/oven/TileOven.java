package io.github.noeppi_noeppi.mods.nextchristmas.oven;

import io.github.noeppi_noeppi.libx.crafting.recipe.RecipeHelper;
import io.github.noeppi_noeppi.libx.inventory.BaseItemStackHandler;
import io.github.noeppi_noeppi.libx.inventory.ItemStackHandlerWrapper;
import io.github.noeppi_noeppi.libx.mod.registration.TileEntityBase;
import io.github.noeppi_noeppi.mods.nextchristmas.ModRecipes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;

public class TileOven extends TileEntityBase implements ITickableTileEntity {

    public static final int SLOT_FUEL = 0;
    public static final int SLOT_IN1 = 1;
    public static final int SLOT_IN2 = 2;
    public static final int SLOT_OUT1 = 3;
    public static final int SLOT_OUT2 = 4;

    private final BaseItemStackHandler handler;
    private final LazyOptional<IItemHandlerModifiable> cap;
    private int fuelTicksMax = 0;
    private int fuelTicks = 0;
    private int cooking1 = 0;
    private int cooking2 = 0;
    private int openTicks = 0;
    private int playersWatching = 0;
    private transient OvenRecipe recipe1 = null;
    private transient OvenRecipe recipe2 = null;

    public TileOven(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        this.handler = new BaseItemStackHandler(5, (slot) -> {
            this.updateRecipes();
            this.markDirty();
            this.markDispatchable();
        }, (slot, stack) -> {
            switch (slot) {
                case SLOT_FUEL:
                    return ForgeHooks.getBurnTime(stack) > 0;
                case SLOT_IN1:
                case SLOT_IN2:
                    return this.world != null && RecipeHelper.isItemValidInput(this.world.getRecipeManager(), ModRecipes.OVEN, stack);
                default:
                    return false;
            }
        });

        this.handler.addSlotLimit(SLOT_IN1, 1);
        this.handler.addSlotLimit(SLOT_OUT1, 1);
        this.handler.addSlotLimit(SLOT_IN2, 1);
        this.handler.addSlotLimit(SLOT_OUT2, 1);

        this.handler.setInputSlots(SLOT_FUEL, SLOT_IN1, SLOT_IN2);

        this.cap = ItemStackHandlerWrapper.create(this.handler, slot -> true, (slot, stack) -> slot == SLOT_FUEL || slot == SLOT_IN1 && this.handler.getStackInSlot(SLOT_OUT1).isEmpty() || slot == SLOT_IN2 && this.handler.getStackInSlot(SLOT_OUT2).isEmpty());
    }

    @Override
    public void tick() {
        if (this.playersWatching > 0) {
            this.calculatePlayersUsing();
            if (this.playersWatching > 0 && this.openTicks < 20) {
                this.openTicks += 1;
            }
        } else if (this.openTicks > 0) {
            this.playersWatching = 0;
            this.openTicks -= 1;
        }
        if (this.fuelTicks > 0) {
            this.fuelTicks -= 1;
        }

        if (this.world != null && !this.world.isRemote) {
            if (this.fuelTicks <= 0 && (this.recipe1 != null || this.recipe2 != null) && !this.handler.getStackInSlot(SLOT_FUEL).isEmpty()) {
                this.fuelTicksMax = Math.max(0, ForgeHooks.getBurnTime(this.handler.getStackInSlot(SLOT_FUEL)));
                this.fuelTicks = this.fuelTicksMax;
                if (this.fuelTicksMax > 0) {
                    ItemStack fuelStack = this.handler.getStackInSlot(SLOT_FUEL);
                    if (fuelStack.getItem() == Items.LAVA_BUCKET) {
                        fuelStack = new ItemStack(Items.BUCKET);
                    } else {
                        fuelStack.shrink(1);
                    }
                    this.handler.setStackInSlot(SLOT_FUEL, fuelStack);
                }
            }

            boolean needsRecipeUpdate = false;

            if (this.recipe1 != null) {
                if (this.cooking1 >= this.recipe1.getCookTime()) {
                    ItemStack output = this.recipe1.getRecipeOutput().copy();
                    this.handler.setStackInSlot(SLOT_IN1, ItemStack.EMPTY);
                    this.handler.setStackInSlot(SLOT_OUT1, output);
                    this.cooking1 = 0;
                    needsRecipeUpdate = true;
                } else if (this.fuelTicks > 0) {
                    this.cooking1 += 1;
                }
            } else {
                this.cooking1 = 0;
            }

            if (this.recipe2 != null) {
                if (this.cooking2 >= this.recipe2.getCookTime()) {
                    ItemStack output = this.recipe2.getRecipeOutput().copy();
                    this.handler.setStackInSlot(SLOT_IN2, ItemStack.EMPTY);
                    this.handler.setStackInSlot(SLOT_OUT2, output);
                    this.cooking2 = 0;
                    needsRecipeUpdate = true;
                } else if (this.fuelTicks > 0) {
                    this.cooking2 += 1;
                }
            } else {
                this.cooking2 = 0;
            }

            if (needsRecipeUpdate) {
                this.updateRecipes();
            }
        }

        if (this.world != null && this.world.isRemote) {
            if (this.cooking1 > 0) {
                this.cooking1 += 1;
            }
            if (this.cooking2 > 0) {
                this.cooking2 += 1;
            }
        }
    }

    private void updateRecipes() {
        if (this.world != null) {
            if (this.handler.getStackInSlot(SLOT_IN1).isEmpty() || !this.handler.getStackInSlot(SLOT_OUT1).isEmpty()) {
                this.recipe1 = null;
            } else {
                this.recipe1 = this.world.getRecipeManager().getRecipesForType(ModRecipes.OVEN).stream().filter(r -> RecipeHelper.matches(r, Collections.singletonList(this.handler.getStackInSlot(SLOT_IN1)), true)).findFirst().orElse(null);
            }

            if (this.handler.getStackInSlot(SLOT_IN2).isEmpty() || !this.handler.getStackInSlot(SLOT_OUT2).isEmpty()) {
                this.recipe2 = null;
            } else {
                this.recipe2 = this.world.getRecipeManager().getRecipesForType(ModRecipes.OVEN).stream().filter(r -> RecipeHelper.matches(r, Collections.singletonList(this.handler.getStackInSlot(SLOT_IN2)), true)).findFirst().orElse(null);
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
        this.fuelTicks = nbt.getInt("fuelTicks");
        this.fuelTicksMax = nbt.getInt("fuelTicksMax");
        this.cooking1 = nbt.getInt("cooking1");
        this.cooking2 = nbt.getInt("cooking2");
        this.openTicks = nbt.getInt("openTicks");
        this.playersWatching = nbt.getInt("playersWatching");
        this.updateRecipes();
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT nbt) {
        nbt.put("inventory", this.handler.serializeNBT());
        nbt.putInt("fuelTicks", this.fuelTicks);
        nbt.putInt("fuelTicksMax", this.fuelTicksMax);
        nbt.putInt("cooking1", this.cooking1);
        nbt.putInt("cooking2", this.cooking2);
        nbt.putInt("openTicks", this.openTicks);
        nbt.putInt("playersWatching", this.playersWatching);
        return super.write(nbt);
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = super.getUpdateTag();
        nbt.put("inventory", this.handler.serializeNBT());
        nbt.putInt("fuelTicks", this.fuelTicks);
        nbt.putInt("fuelTicksMax", this.fuelTicksMax);
        nbt.putInt("cooking1", this.cooking1);
        nbt.putInt("cooking2", this.cooking2);
        nbt.putInt("openTicks", this.openTicks);
        nbt.putInt("playersWatching", this.playersWatching);
        return nbt;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
        super.handleUpdateTag(state, nbt);
        if (this.world != null && this.world.isRemote && nbt.contains("inventory", Constants.NBT.TAG_COMPOUND)) {
            this.handler.deserializeNBT(nbt.getCompound("inventory"));
            this.fuelTicks = nbt.getInt("fuelTicks");
            this.fuelTicksMax = nbt.getInt("fuelTicksMax");
            this.cooking1 = nbt.getInt("cooking1");
            this.cooking2 = nbt.getInt("cooking2");
            this.openTicks = nbt.getInt("openTicks");
            this.playersWatching = nbt.getInt("playersWatching");
            this.updateRecipes();
        }
    }

    private void calculatePlayersUsing() {
        if (this.world != null && this.pos != null && !this.world.isRemote) {
            int using = 0;

            for (PlayerEntity playerentity : this.world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(this.pos.getX() - 5.5, this.pos.getY() - 5.5, this.pos.getZ() - 5.5, this.pos.getX() + 6.5, this.pos.getY() + 6.5, this.pos.getZ() + 6.5))) {
                if (playerentity.openContainer instanceof ContainerOven) {
                    if (((ContainerOven) playerentity.openContainer).tile == this) {
                        using += 1;
                    }
                }
            }

            this.playersWatching = using;
            this.markDirty();
            this.markDispatchable();
        }
    }

    public void playerUse() {
        if (this.world != null && !this.world.isRemote) {
            this.playersWatching += 1;
            this.markDirty();
            this.markDispatchable();
        }
    }

    public IItemHandlerModifiable getInventory() {
        return this.handler;
    }

    public int getOpenTicks() {
        return this.openTicks;
    }

    public float getBurnTime() {
        if (this.fuelTicks <= 0) {
            return 0;
        } else {
            return this.fuelTicks / (float) this.fuelTicksMax;
        }
    }

    public float getCookTime1() {
        if (this.cooking1 <= 0 || this.recipe1 == null) {
            return 0;
        } else {
            return this.cooking1 / (float) this.recipe1.getCookTime();
        }
    }

    public float getCookTime2() {
        if (this.cooking2 <= 0 || this.recipe2 == null) {
            return 0;
        } else {
            return this.cooking2 / (float) this.recipe2.getCookTime();
        }
    }

    public int getPlayersWatching() {
        return this.playersWatching;
    }
}
