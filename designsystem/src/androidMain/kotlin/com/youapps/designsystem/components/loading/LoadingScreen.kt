import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youapps.designsystem.OBFontFamilies

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    loadingText : String?=null
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                12.dp, Alignment.CenterVertically
            )
        ) {
            CircularProgressIndicator(
                modifier = Modifier.requiredSize(24.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 2.dp
            )
            loadingText?.run {
                Text(
                    modifier = Modifier
                        .animateContentSize()
                        .fillMaxWidth(),
                    text =loadingText,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = OBFontFamilies.MainMediumFontFamily,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }

        }
    }
}