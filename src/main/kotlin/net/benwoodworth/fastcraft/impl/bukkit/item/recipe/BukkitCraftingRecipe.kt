package net.benwoodworth.fastcraft.impl.bukkit.item.recipe

import net.benwoodworth.fastcraft.dependencies.item.Item
import net.benwoodworth.fastcraft.dependencies.item.recipe.CraftingRecipe
import net.benwoodworth.fastcraft.dependencies.item.recipe.Ingredient
import net.benwoodworth.fastcraft.dependencies.player.Player
import net.benwoodworth.fastcraft.impl.bukkit.item.BukkitItem
import net.benwoodworth.fastcraft.impl.bukkit.player.BukkitPlayer
import net.benwoodworth.fastcraft.util.Adapter
import net.benwoodworth.fastcraft.util.Grid
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe
import org.bukkit.inventory.Recipe as Bukkit_Recipe

/**
 * Bukkit implementation of [CraftingRecipe].
 */
abstract class BukkitCraftingRecipe private constructor(
        baseRecipe: Bukkit_Recipe
) : CraftingRecipe, Adapter<Bukkit_Recipe>(baseRecipe) {

    override fun prepare(player: Player, items: Grid<Item>): CraftingRecipe.Prepared? {
        val inventory = CustomCraftingInventory(
                (player as BukkitPlayer).base,
                base
        )

        inventory.matrix = Array(
                items.width * items.height,
                {
                    (items[it].toMutable() as BukkitItem.Mutable).base
                }
        )

        val prepareEvent = PrepareItemCraftEvent(
                inventory,
                inventory.View(),
                false
        )

        Bukkit.getPluginManager().callEvent(prepareEvent)

        val result = inventory.result
        if (result == null || result.type == Material.AIR) {
            return null
        }

        // Find results
        val results = mutableListOf<ItemStack>()
        results.add(inventory.getItem(0).clone())
        for (i in 1..9) {
            val item = inventory.getItem(i).clone()
            if (item.amount <= 0) {
                continue
            }

            // Filled buckets result in empty buckets (with data stripped)
            if (item.type == Material.WATER_BUCKET
                    || item.type == Material.MILK_BUCKET
                    || item.type == Material.LAVA_BUCKET) {

                results.add(ItemStack(Material.BUCKET, 1))
            }

            if (--item.amount > 0) {
                results.add(item)
            }
        }

        // Create Prepared recipe
        return inventory.result?.let {
            BukkitCraftingRecipe.Prepared(
                    player,
                    this,
                    items,
                    results.map(::BukkitItem).toList()
            )
        }
    }

    private class Prepared(
            override val player: Player,
            override val recipe: CraftingRecipe,
            override val items: Grid<Item>,
            override val results: List<Item>
    ) : CraftingRecipe.Prepared {

        override fun craft(): List<Item>? {
            TODO("not implemented")
        }
    }

    class Shaped(
            private val baseRecipe: ShapedRecipe
    ) : BukkitCraftingRecipe(baseRecipe) {

        override val id: String
            get() = baseRecipe.key.toString()

        override val ingredients: Grid.Impl<Ingredient> = Grid.Impl(
                3,
                3,
                { x, y ->
                    baseRecipe.shape[y]
                            .takeIf { x < it.length }
                            ?.get(x)
                            ?.let { baseRecipe.ingredientMap[it] }
                            ?.let { BukkitIngredient(it) }
                            ?: BukkitIngredient(ItemStack(Material.AIR))
                }
        )
    }

    class Shapeless(
            private val baseRecipe: ShapelessRecipe
    ) : BukkitCraftingRecipe(baseRecipe) {

        override val id: String
            get() = baseRecipe.key.toString()

        override val ingredients: Grid<Ingredient> = run {
            val ingredients = baseRecipe.ingredientList
                    .map { it?.let(::BukkitIngredient) }
                    .iterator()

            Grid.Impl(
                    3,
                    3,
                    { _, _ ->
                        ingredients
                                .takeIf { it.hasNext() }
                                ?.next()
                                ?: BukkitIngredient(ItemStack(Material.AIR))
                    }
            )
        }
    }
}
