package com.github.rqbik.qologram.general

import com.github.rqbik.qologram.QologramAPI
import com.github.rqbik.qologram.utils.handle
import net.minecraft.server.v1_16_R3.*
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_16_R3.util.CraftChatMessage
import org.bukkit.entity.Player

open class Qologram(
        val player: Player,
        location: Location,
        text: String,
        show: Boolean = false
) {
    private val worldServer = location.world.handle

    private val entityArmorStand =
        EntityArmorStand(EntityTypes.ARMOR_STAND, worldServer).apply {
            setLocation(location.x, location.y, location.z, 0.0f, 0.0f)
        }.also {
            it.customNameVisible = true
            it.isMarker = false
            it.isNoGravity = true
            it.isSmall = true
            it.isInvisible = true
            it.customName = CraftChatMessage.fromStringOrNull(text)

            if (show) show()
            QologramAPI.qolograms.add(this)
        }

    val id
        get() = entityArmorStand.bukkitEntity.entityId

    val location
        get() = entityArmorStand.bukkitEntity.location

    var text = text
        set(value) {
            field = value
            entityArmorStand.customName = CraftChatMessage.fromStringOrNull(text)
            sendMetadata()
        }

    var hasCollision: Boolean
        get() = !entityArmorStand.isMarker
        set(value) {
            entityArmorStand.isMarker = !value
            sendMetadata()
        }

    fun show() {
        val packet = PacketPlayOutSpawnEntityLiving(entityArmorStand)
        player.handle.playerConnection.sendPacket(packet)
        sendMetadata()
    }

    fun hide() {
        val packet = PacketPlayOutEntityDestroy(id)
        player.handle.playerConnection.sendPacket(packet)
    }

    fun setLocation(
        x: Double = location.x,
        y: Double = location.y,
        z: Double = location.z,
        yaw: Float = location.yaw,
        pitch: Float = location.pitch
    ) {
        entityArmorStand.setLocation(x, y, z, yaw, pitch)
        val packet = PacketPlayOutEntityTeleport(entityArmorStand)
        player.handle.playerConnection.sendPacket(packet)
    }

    private fun sendMetadata() {
        val packetMeta = PacketPlayOutEntityMetadata(
            id,
            entityArmorStand.dataWatcher,
            true
        )
        player.handle.playerConnection.sendPacket(packetMeta)
    }
}