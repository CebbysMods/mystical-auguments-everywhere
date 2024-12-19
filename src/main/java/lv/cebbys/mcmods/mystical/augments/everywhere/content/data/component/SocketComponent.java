package lv.cebbys.mcmods.mystical.augments.everywhere.content.data.component;

import com.blakebr0.mysticalagriculture.api.tinkering.AugmentType;
import com.blakebr0.mysticalagriculture.api.tinkering.ITinkerable;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lv.cebbys.mcmods.mystical.augments.everywhere.constant.MysticalAugmentCodecs;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.data.smithing.SocketEngraver;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.data.smithing.SocketEssence;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class SocketComponent implements ITinkerable {
    public static final StreamCodec<RegistryFriendlyByteBuf, SocketComponent> STREAM_CODEC;
    public static final Codec<SocketComponent> CODEC;

    private final Holder<SocketEngraver> engraver;
    private final Holder<SocketEssence> essence;
    private final EnumSet<AugmentType> types;

    @Override
    public int getAugmentSlots() {
        return engraver.value().getSlots();
    }

    @Override
    public EnumSet<AugmentType> getAugmentTypes() {
        return types;
    }

    @Override
    public int getTinkerableTier() {
        return essence.value().getTier();
    }

    /**
     * Returns true if this instance can be used to replace other socket component
     *
     * @param other Socket component to be replaced
     * @return True if can this instance can replace other socket
     */
    public boolean canReplace(@Nullable SocketComponent other) {
        return other == null || (
                other.getAugmentSlots() <= this.getAugmentSlots() && other.getTinkerableTier() <= this.getTinkerableTier()
        );
    }

    static {
        CODEC = RecordCodecBuilder.create((var builder) -> builder
                .group(
                        SocketEngraver.HOLDER_CODEC.fieldOf("engraver").forGetter(SocketComponent::getEngraver),
                        SocketEssence.HOLDER_CODEC.fieldOf("essence").forGetter(SocketComponent::getEssence),
                        MysticalAugmentCodecs.Codecs.AUGMENT_TYPE_ENUMSET.fieldOf("types").forGetter(SocketComponent::getTypes)
                )
                .apply(builder, SocketComponent::new)
        );
        STREAM_CODEC = StreamCodec.composite(
                SocketEngraver.STREAM_HOLDER_CODEC, SocketComponent::getEngraver,
                SocketEssence.STREAM_HOLDER_CODEC, SocketComponent::getEssence,
                MysticalAugmentCodecs.StreamCodecs.AUGMENT_TYPE_ENUMSET, SocketComponent::getAugmentTypes,
                SocketComponent::new
        );
    }
}
