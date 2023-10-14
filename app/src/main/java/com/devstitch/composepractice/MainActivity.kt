package com.devstitch.composepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import com.devstitch.composepractice.ui.theme.ComposePracticeTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposePracticeTheme {
                val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .nestedScroll(scrollBehavior.nestedScrollConnection),
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = "하영님", color = Color.Black, fontSize = 16.sp)
                                },
                                scrollBehavior = scrollBehavior,
                                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                                actions = {
                                    Icon(painter = painterResource(id = R.drawable.baseline_bookmark_border_24), contentDescription = null)
                                }
                            )

                        }
                    ) { contentPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black)
                                .padding(contentPadding)
                        ) {
                            PosterAnimation()
                        }
                    }
//                    animationPractice1()
//                    ComposeLine()
                }
            }
        }
    }
}


@Composable
fun ScreenSizeControllerFeature() {
    val density = LocalDensity.current
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp - 10.dp
    val displayMetrics = context.resources.displayMetrics

    val pxValue = with(density) { 10.dp.toPx() }
    val width = displayMetrics.widthPixels - pxValue


    var offsetX by remember { mutableStateOf((width / 2)) }

    val left = remember { mutableStateOf((screenWidth / 2)) }
    val right = remember { mutableStateOf(screenWidth - left.value) }

    Row(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .width(left.value)
                .fillMaxHeight()
        ) {
            Image(painter = painterResource(id = R.drawable.image1), contentDescription = null)
        }
        Box(
            modifier = Modifier
                .offset { IntOffset(x = 0, 0) }
                .width(10.dp)
                .fillMaxHeight()
                .background(Color.Yellow)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        if (left.value + Dp(delta / density.density) in screenWidth / 6..screenWidth * 5 / 6) {
                            offsetX += delta
                            left.value += Dp(delta / density.density)
                            right.value -= Dp(delta / density.density)
                        }
                    }
                )
        )
        Box(
            modifier = Modifier
                .width(right.value)
                .fillMaxHeight()
        ) {
            Image(painter = painterResource(id = R.drawable.image2), contentDescription = null)
        }
    }

}

//@Composable
//fun AnimationPractice1() {
//    var editable by remember { mutableStateOf(true) }
//
//    Column(
//        modifier = Modifier
//            .size(30.dp)
//            .fillMaxWidth()
//    ) {
//        Button(onClick = { editable = !editable }) {
//            Text(text = "Visibility $editable")
//        }
//
//        AnimatedVisibility(visible = editable) {
//            Text(text = "Edit", fontSize = 30.sp)
//        }
//    }
//
//}

//@Composable
//fun ComposeLine() {
//    val cutCornerSize = 30.dp
//    val cornerRadius = 10.dp
//    Box(
//        modifier = Modifier
//            .size(300.dp)
//            .padding(30.dp)
//    ) {
//        Canvas(modifier = Modifier.matchParentSize()) {
//            val clipPath = Path().apply {
//                lineTo(size.width - cutCornerSize.toPx(), 0f)
//                lineTo(size.width, cutCornerSize.toPx())
//                lineTo(size.width, size.height)
//                lineTo(0f, size.height)
//                close()
//            }
//
//            clipPath(clipPath) {
//                drawRoundRect(
//                    color = Color.Red,
//                    size = size,
//                    cornerRadius = CornerRadius(cornerRadius.toPx())
//                )
//                drawRoundRect(
//                    color = Color(
//                        ColorUtils.blendARGB(Color.Black.toArgb(), 0x000000, 0.5f)
//                    ),
//                    topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
//                    size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 150f),
//                    cornerRadius = CornerRadius(cornerRadius.toPx())
//                )
//            }
//        }
//    }
//}

//@Composable
//fun PosterAnimation() {
//    val posterList1 = listOf(
//        painterResource(id = R.drawable.poster2),
//        painterResource(id = R.drawable.poster3),
//        painterResource(id = R.drawable.poster4),
//        painterResource(id = R.drawable.poster5),
//        painterResource(id = R.drawable.poster6),
//        painterResource(id = R.drawable.poster7),
//        painterResource(id = R.drawable.poster2),
//        painterResource(id = R.drawable.poster3),
//        painterResource(id = R.drawable.poster4),
//        painterResource(id = R.drawable.poster5),
//        painterResource(id = R.drawable.poster6),
//        painterResource(id = R.drawable.poster7),
//        painterResource(id = R.drawable.poster2),
//        painterResource(id = R.drawable.poster3),
//        painterResource(id = R.drawable.poster4),
//        painterResource(id = R.drawable.poster5),
//        painterResource(id = R.drawable.poster6),
//        painterResource(id = R.drawable.poster7)
//    )
//
//    val posterList2 = listOf(
//        painterResource(id = R.drawable.poster3),
//        painterResource(id = R.drawable.poster4),
//        painterResource(id = R.drawable.poster5),
//        painterResource(id = R.drawable.poster2),
//        painterResource(id = R.drawable.poster3),
//        painterResource(id = R.drawable.poster4),
//        painterResource(id = R.drawable.poster5),
//        painterResource(id = R.drawable.poster6),
//        painterResource(id = R.drawable.poster7),
//        painterResource(id = R.drawable.poster2),
//        painterResource(id = R.drawable.poster6),
//        painterResource(id = R.drawable.poster7),
//        painterResource(id = R.drawable.poster2),
//        painterResource(id = R.drawable.poster3),
//        painterResource(id = R.drawable.poster4),
//        painterResource(id = R.drawable.poster5),
//        painterResource(id = R.drawable.poster6),
//        painterResource(id = R.drawable.poster7)
//    )
//
//    val posterList3 = listOf(
//        painterResource(id = R.drawable.poster4),
//        painterResource(id = R.drawable.poster5),
//        painterResource(id = R.drawable.poster6),
//        painterResource(id = R.drawable.poster7),
//        painterResource(id = R.drawable.poster2),
//        painterResource(id = R.drawable.poster3),
//        painterResource(id = R.drawable.poster4),
//        painterResource(id = R.drawable.poster5),
//        painterResource(id = R.drawable.poster6),
//        painterResource(id = R.drawable.poster7),
//        painterResource(id = R.drawable.poster2),
//        painterResource(id = R.drawable.poster3),
//        painterResource(id = R.drawable.poster4),
//        painterResource(id = R.drawable.poster5),
//        painterResource(id = R.drawable.poster6),
//        painterResource(id = R.drawable.poster7),
//        painterResource(id = R.drawable.poster2),
//        painterResource(id = R.drawable.poster3)
//    )
//    LazyColumn(
//        modifier = Modifier.padding(10.dp, 0.dp),
//        verticalArrangement = Arrangement.spacedBy(5.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        item { MainPoster() }
//        item { MoviesRow(posters = posterList1) }
//        item { MoviesRow(posters = posterList2) }
//        item { MoviesRow(posters = posterList3) }
//    }
//}

fun LazyListScope.movieItems(
    items: List<Painter>,
    key: ((item: Painter) -> Any)? = null,
    contentType: (item: Painter) -> Any? = { null }
) = items(
    count = items.size,
    key = if (key != null) { index: Int -> key(items[index]) } else null,
    contentType = { index: Int -> contentType(items[index]) }
) {
    Card(
        modifier = Modifier
            .width(132.dp)
            .fillMaxHeight(),
        shape = RectangleShape,
        elevation = CardDefaults.elevatedCardElevation(3.dp)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = items[it],
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
        )
    }
}


@Composable
fun MoviesRow(
    modifier: Modifier = Modifier,
    posters: List<Painter>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Genre", fontSize = 12.sp, color = Color.White)
        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            movieItems(items = posters)
        }
    }

}

@Composable
fun MainPoster() {
    Box(
        modifier = Modifier
            .width(400.dp)
            .aspectRatio(2.2f / 3f)
            .padding(20.dp)
    ) {
        Card(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                painter = painterResource(id = R.drawable.poster1),
                contentDescription = "poster1"
            )
        }
    }
}
