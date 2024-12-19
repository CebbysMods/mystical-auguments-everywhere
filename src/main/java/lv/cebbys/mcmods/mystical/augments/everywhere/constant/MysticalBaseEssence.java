package lv.cebbys.mcmods.mystical.augments.everywhere.constant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum MysticalBaseEssence {
    INFERIUM("inferium"),
    PRUDENTIUM("prudentium_essence"),
    TERTIUM("tertium_essence"),
    IMPERIUM("imperium_essence"),
    SUPREMIUM("supremium_essence"),
    AWAKENED_SUPREMIUM("awakened_supremium_essence"),
    INSANIUM("insanium_essence");

    private final String id;
}
