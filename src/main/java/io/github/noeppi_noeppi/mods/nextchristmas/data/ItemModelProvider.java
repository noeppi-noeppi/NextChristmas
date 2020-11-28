package io.github.noeppi_noeppi.mods.nextchristmas.data;

import io.github.noeppi_noeppi.libx.data.AlwaysExistentModelFile;
import io.github.noeppi_noeppi.libx.data.provider.ItemModelProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.mods.nextchristmas.ModBlocks;
import io.github.noeppi_noeppi.mods.nextchristmas.ModItems;
import io.github.noeppi_noeppi.mods.nextchristmas.NextChristmas;
import io.github.noeppi_noeppi.mods.nextchristmas.entities.ItemSledge;
import io.github.noeppi_noeppi.mods.nextchristmas.util.ItemStackRenderer;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelProvider extends ItemModelProviderBase {


    public static final ResourceLocation SPAWN_EGG_PARENT = new ResourceLocation("minecraft", "item/template_spawn_egg");

    public static final ResourceLocation ENTITY_PARENT = new ResourceLocation("minecraft", "builtin/entity");
    public static final ResourceLocation TEISR_PARENT = new ResourceLocation(NextChristmas.getInstance().modid, "item/base/teisr");

    public ItemModelProvider(ModX mod, DataGenerator generator, ExistingFileHelper fileHelper) {
        super(mod, generator, fileHelper);
    }

    @Override
    protected void setup() {

    }

    @Override
    protected void defaultItem(ResourceLocation id, Item item) {
        if (item == ModItems.santaHat) {
            this.getBuilder(id.getPath()).parent(new AlwaysExistentModelFile(ENTITY_PARENT));
            this.withExistingParent(id.getPath() + "_model", GENERATED).texture("layer0", new ResourceLocation(id.getNamespace(), "item/" + id.getPath()));
        } else if (item instanceof ItemSledge) {
            this.getBuilder(id.getPath()).parent(this.getExistingFile(TEISR_PARENT));
        } else if (item instanceof SpawnEggItem) {
            this.withExistingParent(id.getPath(), SPAWN_EGG_PARENT);
        } else {
            super.defaultItem(id, item);
        }
    }

    @Override
    protected void defaultBlock(ResourceLocation id, BlockItem item) {
        if (item.getItemStackTileEntityRenderer() == ItemStackRenderer.get()) {
            this.getBuilder(id.getPath()).parent(this.getExistingFile(TEISR_PARENT));
        } else if (item.getBlock() == ModBlocks.gingerbreadHouse) {
            this.getBuilder(id.getPath()).parent(new AlwaysExistentModelFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_0")));
        } else {
            super.defaultBlock(id, item);
        }
    }
}
