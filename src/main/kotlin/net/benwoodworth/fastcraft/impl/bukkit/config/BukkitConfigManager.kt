package net.benwoodworth.fastcraft.impl.bukkit.config

import net.benwoodworth.fastcraft.dependencies.config.Config
import net.benwoodworth.fastcraft.dependencies.config.ConfigManager
import org.bukkit.configuration.file.YamlConfiguration
import java.nio.file.Path

/**
 * Bukkit implementation of [ConfigManager].
 */
class BukkitConfigManager : ConfigManager {

    override fun getEmptyConfig(): Config {
        return BukkitConfig(YamlConfiguration())
    }

    override fun loadConfig(path: Path): Config {
        return BukkitConfig(YamlConfiguration.loadConfiguration(path.toFile()))
    }

    override fun saveConfig(path: Path, config: Config) {
        (config as BukkitConfig).base.save(path.toFile())
    }

    override fun getFileExtension() = ".yml"
}
