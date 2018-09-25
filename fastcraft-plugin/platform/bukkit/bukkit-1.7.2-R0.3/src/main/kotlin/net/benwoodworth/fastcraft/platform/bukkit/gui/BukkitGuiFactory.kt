package net.benwoodworth.fastcraft.platform.bukkit.gui

import net.benwoodworth.fastcraft.platform.api.gui.Gui
import net.benwoodworth.fastcraft.platform.api.gui.GuiFactory
import net.benwoodworth.fastcraft.platform.api.text.FcText
import javax.inject.Inject

class BukkitGuiFactory @Inject constructor(
        private val chestFactory: BukkitGui_ChestFactory,
        private val dispenserFactory: BukkitGui_DispenserFactory,
        private val hopperFactory: BukkitGui_HopperFactory
) : GuiFactory {

    override fun chest(height: Int, title: FcText?): Gui.Chest {
        return chestFactory.create(height, title)
    }

    override fun dispenser(title: FcText?): Gui.Dispenser {
        return dispenserFactory.create(title)
    }

    override fun hopper(title: FcText?): Gui.Hopper {
        return hopperFactory.create(title)
    }
}