package lv.cebbys.mcmods.mystical.augments.everywhere.mixin.slot;

import com.blakebr0.mysticalagriculture.api.tinkering.Augment;
import com.blakebr0.mysticalagriculture.api.tinkering.IAugmentProvider;
import com.blakebr0.mysticalagriculture.api.tinkering.ITinkerable;
import com.blakebr0.mysticalagriculture.container.slot.AugmentSlot;
import lombok.extern.slf4j.Slf4j;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.MysticalAugmentComponentTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Slf4j
@Mixin(AugmentSlot.class)
public abstract class AugmentSlotMixin extends SlotItemHandler {
    public AugmentSlotMixin(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Final
    @Shadow
    private int augmentSlot;

    @Inject(
            cancellable = true,
            method = "mayPlace",
            at = @At("HEAD")
    )
    private void mayPlace(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (super.mayPlace(stack) && this.isActive()) {
            ItemStack item = this.getItemHandler().getStackInSlot(0);
            log.info("Checking if item '{}' can have augment", item);
            var data = item.get(MysticalAugmentComponentTypes.SOCKET_DATA);
            if (data instanceof ITinkerable tinkerable) {
                Item provider = stack.getItem();
                if (provider instanceof IAugmentProvider augmentProvider) {
                    Augment augment = augmentProvider.getAugment();
                    cir.setReturnValue(tinkerable.canApplyAugment(augment));
                }
            }
        }
    }

    @Inject(
            cancellable = true,
            method = "isActive",
            at = @At("HEAD")
    )
    private void isActive(CallbackInfoReturnable<Boolean> cir) {
        ItemStack item = this.getItemHandler().getStackInSlot(0);
        log.info("Checking how many augment slots item '{}' has", item);
        var data = item.get(MysticalAugmentComponentTypes.SOCKET_DATA);
        if (data instanceof ITinkerable tinkerable) {
            cir.setReturnValue(this.augmentSlot < tinkerable.getAugmentSlots());
        }
    }
}
