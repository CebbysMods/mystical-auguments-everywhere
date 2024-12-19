package lv.cebbys.mcmods.mystical.augments.everywhere.provider;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lv.cebbys.mcmods.mystical.augments.everywhere.constant.MysticalAugmentConstants;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.MysticalAugmentItems;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.MysticalAugmentTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
public class MysticalAugmentsItemTagProvider extends ItemTagsProvider {
    private static final Materials MINECRAFT_ARMOR_MATERIALS;
    private static final Materials MINECRAFT_TOOL_MATERIALS;
    private static final List<String> MINECRAFT_ARMOR_TYPES;
    private static final List<String> MINECRAFT_TOOL_TYPES;

    public MysticalAugmentsItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper helper) {
        super(output, lookupProvider, blockTags, MysticalAugmentConstants.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        addEquipmentTag();

        var builder = tag(MysticalAugmentTags.MYSTICAL_AUGMENT_AXE_LIKE);
        MINECRAFT_TOOL_MATERIALS.forEach("axe", builder::addOptional);

        builder = tag(MysticalAugmentTags.MYSTICAL_AUGMENT_HOE_LIKE);
        MINECRAFT_TOOL_MATERIALS.forEach("hoe", builder::addOptional);

        builder = tag(MysticalAugmentTags.MYSTICAL_AUGMENT_PICKAXE_LIKE);
        MINECRAFT_TOOL_MATERIALS.forEach("pickaxe", builder::addOptional);

        builder = tag(MysticalAugmentTags.MYSTICAL_AUGMENT_SHOVEL_LIKE);
        MINECRAFT_TOOL_MATERIALS.forEach("shovel", builder::addOptional);

        builder = tag(MysticalAugmentTags.MYSTICAL_AUGMENT_SWORD_LIKE);
        MINECRAFT_TOOL_MATERIALS.forEach("sword", builder::addOptional);


        builder = tag(MysticalAugmentTags.MYSTICAL_AUGMENT_HELMET_LIKE);
        MINECRAFT_ARMOR_MATERIALS.forEach("helmet", builder::addOptional);

        builder = tag(MysticalAugmentTags.MYSTICAL_AUGMENT_CHESTPLATE_LIKE);
        MINECRAFT_ARMOR_MATERIALS.forEach("chestplate", builder::addOptional);

        builder = tag(MysticalAugmentTags.MYSTICAL_AUGMENT_LEGGINGS_LIKE);
        MINECRAFT_ARMOR_MATERIALS.forEach("leggings", builder::addOptional);

        builder = tag(MysticalAugmentTags.MYSTICAL_AUGMENT_BOOTS_LIKE);
        MINECRAFT_ARMOR_MATERIALS.forEach("boots", builder::addOptional);


        tag(MysticalAugmentTags.ENGRAVER_TEMPLATE)
                .add(MysticalAugmentItems.ENGRAVER_TEMPLATE_I.get())
                .add(MysticalAugmentItems.ENGRAVER_TEMPLATE_II.get());

        tag(MysticalAugmentTags.ESSENCE_STONE)
                .add(MysticalAugmentItems.INFERIUM_SOCKET_STONE.get())
                .add(MysticalAugmentItems.PRUDENTIUM_SOCKET_STONE.get())
                .add(MysticalAugmentItems.TERTIUM_SOCKET_STONE.get())
                .add(MysticalAugmentItems.IMPERIUM_SOCKET_STONE.get())
                .add(MysticalAugmentItems.SUPREMIUM_SOCKET_STONE.get())
                .add(MysticalAugmentItems.AWAKENED_SUPREMIUM_SOCKET_STONE.get())
                .add(MysticalAugmentItems.INSANIUM_SOCKET_STONE.get());
    }

    private void addEquipmentTag() {
        var builder = tag(MysticalAugmentTags.EQUIPMENT);
        MINECRAFT_ARMOR_MATERIALS.forEach(MINECRAFT_ARMOR_TYPES, builder::addOptional);
        MINECRAFT_TOOL_MATERIALS.forEach(MINECRAFT_TOOL_TYPES, builder::addOptional);
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static final class Materials {
        private final String namespace;
        private final List<String> materials;

        private void forEach(Consumer<String> consumer) {
            materials.forEach(consumer);
        }

        private void forEach(String tool, Consumer<ResourceLocation> consumer) {
            materials.forEach((var material) -> {
                var location = ResourceLocation.fromNamespaceAndPath(namespace, "%s_%s".formatted(material, tool));
                consumer.accept(location);
            });
        }

        private void forEach(List<String> tools, Consumer<ResourceLocation> consumer) {
            tools.forEach((var tool) -> forEach(tool, consumer));
        }
    }


    static {
        MINECRAFT_ARMOR_MATERIALS = new Materials("minecraft", List.of(
                "leather", "chainmail", "iron", "golden", "diamond", "netherite"
        ));
        MINECRAFT_TOOL_MATERIALS = new Materials("minecraft", List.of(
                "wooden", "stone", "iron", "golden", "diamond", "netherite"
        ));
        MINECRAFT_ARMOR_TYPES = ImmutableList.<String>builder()
                .add("helmet")
                .add("chestplate")
                .add("leggings")
                .add("boots")
                .build();
        MINECRAFT_TOOL_TYPES = ImmutableList.<String>builder()
                .add("sword")
                .add("shovel")
                .add("pickaxe")
                .add("axe")
                .add("hoe")
                .build();
    }
}
