package net.benwoodworth.fastcraft.sponge.dependencies.inventory

import net.benwoodworth.fastcraft.core.dependencies.inventory.Inventory
import net.benwoodworth.fastcraft.core.dependencies.player.Player
import net.benwoodworth.fastcraft.core.dependencies.text.Text
import net.benwoodworth.fastcraft.core.dependencies.util.Adapter
import org.spongepowered.api.item.inventory.Inventory as SpongeInventory

/**
 * Adapts a Sponge inventory.
 */
class SpongeInventoryAdapter(
        baseInventory: SpongeInventory
) : Inventory, Adapter<SpongeInventory>(baseInventory) {

    override val title: Text?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override val viewers: List<Player>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override val carrier: Any?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun equals(other: Any?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hashCode(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
