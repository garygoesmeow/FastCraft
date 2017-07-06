package net.benwoodworth.fastcraft.sponge.dependencies.text

import net.benwoodworth.fastcraft.core.dependencies.text.Text
import net.benwoodworth.fastcraft.core.util.Adapter
import org.spongepowered.api.text.Text as SpongeText

/**
 * Adapts Sponge text.
 */
class SpongeTextAdapter(
        baseText: SpongeText
) : Text, Adapter<SpongeText>(baseText)