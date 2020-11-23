package io.github.noeppi_noeppi.mods.nextchristmas;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;

import javax.annotation.Nonnull;
import java.util.List;

public class LootModifier extends net.minecraftforge.common.loot.LootModifier {

    private LootModifier(ILootCondition[] conditions) {
        super(conditions);
    }

    @Nonnull
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        BlockState state = context.get(LootParameters.BLOCK_STATE);
        if (state == null)
            return generatedLoot;
        if ((state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.TALL_GRASS) && generatedLoot.isEmpty()) {
            if (context.getRandom().nextInt(50) == 0) {
                generatedLoot.add(new ItemStack(ModItems.vanillaFruits, context.getRandom().nextInt(3) + 1));
            }
        } else if (state.getBlock() == Blocks.DARK_OAK_LEAVES && generatedLoot.stream().noneMatch(stack -> stack.getItem() == Items.DARK_OAK_LEAVES)) {
            if (context.getRandom().nextInt(100) == 0) {
                generatedLoot.add(new ItemStack(ModItems.hazelnut));
            }
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<LootModifier> {

        public static final Serializer INSTANCE = new Serializer();

        private Serializer() {

        }

        public LootModifier read(ResourceLocation location, JsonObject object, ILootCondition[] conditions) {
            return new LootModifier(conditions);
        }

        public JsonObject write(LootModifier modifier) {
            return this.makeConditions(modifier.conditions);
        }
    }

    public static void register() {
        NextChristmas.getInstance().register("lootmod", Serializer.INSTANCE);
    }
}
