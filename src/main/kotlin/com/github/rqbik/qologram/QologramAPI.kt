package com.github.rqbik.qologram

import com.comphenix.tinyprotocol.TinyProtocol
import com.github.rqbik.qologram.event.InteractType
import com.github.rqbik.qologram.event.MultilineQologramInteractEvent
import com.github.rqbik.qologram.event.MultipageQologramInteractEvent
import com.github.rqbik.qologram.event.QologramInteractEvent
import com.github.rqbik.qologram.general.InteractableQologram
import com.github.rqbik.qologram.general.MultilineQologram
import com.github.rqbik.qologram.general.MultipageQologram
import com.github.rqbik.qologram.general.Qologram
import io.netty.channel.Channel
import net.minecraft.server.v1_16_R3.PacketPlayInUseEntity
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

object QologramAPI {
    private lateinit var plugin: Plugin
    private lateinit var packetListener: TinyProtocol

    internal val qolograms: MutableList<Qologram> = mutableListOf()
    internal val multilineQolograms: MutableList<MultilineQologram> = mutableListOf()
    internal val multipageQolograms: MutableList<MultipageQologram> = mutableListOf()

    fun initialize(plugin: Plugin) {
        QologramAPI.plugin = plugin
        packetListener = object : TinyProtocol(plugin) {
            override fun onPacketInAsync(sender: Player?, channel: Channel, packet: Any?): Any? {
                if (packet is PacketPlayInUseEntity) {
                    if (sender != null) {
                        val qologram = qolograms.find { it.id == packet.entityId }
                        if (qologram != null) {
                            val interactType = InteractType.fromPacket(packet.b())
                            if (qologram is InteractableQologram) qologram.handler(QologramInteractEvent(qologram, sender, interactType))
                            val parentMultilineQologram = multilineQolograms.find { it.lines.contains(qologram) }
                            if (parentMultilineQologram != null) {
                                parentMultilineQologram.handler?.let {
                                    it(MultilineQologramInteractEvent(parentMultilineQologram, qologram, sender, interactType))
                                }

                                val parentMultipageQologram = multipageQolograms.find {
                                    it.pages.contains(parentMultilineQologram)
                                }

                                if (parentMultipageQologram != null) parentMultipageQologram.handler?.let {
                                    it(MultipageQologramInteractEvent(parentMultipageQologram, parentMultilineQologram, qologram, sender, interactType))
                                }
                            }
                        }
                    }
                }

                return packet
            }
        }
    }
}