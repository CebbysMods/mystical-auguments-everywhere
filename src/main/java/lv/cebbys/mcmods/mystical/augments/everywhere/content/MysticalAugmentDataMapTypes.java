package lv.cebbys.mcmods.mystical.augments.everywhere.content;

import com.blakebr0.mysticalagriculture.api.tinkering.AugmentType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lv.cebbys.mcmods.mystical.augments.everywhere.constant.MysticalAugmentConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

import java.util.EnumSet;

import static lv.cebbys.mcmods.mystical.augments.everywhere.constant.MysticalAugmentCodecs.Codecs.AUGMENT_TYPE_ENUMSET;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MysticalAugmentDataMapTypes {
    public static final DataMapType<Item, EnumSet<AugmentType>> ITEM_AUGMENT_TYPES = DataMapType
            .builder(
                    MysticalAugmentConstants.resource("item_augment_types"),
                    Registries.ITEM,
                    AUGMENT_TYPE_ENUMSET
            )
            .synced(AUGMENT_TYPE_ENUMSET, true)
            .build();
}
