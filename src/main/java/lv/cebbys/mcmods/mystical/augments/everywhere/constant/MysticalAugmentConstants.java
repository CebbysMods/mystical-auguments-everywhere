package lv.cebbys.mcmods.mystical.augments.everywhere.constant;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.minecraft.resources.ResourceLocation;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MysticalAugmentConstants {
    public static final String MODID = "mysticalaugments";

    public static ResourceLocation resource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public static String id(String path) {
        return resource(path).toString();
    }
}
