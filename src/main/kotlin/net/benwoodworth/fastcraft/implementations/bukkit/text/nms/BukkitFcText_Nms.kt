package net.benwoodworth.fastcraft.implementations.bukkit.text.nms

import net.benwoodworth.fastcraft.implementations.bukkit.text.BukkitFcText

@Suppress("ClassName")
interface BukkitFcText_Nms : BukkitFcText {

    val component: Any

    interface Factory : BukkitFcText.Factory

    interface Builder : BukkitFcText.Builder
}