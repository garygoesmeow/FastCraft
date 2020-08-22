package net.benwoodworth.fastcraft.bukkit.world

import net.benwoodworth.fastcraft.platform.text.FcText
import net.benwoodworth.fastcraft.platform.world.FcItem
import net.benwoodworth.fastcraft.platform.world.FcItemStack
import org.bukkit.Server
import org.bukkit.inventory.ItemStack
import javax.inject.Inject
import javax.inject.Singleton

object BukkitFcItemStack_1_7 {
    @Singleton
    open class Operations @Inject constructor(
        private val fcItemFactory: FcItem.Factory,
        private val fcTextFactory: FcText.Factory,
        private val fcItemOperations: FcItem.Operations,
        private val fcItemStackFactory: FcItemStack.Factory,
    ) : BukkitFcItemStack.Operations {
        override val FcItemStack.itemStack: ItemStack
            get() = value as ItemStack

        override var FcItemStack.type: FcItem
            get() = fcItemFactory.fromMaterialData(itemStack.data)
            set(value) {
                itemStack.data = fcItemOperations.bukkit.run { value.materialData }
            }

        override var FcItemStack.amount: Int
            get() = itemStack.amount
            set(value) {
                itemStack.amount = value
            }

        override val FcItemStack.name: FcText
            get() = itemStack
                .takeIf { it.hasItemMeta() }
                ?.itemMeta
                ?.takeIf { it.hasDisplayName() }
                ?.displayName
                ?.let { fcTextFactory.create(it) }
                ?: fcItemOperations.run { type.name }

        override val FcItemStack.lore: List<FcText>
            get() = itemStack
                .takeIf { it.hasItemMeta() }
                ?.itemMeta
                ?.takeIf { it.hasLore() }
                ?.lore
                ?.map { fcTextFactory.create(it ?: "") }
                ?: emptyList()

        override val FcItemStack.hasMetadata: Boolean
            get() = itemStack.hasItemMeta()

        override fun FcItemStack.copy(): FcItemStack {
            return fcItemStackFactory.create(itemStack.clone())
        }
    }

    @Singleton
    open class Factory @Inject constructor(
        private val fcItemFactory: FcItem.Factory,
        protected val server: Server,
        private val fcItemOperations: FcItem.Operations,
    ) : BukkitFcItemStack.Factory {
        override fun create(item: FcItem, amount: Int): FcItemStack {
            return fcItemOperations.bukkit.run { create(item.toItemStack(amount)) }
        }

        override fun parseOrNull(itemStr: String, amount: Int): FcItemStack? {
            val materialId: String
            val data: String?

            when (val dataIndex = itemStr.indexOf('{')) {
                -1 -> {
                    materialId = itemStr
                    data = null
                }
                else -> {
                    materialId = itemStr.substring(0 until dataIndex)
                    data = itemStr.substring(dataIndex)
                }
            }

            val item = fcItemFactory.parseOrNull(materialId) ?: return null
            val itemStack = fcItemOperations.bukkit.run { item.toItemStack(amount) }

            if (data != null) {
                @Suppress("DEPRECATION")
                server.unsafe.modifyItemStack(itemStack, data)
            }

            return create(itemStack)
        }

        override fun create(itemStack: ItemStack): FcItemStack {
            return FcItemStack(itemStack)
        }
    }
}
