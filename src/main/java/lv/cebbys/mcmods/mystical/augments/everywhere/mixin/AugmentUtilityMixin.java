package lv.cebbys.mcmods.mystical.augments.everywhere.mixin;

import com.blakebr0.mysticalagriculture.api.MysticalAgricultureAPI;
import com.blakebr0.mysticalagriculture.api.MysticalAgricultureDataComponentTypes;
import com.blakebr0.mysticalagriculture.api.components.AugmentComponent;
import com.blakebr0.mysticalagriculture.api.tinkering.Augment;
import com.blakebr0.mysticalagriculture.api.tinkering.ITinkerable;
import com.blakebr0.mysticalagriculture.api.util.AugmentUtils;
import lombok.extern.slf4j.Slf4j;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.MysticalAugmentComponentTypes;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

import static com.blakebr0.mysticalagriculture.api.MysticalAgricultureDataComponentTypes.EQUIPPED_AUGMENTS;
import static lv.cebbys.mcmods.mystical.augments.everywhere.utility.PredicateUtility.match;

@Slf4j
@Mixin(AugmentUtils.class)
public abstract class AugmentUtilityMixin {
    @Inject(
            cancellable = true,
            method = "addAugment",
            at = @At("HEAD")
    )
    private static void addAugment(ItemStack stack, Augment augment, int slot, CallbackInfo ci) {
        var data = stack.get(MysticalAugmentComponentTypes.SOCKET_DATA);
        if (data instanceof ITinkerable tinkerable) {
            if (slot < tinkerable.getAugmentSlots() && tinkerable.getTinkerableTier() >= augment.getTier()) {
                var component = stack.get(EQUIPPED_AUGMENTS);
                var augments = new ArrayList<AugmentComponent>();
                if (component != null && !component.isEmpty()) {
                    augments.addAll(component);
                }
                augments.removeIf(match(AugmentComponent::slot, slot));
                augments.add(new AugmentComponent(augment.getId(), slot));
                stack.set(EQUIPPED_AUGMENTS, augments);
            }
            ci.cancel();
        }
    }


    @Inject(
            cancellable = true,
            method = "removeAugment",
            at = @At("HEAD")
    )
    private static void removeAugment(ItemStack stack, int slot, CallbackInfo ci) {
        var component = stack.get(EQUIPPED_AUGMENTS);
        if (component == null) {
            return;
        }
        var data = stack.get(MysticalAugmentComponentTypes.SOCKET_DATA);
        if (data instanceof ITinkerable tinkerable) {
            Augment augment = AugmentUtils.getAugment(stack, slot);
            if (slot < tinkerable.getAugmentSlots() && augment != null) {
                var augments = new ArrayList<AugmentComponent>();
                if (!component.isEmpty()) {
                    augments.addAll(component);
                }
                augments.removeIf(match(AugmentComponent::slot, slot));
                stack.set(EQUIPPED_AUGMENTS, augments);
            }
            ci.cancel();
        }
    }


    @Inject(
            cancellable = true,
            method = "getAugment",
            at = @At("HEAD")
    )
    private static void getAugment(ItemStack stack, int slot, CallbackInfoReturnable<Augment> cir) {
        var component = stack.get(EQUIPPED_AUGMENTS);
        if (component == null) {
            return;
        }
        var data = stack.get(MysticalAugmentComponentTypes.SOCKET_DATA);
        if (data instanceof ITinkerable tinkerable) {
            var augment = component.stream()
                    .filter(match(AugmentComponent::slot, slot))
                    .findFirst()
                    .orElse(null);
            if (augment != null) {
                if (slot < tinkerable.getAugmentSlots()) {
                    cir.setReturnValue(MysticalAgricultureAPI.getAugmentRegistry().getAugmentById(augment.id()));
                }
            }
        }
    }


    @Inject(
            cancellable = true,
            method = "getAugments",
            at = @At("HEAD")
    )
    private static void getAugments(ItemStack stack, CallbackInfoReturnable<List<Augment>> cir) {
        var component = stack.get(MysticalAgricultureDataComponentTypes.EQUIPPED_AUGMENTS);
        if (component == null) {
            return;
        }
        var augments = new ArrayList<Augment>();
        var data = stack.get(MysticalAugmentComponentTypes.SOCKET_DATA);
        if (data instanceof ITinkerable tinkerable) {
            int slots = tinkerable.getAugmentSlots();
            for (int i = 0; i < slots; ++i) {
                Augment augment = AugmentUtils.getAugment(stack, i);
                if (augment != null) {
                    augments.add(augment);
                }
                cir.setReturnValue(augments);
            }
        }
    }
}
