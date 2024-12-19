package lv.cebbys.mcmods.mystical.augments.everywhere.mixin;

import lv.cebbys.mcmods.mystical.augments.everywhere.api.event.ItemStackTickEvent;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.extensions.IItemStackExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements DataComponentHolder, IItemStackExtension, MutableDataComponentHolder {
    @Inject(method = "inventoryTick", at = @At(value = "HEAD"))
    private void preInventoryTick(Level level, Entity entity, int slot, boolean current, CallbackInfo ci) {
        var stack = (ItemStack) (Object) this;
        NeoForge.EVENT_BUS.post(new ItemStackTickEvent.Pre(stack, level, entity, slot, current));
    }

    @Inject(method = "inventoryTick", at = @At(value = "RETURN"))
    private void postInventoryTick(Level level, Entity entity, int slot, boolean current, CallbackInfo ci) {
        var stack = (ItemStack) (Object) this;
        NeoForge.EVENT_BUS.post(new ItemStackTickEvent.Post(stack, level, entity, slot, current));
    }
}
