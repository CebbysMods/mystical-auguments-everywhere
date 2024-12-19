package lv.cebbys.mcmods.mystical.augments.everywhere;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.cebbys.mcmods.mystical.augments.everywhere.constant.MysticalAugmentConstants;
import lv.cebbys.mcmods.mystical.augments.everywhere.provider.MysticalAugmentsDataMapTypeProvider;
import lv.cebbys.mcmods.mystical.augments.everywhere.provider.MysticalAugmentsItemModelProvider;
import lv.cebbys.mcmods.mystical.augments.everywhere.provider.MysticalAugmentsItemTagProvider;
import lv.cebbys.mcmods.mystical.augments.everywhere.provider.MysticalAugmentsRecipeProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EventBusSubscriber(value = {Dist.DEDICATED_SERVER, Dist.CLIENT}, modid = MysticalAugmentConstants.MODID, bus = Bus.MOD)
public final class MysticalAugmentsDatagen {

    @SubscribeEvent
    private static void onGenerateData(final GatherDataEvent event) {
        log.info("On '{}' generate server data", MysticalAugmentConstants.MODID);
        var generator = event.getGenerator();
        var output = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();
        var helper = event.getExistingFileHelper();

        var future = CompletableFuture.completedFuture(TagsProvider.TagLookup.<Block>empty());

//        generator.addProvider(event.includeServer(), new MysticalAugmentDataProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new MysticalAugmentsRecipeProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new MysticalAugmentsItemTagProvider(output, lookupProvider, future, helper));
        generator.addProvider(event.includeServer(), new MysticalAugmentsItemModelProvider(output, helper));
        generator.addProvider(event.includeServer(), new MysticalAugmentsDataMapTypeProvider(output, lookupProvider));
    }
}
