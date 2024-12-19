package lv.cebbys.mcmods.mystical.augments.everywhere.content.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.MysticalAugmentComponentTypes;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.MysticalAugmentDataMapTypes;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.MysticalAugmentRecipes;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.data.component.SocketComponent;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.data.smithing.SocketEngraver;
import lv.cebbys.mcmods.mystical.augments.everywhere.content.data.smithing.SocketEssence;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.SmithingTrimRecipeBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class EngravingSmithingRecipe implements SmithingRecipe {
    private final Ingredient equipment;
    private final Ingredient engraver;
    private final Ingredient essence;

    @Override
    public boolean matches(@NonNull SmithingRecipeInput input, @NonNull Level level) {
        var result = this.isBaseIngredient(input.base()) && this.isTemplateIngredient(input.template()) && this.isAdditionIngredient(input.addition());
        return result;
    }

    @NonNull
    @Override
    public ItemStack assemble(@NonNull SmithingRecipeInput input, @NonNull HolderLookup.Provider provider) {
        var stack = input.base();
        if (!this.isBaseIngredient(stack)) {
            return ItemStack.EMPTY;
        }

        var o1 = SocketEngraver.getFromTemplate(provider, input.template());
        var o2 = SocketEssence.getFromIngredient(provider, input.addition());
        var types = stack.getItemHolder().getData(MysticalAugmentDataMapTypes.ITEM_AUGMENT_TYPES);
        if (o1.isEmpty() || o2.isEmpty() || types == null) {
            return ItemStack.EMPTY;
        }

        var upgrade = new SocketComponent(o1.get(), o2.get(), types);
        var data = stack.get(MysticalAugmentComponentTypes.SOCKET_DATA);
        if (upgrade.canReplace(data)) {
            data = upgrade;
        } else {
            return ItemStack.EMPTY;
        }

        var out = stack.copyWithCount(1);
        out.set(MysticalAugmentComponentTypes.SOCKET_DATA, data);
        return out;
    }

    @NonNull
    @Override
    public ItemStack getResultItem(@NonNull HolderLookup.Provider registries) {
        ItemStack itemstack = new ItemStack(Items.IRON_CHESTPLATE);
        Optional<Holder.Reference<TrimPattern>> optional = registries.lookupOrThrow(Registries.TRIM_PATTERN).listElements().findFirst();
        Optional<Holder.Reference<TrimMaterial>> optional1 = registries.lookupOrThrow(Registries.TRIM_MATERIAL).get(TrimMaterials.REDSTONE);
        if (optional.isPresent() && optional1.isPresent()) {
            itemstack.set(DataComponents.TRIM, new ArmorTrim(optional1.get(), optional.get()));
        }

        return itemstack;
    }


    @Override
    public boolean isTemplateIngredient(@NonNull ItemStack stack) {
        return this.engraver.test(stack);
    }

    @Override
    public boolean isBaseIngredient(@NonNull ItemStack stack) {
        return this.equipment.test(stack);
    }

    @Override
    public boolean isAdditionIngredient(@NonNull ItemStack stack) {
        return this.essence.test(stack);
    }

    public boolean isIncomplete() {
        return Stream.of(this.engraver, this.equipment, this.essence).anyMatch(Ingredient::hasNoItems);
    }

    @NonNull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return MysticalAugmentRecipes.ENGRAVING_SERIALIZER.get();
    }

    @ParametersAreNonnullByDefault
    @MethodsReturnNonnullByDefault
    public static class Serializer implements RecipeSerializer<EngravingSmithingRecipe> {
        public static final StreamCodec<RegistryFriendlyByteBuf, EngravingSmithingRecipe> STREAM_CODEC;
        public static final MapCodec<EngravingSmithingRecipe> MAP_CODEC;


        @Override
        public MapCodec<EngravingSmithingRecipe> codec() {
            return MAP_CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, EngravingSmithingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static EngravingSmithingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            return new EngravingSmithingRecipe(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer), Ingredient.CONTENTS_STREAM_CODEC.decode(buffer), Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, EngravingSmithingRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getEquipment());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getEngraver());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getEssence());
        }

        static {
            MAP_CODEC = RecordCodecBuilder.mapCodec((var builder) -> builder.group(Ingredient.CODEC.fieldOf("equipment").forGetter(EngravingSmithingRecipe::getEquipment), Ingredient.CODEC.fieldOf("engraver").forGetter(EngravingSmithingRecipe::getEngraver), Ingredient.CODEC.fieldOf("essence").forGetter(EngravingSmithingRecipe::getEssence)).apply(builder, EngravingSmithingRecipe::new));
            STREAM_CODEC = StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);
        }
    }
}
