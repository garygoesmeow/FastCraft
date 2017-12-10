package net.benwoodworth.fastcraft.dependencies.abstractions.item

import net.benwoodworth.fastcraft.dependencies.abstractions.text.Text
import net.benwoodworth.fastcraft.util.TransMutable

/**
 * An immutable Minecraft item.
 */
interface Item : TransMutable<Item, Item.Mutable> {

    /**
     * The amount of items in this stack.
     */
    val amount: Int

    /**
     * The name of this item.
     */
    val name: Text

    /**
     * The display name of this item.
     */
    val displayName: Text?

    /**
     * The item's lore.
     */
    val lore: List<Text?>?

    /**
     * The maximum size of this stack.
     */
    val maxStackSize: Int

    /**
     * Compare equality of this base item to another, ignoring amount.
     *
     * @param item the [Item] to compare to
     * @return `true` iff the items are similar
     */
    fun isSimilar(item: Item): Boolean

    /**
     * A mutable [Item].
     */
    interface Mutable : Item {

        override var amount: Int

        override var displayName: Text?

        override var lore: List<Text?>?
    }

    /**
     * A builder that creates Minecraft items.
     */
    interface Builder {

        /**
         * Build the [Item].
         *
         * @return the built [Item]
         */
        fun build(): Item

        /**
         * Resets this builder and uses the values from this item.
         *
         * @Return This builder, for chaining
         */
        fun from(item: Item): Item.Builder

        /**
         * Set the item type.
         *
         * @param typeId The Minecraft item type ID
         * @Return This builder, for chaining
         */
        fun type(typeId: String): Item.Builder

        /**
         * Set the item amount.
         *
         * @param amount The item amount
         * @Return This builder, for chaining
         */
        fun amount(amount: Int): Item.Builder

        /**
         * Set the item's display name.
         *
         * @param displayName The display name
         * @Return This builder, for chaining
         */
        fun displayName(displayName: Text?): Item.Builder

        /**
         * Set the item's lore.
         *
         * @param lore The lore
         * @Return This builder, for chaining
         */
        fun lore(vararg lore: Text?): Item.Builder
    }
}