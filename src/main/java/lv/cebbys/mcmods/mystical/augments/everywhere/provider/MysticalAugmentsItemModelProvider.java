package lv.cebbys.mcmods.mystical.augments.everywhere.provider;

import com.google.common.collect.ImmutableList;
import lv.cebbys.mcmods.mystical.augments.everywhere.constant.MysticalAugmentConstants;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.MysticalAugmentItems;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.MysticalAugmentTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@ParametersAreNonnullByDefault
public class MysticalAugmentsItemModelProvider extends ItemModelProvider {

    public MysticalAugmentsItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MysticalAugmentConstants.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        MysticalAugmentItems.ITEMS.forEach((var holder) -> {
            var item = holder.get();
            var id = BuiltInRegistries.ITEM.getKey(item);
            basicItem(item)
                    .parent(new UncheckedModelFile("minecraft:item/generated"))
                    .texture("layer0", MysticalAugmentConstants.resource("item/%s".formatted(id.getPath())));
        });
    }
}
