package lv.cebbys.mcmods.mystical.augments.everywhere.provider;

import com.blakebr0.mysticalagriculture.api.tinkering.AugmentType;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.MysticalAugmentDataMapTypes;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.MysticalAugmentTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.EnumSet;
import java.util.concurrent.CompletableFuture;

public class MysticalAugmentsDataMapTypeProvider extends DataMapProvider {
    public MysticalAugmentsDataMapTypeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        new Builder(builder(MysticalAugmentDataMapTypes.ITEM_AUGMENT_TYPES))
                .add(MysticalAugmentTags.MYSTICAL_AUGMENT_AXE_LIKE,
                        AugmentType.TOOL, AugmentType.WEAPON, AugmentType.AXE
                )
                .add(MysticalAugmentTags.MYSTICAL_AUGMENT_HOE_LIKE,
                        AugmentType.TOOL, AugmentType.HOE
                )
                .add(MysticalAugmentTags.MYSTICAL_AUGMENT_PICKAXE_LIKE,
                        AugmentType.TOOL, AugmentType.PICKAXE
                )
                .add(MysticalAugmentTags.MYSTICAL_AUGMENT_SHOVEL_LIKE,
                        AugmentType.TOOL, AugmentType.SHOVEL
                )
                .add(MysticalAugmentTags.MYSTICAL_AUGMENT_SWORD_LIKE,
                        AugmentType.WEAPON, AugmentType.SWORD
                )
                .add(MysticalAugmentTags.MYSTICAL_AUGMENT_HELMET_LIKE,
                        AugmentType.ARMOR, AugmentType.HELMET
                )
                .add(MysticalAugmentTags.MYSTICAL_AUGMENT_CHESTPLATE_LIKE,
                        AugmentType.ARMOR, AugmentType.CHESTPLATE
                )
                .add(MysticalAugmentTags.MYSTICAL_AUGMENT_LEGGINGS_LIKE,
                        AugmentType.ARMOR, AugmentType.LEGGINGS
                )
                .add(MysticalAugmentTags.MYSTICAL_AUGMENT_BOOTS_LIKE,
                        AugmentType.ARMOR, AugmentType.BOOTS
                );
    }

    private record Builder(DataMapProvider.Builder<EnumSet<AugmentType>, Item> builder) {
        public Builder add(TagKey<Item> key, AugmentType first, AugmentType... rest) {
            builder.add(key, EnumSet.of(first, rest), true);
            return this;
        }
    }
}
