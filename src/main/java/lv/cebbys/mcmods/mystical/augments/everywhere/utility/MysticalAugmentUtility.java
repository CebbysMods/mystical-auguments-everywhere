package lv.cebbys.mcmods.mystical.augments.everywhere.utility;

import com.blakebr0.mysticalagriculture.api.tinkering.Augment;
import com.blakebr0.mysticalagriculture.api.tinkering.ITinkerable;
import com.blakebr0.mysticalagriculture.api.util.AugmentUtils;
import lombok.extern.slf4j.Slf4j;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.MysticalAugmentComponentTypes;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

@Slf4j
public final class MysticalAugmentUtility {
    public static boolean isTinkerable(ItemStack stack) {
        return isSimpleTinkerable(stack) || isAdvancedTinkerable(stack);
    }

    public static boolean isSimpleTinkerable(ItemStack stack) {
        log.debug("Checking if item '{}' is simple tinkerable", stack);
        return stack != null && stack.getItem() instanceof ITinkerable;
    }

    public static boolean isAdvancedTinkerable(ItemStack stack) {
        var out = false;
        try {
            log.debug("Checking if item '{}' is advanced tinkerable", stack);
            var data = stack.get(MysticalAugmentComponentTypes.SOCKET_DATA);
            return data != null;
        } catch (Exception e) {
            log.warn("Failure in advanced tinkerable item '{}' test", stack, e);
        }
        return out;
    }
}
