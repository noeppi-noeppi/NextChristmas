package io.github.noeppi_noeppi.mods.nextchristmas.entities;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import io.github.noeppi_noeppi.mods.nextchristmas.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Predicate;

public class ItemSledge extends ItemBase {

    private static final Predicate<Entity> NOT_SPECTATING_COLLIDABLE = EntityPredicates.NOT_SPECTATING.and(Entity::canBeCollidedWith);

    public final SledgeType type;

    public ItemSledge(ModX mod, SledgeType type, Properties properties) {
        super(mod, properties.setISTER(() -> () -> RenderItemSledge.INSTANCE).maxStackSize(1));
        this.type = type;
    }

    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, @Nonnull PlayerEntity player, @Nonnull Hand hand) {
        if (!world.isRemote) {
            ItemStack stack = player.getHeldItem(hand);
            RayTraceResult hit = rayTrace(world, player, RayTraceContext.FluidMode.ANY);
            if (hit.getType() == RayTraceResult.Type.MISS) {
                return ActionResult.resultPass(stack);
            } else {
                Vector3d vector3d = player.getLook(1);
                List<Entity> collidableEntities = world.getEntitiesInAABBexcluding(player, player.getBoundingBox().expand(vector3d.scale(5)).grow(1), NOT_SPECTATING_COLLIDABLE);
                Vector3d playerVec = player.getEyePosition(1.0F);
                for (Entity entity : collidableEntities) {
                    AxisAlignedBB aabb = entity.getBoundingBox().grow(entity.getCollisionBorderSize());
                    if (aabb.contains(playerVec)) {
                        return ActionResult.resultPass(stack);
                    }
                }

                if (hit.getType() == RayTraceResult.Type.BLOCK) {
                    Sledge sledge = new Sledge(ModEntities.sledge, world);
                    sledge.setPosition(hit.getHitVec().x, hit.getHitVec().y, hit.getHitVec().z);
                    sledge.setSledgeType(this.type);
                    sledge.rotationYaw = player.rotationYaw;
                    if (!world.hasNoCollisions(sledge, sledge.getBoundingBox().grow(-0.1))) {
                        return ActionResult.resultFail(stack);
                    } else {
                        world.addEntity(sledge);
                        if (!player.abilities.isCreativeMode) {
                            stack.shrink(1);
                            player.setHeldItem(hand, stack);
                        }

                        player.addStat(Stats.ITEM_USED.get(this));
                        return ActionResult.resultConsume(stack);
                    }
                } else {
                    return ActionResult.resultPass(stack);
                }
            }
        } else {
            return ActionResult.resultPass(player.getHeldItem(hand));
        }
    }
}
