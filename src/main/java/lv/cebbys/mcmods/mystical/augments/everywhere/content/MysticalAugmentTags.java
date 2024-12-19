package lv.cebbys.mcmods.mystical.augments.everywhere.content;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.cebbys.mcmods.mystical.augments.everywhere.constant.MysticalAugmentConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static lv.cebbys.mcmods.mystical.augments.everywhere.constant.MysticalAugmentConstants.resource;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MysticalAugmentTags {
    public static final TagKey<Item> EQUIPMENT;
    public static final TagKey<Item> ENGRAVER_TEMPLATE;
    public static final TagKey<Item> ESSENCE_STONE;
    public static final TagKey<Item> MYSTICAL_AUGMENT_AXE_LIKE;
    public static final TagKey<Item> MYSTICAL_AUGMENT_HOE_LIKE;
    public static final TagKey<Item> MYSTICAL_AUGMENT_PICKAXE_LIKE;
    public static final TagKey<Item> MYSTICAL_AUGMENT_SHOVEL_LIKE;
    public static final TagKey<Item> MYSTICAL_AUGMENT_SWORD_LIKE;
    public static final TagKey<Item> MYSTICAL_AUGMENT_HELMET_LIKE;
    public static final TagKey<Item> MYSTICAL_AUGMENT_CHESTPLATE_LIKE;
    public static final TagKey<Item> MYSTICAL_AUGMENT_LEGGINGS_LIKE;
    public static final TagKey<Item> MYSTICAL_AUGMENT_BOOTS_LIKE;

    public static void load() {
        log.info("Successfully created '{}' tags", MysticalAugmentConstants.MODID);
    }

    static {
        EQUIPMENT = create("engraveable_equipment");
        ENGRAVER_TEMPLATE = create("engraver_template");
        ESSENCE_STONE = create("essence_stone");

        MYSTICAL_AUGMENT_AXE_LIKE = create("mystical_augment_axe_like");
        MYSTICAL_AUGMENT_HOE_LIKE = create("mystical_augment_hoe_like");
        MYSTICAL_AUGMENT_PICKAXE_LIKE = create("mystical_augment_pickaxe_like");
        MYSTICAL_AUGMENT_SHOVEL_LIKE = create("mystical_augment_shovel_like");
        MYSTICAL_AUGMENT_SWORD_LIKE = create("mystical_augment_sword_like");

        MYSTICAL_AUGMENT_HELMET_LIKE = create("mystical_augment_helmet_like");
        MYSTICAL_AUGMENT_CHESTPLATE_LIKE = create("mystical_augment_chestplate_like");
        MYSTICAL_AUGMENT_LEGGINGS_LIKE = create("mystical_augment_leggings_like");
        MYSTICAL_AUGMENT_BOOTS_LIKE = create("mystical_augment_boots_like");
    }

    private static TagKey<Item> create(String id) {
        var location = resource(id);
        log.debug("Registering '{}' item tag '{}'", MysticalAugmentConstants.MODID, location);
        return TagKey.create(Registries.ITEM, location);
    }
}
