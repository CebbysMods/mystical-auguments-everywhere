package lv.cebbys.mcmods.mystical.augments.everywhere.content;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.cebbys.mcmods.mystical.augments.everywhere.constant.MysticalAugmentConstants;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.data.smithing.SocketEngraver;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.data.smithing.SocketEssence;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.List;
import java.util.function.Consumer;

import static lv.cebbys.mcmods.mystical.augments.everywhere.constant.MysticalAugmentConstants.resource;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MysticalAugmentRegistries {
    private static final List<Registry<?>> REGISTRIES;

    public static final ResourceKey<Registry<SocketEngraver>> ENGRAVER_KEY;
    public static final ResourceKey<Registry<SocketEssence>> ESSENCE_KEY;
    public static final Registry<SocketEngraver> ENGRAVER;
    public static final Registry<SocketEssence> ESSENCE;

    public static void load() {
        log.info("Successfully registered '{}' registries", MysticalAugmentConstants.MODID);
    }

    public static void register(final NewRegistryEvent event) {
        REGISTRIES.forEach(event::register);
    }

    static {
        ENGRAVER_KEY = ResourceKey.createRegistryKey(resource("engravers"));
        ESSENCE_KEY = ResourceKey.createRegistryKey(resource("essences"));

        var builder = new SimpleRegistryBuilder();
        ENGRAVER = builder.create(ENGRAVER_KEY, (var b) -> b.sync(true).maxId(256));
        ESSENCE = builder.create(ESSENCE_KEY, (var b) -> b.sync(true).maxId(256));
        REGISTRIES = builder.build();
    }

    private static final class SimpleRegistryBuilder {
        private final ImmutableList.Builder<Registry<?>> builder = new ImmutableList.Builder<>();

        private <T> Registry<T> create(ResourceKey<Registry<T>> key, Consumer<RegistryBuilder<T>> consumer) {
            var rb = new RegistryBuilder<T>(key);
            consumer.accept(rb);
            var registry = rb.create();
            builder.add(registry);
            return registry;
        }

        public List<Registry<?>> build() {
            return builder.build();
        }
    }
}
