package lv.cebbys.mcmods.mystical.augments.everywhere.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlayerInventoryUtility {
    public static final int HELMET_SLOT = 36;
    public static final int CHESTPLATE_SLOT = 37;
    public static final int LEGGINGS_SLOT = 38;
    public static final int BOOTS_SLOT = 39;

    public static boolean isArmorSlot(int slot) {
        return slot >= HELMET_SLOT && slot <= BOOTS_SLOT;
    }

    public static boolean isHelmetSlot(int slot) {
        return slot == HELMET_SLOT;
    }

    public static boolean isChestplateSlot(int slot) {
        return slot == CHESTPLATE_SLOT;
    }

    public static boolean isLeggingsSlot(int slot) {
        return slot == LEGGINGS_SLOT;
    }

    public static boolean isBootsSlot(int slot) {
        return slot == BOOTS_SLOT;
    }
}
