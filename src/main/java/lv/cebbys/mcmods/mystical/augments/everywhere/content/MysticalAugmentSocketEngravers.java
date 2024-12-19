package lv.cebbys.mcmods.mystical.augments.everywhere.content;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.data.smithing.SocketEngraver;
import net.neoforged.neoforge.registries.DeferredHolder;

import static lv.cebbys.mcmods.mystical.augments.everywhere.constant.MysticalAugmentConstants.MODID;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MysticalAugmentSocketEngravers {
    public static final DeferredHolder<SocketEngraver, SocketEngraver> ENGRAVER_I;
    public static final DeferredHolder<SocketEngraver, SocketEngraver> ENGRAVER_II;

    public static void load() {
        log.info("Successfully registered '{}' socket engravers", MODID);
    }

    private static DeferredHolder<SocketEngraver, SocketEngraver> register(String id, SocketEngraver engraver) {
        return MysticalAugmentDeferredRegisters.SOCKET_ENGRAVER.register(id, () -> engraver);
    }

    static {
        ENGRAVER_I = register("socket_engraver_i", new SocketEngraver(MysticalAugmentItems.ENGRAVER_TEMPLATE_I, 1));
        ENGRAVER_II = register("socket_engraver_ii", new SocketEngraver(MysticalAugmentItems.ENGRAVER_TEMPLATE_II, 2));
    }
}
