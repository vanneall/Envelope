package com.point.tea.commands

import com.point.tea.dependency.EmptyDependency
import com.point.tea.effect.EmptyEffect
import com.point.tea.events.EmptyEvent

class EmptyCommandHandler : CommandHandler<EmptyEvent, EmptyDependency, EmptyEffect> {

    override suspend fun handle(dependency: EmptyDependency, effectProvider: (EmptyEffect) -> Unit): EmptyEvent =
        EmptyEvent
}

fun <Event, Dependency> noEffect() = EmptyCommandHandler()