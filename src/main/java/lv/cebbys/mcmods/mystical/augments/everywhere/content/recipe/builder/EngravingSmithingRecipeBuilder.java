package lv.cebbys.mcmods.mystical.augments.everywhere.content.recipe.builder;

import lv.cebbys.mcmods.mystical.augments.everywhere.content.recipe.EngravingSmithingRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class EngravingSmithingRecipeBuilder extends SimpleRecipeBuilder {
    private final Ingredient engraver;
    private final Ingredient essence;

    public EngravingSmithingRecipeBuilder(Ingredient equipment, Ingredient engraver, Ingredient essence) {
        super(equipment);
        this.engraver = engraver;
        this.essence = essence;
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation resource) {
        var recipe = new EngravingSmithingRecipe(
                result,
                engraver,
                essence
        );
        output.accept(resource, recipe, null);
    }
}
