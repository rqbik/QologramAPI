package com.github.rqbik.qologram.event

import org.bukkit.entity.Player

interface InteractEvent {
    val player: Player
    val interactType: InteractType
}