package lv.cebbys.mcmods.mystical.augments.everywhere.constant;

import com.blakebr0.mysticalagriculture.api.tinkering.AugmentType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.EnumSet;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MysticalAugmentCodecs {
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class MapCodecs {
        public static final MapCodec<AugmentType> AUGMENT_TYPE;

        static {
            AUGMENT_TYPE = RecordCodecBuilder.mapCodec((var builder) -> builder
                    .group(Codec.STRING.fieldOf("name").forGetter(AugmentType::getName))
                    .apply(builder, AugmentType::fromName)
            );
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Codecs {
        public static final Codec<EnumSet<AugmentType>> AUGMENT_TYPE_ENUMSET;
        public static final Codec<List<AugmentType>> AUGMENT_TYPE_LIST;
        public static final Codec<AugmentType> AUGMENT_TYPE;

        static {
            AUGMENT_TYPE = MapCodecs.AUGMENT_TYPE.codec();
            AUGMENT_TYPE_LIST = Codec.list(AUGMENT_TYPE);
            AUGMENT_TYPE_ENUMSET = AUGMENT_TYPE_LIST.xmap(EnumSet::copyOf, List::copyOf);
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class StreamCodecs {
        public static final StreamCodec<FriendlyByteBuf, EnumSet<AugmentType>> AUGMENT_TYPE_ENUMSET;
        public static final StreamCodec<FriendlyByteBuf, List<AugmentType>> AUGMENT_TYPE_LIST;
        public static final StreamCodec<FriendlyByteBuf, AugmentType> AUGMENT_TYPE;

        static {
            AUGMENT_TYPE = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, AugmentType::getName, AugmentType::fromName);
            AUGMENT_TYPE_LIST = AUGMENT_TYPE.apply(ByteBufCodecs.list());
            AUGMENT_TYPE_ENUMSET = AUGMENT_TYPE_LIST.map(EnumSet::copyOf, List::copyOf);
        }
    }

}
