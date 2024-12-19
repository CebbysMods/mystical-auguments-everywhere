package lv.cebbys.mcmods.mystical.augments.everywhere.provider;

import lv.cebbys.mcmods.mystical.augments.everywhere.content.MysticalAugmentTags;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.recipe.builder.EngravingSmithingRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

@ParametersAreNonnullByDefault
public class MysticalAugmentsRecipeProvider extends RecipeProvider {
    public MysticalAugmentsRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipes) {
        new EngravingSmithingRecipeBuilder(
                Ingredient.of(MysticalAugmentTags.EQUIPMENT),
                Ingredient.of(MysticalAugmentTags.ENGRAVER_TEMPLATE),
                Ingredient.of(MysticalAugmentTags.ESSENCE_STONE)
        ).save(recipes);
    }
}
