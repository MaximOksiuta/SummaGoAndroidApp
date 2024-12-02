package com.sirius.siriussummago.ui

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import com.sirius.siriussummago.R
import com.sirius.siriussummago.noRippleClickable
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