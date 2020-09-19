package com.github.rqbik.qologram.event

import com.github.rqbik.qologram.general.MultilineQologram
import com.github.rqbik.qologram.general.Qologram
import org.bukkit.entity.Player

data class MultilineQologramInteractEvent(
        val hologram: MultilineQologram,
        val line: Qologram,
        override val player: Player,
        override val interactType: InteractType
) : InteractEvent