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
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SimpleSamples : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create ScrollView as root container
        val scrollView = ScrollView(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        val gridLayout = GridLayout(this).apply {
            columnCount = 2
            // Remove rowCount - let it expand as needed
            setPadding(16, 16, 16, 16)
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        val viewsWithTags = listOf(
            "Rectangle" to RectangleView(this),
            "Round Rectangle" to RoundRectangleView(this),
            "Circle" to CircleView(this),
            "Oval" to OvalView(this),
            "Arc" to ArcView(this),
            "Sector" to SectorView(this),
            "Point/Points" to PointView(this),
            "Line/Lines" to LineView(this)
        )

        viewsWithTags.forEachIndexed { index, (tag, view) ->
            val container = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                setPadding(8, 8, 8, 8)

                // Set fixed size for each view
                val size = 400
                if (view is LineView) {
                    view.layoutParams = ViewGroup.LayoutParams(size * 2, size)
                } else {
                    view.layoutParams = ViewGroup.LayoutParams(size, size)
                }

                // Label
                val label = TextView(this@SimpleSamples).apply {
                    text = tag
                    gravity = Gravity.CENTER
                    setTextColor(Color.BLACK)
                }

                addView(view)
                addView(label)
            }

            // Special layout for LineView (span 2 columns)
            if (view is LineView) {
                val params = GridLayout.LayoutParams().apply {
                    columnSpec = GridLayout.spec(0, 2)  // Start at column 0, span 2 columns
                    setMargins(0, 16, 0, 0)          // Add some top margin
                }
                container.layoutParams = params
            }

            gridLayout.addView(container)
        }

        scrollView.addView(gridLayout)
        setContentView(scrollView)
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
        canvas.drawRect(100f, 100f, 800f, 600f, paint)
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
        canvas.drawRoundRect(100f, 100f, 800f, 600f, 50f, 50f, paint)
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
        canvas.drawCircle(500f, 500f, 400f, paint)
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
        canvas.drawOval(100f, 100f, 800f, 500f, paint)
    }
}

class ArcView(context: Context) : View(context) {
    private val paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    private val rect = RectF()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        rect.set(100f, 100f, 400f, 400f)

        // Arc without center (like a curved line)
        canvas.drawArc(rect, 0f, 90f, false, paint)
        canvas.drawArc(rect, 90f, 90f, false, paint)
        canvas.drawArc(rect, 180f, 90f, false, paint)
        canvas.drawArc(rect, 270f, 90f, false, paint)
    }
}

class SectorView(context: Context) : View(context) {
    private val paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    private val rect = RectF()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Make the rect fill most of the view (with some padding)
        val padding = 50f
        rect.set(padding, padding, width.toFloat() - padding, height.toFloat() - padding)

        // Draw four sectors (quarter circles)
        paint.color = Color.RED
        canvas.drawArc(rect, 0f, 90f, true, paint)

        paint.color = Color.GREEN
        canvas.drawArc(rect, 90f, 90f, true, paint)

        paint.color = Color.BLUE
        canvas.drawArc(rect, 180f, 90f, true, paint)

        paint.color = Color.YELLOW
        canvas.drawArc(rect, 270f, 90f, true, paint)
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

        // Alphabet A (first letter)
        canvas.drawLine(200f, 100f, 100f, 400f, paint)  // Left diagonal
        canvas.drawLine(200f, 100f, 250f, 400f, paint)  // Right diagonal
        canvas.drawLine(130f, 280f, 230f, 280f, paint)  // Crossbar

        // Alphabet S
        // Top curve of S
        canvas.drawLine(300f, 200f, 400f, 200f, paint)  // Top horizontal
        canvas.drawLine(300f, 200f, 300f, 300f, paint)  // Left vertical
        canvas.drawLine(400f, 300f, 300f, 300f, paint)  // Middle horizontal

        // Bottom curve of S
        canvas.drawLine(400f, 300f, 400f, 400f, paint)  // Right vertical
        canvas.drawLine(300f, 400f, 400f, 400f, paint)  // Bottom horizontal

        // Alphabet A (second letter) - shifted right
        canvas.drawLine(500f, 100f, 420f, 400f, paint)  // Left diagonal
        canvas.drawLine(500f, 100f, 550f, 400f, paint)  // Right diagonal
        canvas.drawLine(450f, 280f, 530f, 280f, paint)  // Crossbar

        // Letter D using drawLines() with float array
        val dPoints = floatArrayOf(
            // Vertical line
            600f, 100f, 600f, 400f,

            // Top horizontal
            600f, 100f, 700f, 100f,

            // Top-right curve part 1
            700f, 100f, 725f, 150f,

            // Top-right curve part 2
            725f, 150f, 750f, 200f,

            // Right vertical
            750f, 200f, 750f, 300f,

            // Bottom-right curve part 1
            750f, 300f, 725f, 350f,

            // Bottom-right curve part 2
            725f, 350f, 700f, 400f,

            // Bottom horizontal
            700f, 400f, 600f, 400f
        )
        canvas.drawLines(dPoints, paint)
    }
}

class PointView(context: Context) : View(context) {
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