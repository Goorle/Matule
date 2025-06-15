package com.example.matule.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.matule.domain.font.poppins
import com.example.matule.domain.models.Notification
import com.example.matule.presentation.ui.theme.Background
import com.example.matule.presentation.ui.theme.SubTextDark
import com.example.matule.presentation.ui.theme.TextColor
import com.example.matule.presentation.viewmodel.NotificationViewModel

@Composable
fun CardNotification(
    notification: Notification
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Background,
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)
        ) {
            Text(
                text = notification.title,
                fontSize = 16.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                color = TextColor
            )

            Spacer(Modifier.height(5.dp))

            Text(
                text = notification.text,
                color = TextColor,
                fontFamily = poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 15.sp
            )

            Spacer(Modifier.height(5.dp))

            Text(
                text = notification.createdAt,
                color = SubTextDark,
                fontFamily = poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            )

        }
    }
}


@Preview
@Composable
private fun CardPreview() {
//    CardNotification()
}