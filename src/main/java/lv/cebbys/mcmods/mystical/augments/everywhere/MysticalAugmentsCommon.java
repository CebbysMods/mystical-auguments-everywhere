package lv.cebbys.mcmods.mystical.augments.everywhere;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.cebbys.mcmods.mystical.augments.everywhere.constant.MysticalAugmentConstants;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.MysticalAugmentDataMapTypes;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.MysticalAugmentItems;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.MysticalAugmentRegistries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EventBusSubscriber(value = {Dist.CLIENT, Dist.DEDICATED_SERVER}, modid = MysticalAugmentConstants.MODID, bus = Bus.MOD)
public final class MysticalAugmentsCommon {
    @SubscribeEvent
    public static void onInitializeCommon(final FMLCommonSetupEvent event) {
        log.info("Initializing '{}' common side", MysticalAugmentConstants.MODID);
    }

    @SubscribeEvent
    public static void onNewRegistry(final NewRegistryEvent event) {
        MysticalAugmentRegistries.register(event);
    }

    @SubscribeEvent
    public static void onBuildCreativeTab(final BuildCreativeModeTabContentsEvent event) {
        MysticalAugmentItems.onBuildCreativeTab(event.getTabKey(), (var holder) -> event.accept(holder.get()));
    }

    @SubscribeEvent
    public static void onRegisterDataMapTypes(final RegisterDataMapTypesEvent event) {
        event.register(MysticalAugmentDataMapTypes.ITEM_AUGMENT_TYPES);
    }
}
