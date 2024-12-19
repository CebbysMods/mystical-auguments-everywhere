package lv.cebbys.mcmods.mystical.augments.everywhere.mixin;

import lv.cebbys.mcmods.mystical.augments.everywhere.api.event.InventoryTickEvent;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.commons.lang3.function.TriFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Inventory.class)
public abstract class InventoryMixin implements Container, Nameable {
    @Inject(
            method = "tick",
            at = @At(value = "HEAD")
    )
    private void preInventoryTick(CallbackInfo ci) {
        var inventory = (Inventory) (Object) this;
        emit(inventory, InventoryTickEvent.Pre::new);
    }

    @Inject(
            method = "tick",
            at = @At(value = "RETURN")
    )
    private void postInventoryTick(CallbackInfo ci) {
        var inventory = (Inventory) (Object) this;
        emit(inventory, InventoryTickEvent.Post::new);
    }

    @Unique
    private static <E extends Event> void emit(
            Inventory inventory,
            TriFunction<Inventory, NonNullList<NonNullList<ItemStack>>, Integer, E> factory
    ) {
        var items = NonNullList.of(inventory.items, inventory.armor, inventory.offhand);
        var count = items.stream().map(NonNullList::size).reduce(Integer::sum).orElse(0);
        NeoForge.EVENT_BUS.post(factory.apply(inventory, items, count));
    }
}
