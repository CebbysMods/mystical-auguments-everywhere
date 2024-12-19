package lv.cebbys.mcmods.mystical.augments.everywhere;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.cebbys.mcmods.mystical.augments.everywhere.constant.MysticalAugmentConstants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EventBusSubscriber(value = {Dist.DEDICATED_SERVER}, modid = MysticalAugmentConstants.MODID, bus = Bus.MOD)
public final class MysticalAugmentsServer {
    @SubscribeEvent
    private static void onInitializeServer(final FMLDedicatedServerSetupEvent event) {
        log.info("On '{}' initialize server side", MysticalAugmentConstants.MODID);
    }
}
