package com.example.tmdb.presentation.discoverScreen.components

//@Composable
//fun DiscoverScreenIntents(viewModel: DiscoverScreenViewModel,actorId:Int){
//    val iconState=viewModel.iconState.collectAsStateWithLifecycle()
//    Box(
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        IconButton(
//            onClick = {viewModel.onEvent(Event.VISIBLE)},
//            modifier = Modifier.align(Alignment.TopStart)) {
//            Icon(
//                imageVector = if(iconState.value.showOverview) Icons.Default.VisibilityOff
//                else Icons.Default.Visibility ,
//                contentDescription = null,
//            )
//        }
//        IconButton(
//            onClick = {viewModel.onEvent(Event.BOOKMARK)
//                      viewModel.onBookmarkClick(actorId)},
//            modifier = Modifier.align(Alignment.BottomEnd)) {
//            Icon(
//                imageVector = if(iconState.value.addBookmark) Icons.Default.Bookmark
//                else Icons.Default.BookmarkBorder,
//                contentDescription = null,
//            )
//        }
//    }
//}