package lv.cebbys.mcmods.mystical.augments.everywhere.content.data.smithing;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;
import java.util.function.Predicate;

import static lv.cebbys.mcmods.mystical.augments.everywhere.content.MysticalAugmentRegistries.ESSENCE_KEY;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class SocketEssence {
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<SocketEssence>> STREAM_HOLDER_CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, SocketEssence> STREAM_CODEC;
    public static final Codec<Holder<SocketEssence>> HOLDER_CODEC;
    public static final Codec<SocketEssence> CODEC;

    @Getter
    private final Holder<Item> item;
    @Getter
    private final int tier;

    public static Optional<Holder.Reference<SocketEssence>> getFromIngredient(HolderLookup.Provider registries, ItemStack stack) {
        return registries.lookup(ESSENCE_KEY).flatMap((var registry) -> registry
                .listElements()
                .filter(SocketEssence.matcher(stack))
                .findFirst());
    }

    public static Predicate<Holder.Reference<SocketEssence>> matcher(ItemStack stack) {
        return (var engraver) -> stack.is(engraver.value().item);
    }

    static {
        CODEC = RecordCodecBuilder.create((var builder) -> builder
                .group(
                        RegistryFixedCodec.create(Registries.ITEM).fieldOf("item").forGetter(SocketEssence::getItem),
                        Codec.INT.fieldOf("slots").forGetter(SocketEssence::getTier)
                )
                .apply(builder, SocketEssence::new)
        );
        STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.holderRegistry(Registries.ITEM), SocketEssence::getItem,
                ByteBufCodecs.INT, SocketEssence::getTier,
                SocketEssence::new
        );
        HOLDER_CODEC = RegistryFileCodec.create(ESSENCE_KEY, CODEC);
        STREAM_HOLDER_CODEC = ByteBufCodecs.holder(ESSENCE_KEY, STREAM_CODEC);
    }
}
