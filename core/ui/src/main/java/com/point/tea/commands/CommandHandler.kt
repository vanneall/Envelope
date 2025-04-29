package com.point.tea.commands

interface CommandHandler<Event, Dependency, Effect> {

    suspend fun handle(dependency: Dependency, effectProvider: (Effect) -> Unit): Event
}