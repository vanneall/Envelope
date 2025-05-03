package com.point.envelope.navigation.feature

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.toRoute
import com.point.chats.camera.CameraScreen
import com.point.chats.chats.ui.ChatsScreen
import com.point.chats.creation.group.confirm.ui.ChatGroupConfirmScreen
import com.point.chats.creation.group.ui.ChatCreationGroupScreen
import com.point.chats.creation.single.ui.ChatCreationScreen
import com.point.chats.dialog.ui.ChatDialogScreen
import com.point.chats.dialog.viewmodel.ChatDialogAction
import com.point.chats.dialog.viewmodel.ChatDialogViewModel
import com.point.chats.gallery.GalleryScreen
import com.point.chats.multi.info.ui.MultiChatInfoScreen
import com.point.chats.multi.info.viewmodel.MultiChatInfoViewModel
import com.point.chats.photo.PhotoViewScreen
import com.point.chats.search.ui.ChatsSearchScreen
import com.point.envelope.BottomBarState
import com.point.envelope.navigation.extensions.entryComposable
import com.point.envelope.navigation.extensions.subComposable
import com.point.envelope.navigation.navhost.ComposeNavigationRoute.EntryRoute
import com.point.envelope.navigation.navhost.ComposeNavigationRoute.SubRoute
import com.point.envelope.navigation.navhost.asComposeRoute
import com.point.navigation.Route
import com.point.services.chats.models.ChatType
import com.point.ui.scaffold.fab.FabState
import com.point.ui.scaffold.topappbar.state.TopAppBarState
import com.point.ui.scaffold.topappbar.type.AppBarType

internal fun NavGraphBuilder.chatsFeature(
    navController: NavController,
    useAnim: Boolean,
    topAppBarState: MutableState<TopAppBarState>,
    bottomBarState: MutableState<BottomBarState>,
    fabState: MutableState<FabState>,
) {
    entryComposable<EntryRoute.Chats>(useAnim) {
        val focusManager = LocalFocusManager.current
        LaunchedEffect(Unit) {
            focusManager.clearFocus()
        }

        bottomBarState.value = BottomBarState(true)

        fabState.value = FabState.Showed(
            icon = Icons.Default.Add,
            action = { navController.navigate(SubRoute.ChatCreation) },
        )

        ChatsScreen(
            navigate = { route -> navController.navigate(route.asComposeRoute) },
            modifier = Modifier.fillMaxSize()
        )
    }

    subComposable<SubRoute.SearchChats>(useAnim) {

        fabState.value = FabState.Hidden

        ChatsSearchScreen(
            navigate = { },
            back = { navController.popBackStack() },
            modifier = Modifier.fillMaxSize(),
        )
    }

    subComposable<SubRoute.Messaging>(useAnim) {
        val args = it.toRoute<SubRoute.Messaging>()

        val viewModel: ChatDialogViewModel = hiltViewModel<ChatDialogViewModel, ChatDialogViewModel.Factory>(
            creationCallback = { factory -> factory.create(args.chatId) }
        )

        bottomBarState.value = BottomBarState(false)

        fabState.value = FabState.Hidden

        val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle

        LaunchedEffect(Unit) {
            val photoUris: List<Uri>? = savedStateHandle?.get("photo_result_list")

            photoUris?.let {
                viewModel.emitAction(ChatDialogAction.UiEvent.OnPhotoPicked(it))
                savedStateHandle["photo_result_list"] = null
            }
        }

        LaunchedEffect(Unit) {
            topAppBarState.value = TopAppBarState(
                appBarType = AppBarType.UserAppBar(
                    name = "",
                    photo = null,
                    isPrivate = false,
                    onUserProfileClick = {}
                ),
                onBack = { navController.popBackStack() }
            )
        }


        val kbm = stringResource(com.point.chats.R.string.bookmars)
        val lifecycleOwner = LocalLifecycleOwner.current
        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    val currentState = viewModel.state
                    topAppBarState.value = TopAppBarState(
                        appBarType = AppBarType.UserAppBar(
                            name = if (currentState.chatType == ChatType.PRIVATE) {
                                kbm
                            } else {
                                currentState.name
                            },
                            photo = currentState.photo,
                            isPrivate = currentState.chatType == ChatType.PRIVATE,
                            onUserProfileClick = {
                                if (currentState.chatType == ChatType.MANY) {
                                    navController.navigate(SubRoute.MultiChatInfo(viewModel.chatId))
                                }
                            }
                        ),
                        onBack = { navController.popBackStack() }
                    )
                }
            }

            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }

        ChatDialogScreen(
            state = viewModel.composableState.value,
            onAction = viewModel::emitAction,
            route = { r -> navController.navigate(r.asComposeRoute) },
            modifier = Modifier.fillMaxSize()
        )
    }

    subComposable<SubRoute.ChatCreation>(useAnim) {
        bottomBarState.value = BottomBarState(false)

        fabState.value = FabState.Hidden

        ChatCreationScreen(
            navigate = { route ->
                if (route is Route.ChatsFeature.Messaging) {
                    navController.navigate(route.asComposeRoute) {
                        popUpTo(EntryRoute.Chats) { inclusive = false }
                        launchSingleTop = true
                    }
                } else {
                    navController.navigate(route.asComposeRoute)
                }
            },
            back = { navController.popBackStack() },
            modifier = Modifier.fillMaxSize()
        )
    }

    subComposable<SubRoute.ChatCreationGroup>(useAnim) {

        bottomBarState.value = BottomBarState(false)

        ChatCreationGroupScreen(
            navigate = { route -> navController.navigate(route.asComposeRoute) },
            back = { navController.popBackStack() },
            modifier = Modifier.fillMaxSize()
        )
    }

    subComposable<SubRoute.GroupChatCreationConfirm>(useAnim) {

        val ids = it.toRoute<SubRoute.GroupChatCreationConfirm>().ids

        ChatGroupConfirmScreen(
            ids = ids,
            navigate = { route ->
                if (route is Route.ChatsFeature.Messaging) {
                    navController.navigate(route.asComposeRoute) {
                        popUpTo(EntryRoute.Chats) { inclusive = false }
                        launchSingleTop = true
                    }
                } else {
                    navController.navigate(route.asComposeRoute)
                }
            },
            back = { navController.popBackStack() },
            modifier = Modifier.fillMaxSize(),
        )
    }

    subComposable<SubRoute.MultiChatInfo>(useAnim) {
        val args = it.toRoute<SubRoute.MultiChatInfo>()

        topAppBarState.value = TopAppBarState(
            appBarType = AppBarType.HeaderAppBar(
                header = "Инфо",
            ),
            onBack = { navController.popBackStack() }
        )

        bottomBarState.value = BottomBarState(false)

        fabState.value = FabState.Hidden

        val viewModel = hiltViewModel<MultiChatInfoViewModel, MultiChatInfoViewModel.Factory> { factory ->
            factory.create(args.chatId)
        }

        MultiChatInfoScreen(
            state = viewModel.composableState.value,
            onAction = viewModel::emitAction,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        )
    }

    subComposable<SubRoute.Camera>(useAnim) {
        topAppBarState.value = TopAppBarState(
            appBarType = AppBarType.Invisible,
            onBack = { navController.popBackStack() }
        )
        bottomBarState.value = BottomBarState(false)
        fabState.value = FabState.Hidden

        CameraScreen(
            onImageCaptured = {},
            navigate = { r -> navController.navigate(r.asComposeRoute) },
            back = { navController.popBackStack() },
            onBack = {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("photo_result_list", it.toList())

                navController.popBackStack()
            },
            modifier = Modifier.fillMaxSize()
        )
    }

    subComposable<SubRoute.Gallery>(useAnim) {
        topAppBarState.value = TopAppBarState(
            appBarType = AppBarType.HeaderAppBar(header = "Галерея"),
            onBack = { navController.popBackStack() }
        )
        bottomBarState.value = BottomBarState(false)
        fabState.value = FabState.Hidden

        GalleryScreen(
            modifier = Modifier.fillMaxSize(),
            onPhotoClick = { r -> navController.navigate(Route.ChatsFeature.Photo(r).asComposeRoute) }
        )
    }

    subComposable<SubRoute.Photo>(useAnim) {
        topAppBarState.value = TopAppBarState(
            appBarType = AppBarType.Invisible,
            onBack = { navController.popBackStack() }
        )
        bottomBarState.value = BottomBarState(false)
        fabState.value = FabState.Hidden

        PhotoViewScreen(Uri.decode(it.toRoute<SubRoute.Photo>().uri))
    }
}
