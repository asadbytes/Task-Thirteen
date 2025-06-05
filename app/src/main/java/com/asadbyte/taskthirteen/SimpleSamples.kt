package com.asadbyte.taskthirteen

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SimpleSamples : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gridLayout = GridLayout(this).apply {
            rowCount = 3
            columnCount = 2
            setPadding(16, 16, 16, 16)
        }

        val viewsWithTags = listOf(
            "Rectangle" to RectangleView(this),
            "Round Rectangle" to RoundRectangleView(this),
            "Circle" to CircleView(this),
            "Oval" to OvalView(this),
            "Arc/Sector" to ArcSectorView(this),
            "Line/Lines" to LineView(this),
            "Point/Points" to PointView(this)
        )

        viewsWithTags.forEach { (tag, view) ->
            val container = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                setPadding(8, 8, 8, 8)

                // Set fixed size for each view
                val size = 400
                view.layoutParams = ViewGroup.LayoutParams(size, size)

                // Label
                val label = TextView(this@SimpleSamples).apply {
                    text = tag
                    gravity = Gravity.CENTER
                    setTextColor(Color.BLACK)
                }

                addView(view)
                addView(label)
            }

            gridLayout.addView(container)
        }

        setContentView(gridLayout)
    }
}

class RectangleView(context: Context) : View(context) {
    private val paint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(100f, 100f, 400f, 300f, paint)
    }
}

class RoundRectangleView(context: Context) : View(context) {

    private val paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRoundRect(100f, 100f, 400f, 300f, 50f, 50f, paint)
    }
}

class CircleView(context: Context) : View(context) {
    private val paint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(200f, 200f, 100f, paint)
    }
}

class OvalView(context: Context) : View(context) {
    private val paint = Paint().apply {
        color = Color.YELLOW
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawOval(100f, 100f, 400f, 250f, paint)
    }
}

class ArcSectorView(context: Context) : View(context) {
    private val paint = Paint().apply {
        color = Color.MAGENTA
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val rect = RectF(100f, 100f, 400f, 400f)

        // Arc without center (like a curved line)
        paint.color = Color.RED
        canvas.drawArc(rect, 0f, 90f, false, paint)

        // Sector (arc with center filled)
        paint.color = Color.GREEN
        canvas.drawArc(rect, 180f, 90f, true, paint)
    }
}

class LineView(context: Context) : View(context) {
    private val paint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 5f
        isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.strokeWidth = 8f

        // Single line
        canvas.drawLine(100f, 100f, 400f, 100f, paint)

        // Multiple lines
        val points = floatArrayOf(
            100f, 150f, 400f, 150f,
            100f, 200f, 400f, 200f
        )
        canvas.drawLines(points, paint)
    }
}

class PointView(context: Context): View(context) {
    private val paint = Paint().apply {
        color = Color.RED
        strokeWidth = 20f
        isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.strokeWidth = 20f

        // Single point
        canvas.drawPoint(250f, 250f, paint)

        // Multiple points
        val points = floatArrayOf(100f, 100f, 150f, 150f, 200f, 200f)
        canvas.drawPoints(points, paint)
    }
}