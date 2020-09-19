package com.github.rqbik.qologram.general

import com.github.rqbik.qologram.QologramAPI
import com.github.rqbik.qologram.event.InteractEventHandler
import com.github.rqbik.qologram.event.MultilineQologramInteractEvent
import com.github.rqbik.qologram.event.QologramInteractEvent
import org.apache.commons.lang.StringUtils
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.Player

open class MultilineQologram(
    val player: Player,
    location: Location,
    private val builder: MultilineQologram.() -> Unit = {}
) {
    private val _lines = mutableListOf<Qologram>()

    val lines
        get() = _lines.toList()

    var location = location
        private set

    fun setLocation(newLocation: Location, updateY: Boolean = true)  {
        _lines.forEachIndexed { idx, it ->
            it.setLocation(
                newLocation.x,
                if (updateY) newLocation.y + idx * margin else newLocation.y + (it.location.y - location.y),
                newLocation.z
            )
        }
        location = newLocation
    }

    var margin = DEFAULT_MARGIN
        set(value) {
            field = value
            updateLineMargin()
        }

    var minLength = DEFAULT_MIN_LENGTH
        set(value) {
            field = value
            _lines.forEach { it.text = padCenter(it.text, value) }
        }

    fun addLine(line: Qologram) = line.apply {
        setLocation(y = location.y + _lines.size * margin)
    }.also {
        if (minLength != 0) it.text = padCenter(it.text, minLength)
        _lines.add(it)
    }

    fun addLine(line: String) = Qologram(
        player,
        location.clone().apply { y += _lines.size * margin },
        line
    ).also {
        if (minLength != 0) it.text = padCenter(it.text, minLength)
        _lines.add(it)
    }

    fun addLine(line: String, handler: InteractEventHandler<QologramInteractEvent>) =
        InteractableQologram(
            player,
            location.clone().apply { y += _lines.size * margin },
            line,
            handler
        ).also {
            if (minLength != 0) it.text = padCenter(it.text, minLength)
            _lines.add(it)
        }

    fun removeLine(line: Qologram, updateMargin: Boolean = true) = line.hide().also {
        _lines.remove(line)
        if (updateMargin) updateLineMargin()
    }

    fun removeLine(idx: Int, updateMargin: Boolean = true) = _lines.removeAt(idx).also {
        it.hide()
        if (updateMargin) updateLineMargin()
    }

    internal var handler: InteractEventHandler<MultilineQologramInteractEvent>? = null

    fun onInteract(handler: InteractEventHandler<MultilineQologramInteractEvent>) { this.handler = handler }

    fun show() = _lines.forEach { it.show() }
    fun hide() = _lines.forEach { it.hide() }

    open fun build() = this.also {
        builder(this)
        QologramAPI.multilineQolograms.add(this)
    }

    private fun updateLineMargin() = _lines.forEachIndexed { idx, it ->
        it.setLocation(y = location.y + idx * margin)
    }

    companion object {
        const val DEFAULT_MARGIN = 0.3
        const val DEFAULT_MIN_LENGTH = 0

        internal fun padCenter(string: String, length: Int): String =
            StringUtils.center(string, length + (string.length - ChatColor.stripColor(string)!!.length))
    }
}