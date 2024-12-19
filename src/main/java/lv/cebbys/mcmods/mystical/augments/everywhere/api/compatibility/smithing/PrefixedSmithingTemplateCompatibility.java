package lv.cebbys.mcmods.mystical.augments.everywhere.api.compatibility.smithing;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

import static lv.cebbys.mcmods.mystical.augments.everywhere.constant.MysticalAugmentConstants.resource;

public interface PrefixedSmithingTemplateCompatibility extends SmithingTemplateCompatibility {
    String getPathPrefix();

    default void appendBaseIconPaths(Consumer<String> registry) {

    }

    default void appendAdditionIconPaths(Consumer<String> registry) {

    }

    default void appendBaseIconLocations(Consumer<ResourceLocation> registry) {
        appendBaseIconPaths((var path) -> {
            registry.accept(resource("%s/%s".formatted(getPathPrefix(), path)));
        });
    }

    default void appendAdditionIconLocations(Consumer<ResourceLocation> registry) {
        appendAdditionIconPaths((var path) -> {
            registry.accept(resource("%s/%s".formatted(getPathPrefix(), path)));
        });
    }
}
