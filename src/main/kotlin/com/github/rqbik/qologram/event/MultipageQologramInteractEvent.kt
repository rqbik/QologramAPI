package com.github.rqbik.qologram.event

import com.github.rqbik.qologram.general.MultilineQologram
import com.github.rqbik.qologram.general.MultipageQologram
import com.github.rqbik.qologram.general.Qologram
import org.bukkit.entity.Player

data class MultipageQologramInteractEvent(
        val hologram: MultipageQologram,
        val page: MultilineQologram,
        val line: Qologram,
        override val player: Player,
        override val interactType: InteractType
) : InteractEvent