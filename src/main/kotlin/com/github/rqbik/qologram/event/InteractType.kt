package com.github.rqbik.qologram.event

import net.minecraft.server.v1_16_R3.PacketPlayInUseEntity

enum class InteractType {
    RIGHT_CLICK,
    LEFT_CLICK;

    companion object {
        fun fromPacket(action: PacketPlayInUseEntity.EnumEntityUseAction) = when (action) {
            PacketPlayInUseEntity.EnumEntityUseAction.ATTACK -> LEFT_CLICK
            else -> RIGHT_CLICK
        }
    }
}