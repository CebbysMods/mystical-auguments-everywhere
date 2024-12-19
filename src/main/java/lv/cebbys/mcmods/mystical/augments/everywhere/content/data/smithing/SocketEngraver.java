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

import static lv.cebbys.mcmods.mystical.augments.everywhere.content.MysticalAugmentRegistries.ENGRAVER_KEY;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class SocketEngraver {
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<SocketEngraver>> STREAM_HOLDER_CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, SocketEngraver> STREAM_CODEC;
    public static final Codec<Holder<SocketEngraver>> HOLDER_CODEC;
    public static final Codec<SocketEngraver> CODEC;

    @Getter
    private final Holder<Item> item;
    @Getter
    private final int slots;

    public static Optional<Holder.Reference<SocketEngraver>> getFromTemplate(HolderLookup.Provider registries, ItemStack stack) {
        return registries.lookup(ENGRAVER_KEY).flatMap((var registry) -> registry
                .listElements()
                .filter(SocketEngraver.matcher(stack))
                .findFirst());
    }

    public static Predicate<Holder.Reference<SocketEngraver>> matcher(ItemStack stack) {
        return (var engraver) -> stack.is(engraver.value().item);
    }

    static {
        CODEC = RecordCodecBuilder.create((var builder) -> builder
                .group(
                        RegistryFixedCodec.create(Registries.ITEM).fieldOf("item").forGetter(SocketEngraver::getItem),
                        Codec.INT.fieldOf("slots").forGetter(SocketEngraver::getSlots)
                )
                .apply(builder, SocketEngraver::new)
        );
        STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.holderRegistry(Registries.ITEM), SocketEngraver::getItem,
                ByteBufCodecs.INT, SocketEngraver::getSlots,
                SocketEngraver::new
        );
        HOLDER_CODEC = RegistryFileCodec.create(ENGRAVER_KEY, CODEC);
        STREAM_HOLDER_CODEC = ByteBufCodecs.holder(ENGRAVER_KEY, STREAM_CODEC);
    }
}
