package com.sirius.siriussummago.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sirius.siriussummago.R
import com.sirius.siriussummago.noRippleClickable
import com.sirius.siriussummago.ui.theme.LocalDim
import com.sirius.siriussummago.ui.theme.LocalExColorScheme

@Composable
fun NextButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    ConstraintLayout(
        modifier = modifier.noRippleClickable {
            onClick.invoke()
        }
    ) {
        val (buttonBackgroundRef, buttonArrowRef) = createRefs()
        Icon(
            painter = painterResource(R.drawable.next_button_background),
            contentDescription = "Next",
            tint = LocalExColorScheme.current.accent.color,
            modifier = Modifier
                .constrainAs(buttonBackgroundRef) {
                    centerTo(parent)
                }
        )
        Icon(
            painter = painterResource(R.drawable.ic_arrow_forward),
            contentDescription = "Next",
            tint = LocalExColorScheme.current.accent.onColor,
            modifier = Modifier.constrainAs(buttonArrowRef) {
                centerHorizontallyTo(parent)
                linkTo(parent.top, parent.bottom, bias = 0.73f)
            }
        )
    }
}

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    @StringRes textRes: Int,
    @DrawableRes iconRes: Int? = null,
    onClick: (() -> Unit)? = null
) {
    TopBar(
        modifier = modifier,
        text = stringResource(textRes),
        iconRes = iconRes,
        onClick = onClick
    )
}

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes iconRes: Int? = null,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(1f))

        // screen title
        Text(
            text = text,
            style = typography.titleLarge,
            color = colorScheme.onBackground
        )
        if (iconRes != null) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // button
                Icon(
                    painterResource(iconRes),
                    contentDescription = "button",
                    tint = colorScheme.onBackground,
                    modifier = Modifier
                        .size(32.dp)
                        .noRippleClickable { onClick?.invoke() }
                )

                Spacer(modifier = Modifier.width(LocalDim.current.spaceMedium))
            }
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}