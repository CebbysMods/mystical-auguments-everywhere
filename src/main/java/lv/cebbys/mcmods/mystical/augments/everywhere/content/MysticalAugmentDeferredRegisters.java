package lv.cebbys.mcmods.mystical.augments.everywhere.content;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.data.smithing.SocketEngraver;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.data.smithing.SocketEssence;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

import static lv.cebbys.mcmods.mystical.augments.everywhere.constant.MysticalAugmentConstants.MODID;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MysticalAugmentDeferredRegisters {
    private static final List<DeferredRegister<?>> REGISTERS;

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPE;
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER;
    public static final DeferredRegister<SocketEngraver> SOCKET_ENGRAVER;
    public static final DeferredRegister<SocketEssence> SOCKET_ESSENCE;
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPE;
    public static final DeferredRegister<Item> ITEM;

    public static void register(IEventBus event) {
        REGISTERS.forEach((var register) -> register.register(event));
    }

    static {
        var builder = new RegisterBuilder();
        DATA_COMPONENT_TYPE = builder.create(Registries.DATA_COMPONENT_TYPE);
        RECIPE_SERIALIZER = builder.create(Registries.RECIPE_SERIALIZER);
        SOCKET_ENGRAVER = builder.create(MysticalAugmentRegistries.ENGRAVER_KEY);
        SOCKET_ESSENCE = builder.create(MysticalAugmentRegistries.ESSENCE_KEY);
        RECIPE_TYPE = builder.create(Registries.RECIPE_TYPE);
        ITEM = builder.create(Registries.ITEM);
        REGISTERS = builder.build();
    }

    private static final class RegisterBuilder {
        private final ImmutableList.Builder<DeferredRegister<?>> builder = ImmutableList.builder();

        public <T> DeferredRegister<T> create(ResourceKey<Registry<T>> key) {
            log.info("Creating '{}' deferred register for registry key '{}'", MODID, key);
            var register = DeferredRegister.create(key, MODID);
            this.builder.add(register);
            return register;
        }

        public List<DeferredRegister<?>> build() {
            return builder.build();
        }
    }
}
