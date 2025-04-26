package com.point.ui.items

import androidx.compose.runtime.Immutable

@Immutable
interface UiItem

inline fun <reified T: UiItem, R: UiItem> List<UiItem>.mapUi(transform: (T) -> R) = map { item -> transform(item as T) }