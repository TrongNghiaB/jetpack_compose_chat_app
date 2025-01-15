import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.example.crypto_app.ui.theme.ButtonColor
import com.example.crypto_app.util.Constants

@Composable
fun CommonTextButton(
    modifier: Modifier = Modifier,
    text: String,
    cornerRadius: Dp? = null,
    buttonColor: Color? = null,
    contentPadding: PaddingValues? = null,
    color: Color? = null,
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(cornerRadius ?: Constants.Padding10),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = buttonColor ?: ButtonColor),
        contentPadding = contentPadding ?: PaddingValues(
            horizontal = Constants.Padding15,
            vertical = Constants.Padding5
        ),
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.W700,
            fontSize = 15.sp,
            color = color ?: Color.Black,
            textAlign = TextAlign.Center
        )
    }
}