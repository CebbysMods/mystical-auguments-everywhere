package lv.cebbys.mcmods.mystical.augments.everywhere.api.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class ItemStackTickEvent extends Event {
    @Getter
    private final ItemStack stack;
    @Getter
    private final Level level;
    @Getter
    private final Entity entity;
    @Getter
    private final int slot;
    @Getter
    private final boolean current;

    public static class Pre extends ItemStackTickEvent {
        public Pre(ItemStack stack, Level level, Entity entity, int slot, boolean current) {
            super(stack, level, entity, slot, current);
        }
    }

    public static class Post extends ItemStackTickEvent {
        public Post(ItemStack stack, Level level, Entity entity, int slot, boolean current) {
            super(stack, level, entity, slot, current);
        }
    }
}
