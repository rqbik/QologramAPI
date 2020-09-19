package com.github.rqbik.qologram.event

import com.github.rqbik.qologram.general.Qologram
import org.bukkit.entity.Player

data class QologramInteractEvent(
        val hologram: Qologram,
        override val player: Player,
        override val interactType: InteractType
) : InteractEvent