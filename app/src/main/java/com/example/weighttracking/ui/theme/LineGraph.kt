package com.example.weighttracking.ui.theme

import android.util.Log
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@Composable
fun GraphView(data: List<Pair<String, Float>>) {

    Log.d("GraphView", "Data: $data")

    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                this.description.isEnabled = true
                this.xAxis.position = XAxis.XAxisPosition.BOTTOM
                this.axisLeft.axisMinimum = 0f // Adjust if needed
                this.axisRight.isEnabled = false
                this.description.text = "Average Weight each week for the last 9 weeks"
                this.description.textSize = 12f
            }
        },
        update = { chart ->
            val entries = data.mapIndexed { index, pair ->
                Entry(index.toFloat() + 1, pair.second)
            }
            val dataSet = LineDataSet(entries, "Weight Over Time").apply {
                color = android.graphics.Color.BLUE
                valueTextColor = android.graphics.Color.BLACK
                setCircleColor(android.graphics.Color.RED)
                lineWidth = 2f
                circleRadius = 4f
            }
            chart.data = LineData(dataSet)
            chart.invalidate() // Refresh the chart with new data
        },
        modifier = Modifier.aspectRatio(1f)
    )
}




