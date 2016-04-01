package co.kepler.fastcraftplus.recipes.custom;

import co.kepler.fastcraftplus.config.Recipes;
import co.kepler.fastcraftplus.recipes.FastRecipe;
import co.kepler.fastcraftplus.recipes.Ingredient;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.Map;

/**
 * A shapeless recipe than supports ingredients with metadata.
 */
public class CustomShapelessRecipe extends FastRecipe implements CustomRecipe {
    private final ItemStack result;
    private final ShapelessRecipe recipe;
    Map<Ingredient, Integer> ingredients;

    /**
     * Create a new instance of CustomShapelessRecipe.
     *
     * @param result      The result of the recipe.
     * @param ingredients The ingredients of the recipe.
     * @throws Recipes.RecipeException Thrown if the recipe is misconfigured.
     */
    public CustomShapelessRecipe(ItemStack result, Map<Ingredient, Integer> ingredients) throws Recipes.RecipeException {
        this.result = result;
        this.ingredients = ingredients;

        // Check that there aren't too many ingredients.
        int totalIngredients = 0;
        for (int i : ingredients.values()) {
            totalIngredients += i;
        }
        if (totalIngredients > 9) {
            throw new Recipes.RecipeException("Recipes can have at most 9 ingredients");
        }

        // Create Recipe
        ItemStack[] matrix = new ItemStack[9];
        int matIndex = 0;
        recipe = new ShapelessRecipe(result);
        for (Ingredient ingredient : ingredients.keySet()) {
            int amount = ingredients.get(ingredient);
            recipe.addIngredient(amount, ingredient.getMaterialData());

            // Add items to matrix
            ItemStack matItem = ingredient.toItemStack(1);
            for (int i = 0; i < amount; i++) {
                matrix[matIndex++] = matItem;
            }
        }
    }

    @Override
    public ItemStack getResult() {
        return result.clone();
    }

    @Override
    public ShapelessRecipe getRecipe() {
        return recipe;
    }

    @Override
    public Map<Ingredient, Integer> getIngredients() {
        return ingredients;
    }

    @Override
    public boolean matchesMatrix(ItemStack[] matrix) {
        // Clone matrix, and set all items in matrix to null, or set amount to 1
        matrix = matrix.clone();
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i] == null) continue;
            if (matrix[i].getAmount() <= 0) {
                matrix[i] = null;
            } else {
                matrix[i] = matrix[i].clone();
                matrix[i].setAmount(1);
            }
        }

        // Make sure all ingredients exist, and that there aren't extra items
        if (!removeIngredients(matrix)) return false;
        for (ItemStack is : matrix) {
            if (is != null) return false;
        }

        // Matches the matrix
        return true;
    }
}
