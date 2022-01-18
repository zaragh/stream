package com.example.my_application.feature_movielist.presentation.shows.component

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.my_application.R
import com.example.my_application.feature_movielist.data.Constants.COLLAPSE_ANIMATION_DURATION
import com.example.my_application.feature_movielist.data.Constants.EXPAND_ANIMATION_DURATION
import com.example.my_application.feature_movielist.data.Constants.FADE_IN_ANIMATION_DURATION
import com.example.my_application.feature_movielist.data.Constants.FADE_OUT_ANIMATION_DURATION
import com.example.my_application.feature_movielist.domain.model.Show
import okhttp3.HttpUrl

@SuppressLint("UnusedTransitionTargetStateParameter")
@ExperimentalAnimationApi
@Preview
@Composable
fun ShowListItem(
    show: Show,
    onCardArrowClick: () -> Unit,
    expanded: Boolean,
) {
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }

    val transition = updateTransition(transitionState, label = "")
    val cardBgColor by transition.animateColor({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }, label = "") {
        if (expanded) Color.Red else Color.Red
    }

    val cardElevation by transition.animateDp({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }, label = "") {
        if (expanded) 10.dp else 4.dp
    }
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }, label = "") {
        if (expanded) 0f else 180f
    }
    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Card(
            modifier = Modifier
                .height(200.dp)
                .clickable {
                    isExpanded = !isExpanded
                },
            shape = RoundedCornerShape(10.dp),
            elevation = cardElevation,
            backgroundColor = cardBgColor
        ) {

            Column {
                Box {
                    CardArrow(
                        degrees = arrowRotationDegree,
                        onClick = onCardArrowClick
                    )

                    show.title?.let { CardTitle(title = it ) }
                }
                show.description?.let {
                    val httpUrl = HttpUrl.Builder()
                        .scheme("https")
                        .host("imageproxy.b17g.services")
                        .addPathSegment("convert")
                        .addQueryParameter("resize", "x150")
                        .addQueryParameter("source", show.image)
                        .build()
                    ExpandableContent(visible = expanded, initialVisibility = expanded,
                        it, httpUrl
                    )
                }
            }

        }

    }
}


@Composable
fun CardTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        textAlign = TextAlign.Center,
    )

}
@Composable
fun CardArrow(
    degrees: Float,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        content = {
            Icon(
                painter = painterResource(id = R.drawable.ic_expand_less_24),
                contentDescription = "Expandable Arrow",
                modifier = Modifier.rotate(degrees),
            )
        }
    )
}
@ExperimentalAnimationApi
@Composable
fun ExpandableContent(
    visible: Boolean,
    initialVisibility: Boolean = false,
    showDescription:String, image: HttpUrl
) {
    val enterFadeIn = remember {
        fadeIn(
            animationSpec = TweenSpec(
                durationMillis = FADE_IN_ANIMATION_DURATION,
                easing = FastOutLinearInEasing
            )
        )
    }
    val enterExpand = remember {
        expandVertically(animationSpec = tween(EXPAND_ANIMATION_DURATION))
    }
    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                durationMillis = FADE_OUT_ANIMATION_DURATION,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween(COLLAPSE_ANIMATION_DURATION))
    }
    AnimatedVisibility(
        visible = visible,
        initiallyVisible = initialVisibility,
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            Spacer(modifier = Modifier.heightIn(5.dp))
            Text(
                text = showDescription,
                textAlign = TextAlign.Center
            )
        }

    }
    Button(onClick = { !visible }){
        Box(modifier = Modifier.height(200.dp)) {

            Image(
                painter = rememberImagePainter(image),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
    }

}
