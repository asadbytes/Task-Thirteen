package com.asadbyte.taskthirteen

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.cos
import kotlin.math.sin

class CustomShapes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scrollView = ScrollView(this)
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 16, 16, 16)
        }

        val viewItems = listOf(
            "Triangle" to TriangleView(this),
            "Pentagon" to PentagonView(this),
            "Hexagon (PathDrawingView)" to PathDrawingView(this),
            "Star" to StarView(this),
            "Heart" to HeartView(this),
            "Wave" to WaveView(this),
            "Quadratic Bezier Curve" to QuadraticBezierCurveView(this),
            "Cubic Bezier Curve" to CubicBezierCurveView(this)
        )

        viewItems.forEach { (label, shapeView) ->
            val wrapper = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                setPadding(8, 8, 8, 24)

                val labelView = TextView(this@CustomShapes).apply {
                    text = label
                    setTextColor(Color.BLACK)
                    gravity = Gravity.CENTER
                    textSize = 16f
                }

                shapeView.layoutParams = LinearLayout.LayoutParams(600, 600)
                addView(labelView)
                addView(shapeView)
            }

            container.addView(wrapper)
        }

        scrollView.addView(container)
        setContentView(scrollView)
    }
}


class TriangleView(context: Context) : View(context) {
    private val paint = Paint().apply {
        color = Color.MAGENTA
        style = Paint.Style.STROKE
        strokeWidth = 5f
        isAntiAlias = true
    }

    private val path = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val cx = width / 2f
        val cy = height / 2f
        val radius = 200f
        val sides = 3

        path.reset()
        for (i in 0..sides) {
            val angle = Math.toRadians((360.0 / sides * i))
            val x = (cx + radius * cos(angle)).toFloat()
            val y = (cy + radius * sin(angle)).toFloat()
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        path.close()

        canvas.drawPath(path, paint)
    }
}

class PentagonView(context: Context) : View(context) {
    private val paint = Paint().apply {
        color = Color.MAGENTA
        style = Paint.Style.STROKE
        strokeWidth = 5f
        isAntiAlias = true
    }

    private val path = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val cx = width / 2f
        val cy = height / 2f
        val radius = 200f
        val sides = 5

        path.reset()
        for (i in 0..sides) {
            val angle = Math.toRadians((360.0 / sides * i))
            val x = (cx + radius * cos(angle)).toFloat()
            val y = (cy + radius * sin(angle)).toFloat()
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        path.close()

        canvas.drawPath(path, paint)
    }

}

class PathDrawingView(context: Context) : View(context) {
    private val paint = Paint().apply {
        color = Color.MAGENTA
        style = Paint.Style.STROKE
        strokeWidth = 5f
        isAntiAlias = true
    }

    private val path = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val cx = width / 2f
        val cy = height / 2f
        val radius = 200f
        val sides = 6
        path.reset()
        for (i in 0..sides) {
            val angle = Math.toRadians((360.0 / sides * i))
            val x = (cx + radius * cos(angle)).toFloat()
            val y = (cy + radius * sin(angle)).toFloat()
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        path.close()

        canvas.drawPath(path, paint)
    }

}

class StarView(context: Context) : View(context) {
    private val paint = Paint().apply {
        color = Color.MAGENTA
        style = Paint.Style.STROKE
        strokeWidth = 5f
        isAntiAlias = true
    }

    private val path = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val cx = width / 2f
        val cy = height / 2f
        val outerRadius = 200f
        val innerRadius = 80f
        val points = 5

        path.reset()
        for (i in 0 until points * 2) {
            val angle = Math.PI * i / points
            val r = if (i % 2 == 0) outerRadius else innerRadius
            val x = (cx + cos(angle) * r).toFloat()
            val y = (cy + sin(angle) * r).toFloat()
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        path.close()

        canvas.drawPath(path, paint)
    }


}

class HeartView(context: Context) : View(context) {
    private val paint = Paint().apply {
        color = Color.MAGENTA
        style = Paint.Style.STROKE
        strokeWidth = 5f
        isAntiAlias = true
    }

    private val path = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val x = width / 2f
        val y = height / 2f

        path.reset()
        path.moveTo(x, y)

        path.cubicTo(x - 150, y - 150, x - 300, y + 100, x, y + 300)
        path.cubicTo(x + 300, y + 100, x + 150, y - 150, x, y)

        canvas.drawPath(path, paint)
    }
}

class WaveView(context: Context) : View(context) {
    private val paint = Paint().apply {
        color = Color.MAGENTA
        style = Paint.Style.STROKE
        strokeWidth = 5f
        isAntiAlias = true
    }

    private val path = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val waveHeight = 100f
        val waveLength = width / 2f

        path.reset()
        path.moveTo(0f, height / 2f)

        var x = 0f
        while (x < width) {
            path.rQuadTo(waveLength / 4, -waveHeight, waveLength / 2, 0f)
            path.rQuadTo(waveLength / 4, waveHeight, waveLength / 2, 0f)
            x += waveLength
        }

        canvas.drawPath(path, paint)
    }

}

class QuadraticBezierCurveView(context: Context) : View(context) {
    private val paint = Paint().apply {
        color = Color.MAGENTA
        style = Paint.Style.STROKE
        strokeWidth = 5f
        isAntiAlias = true
    }

    private val path = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        path.reset()
        path.moveTo(100f, 500f)
        path.quadTo(400f, 100f, 700f, 500f)

        canvas.drawPath(path, paint)
    }
}

class CubicBezierCurveView(context: Context) : View(context) {
    private val paint = Paint().apply {
        color = Color.MAGENTA
        style = Paint.Style.STROKE
        strokeWidth = 5f
        isAntiAlias = true
    }

    private val path = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        path.reset()
        path.moveTo(100f, 500f)
        path.cubicTo(300f, 100f, 500f, 900f, 700f, 500f)

        canvas.drawPath(path, paint)
    }
}