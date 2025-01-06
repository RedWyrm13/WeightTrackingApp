package com.example.weighttracking.ui.theme

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@Composable
fun GraphView(data: List<Pair<String, Float>>) {

    Log.d("GraphView", "Data: $data")
    AndroidView(factory = { context ->
        LineChart(context).apply {
            val entries = data.mapIndexed { index, pair ->
                Entry(index.toFloat(), pair.second)
            }
            val dataSet = LineDataSet(entries, "Weight Over Time")
            this.data = LineData(dataSet)
            this.invalidate() // Refresh chart
        }
    },
        modifier = Modifier
            .width(350.dp)  // Set width for 9 data points
            .height(250.dp) // Set height
    )
}



