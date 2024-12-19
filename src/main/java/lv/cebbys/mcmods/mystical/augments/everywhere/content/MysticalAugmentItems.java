package lv.cebbys.mcmods.mystical.augments.everywhere.content;

import com.blakebr0.mysticalagriculture.init.ModCreativeModeTabs;
import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.cebbys.mcmods.mystical.augments.everywhere.constant.MysticalAugmentConstants;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.item.AugmentSocketStoneItem;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.item.SocketEngraverTemplateItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static lv.cebbys.mcmods.mystical.augments.everywhere.content.MysticalAugmentDeferredRegisters.ITEM;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MysticalAugmentItems {
    public static final List<DeferredHolder<Item, ? extends Item>> ITEMS;

    public static final DeferredHolder<Item, SocketEngraverTemplateItem> ENGRAVER_TEMPLATE_I;
    public static final DeferredHolder<Item, SocketEngraverTemplateItem> ENGRAVER_TEMPLATE_II;

    public static final DeferredHolder<Item, AugmentSocketStoneItem> INFERIUM_SOCKET_STONE;
    public static final DeferredHolder<Item, AugmentSocketStoneItem> PRUDENTIUM_SOCKET_STONE;
    public static final DeferredHolder<Item, AugmentSocketStoneItem> TERTIUM_SOCKET_STONE;
    public static final DeferredHolder<Item, AugmentSocketStoneItem> IMPERIUM_SOCKET_STONE;
    public static final DeferredHolder<Item, AugmentSocketStoneItem> SUPREMIUM_SOCKET_STONE;
    public static final DeferredHolder<Item, AugmentSocketStoneItem> AWAKENED_SUPREMIUM_SOCKET_STONE;
    public static final DeferredHolder<Item, AugmentSocketStoneItem> INSANIUM_SOCKET_STONE;

    public static void load() {
        log.info("Successfully registered '{}' items", MysticalAugmentConstants.MODID);
    }

    public static void onBuildCreativeTab(ResourceKey<CreativeModeTab> tab, Consumer<DeferredHolder<Item, ? extends Item>> registry) {
        if (tab == ModCreativeModeTabs.CREATIVE_TAB.getKey()) {
            ITEMS.forEach(registry::accept);
        }
    }

    static {
        var builder = new Builder();

        ENGRAVER_TEMPLATE_I = builder.register("engraver_template_i", SocketEngraverTemplateItem::new);
        ENGRAVER_TEMPLATE_II = builder.register("engraver_template_ii", SocketEngraverTemplateItem::new);

        INFERIUM_SOCKET_STONE = builder.registerSocket("inferium", 1);
        PRUDENTIUM_SOCKET_STONE = builder.registerSocket("prudentium", 2);
        TERTIUM_SOCKET_STONE = builder.registerSocket("tertium", 3);
        IMPERIUM_SOCKET_STONE = builder.registerSocket("imperium", 4);
        SUPREMIUM_SOCKET_STONE = builder.registerSocket("supremium", 5);
        AWAKENED_SUPREMIUM_SOCKET_STONE = builder.registerSocket("awakened_supremium", 5);
        INSANIUM_SOCKET_STONE = builder.registerSocket("insanium", 6);

        ITEMS = builder.build();
    }

    private static final class Builder {
        private final ImmutableList.Builder<DeferredHolder<Item, ? extends Item>> items = ImmutableList.builder();

        public <I extends Item> DeferredHolder<Item, I> register(String name, Supplier<I> factory) {
            log.info("Registering item '{}'", name);
            var entry = ITEM.register(name, (var properties) -> factory.get());
            items.add(entry);
            return entry;
        }

        public DeferredHolder<Item, AugmentSocketStoneItem> registerSocket(String name, int tier) {
            return register("%s_essence_crystal".formatted(name), () -> new AugmentSocketStoneItem(tier));
        }

        public List<DeferredHolder<Item, ? extends Item>> build() {
            return items.build();
        }
    }
}
