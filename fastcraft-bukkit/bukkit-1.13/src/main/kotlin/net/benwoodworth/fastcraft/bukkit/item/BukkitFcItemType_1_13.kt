package net.benwoodworth.fastcraft.bukkit.item

import net.benwoodworth.fastcraft.bukkit.item.BukkitFcItemType.Companion.material
import net.benwoodworth.fastcraft.bukkit.item.BukkitFcItemType.Companion.materialData
import net.benwoodworth.fastcraft.bukkit.item.BukkitFcItemTypes.Companion.fromMaterial
import net.benwoodworth.fastcraft.bukkit.text.BukkitFcTextFactory.Companion.createFcTextTranslate
import net.benwoodworth.fastcraft.bukkit.text.BukkitLocalizer
import net.benwoodworth.fastcraft.platform.item.FcItemType
import net.benwoodworth.fastcraft.platform.item.FcItemTypes
import net.benwoodworth.fastcraft.platform.text.FcText
import net.benwoodworth.fastcraft.platform.text.FcTextFactory
import org.bukkit.Material
import java.util.*

open class BukkitFcItemType_1_13(
    override val material: Material,
    private val textFactory: FcTextFactory,
    private val localizer: BukkitLocalizer,
    protected val itemTypes: FcItemTypes,
) : BukkitFcItemType {
    override val id: String
        get() = material.key.toString()

    override val materialData: Nothing?
        get() = null

    private fun Material.getNameLocaleKey(prefix: String): String {
        return "$prefix.${key.namespace}.${key.key}"
    }

    override val itemName: FcText
        get() {
            var localeKey = material.getNameLocaleKey("item")

            if (localizer.localize(localeKey, Locale.ENGLISH) == null) {
                val blockKey = material.getNameLocaleKey("block")
                if (localizer.localize(blockKey, Locale.ENGLISH) != null) {
                    localeKey = blockKey
                }
            }

            return textFactory.createFcTextTranslate(localeKey)
        }

    override val blockName: FcText
        get() {
            var localeKey = material.getNameLocaleKey("block")

            if (localizer.localize(localeKey, Locale.ENGLISH) == null) {
                val itemKey = material.getNameLocaleKey("item")
                if (localizer.localize(itemKey, Locale.ENGLISH) != null) {
                    localeKey = itemKey
                }
            }

            return textFactory.createFcTextTranslate(localeKey)
        }

    override val maxAmount: Int
        get() = material.maxStackSize

    override val craftingResult: FcItemType?
        get() = when (material) {
            Material.LAVA_BUCKET,
            Material.MILK_BUCKET,
            Material.WATER_BUCKET,
            -> itemTypes.fromMaterial(Material.BUCKET)

            Material.DRAGON_BREATH,
            -> itemTypes.fromMaterial(Material.GLASS_BOTTLE)

            else -> null
        }

    override fun equals(other: Any?): Boolean {
        return other is FcItemType &&
                material == other.material &&
                materialData == other.materialData
    }

    override fun hashCode(): Int {
        return material.hashCode()
    }
}
