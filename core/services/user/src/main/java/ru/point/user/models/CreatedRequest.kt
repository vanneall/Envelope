package ru.point.user.models

import ru.point.user.responses.CreatedRequestResponse

data class CreatedRequest(val id: Long)

internal fun CreatedRequestResponse.toModel() = CreatedRequest(id = id)
