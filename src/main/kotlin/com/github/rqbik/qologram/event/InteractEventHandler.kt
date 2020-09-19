package com.github.rqbik.qologram.event

fun interface InteractEventHandler<T : InteractEvent> : (T) -> Unit