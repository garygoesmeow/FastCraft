package net.benwoodworth.fastcraft.sponge.dependencies.player

import net.benwoodworth.fastcraft.core.dependencies.permission.Permission
import net.benwoodworth.fastcraft.core.dependencies.player.Player
import net.benwoodworth.fastcraft.core.dependencies.text.Text
import net.benwoodworth.fastcraft.core.util.Adapter
import net.benwoodworth.fastcraft.sponge.dependencies.text.SpongeTextAdapter
import java.util.UUID
import org.spongepowered.api.entity.living.player.Player as SpongePlayer

/**
 * Adapter for Sponge players.
 */
class SpongePlayerAdapter(
        basePlayer: SpongePlayer
) : Player, Adapter<SpongePlayer>(basePlayer) {

    override val username: String
        get() = base.name

    override var displayName: Text?
        get() = SpongeTextAdapter(base.displayNameData.displayName().get())
        set(value) {
            base.displayNameData.displayName().set(
                    (value as SpongeTextAdapter).base
            )
        }

    override val uuid: UUID
        get() = base.uniqueId

    override fun sendMessage(message: Text) {
        base.sendMessage(
                (message as SpongeTextAdapter).base
        )
    }

    override fun hasPermission(permission: Permission): Boolean {
        return base.hasPermission(permission.id)
    }
}