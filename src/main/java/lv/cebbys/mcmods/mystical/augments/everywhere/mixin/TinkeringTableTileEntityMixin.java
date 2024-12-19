package lv.cebbys.mcmods.mystical.augments.everywhere.mixin;

import com.blakebr0.cucumber.inventory.BaseItemStackHandler;
import com.blakebr0.cucumber.inventory.OnContentsChangedFunction;
import com.blakebr0.mysticalagriculture.api.tinkering.IAugmentProvider;
import com.blakebr0.mysticalagriculture.lib.ModCrops;
import com.blakebr0.mysticalagriculture.tileentity.TinkeringTableTileEntity;
import lombok.extern.slf4j.Slf4j;
import lv.cebbys.mcmods.mystical.augments.everywhere.utility.MysticalAugmentUtility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Slf4j
@Mixin(TinkeringTableTileEntity.class)
public abstract class TinkeringTableTileEntityMixin {

    @Inject(
            method = "createInventoryHandler(Lcom/blakebr0/cucumber/inventory/OnContentsChangedFunction;)Lcom/blakebr0/cucumber/inventory/BaseItemStackHandler;",
            at = @At(value = "INVOKE", target = "Lcom/blakebr0/cucumber/inventory/BaseItemStackHandler;create(ILcom/blakebr0/cucumber/inventory/OnContentsChangedFunction;Ljava/util/function/Consumer;)Lcom/blakebr0/cucumber/inventory/BaseItemStackHandler;"),
            cancellable = true
    )
    private static void createInventoryHandler(OnContentsChangedFunction event, CallbackInfoReturnable<BaseItemStackHandler> cir) {
        log.info("Patching {}.createInventoryHandler", TinkeringTableTileEntityMixin.class.getSimpleName());
        cir.setReturnValue(BaseItemStackHandler.create(7, event, (var builder) -> {
            builder.setDefaultSlotLimit(1);
            builder.setCanInsert((slot, stack) -> {
                var item = stack.getItem();
                return switch (slot) {
                    case 0 -> MysticalAugmentUtility.isTinkerable(stack);
                    case 1, 2 -> item instanceof IAugmentProvider;
                    case 3 -> item == ModCrops.AIR.getEssenceItem();
                    case 4 -> item == ModCrops.EARTH.getEssenceItem();
                    case 5 -> item == ModCrops.WATER.getEssenceItem();
                    case 6 -> item == ModCrops.FIRE.getEssenceItem();
                    default -> true;
                };
            });
        }));
        cir.cancel();
    }

}
