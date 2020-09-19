package com.github.rqbik.qologram.general

import com.github.rqbik.qologram.event.InteractEventHandler
import com.github.rqbik.qologram.event.QologramInteractEvent
import org.bukkit.Location
import org.bukkit.entity.Player

open class InteractableQologram(
        player: Player,
        location: Location,
        name: String,
        val handler: InteractEventHandler<QologramInteractEvent>,
        show: Boolean = false
) : Qologram(player, location, name, show)