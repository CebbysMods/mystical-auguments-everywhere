package lv.cebbys.mcmods.mystical.augments.everywhere.content;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.cebbys.mcmods.mystical.augments.everywhere.constant.MysticalBaseEssence;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.data.smithing.SocketEssence;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

import static lv.cebbys.mcmods.mystical.augments.everywhere.constant.MysticalAugmentConstants.MODID;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MysticalAugmentSocketEssences {
    public static final DeferredHolder<SocketEssence, SocketEssence> INFERIUM;
    public static final DeferredHolder<SocketEssence, SocketEssence> PRUDENTIUM;
    public static final DeferredHolder<SocketEssence, SocketEssence> TERTIUM;
    public static final DeferredHolder<SocketEssence, SocketEssence> IMPERIUM;
    public static final DeferredHolder<SocketEssence, SocketEssence> SUPREMIUM;
    public static final DeferredHolder<SocketEssence, SocketEssence> INSANIUM;
    public static final DeferredHolder<SocketEssence, SocketEssence> AWAKENED_SUPREMIUM;

    public static void load() {
        log.info("Successfully registered '{}' socket engravers", MODID);
    }

    private static DeferredHolder<SocketEssence, SocketEssence> register(MysticalBaseEssence id, Holder<Item> item, int tier) {
        return register(id.getId(), item, tier);
    }

    private static DeferredHolder<SocketEssence, SocketEssence> register(String id, Holder<Item> item, int tier) {
        return register(id, new SocketEssence(item, tier));
    }

    private static DeferredHolder<SocketEssence, SocketEssence> register(String id, SocketEssence engraver) {
        return MysticalAugmentDeferredRegisters.SOCKET_ESSENCE.register("%s_essence".formatted(id), () -> engraver);
    }

    static {
        INFERIUM = register(MysticalBaseEssence.INFERIUM, MysticalAugmentItems.INFERIUM_SOCKET_STONE, 1);
        PRUDENTIUM = register(MysticalBaseEssence.PRUDENTIUM, MysticalAugmentItems.PRUDENTIUM_SOCKET_STONE, 2);
        TERTIUM = register(MysticalBaseEssence.TERTIUM, MysticalAugmentItems.TERTIUM_SOCKET_STONE, 3);
        IMPERIUM = register(MysticalBaseEssence.IMPERIUM, MysticalAugmentItems.IMPERIUM_SOCKET_STONE, 4);
        SUPREMIUM = register(MysticalBaseEssence.SUPREMIUM, MysticalAugmentItems.SUPREMIUM_SOCKET_STONE, 5);
        INSANIUM = register(MysticalBaseEssence.INSANIUM, MysticalAugmentItems.INSANIUM_SOCKET_STONE, 6);
        AWAKENED_SUPREMIUM = register(MysticalBaseEssence.AWAKENED_SUPREMIUM, MysticalAugmentItems.AWAKENED_SUPREMIUM_SOCKET_STONE, 6);
    }

}
