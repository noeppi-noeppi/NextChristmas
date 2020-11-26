package io.github.noeppi_noeppi.mods.nextchristmas.entities;

import io.github.noeppi_noeppi.libx.inventory.VanillaWrapper;
import io.github.noeppi_noeppi.mods.nextchristmas.ModTags;
import io.github.noeppi_noeppi.mods.nextchristmas.NextChristmas;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class Sledge extends Entity {

    public static final int STATUS_ON_SNOW = 0;
    public static final int STATUS_ON_LAND = 1;
    public static final int STATUS_IN_WATER = 2;
    public static final int STATUS_IN_AIR = 3;

    private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.createKey(Sledge.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> FORWARD_DIRECTION = EntityDataManager.createKey(Sledge.class, DataSerializers.VARINT);
    private static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.createKey(Sledge.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> SLEDGE_TYPE = EntityDataManager.createKey(Sledge.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> STEP_COOLDOWN = EntityDataManager.createKey(Sledge.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> WINGED = EntityDataManager.createKey(Sledge.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> FLYING = EntityDataManager.createKey(Sledge.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CHESTED = EntityDataManager.createKey(Sledge.class, DataSerializers.BOOLEAN);

    private float deltaRotation;
    private int lerpSteps;
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpYaw;
    private double lerpPitch;
    private boolean leftInputDown;
    private boolean rightInputDown;
    private boolean forwardInputDown;
    private boolean backInputDown;
    private boolean boostDown;
    private boolean flyDown;
    private int status;

    private final ItemStackHandler inventory = new ItemStackHandler(27);

    public Sledge(EntityType<? extends Sledge> type, @Nonnull World world) {
        super(type, world);
        this.preventEntitySpawning = true;
    }

    @Override
    protected void registerData() {
        this.dataManager.register(TIME_SINCE_HIT, 0);
        this.dataManager.register(FORWARD_DIRECTION, 1);
        this.dataManager.register(DAMAGE_TAKEN, 0f);
        this.dataManager.register(SLEDGE_TYPE, SledgeType.OAK.ordinal());
        this.dataManager.register(STEP_COOLDOWN, 0);
        this.dataManager.register(WINGED, false);
        this.dataManager.register(FLYING, false);
        this.dataManager.register(CHESTED, false);
    }

    @Override
    public void tick() {
        this.updateStatus();

        if (this.onGround) {
            this.dataManager.set(FLYING, false);
        }

        if (this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }

        if (this.getDamageTaken() > 0.0F) {
            this.setDamageTaken(this.getDamageTaken() - 1.0F);
        }

        super.tick();
        this.tickLerp();

        if (this.canPassengerSteer()) {
            this.controlSledge();
            this.updateMotion();
            this.updateStep();
            this.move(MoverType.SELF, this.getMotion());
        } else {
            this.setMotion(Vector3d.ZERO);
        }

        this.doBlockCollisions();
    }

    @Override
    protected float getEyeHeight(@Nonnull Pose pose, EntitySize sizeIn) {
        return sizeIn.height;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public boolean canCollide(@Nonnull Entity entity) {
        return func_242378_a(this, entity);
    }

    public static boolean func_242378_a(Entity p_242378_0_, Entity entity) {
        return (entity.func_241845_aY() || entity.canBePushed()) && !p_242378_0_.isRidingSameEntity(entity);
    }

    @Override
    public boolean func_241845_aY() {
        return true;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    @Nonnull
    @Override
    protected Vector3d func_241839_a(@Nonnull Direction.Axis axis, @Nonnull TeleportationRepositioner.Result result) {
        return LivingEntity.func_242288_h(super.func_241839_a(axis, result));
    }

    @Override
    public double getMountedYOffset() {
        return 0.4;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean attackEntityFrom(@Nonnull DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (!this.world.isRemote && !this.removed) {
            this.setForwardDirection(-this.getForwardDirection());
            this.setTimeSinceHit(10);
            this.setDamageTaken(this.getDamageTaken() + amount * 10.0F);
            this.markVelocityChanged();
            boolean isCreative = source.getTrueSource() instanceof PlayerEntity && ((PlayerEntity) source.getTrueSource()).abilities.isCreativeMode;
            if (isCreative || this.getDamageTaken() > 40.0F) {
                if (!isCreative && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                    this.entityDropItem(this.getSledgeType().sledgeItem.getValue());
                    if (this.dataManager.get(WINGED)) {
                        this.entityDropItem(Items.ELYTRA);
                    }
                    if (this.dataManager.get(CHESTED)) {
                        this.entityDropItem(Items.CHEST);
                        for (int slot = 0; slot < this.inventory.getSlots(); slot++) {
                            ItemStack stack = this.inventory.getStackInSlot(slot);
                            if (!stack.isEmpty()) {
                                this.entityDropItem(stack.copy());
                            }
                            this.inventory.setStackInSlot(slot, ItemStack.EMPTY);
                        }

                    }
                }
                this.remove();
            }
            return true;
        } else {
            return true;
        }
    }

    @Override
    public void applyEntityCollision(@Nonnull Entity entity) {
        if (entity instanceof Sledge) {
            if (entity.getBoundingBox().minY < this.getBoundingBox().maxY) {
                super.applyEntityCollision(entity);
            }
        } else if (entity.getBoundingBox().minY <= this.getBoundingBox().minY) {
            super.applyEntityCollision(entity);
        }
    }

    @Override
    public void performHurtAnimation() {
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11.0F);
    }

    @Override
    public boolean canBeCollidedWith() {
        //noinspection deprecation
        return !this.removed;
    }

    @Override
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpYaw = yaw;
        this.lerpPitch = pitch;
        this.lerpSteps = 10;
    }

    @Nonnull
    @Override
    public Direction getAdjustedHorizontalFacing() {
        return this.getHorizontalFacing().rotateY();
    }

    private void tickLerp() {
        if (this.canPassengerSteer()) {
            this.lerpSteps = 0;
            this.setPacketCoordinates(this.getPosX(), this.getPosY(), this.getPosZ());
        }

        if (this.lerpSteps > 0) {
            double x = this.getPosX() + (this.lerpX - this.getPosX()) / this.lerpSteps;
            double y = this.getPosY() + (this.lerpY - this.getPosY()) / this.lerpSteps;
            double z = this.getPosZ() + (this.lerpZ - this.getPosZ()) / this.lerpSteps;
            double yaw = MathHelper.wrapDegrees(this.lerpYaw - this.rotationYaw);
            this.rotationYaw = (float) (this.rotationYaw + yaw / this.lerpSteps);
            this.rotationPitch = (float) (this.rotationPitch + (this.lerpPitch - this.rotationPitch) / this.lerpSteps);
            this.lerpSteps--;
            this.setPosition(x, y, z);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
    }

    private void updateMotion() {
        float momentum = 0.05F;
        double falling = this.hasNoGravity() ? 0 : -0.04;
        if (this.status == STATUS_IN_WATER) {
            momentum = 0.2f;
        } else if (this.status == STATUS_ON_LAND) {
            momentum = 0.45f;
        } else if (this.status == STATUS_ON_SNOW || (this.status == STATUS_IN_AIR && this.dataManager.get(WINGED) && this.dataManager.get(FLYING))) {
            momentum = 0.9f;
        } else if (this.status == STATUS_IN_AIR) {
            momentum = 0.7f;
        }

        Vector3d motion = this.getMotion();
        motion = motion.add(0, falling, 0);
        if (this.dataManager.get(WINGED) && this.dataManager.get(FLYING)) {
            motion = new Vector3d(motion.x, Math.max(motion.y + falling, -0.03), motion.z);
        } else {
            motion = motion.add(0, falling, 0);
        }
        motion = motion.mul(momentum, 1, momentum);
        this.setMotion(motion);
        this.deltaRotation *= momentum;
    }

    private void updateStep() {
        if (this.dataManager.get(STEP_COOLDOWN) > 0) {
            if (this.onGround) {
                this.dataManager.set(STEP_COOLDOWN, 0);
            } else {
                this.dataManager.set(STEP_COOLDOWN, this.dataManager.get(STEP_COOLDOWN) - 1);
            }
            this.setMotion(this.getMotion().mul(1, 0, 1));
        } else if (this.status == STATUS_ON_SNOW && this.onGround) {
            Vector3d lookVec = this.getLookVec().normalize();
            Vector3d motion = this.getMotion().mul(1, 0, 1).normalize();

            if (motion.lengthSquared() > 0) {
                double angle = Math.abs(Math.acos(lookVec.dotProduct(motion)));
                if (angle < Math.toRadians(60)) {
                    double mul = (1 / Math.max(Math.abs(motion.x), Math.abs(motion.z)));
                    if (Double.isFinite(mul)) {
                        motion = motion.mul(mul, mul, mul);
                        Vector3d start = new Vector3d(this.getPosX(), this.getPosY() + 0.01, this.getPosZ());
                        Vector3d end = new Vector3d(start.x + motion.x, start.y, start.z + motion.z);
                        BlockRayTraceResult result = this.world.rayTraceBlocks(new RayTraceContext(start, end, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
                        if (result.getType() == RayTraceResult.Type.BLOCK) {
                            BlockPos pos = result.getPos();
                            BlockState state = this.world.getBlockState(pos);
                            BlockState stateUp = this.world.getBlockState(pos.up());
                            if ((ModTags.Blocks.sledgeSurface.contains(state.getBlock()) || ModTags.Blocks.sledgeSurface.contains(stateUp.getBlock())) && stateUp.getCollisionShape(this.world, pos.up()).isEmpty()) {
                                VoxelShape shape = state.getCollisionShape(this.world, pos);
                                Vector3d hitStart = new Vector3d(result.getHitVec().x, pos.getY() + 1.2, result.getHitVec().z);
                                Vector3d hitEnd = new Vector3d(result.getHitVec().x, pos.getY(), result.getHitVec().z);
                                BlockRayTraceResult target = shape.rayTrace(hitStart, hitEnd, pos);
                                if (target != null) {
                                    Vector3d targetVec = target.getHitVec();
                                    this.dataManager.set(STEP_COOLDOWN, 5);
                                    this.setPosition(this.getPosX(), Math.max(this.getPosY(), targetVec.y + 0.2), this.getPosZ());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void controlSledge() {
        if (this.isBeingRidden()) {
            float f = 0;
            if (this.leftInputDown) {
                --this.deltaRotation;
            }

            if (this.rightInputDown) {
                ++this.deltaRotation;
            }

            if (this.rightInputDown != this.leftInputDown && !this.forwardInputDown && !this.backInputDown) {
                f += 0.005f;
            }

            this.rotationYaw += this.deltaRotation;
            if (this.forwardInputDown) {
                f += this.boostDown && !this.dataManager.get(FLYING) && this.onGround ? 0.06f :  0.04f;
            }

            if (this.backInputDown) {
                f -= 0.005f;
            }

            if (this.flyDown && this.dataManager.get(WINGED) && this.getMotion().mul(1, 0, 1).lengthSquared() > 0.35 * 0.35) {
                this.setMotion(this.getMotion().add(0, 0.3, 0));
                this.setMotion(this.getMotion().mul(0.8, 1, 0.8));
                this.dataManager.set(FLYING, true);
            } else {
                this.setMotion(this.getMotion().add(MathHelper.sin(-this.rotationYaw * ((float) Math.PI / 180)) * f, 0, MathHelper.cos(this.rotationYaw * ((float) Math.PI / 180)) * f));
            }
        }
    }

    @Override
    protected void addPassenger(@Nonnull Entity passenger) {
        super.addPassenger(passenger);
        if (this.canPassengerSteer() && this.lerpSteps > 0) {
            this.lerpSteps = 0;
            this.setPositionAndRotation(this.lerpX, this.lerpY, this.lerpZ, (float) this.lerpYaw, (float) this.lerpPitch);
        }
    }

    @Override
    protected void removePassenger(@Nonnull Entity passenger) {
        super.removePassenger(passenger);
        this.forwardInputDown = false;
        this.backInputDown = false;
        this.rightInputDown = false;
        this.leftInputDown = false;
        this.deltaRotation = 0;
        this.dataManager.set(FLYING, false);
    }

    @Nullable
    @Override
    public Entity getControllingPassenger() {
        List<Entity> list = this.getPassengers();
        return list.isEmpty() ? null : list.get(0);
    }

    public void updateInputs(boolean leftInputDown, boolean rightInputDown, boolean forwardInputDown, boolean backInputDown, boolean boostDown, boolean flyDown) {
        this.leftInputDown = leftInputDown;
        this.rightInputDown = rightInputDown;
        this.forwardInputDown = forwardInputDown;
        this.backInputDown = backInputDown;
        this.boostDown = boostDown;
        this.flyDown = flyDown;
        if (this.world != null && this.world.isRemote) {
            NextChristmas.getNetwork().updateSledgeMovement(this, this.forwardInputDown, this.backInputDown, this.rightInputDown, this.leftInputDown, this.boostDown, this.flyDown);
        }
    }

    @SuppressWarnings("deprecation")
    private void updateStatus() {
        BlockState surface = this.world.getBlockState(this.getPosition());
        if (surface.isAir()) {
            surface = this.world.getBlockState(this.getPositionUnderneath());
        }
        if (this.isInWater()) {
            this.status = STATUS_IN_WATER;
        } else if (surface.isAir()) {
            this.status = STATUS_IN_AIR;
        } else if (ModTags.Blocks.sledgeSurface.contains(surface.getBlock())) {
            this.status = STATUS_ON_SNOW;
        } else {
            this.status = STATUS_ON_LAND;
        }
    }

    @Override
    protected void writeAdditional(CompoundNBT nbt) {
        nbt.putString("Type", this.getSledgeType().name());
        nbt.putBoolean("Winged", this.isWinged());
        nbt.putBoolean("Chested", this.isChested());
        nbt.put("Inventory", this.inventory.serializeNBT());
    }

    @Override
    protected void readAdditional(CompoundNBT nbt) {
        if (nbt.contains("Type", Constants.NBT.TAG_STRING)) {
            this.setSledgeType(SledgeType.valueOf(nbt.getString("Type")));
        }
        this.setWinged(nbt.getBoolean("Winged"));
        this.setChested(nbt.getBoolean("Chested"));
        if (nbt.contains("Inventory", Constants.NBT.TAG_COMPOUND)) {
            this.inventory.deserializeNBT(nbt.getCompound("Inventory"));
        }
    }

    @Nonnull
    @Override
    public ActionResultType processInitialInteract(@Nonnull PlayerEntity player, @Nonnull Hand hand) {
        if (player.isSecondaryUseActive()) {
            return ActionResultType.PASS;
        } else {
            if (!this.world.isRemote) {
                ItemStack held = player.getHeldItem(hand);
                if (held.getItem() == Items.ELYTRA && held.getDamage() <= 0 && !this.dataManager.get(WINGED)) {
                    held.shrink(1);
                    player.setHeldItem(hand, held);
                    player.swing(hand, true);
                    this.dataManager.set(WINGED, true);
                    return ActionResultType.CONSUME;
                } else if (held.getItem() == Items.CHEST && !this.dataManager.get(CHESTED)) {
                    held.shrink(1);
                    player.setHeldItem(hand, held);
                    player.swing(hand, true);
                    this.dataManager.set(CHESTED, true);
                    return ActionResultType.CONSUME;
                } else {
                    return player.startRiding(this) ? ActionResultType.CONSUME : ActionResultType.PASS;
                }
            } else {
                return ActionResultType.SUCCESS;
            }
        }
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, @Nonnull BlockState state, @Nonnull BlockPos pos) {
        if (!this.isPassenger()) {
            if (onGroundIn) {
                if (this.fallDistance > 3) {
                    this.onLivingFall(this.fallDistance, 1);
                }
                this.fallDistance = 0.0F;
            } else if (!this.world.getFluidState(this.getPosition().down()).isTagged(FluidTags.WATER) && y < 0.0D) {
                this.fallDistance = (float) (this.fallDistance - y);
            }
        }
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NextChristmas.getNetwork().getSledgeSpawnPacket(this);
    }

    @Nonnull
    public SledgeType getSledgeType() {
        SledgeType[] types = SledgeType.values();
        int type = this.dataManager.get(SLEDGE_TYPE);
        if (type < 0 || type >= types.length) {
            return SledgeType.OAK;
        } else {
            return types[type];
        }
    }

    public int getTimeSinceHit() {
        return this.dataManager.get(TIME_SINCE_HIT);
    }

    public float getDamageTaken() {
        return this.dataManager.get(DAMAGE_TAKEN);
    }

    public int getForwardDirection() {
        return this.dataManager.get(FORWARD_DIRECTION);
    }

    public boolean isWinged() {
        return this.dataManager.get(WINGED);
    }

    public boolean isFlying() {
        return this.dataManager.get(FLYING);
    }

    public boolean isChested() {
        return this.dataManager.get(CHESTED);
    }

    public void setSledgeType(@Nonnull SledgeType type) {
        this.dataManager.set(SLEDGE_TYPE, type.ordinal());
    }

    public void setTimeSinceHit(int timeSinceHit) {
        this.dataManager.set(TIME_SINCE_HIT, timeSinceHit);
    }

    public void setDamageTaken(float damage) {
        this.dataManager.set(DAMAGE_TAKEN, damage);
    }

    public void setForwardDirection(int forward) {
        this.dataManager.set(FORWARD_DIRECTION, forward);
    }

    public void setWinged(boolean winged) {
        this.dataManager.set(WINGED, winged);
    }

    public void setFlying(boolean flying) {
        this.dataManager.set(FLYING, flying && this.dataManager.get(WINGED));
    }

    public void setChested(boolean chested) {
        this.dataManager.set(CHESTED, chested);
    }

    public IItemHandlerModifiable getInventory() {
        return this.inventory;
    }

    public void openInventory(PlayerEntity player) {
        if (this.isChested()) {
            player.openContainer(new INamedContainerProvider() {

                @Nonnull
                @Override
                public ITextComponent getDisplayName() {
                    return Sledge.this.getDisplayName();
                }

                @Override
                public Container createMenu(int windowId, @Nonnull PlayerInventory playerInv, @Nonnull PlayerEntity player) {
                    return ChestContainer.createGeneric9X3(windowId, playerInv, new VanillaWrapper(Sledge.this.inventory, null));
                }
            });
        }
    }
}
