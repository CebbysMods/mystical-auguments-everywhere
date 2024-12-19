package lv.cebbys.mcmods.mystical.augments.everywhere.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Function;
import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PredicateUtility {
    public static <I, O> Predicate<I> match(Function<I, O> mapper, O equal) {
        return (var instance) -> mapper.apply(instance) == equal;
    }
}
