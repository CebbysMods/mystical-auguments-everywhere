package lv.cebbys.mcmods.mystical.augments.everywhere.content;

import lombok.extern.slf4j.Slf4j;
import lv.cebbys.mcmods.mystical.augments.everywhere.constant.MysticalAugmentConstants;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.recipe.EngravingSmithingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;

import static lv.cebbys.mcmods.mystical.augments.everywhere.constant.MysticalAugmentConstants.resource;
import static lv.cebbys.mcmods.mystical.augments.everywhere.content.MysticalAugmentDeferredRegisters.RECIPE_SERIALIZER;
import static lv.cebbys.mcmods.mystical.augments.everywhere.content.MysticalAugmentDeferredRegisters.RECIPE_TYPE;

@Slf4j
public class MysticalAugmentRecipes {
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<EngravingSmithingRecipe>> ENGRAVING_SERIALIZER;
    public static final DeferredHolder<RecipeType<?>, RecipeType<EngravingSmithingRecipe>> ENGRAVING_TYPE;

    public static void load() {
        log.info("Successfully registered '{}' recipe serializers", MysticalAugmentConstants.MODID);
    }

    static {
        ENGRAVING_TYPE = RECIPE_TYPE.register("engraving", () -> RecipeType.simple(resource("engraving")));
        ENGRAVING_SERIALIZER = RECIPE_SERIALIZER.register("engraving", (var location) -> new EngravingSmithingRecipe.Serializer());
    }
}
