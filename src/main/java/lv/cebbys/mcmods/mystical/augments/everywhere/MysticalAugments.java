package lv.cebbys.mcmods.mystical.augments.everywhere;

import lombok.extern.slf4j.Slf4j;
import lv.cebbys.mcmods.mystical.augments.everywhere.constant.MysticalAugmentConstants;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Slf4j
@Mod(MysticalAugmentConstants.MODID)
public class MysticalAugments {

    public MysticalAugments(IEventBus bus) {
        MysticalAugmentItems.load();
        MysticalAugmentComponentTypes.load();
        MysticalAugmentRecipes.load();
        MysticalAugmentSocketEngravers.load();
        MysticalAugmentSocketEssences.load();
        MysticalAugmentTags.load();

        MysticalAugmentDeferredRegisters.register(bus);
    }
}
