package lv.cebbys.mcmods.mystical.augments.everywhere.content.item;

import lv.cebbys.mcmods.mystical.augments.everywhere.compatability.minecraft.MinecraftSmithingTemplateCompatibility;
import lv.cebbys.mcmods.mystical.augments.everywhere.compatability.mystical.agriculture.MysticalAgricultureSmithingTemplateCompatibility;
import lv.cebbys.mcmods.mystical.augments.everywhere.constant.MysticalAugmentConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;

public class SocketEngraverTemplateItem extends SmithingTemplateItem {
    private static final ChatFormatting TITLE;
    private static final ChatFormatting DESCRIPTION;
    private static final Component APPLIES_TO_EQUIPMENT;
    private static final Component SOCKET_INGREDIENTS;
    private static final Component SOCKET_UPGRADE;
    private static final Component BASE_SLOT_DESCRIPTION;
    private static final Component ADDITION_SLOT_DESCRIPTION;
    private static final List<ResourceLocation> BASE_SLOT_ICONS;
    private static final List<ResourceLocation> ADDITION_SLOT_ICONS;

    public SocketEngraverTemplateItem() {
        super(
                APPLIES_TO_EQUIPMENT,
                SOCKET_INGREDIENTS,
                SOCKET_UPGRADE,
                BASE_SLOT_DESCRIPTION,
                ADDITION_SLOT_DESCRIPTION,
                BASE_SLOT_ICONS,
                ADDITION_SLOT_ICONS
        );
    }

    private static Component createItemComponent(String id) {
        return createItemComponent(id, (var style) -> style);
    }

    private static Component createItemComponent(String id, UnaryOperator<Style> function) {
        return createComponent("item", id, function);
    }

    private static Component createUpgradeComponent(String id, UnaryOperator<Style> function) {
        return createComponent("upgrade", id, function);
    }

    private static Component createComponent(String type, String id, UnaryOperator<Style> function) {
        var name = ResourceLocation.fromNamespaceAndPath(MysticalAugmentConstants.MODID, id);
        var description = Util.makeDescriptionId(type, name);
        return Component.translatable(description).withStyle(function);
    }

    private static List<ResourceLocation> createBaseSlotIconList() {
        var list = new LinkedList<ResourceLocation>();
        MinecraftSmithingTemplateCompatibility.INSTANCE.appendBaseIconLocations(list::add);
        MysticalAgricultureSmithingTemplateCompatibility.INSTANCE.appendBaseIconLocations(list::add);
        return List.copyOf(Set.copyOf(list));
    }

    private static List<ResourceLocation> createAdditionSlotIconList() {
        var list = new LinkedList<ResourceLocation>();
        MinecraftSmithingTemplateCompatibility.INSTANCE.appendAdditionIconLocations(list::add);
        MysticalAgricultureSmithingTemplateCompatibility.INSTANCE.appendAdditionIconLocations(list::add);
        return List.copyOf(Set.copyOf(list));
    }

    static {
        TITLE = ChatFormatting.GRAY;
        DESCRIPTION = ChatFormatting.BLUE;
        APPLIES_TO_EQUIPMENT = createItemComponent(
                "smithing_template.mystical_socket_upgrade.applies_to",
                (var style) -> style.applyFormat(DESCRIPTION)
        );
        SOCKET_INGREDIENTS = createItemComponent(
                "smithing_template.mystical_socket_upgrade.ingredients",
                (var style) -> style.applyFormat(DESCRIPTION)
        );
        SOCKET_UPGRADE = createUpgradeComponent(
                "smithing_template.mystical_socket_upgrade",
                (var style) -> style.applyFormat(TITLE)
        );
        BASE_SLOT_DESCRIPTION = createItemComponent(
                "smithing_template.mystical_socket_upgrade.slot_description"
        );
        ADDITION_SLOT_DESCRIPTION = createItemComponent(
                "smithing_template.mystical_socket_upgrade.additions_description"
        );
        BASE_SLOT_ICONS = createBaseSlotIconList();
        ADDITION_SLOT_ICONS = createAdditionSlotIconList();
    }
}
