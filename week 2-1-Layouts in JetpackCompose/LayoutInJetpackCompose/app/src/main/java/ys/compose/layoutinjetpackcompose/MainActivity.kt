package ys.compose.layoutinjetpackcompose

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.solver.state.State
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch
import ys.compose.layoutinjetpackcompose.ui.theme.LayoutInJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutInJetpackComposeTheme {
                Week2_1()
            }
        }
    }
}

@Composable
private fun Week2_1() {
    var chosenChapter by remember { mutableStateOf(0) }
//    val backBtn = createRef()

    Column {
        if (chosenChapter != 0) {
            Button(
                onClick = { chosenChapter = 0 },
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = "Back")
            }
        }

        when (chosenChapter) {
            0 -> {
                ShowChatpers(onClicked = { idx -> chosenChapter = idx })
            }
            1 -> {
                ShowModifier()
            }
            2 -> {
                ShowMaterialComponents()
            }
            3 -> {
                ShowCreateYourCustomLayout()
            }
            4 -> {
                ShowComplexCustomLayout()
            }
            5 -> {
                ShowLayoutModifiersUnderTheHood()
            }
            6 -> {
                ShowConstraintLayout()
            }
            7 -> {
                ShowIntrinsics()
            }
        }
    }
}

/**
 * Show Chapter
 */
@Composable
private fun ShowChatpers(onClicked: (idx: Int) -> Unit) {
    var chapters = listOf(
        "Modifiers",
        "Material Components",
        "Create your custom layout",
        "Complex custom layout",
        "Layout modifiers under the hood",
        "Constraint Layout",
        "Intrinsics"
    )
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for ((idx, name) in chapters.withIndex()) {
            Log.d("Week2", "${idx} / ${name}")
            Button(
                onClick = { onClicked(idx + 1) },
                modifier = Modifier.padding(5.dp)
            ) {
                Text(text = "#${idx}_${name}")
            }
        }
    }
}

//@Preview
@Composable
fun MenuLayoutPreview() {
    LayoutInJetpackComposeTheme {
        ShowChatpers(onClicked = {})
    }
}

/**
 * Show Modifier
 */
@Composable
fun ShowModifier(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
            .clickable(onClick = { })
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Surface(
            modifier = modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {

        }
        Column(
            modifier = modifier
                .padding(horizontal = 4.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(text = "Alfred Sisley", fontWeight = FontWeight.Bold)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = "3 minutes ago", style = MaterialTheme.typography.body2)
            }
        }
    }
}

//@Preview
@Composable
fun ShowModifierPreview() {
    ShowModifier()
}

/**
 * Show MaterialComponents
 */
@Composable
fun ShowMaterialComponents() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "LayoutYSCode")
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Settings, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        BodyContents(
            Modifier
                .padding(innerPadding)
                .padding(8.dp)
        )
    }
}

@Composable
fun BodyContents(modifier: Modifier = Modifier) {
    Text(text = "Hi there!")
    Text(text = "Thanks for going through the Layouts YSCode")
    ScrollingList()
}

@Composable
fun ScrollingList() {
    val listSize = 100
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(top = 15.dp)) {
        Row {
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(0)
                }
            }) {
                Text(text = "Scroll to the top")
            }
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(listSize - 1)
                }
            }) {
                Text(text = "Scroll to the Bottom")
            }
        }

        LazyColumn(state = scrollState) {
            items(listSize) {
                ImageListItem(itemIdx = it)
            }
        }
    }
}

@Composable
fun ImageListItem(itemIdx: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberImagePainter(data = "https://developer.android.com/images/brand/Android_Robot.png"),
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "Item #$itemIdx", style = MaterialTheme.typography.subtitle1)
    }
}

/**
 * Show CreateYourCustomLayout
 */
@Composable
fun ShowCreateYourCustomLayout() {
    MyOwnColumn(Modifier.padding(8.dp)) {
        Text("MyOwnColumn")
        Text("places items")
        Text("vertically.")
        Text("We've done it by hand!")
    }
}

@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurable, constraints ->
        val placealbes = measurable.map { measurable ->
            measurable.measure(constraints)
        }

        var yPosition = 0

        layout(constraints.maxWidth, constraints.maxHeight) {
            placealbes.forEach { placeable ->
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
        }
    }
}

/**
 * Show ComplexCustomLayout
 */

@Composable
fun ShowComplexCustomLayout() {

}

/**
 * Show LayoutModifiersUnderTheHood
 */
@Composable
fun ShowLayoutModifiersUnderTheHood() {

}

/**
 * Show ConstraintLayout
 */
@Composable
fun ShowConstraintLayout() {
    ConstraintLayout {
        val (btn, text) = createRefs()

        Button(onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(btn) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text(text = "Button")
        }

        Text(text = "Text", modifier = Modifier.constrainAs(text) {
            top.linkTo(btn.bottom, margin = 16.dp)
            centerHorizontallyTo(parent)
        })
    }

    ConstraintLayout {
        val (btn1, btn2, text) = createRefs()

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(btn1) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text(text = "Button 1")
        }

        Text(
            text = "Text",
            modifier = Modifier.constrainAs(text) {
                top.linkTo(btn1.bottom, margin = 16.dp)
                centerAround(btn1.end)
            })

        val barrier = createEndBarrier(btn1, text)

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(btn2) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(barrier)
            }
        ) {
            Text(text = "Button 2")
        }
    }

    ConstraintLayout {
        val text = createRef()
        val guideLine = createGuidelineFromStart(fraction = 0.5f)
        Text(
            text = "This is very very very very long text",
            modifier = Modifier.constrainAs(text) {
                linkTo(start = guideLine, end = parent.end)
            }
        )
    }

    ConstraintLayout {
        val text = createRef()
        val guideLine = createGuidelineFromStart(fraction = 0.5f)
        Text(text = "This is very very very very very very very very very long text",
            modifier = Modifier.constrainAs(text) {
                linkTo(guideLine, parent.end)
                width = Dimension.preferredWrapContent
            })
    }

    DecoupledConstraintLayout()
}

@Composable
fun DecoupledConstraintLayout() {
    BoxWithConstraints {
        val constraints = if (maxWidth < maxHeight) {
            decoupledConstraints(margin = 16.dp)
        } else {
            decoupledConstraints(margin = 32.dp)
        }

        ConstraintLayout(constraints) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.layoutId("button")
            ) {
                Text(text = "Button")
            }

            Text(text = "Text", modifier = Modifier.layoutId("text"))
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val btn = createRefFor("button")
        val text = createRefFor("text")

        constrain(btn) {
            top.linkTo(parent.top, margin = margin)
        }
        constrain(text) {
            top.linkTo(btn.bottom, margin = margin)
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun ShowConstraintLayoutPreview() {
    ShowConstraintLayout()
}

/**
 * Show Intrinsics
 */
@Composable
fun ShowIntrinsics() {
    LayoutInJetpackComposeTheme {
        Surface {
            TwoTexts(text1 = "Hi", text2 = "There")
        }
    }
}

@Composable
fun TwoTexts(modifier: Modifier = Modifier, text1: String, text2: String) {
    Row(modifier = modifier) {
        Text(
            text = text1,
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.Start)
                .align(Alignment.CenterVertically)
        )
        Divider(color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text(
            text = text2,
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .wrapContentWidth(Alignment.End)
        )
    }
}