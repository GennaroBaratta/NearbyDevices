package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.NearbyDevice

@Composable
fun DeviceCard(device: NearbyDevice) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(top = 6.dp, bottom = 6.dp), elevation = 8.dp
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
        ) {
            Text(
                text = device.id.toString(),
                modifier = Modifier.fillMaxWidth(0.85f),
                style = MaterialTheme.typography.h3
            )
        }
    }
}

@Preview
@Composable
fun DeviceCardPreview() {
    DeviceCard(NearbyDevice(0L))
}