package io.github.noeppi_noeppi.mods.nextchristmas.entities;

import io.github.noeppi_noeppi.mods.nextchristmas.ModWorldGen;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.IFlinging;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.monster.ZoglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Random;

public class Reindeer extends CreatureEntity {

    public Reindeer(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(11, new WaterAvoidingRandomWalkingGoal(this, 0.7D));
        this.goalSelector.addGoal(12, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(13, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, RavagerEntity.class, 0, true, false, entity -> true));
        this.initExtraAI();
    }

    protected void initExtraAI() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
    }

    public int getMaxSpawnedInChunk() {
        return 20;
    }

    protected float getSoundVolume() {
        return 0.8F;
    }

    public int getTalkInterval() {
        return 400;
    }

    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote && this.isAlive()) {
            if (this.rand.nextInt(100) == 0 && this.deathTime == 0) {
                this.heal(1.0F);
            }
        }
    }

    public boolean isOnLadder() {
        return false;
    }

    protected float getStandingEyeHeight(@Nonnull Pose pose, EntitySize sizeIn) {
        return sizeIn.height * 0.95F;
    }

    @Override
    protected double followLeashSpeed() {
        return 1.5;
    }

    @Override
    public boolean attackEntityAsMob(@Nonnull Entity entity) {
        if (super.attackEntityAsMob(entity)) {
            if (entity instanceof LivingEntity) {
                return IFlinging.func_234403_a_(this, (LivingEntity) entity);
            }
            return true;
        } else {
            return false;
        }
    }

    public static AttributeModifierMap defaultAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 40)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.4)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 10)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 1)
                .create();
    }

    public static boolean canSpawnAt(EntityType<Reindeer> type, IWorld world, SpawnReason reason, BlockPos pos, Random random) {
        return Objects.equals(ModWorldGen.christmasForest.getRegistryName(), world.getBiome(pos).getRegistryName());
    }
}
