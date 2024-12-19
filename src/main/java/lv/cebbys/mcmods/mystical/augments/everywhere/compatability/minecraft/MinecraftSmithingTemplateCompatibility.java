package lv.cebbys.mcmods.mystical.augments.everywhere.compatability.minecraft;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lv.cebbys.mcmods.mystical.augments.everywhere.api.compatibility.smithing.PrefixedSmithingTemplateCompatibility;
import lv.cebbys.mcmods.mystical.augments.everywhere.api.compatibility.smithing.SmithingTemplateCompatibility;

import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MinecraftSmithingTemplateCompatibility implements PrefixedSmithingTemplateCompatibility {
    public static final SmithingTemplateCompatibility INSTANCE = new MinecraftSmithingTemplateCompatibility();

    @Override
    public String getPathPrefix() {
        return "item/smithing/minecraft";
    }

    @Override
    public void appendBaseIconPaths(Consumer<String> registry) {
        registry.accept("empty_armor_slot_boots");
        registry.accept("empty_armor_slot_chestplate");
        registry.accept("empty_armor_slot_helmet");
        registry.accept("empty_armor_slot_leggings");
        registry.accept("empty_armor_slot_shield");
        registry.accept("empty_slot_hoe");
        registry.accept("empty_slot_axe");
        registry.accept("empty_slot_sword");
        registry.accept("empty_slot_shovel");
        registry.accept("empty_slot_pickaxe");
    }
}
