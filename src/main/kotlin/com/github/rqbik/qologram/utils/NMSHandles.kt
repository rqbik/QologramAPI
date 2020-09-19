package com.github.rqbik.qologram.utils

import net.minecraft.server.v1_16_R2.EntityPlayer
import net.minecraft.server.v1_16_R2.WorldServer
import org.bukkit.World
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer
import org.bukkit.entity.Player

val Player.handle: EntityPlayer get() = (this as CraftPlayer).handle
val World.handle: WorldServer get() = (this as CraftWorld).handle
