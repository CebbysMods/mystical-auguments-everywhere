package lv.cebbys.mcmods.mystical.augments.everywhere.content;

import com.blakebr0.mysticalagriculture.api.components.AugmentComponent;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.cebbys.mcmods.mystical.augments.everywhere.constant.MysticalAugmentConstants;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.data.component.SocketComponent;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;

import static lv.cebbys.mcmods.mystical.augments.everywhere.content.MysticalAugmentDeferredRegisters.DATA_COMPONENT_TYPE;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MysticalAugmentComponentTypes {
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<AugmentComponent>>> AUGMENT_DATA;
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SocketComponent>> SOCKET_DATA;

    public static void load() {
        log.info("Successfully registered '{}' component types", MysticalAugmentConstants.MODID);
    }

    static {
        AUGMENT_DATA = DATA_COMPONENT_TYPE.register("augment_data", () ->
                DataComponentType.<List<AugmentComponent>>builder()
                        .persistent(AugmentComponent.EQUIPPED_CODEC)
                        .networkSynchronized(AugmentComponent.EQUIPPED_STREAM_CODEC)
                        .build());
        SOCKET_DATA = DATA_COMPONENT_TYPE.register("socket_data", () ->
                DataComponentType.<SocketComponent>builder()
                        .persistent(SocketComponent.CODEC)
                        .networkSynchronized(SocketComponent.STREAM_CODEC)
                        .build()
        );
    }
}
