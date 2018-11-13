package net.benwoodworth.fastcraft.bukkit.text

import net.benwoodworth.fastcraft.platform.text.FcText
import net.benwoodworth.fastcraft.platform.text.FcTextBuilder
import net.benwoodworth.fastcraft.platform.text.FcTextColor

@Suppress("ClassName")
class Bukkit_11300R01_FcTextBuilder(
    val text: String? = null,
    val translate: String? = null
) : FcTextBuilder {

    override var color: FcTextColor? = null
    override var bold: Boolean? = null
    override var italic: Boolean? = null
    override var underline: Boolean? = null
    override var strikethrough: Boolean? = null
    override var obfuscate: Boolean? = null
    override var extra: MutableList<FcText> = mutableListOf()

    override fun build(): FcText {
        return when {
            text != null ->
                Bukkit_11300R01_FcText.Text(this)

            translate != null ->
                Bukkit_11300R01_FcText.Text(this)

            else ->
                throw IllegalStateException()
        }
    }
}