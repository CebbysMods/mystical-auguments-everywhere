package lv.cebbys.mcmods.mystical.augments.everywhere.compatability.mystical.agriculture;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lv.cebbys.mcmods.mystical.augments.everywhere.api.compatibility.smithing.PrefixedSmithingTemplateCompatibility;
import lv.cebbys.mcmods.mystical.augments.everywhere.api.compatibility.smithing.SmithingTemplateCompatibility;

import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MysticalAgricultureSmithingTemplateCompatibility implements PrefixedSmithingTemplateCompatibility {
    public static final SmithingTemplateCompatibility INSTANCE = new MysticalAgricultureSmithingTemplateCompatibility();

    @Override
    public String getPathPrefix() {
        return "item/smithing/mysticalagriculture";
    }

    @Override
    public void appendAdditionIconPaths(Consumer<String> registry) {
        registry.accept("empty_slot_socket_stone");
    }
}
