package net.benwoodworth.fastcraftplus.core.players

import java.util.*

/**
 * Provides players for the plugin.
 *
 * @param TPlayer The type of player.
 */
interface PlayerProvider<out TPlayer : FcPlayer<*>> {

    /**
     * Get a list of online players.
     *
     * @return A list of online players.
     */
    fun getOnlinePlayers(): List<TPlayer>

    /**
     * Get an online player given a UUID.
     *
     * @param uuid The player's UUID.
     * @return The player with the given UUID, or null if none exists.
     */
    fun getOnlinePlayer(uuid: UUID): TPlayer?
}
