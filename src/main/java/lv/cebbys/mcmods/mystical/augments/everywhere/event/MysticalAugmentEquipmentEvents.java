package lv.cebbys.mcmods.mystical.augments.everywhere.event;

import com.blakebr0.mysticalagriculture.api.tinkering.Augment;
import com.blakebr0.mysticalagriculture.api.util.AugmentUtils;
import com.blakebr0.mysticalagriculture.lib.ModTooltips;
import lombok.extern.slf4j.Slf4j;
import lv.cebbys.mcmods.mystical.augments.everywhere.api.event.ItemStackTickEvent;
import lv.cebbys.mcmods.mystical.augments.everywhere.constant.MysticalAugmentConstants;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.MysticalAugmentComponentTypes;
import lv.cebbys.mcmods.mystical.augments.everywhere.utility.PlayerInventoryUtility;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.function.Consumer;

@Slf4j
@EventBusSubscriber(value = {Dist.CLIENT, Dist.DEDICATED_SERVER}, modid = MysticalAugmentConstants.MODID, bus = EventBusSubscriber.Bus.GAME)
public class MysticalAugmentEquipmentEvents {

    @SubscribeEvent
    public static void useOn(PlayerInteractEvent.RightClickBlock event) {
        var player = event.getEntity();
        var stack = player.getMainHandItem();
        if (isAugmentedStack(stack)) {
            var hand = event.getHand();
            var result = event.getHitVec();
            var ctx = new UseOnContext(player, hand, result);
            forEachAugment(stack, (var augment) -> augment.onItemUse(ctx));
        }
    }

    @SubscribeEvent
    public static void use(PlayerInteractEvent.RightClickItem event) {
        var player = event.getEntity();
        var stack = player.getMainHandItem();
        if (isAugmentedStack(stack)) {
            var level = player.level();
            var hand = event.getHand();
            forEachAugment(stack, (var augment) -> augment.onRightClick(stack, level, player, hand));
        }
    }

    @SubscribeEvent
    public static void interactLivingEntity(PlayerInteractEvent.EntityInteract event) {
        var player = event.getEntity();
        var hand = event.getHand();
        var stack = player.getItemInHand(hand);
        if (isAugmentedStack(stack) && event.getTarget() instanceof LivingEntity target) {
            forEachAugment(stack, (var augment) -> augment.onRightClickEntity(stack, player, target, hand));
        }
    }

    @SubscribeEvent
    public static void hurtEnemy(LivingDamageEvent.Pre event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            var stack = attacker.getWeaponItem();
            var target = event.getEntity();
            if (isAugmentedStack(stack)) {
                forEachAugment(stack, (var augment) -> augment.onHitEntity(stack, target, attacker));
            }
        }
    }

    @SubscribeEvent
    public static void mineBlock(BlockEvent.BreakEvent event) {
        var player = event.getPlayer();
        var stack = player.getMainHandItem();
        if (isAugmentedStack(stack)) {
            var level = player.level();
            var state = event.getState();
            var pos = event.getPos();
            forEachAugment(stack, (var augment) -> augment.onBlockDestroyed(stack, level, state, pos, player));
        }
    }

    @SubscribeEvent
    public static void inventoryTick(ItemStackTickEvent.Pre event) {
        var stack = event.getStack();
        if (isAugmentedStack(stack)) {
            var level = event.getLevel();
            var entity = event.getEntity();
            var slot = event.getSlot();
            if (isArmorTickEvent(stack, entity, slot)) {
                forEachAugment(stack, (var augment) -> augment.onArmorTick(stack, level, (Player) entity));
            }
            var selected = event.isCurrent();
            forEachAugment(stack, (var augment) -> augment.onInventoryTick(stack, level, entity, slot, selected));
        }
    }

    @SubscribeEvent
    public static void appendHoverText(ItemTooltipEvent event) {
        var stack = event.getItemStack();
        var data = stack.get(MysticalAugmentComponentTypes.SOCKET_DATA);
        if (data != null) {
            var tooltip = new ArrayList<Component>();
            var tier = data.getEssence().value().getTier();
            tooltip.add(ModTooltips.getTooltipForTier(tier));
            var slots = data.getEngraver().value().getSlots();
            ModTooltips.addAugmentListToTooltip(tooltip, stack, slots);
            event.getToolTip().addAll(1, tooltip);
        }
    }

    private static boolean isArmorTickEvent(ItemStack stack, Entity entity, int slot) {
        return stack.getItem() instanceof ArmorItem
                && PlayerInventoryUtility.isArmorSlot(slot)
                && entity instanceof Player;
    }

    private static boolean isAugmentedStack(@Nullable ItemStack stack) {
        return stack != null && stack.get(MysticalAugmentComponentTypes.SOCKET_DATA) != null;
    }

    private static void forEachAugment(ItemStack stack, Consumer<Augment> consumer) {
        AugmentUtils.getAugments(stack).forEach(consumer);
    }
}
