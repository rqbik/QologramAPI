package com.github.rqbik.qologram.general

import com.github.rqbik.qologram.QologramAPI
import com.github.rqbik.qologram.event.InteractEventHandler
import com.github.rqbik.qologram.event.MultipageQologramInteractEvent
import org.bukkit.Location
import org.bukkit.entity.Player

open class MultipageQologram(val player: Player, location: Location, val builder: MultipageQologram.() -> Unit) {
    private var built: Boolean = false
    val pages = mutableListOf<MultilineQologram>()

    var currentPage = 0
        set(value) {
            if (value > pages.size - 1 || value < 0) return
            pages[field].hide()
            field = value
            pages[value].show()
        }

    var location = location
        private set

    fun setLocation(x: Double, y: Double, z: Double, yaw: Float, pitch: Float, updateY: Boolean = true) =
        pages.forEach { it.setLocation(x, y, z, yaw, pitch, updateY) }

    fun setLocation(location: Location, updateY: Boolean = true) =
        setLocation(location.x, location.y, location.z, location.yaw, location.pitch, updateY)

    fun addPage(builder: MultilineQologram.() -> Unit = {}) =
        pages.add(MultilineQologram(player, location, builder).build())

    fun show() {
        if (!built) build()
        pages[currentPage].show()
    }

    fun hide() = pages[currentPage].hide() 

    internal var handler: InteractEventHandler<MultipageQologramInteractEvent>? = null

    fun onInteract(handler: InteractEventHandler<MultipageQologramInteractEvent>) { this.handler = handler }

    fun build() = this.also {
        builder(this)
        QologramAPI.multipageQolograms.add(this)
        built = true
    }
}