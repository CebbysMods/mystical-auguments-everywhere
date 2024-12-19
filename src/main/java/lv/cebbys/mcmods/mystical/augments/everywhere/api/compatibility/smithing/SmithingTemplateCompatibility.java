package lv.cebbys.mcmods.mystical.augments.everywhere.api.compatibility.smithing;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public interface SmithingTemplateCompatibility {
    default void appendBaseIconLocations(Consumer<ResourceLocation> registry) {

    }

    default void appendAdditionIconLocations(Consumer<ResourceLocation> registry) {

    }
}
