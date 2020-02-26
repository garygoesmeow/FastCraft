package net.benwoodworth.fastcraft.bukkit.item

import net.benwoodworth.fastcraft.platform.item.FcItemType
import net.benwoodworth.fastcraft.platform.text.FcTextFactory
import org.bukkit.Material
import org.bukkit.material.MaterialData
import javax.inject.Inject

class BukkitFcItemTypes_1_8_R01 @Inject constructor(
    private val textFactory: FcTextFactory
) : BukkitFcItemTypes {
    override val air = fromMaterial(Material.AIR)

    override val ironSword = fromMaterial(Material.IRON_SWORD)

    override val craftingTable = fromMaterial(Material.WORKBENCH)

    override val anvil = fromMaterial(Material.ANVIL)

    override val netherStar = fromMaterial(Material.NETHER_STAR)

    override fun fromMaterial(material: Material): FcItemType {
        return fromMaterialData(MaterialData(material))
    }

    override fun fromMaterialData(materialData: Any): FcItemType {
        require(materialData is MaterialData)
        return BukkitFcItemType_1_8_R01(materialData, textFactory)
    }
}
