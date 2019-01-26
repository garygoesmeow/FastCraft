package net.benwoodworth.fastcraft.platform.gui

import net.benwoodworth.fastcraft.platform.item.FcItem

interface FcGuiLayoutSlot : FcGuiLayoutGrid {

    var item: FcItem?

    fun setClickHandler(column: Int, row: Int, handler: (event: FcGuiClickEvent) -> Unit)

    fun removeClickHandler(column: Int, row: Int)
}