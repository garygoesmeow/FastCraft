package net.benwoodworth.fastcraft

import net.benwoodworth.fastcraft.crafting.FastCraftGuiFactory
import net.benwoodworth.fastcraft.platform.player.FcPlayerEvents
import net.benwoodworth.fastcraft.platform.player.FcPlayerJoinEvent
import net.benwoodworth.fastcraft.platform.server.FcTaskFactory
import javax.inject.Inject

class FastCraft @Inject internal constructor(
    playerEventsListeners: FcPlayerEvents,
    private val taskFactory: FcTaskFactory,
    private val fastCraftGuiFactory: FastCraftGuiFactory
) {
    init {
        playerEventsListeners.onPlayerJoin += ::onPlayerJoin
    }

    fun disable() {
    }

    private fun onPlayerJoin(event: FcPlayerJoinEvent) {
        val task = taskFactory.createFcTask(delaySeconds = 2.0) {
            fastCraftGuiFactory
                .createFastCraftGui(event.player)
                .open()
        }
        task.schedule()
    }
}
