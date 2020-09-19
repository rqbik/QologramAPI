# QologramAPI

Modern hologram API written in Kotlin.

Usage:
```kotlin
class MyPlugin : JavaPlugin(), Listener {
    override fun onEnable() {
        // Initialize QologramAPI 
        QologramAPI.initialize(this)
    }

    companion object {
        fun createHologram(player: Player, location: Location) {
            // A simple one line hologram
            val qologram = Qologram(player, location, "Hello world!")

            // A more complex multiline hologram
            val multilineHologram = MultilineQologram(player, location) {
                // Let's add some lines!
                // Beware that they will be positioned from bottom to top.

                // We can add lines that have interact event handlers
                addLine("An interactive text!") { event ->
                    if (event.type == InteractType.RIGHT_CLICK)
                        event.player.sendMessage("You clicked on me!")
                    
                    // Hide this hologram after click
                    event.hologram.hide()
                }

                // ...and we can add lines that don't!
                addLine("A simple text.")

                // We also can listen to events on any of lines:
                onInteract { event ->
                    event.player.sendMessage("You clicked on hologram with text: ${event.line.text}")
                }
            }

            // An even more complex _multipage_ hologram!
            val multipageHologram = MultipageQologram(player, location) {
                // The first page is automatically the one that is visible
                addPage {
                    addLine("Click on me to go to the next page!") {
                        // Increment `currentPage` to go to the next page.
                        currentPage++
                    }
                }

                addPage {
                    addLine("Click on me to go to the previous page!") {
                        // Decrement `currentPage` to go to the previous page.
                        currentPage--
                    }

                    addLine("Go to the next page") {
                        currentPage++
                    }
                }

                addPage {
                    addLine("Go back to first one") {
                        // Set `currentPage` to 0 to go to the first page.
                        currentPage = 0
                    }

                    addLine("I can't be interacted with.") {
                        // This will never fire!                 
                    }.apply {
                        // Remove collision
                        // This allows you to shoot projectiles through hologram,
                        // but prevents you from interacting with it.
                        hasCollision = false
                    }

                }

                onInteract { event ->
                    if (event.interactType == InteractType.LEFT_CLICK)
                        event.player.sendMessage("Hey! Don't attack holograms. They have feelings too!")
                }
            }
        }
    }
}
```