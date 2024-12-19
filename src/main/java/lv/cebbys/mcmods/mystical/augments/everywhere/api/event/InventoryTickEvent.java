package lv.cebbys.mcmods.mystical.augments.everywhere.api.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class InventoryTickEvent extends Event {
    @Getter
    private final Inventory inventory;
    @Getter
    private final NonNullList<NonNullList<ItemStack>> inventoryItems;
    @Getter
    private final int inventorySize;

    public static class Pre extends InventoryTickEvent {
        public Pre(Inventory inventory, NonNullList<NonNullList<ItemStack>> inventoryItems, int inventorySize) {
            super(inventory, inventoryItems, inventorySize);
        }
    }

    public static class Post extends InventoryTickEvent {
        public Post(Inventory inventory, NonNullList<NonNullList<ItemStack>> inventoryItems, int inventorySize) {
            super(inventory, inventoryItems, inventorySize);
        }
    }

    public Entity getEntity() {
        return inventory.player;
    }

    public Player getPlayer() {
        return inventory.player;
    }
}
