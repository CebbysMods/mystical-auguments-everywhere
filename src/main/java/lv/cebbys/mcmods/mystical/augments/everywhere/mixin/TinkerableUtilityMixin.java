package lv.cebbys.mcmods.mystical.augments.everywhere.mixin;

import com.blakebr0.mysticalagriculture.api.crop.CropTier;
import com.blakebr0.mysticalagriculture.api.util.TinkerableUtils;
import net.minecraft.ChatFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TinkerableUtils.class)
public class TinkerableUtilityMixin {
    @Inject(method = "getColorForTier", at = @At("HEAD"), cancellable = true)
    private static void getColorForTier(int tier, CallbackInfoReturnable<ChatFormatting> cir) {
        ChatFormatting var;
        switch (tier) {
            case 1 -> var = CropTier.ONE.getTextColor();
            case 2 -> var = CropTier.TWO.getTextColor();
            case 3 -> var = CropTier.THREE.getTextColor();
            case 4 -> var = CropTier.FOUR.getTextColor();
            case 5 -> var = CropTier.FIVE.getTextColor();
            case 6 -> var = ChatFormatting.DARK_PURPLE;
            default -> var = ChatFormatting.GRAY;
        }
        cir.setReturnValue(var);
    }
}
