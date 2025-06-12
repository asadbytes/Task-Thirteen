package com.asadbyte.taskthirteen

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.ComposeShader
import android.graphics.DashPathEffect
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.SweepGradient
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StylingAndEffects : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scrollView = ScrollView(this)
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 16, 16, 16)
        }

        val viewItems = listOf(
            "Fill / Stroke / Fill_And_Stroke" to FillStrokeView(this),
            "Stroke Width & Cap" to StrokeWidthView(this),
            "Dashed Path Effect" to DashPathEffectView(this),
            "Linear Gradient" to LinearGradientView(this),
            "Radial Gradient" to RadialGradientView(this),
            "Sweep Gradient" to SweepGradientView(this),
            "Shadow Layer" to ShadowView(this),
            "Color Filter (Invert Red)" to FilterColorsView(this),
            "PorterDuff Blending (Multiply)" to PorterBluffBlendingView(this),
            "PorterDuff Modes" to ExtraPorterDuffModesView(this),
            "Compose Shader" to ComposeShaderView(this)
        )

        viewItems.forEach { (label, view) ->
            val wrapper = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                setPadding(8, 8, 8, 24)

                val textView = TextView(this@StylingAndEffects).apply {
                    text = label
                    setTextColor(Color.BLACK)
                    gravity = Gravity.CENTER
                    textSize = 16f
                }

                view.layoutParams = LinearLayout.LayoutParams(800, 800)
                addView(textView)
                addView(view)
            }
            container.addView(wrapper)
        }

        scrollView.addView(container)
        setContentView(scrollView)
    }
}


class FillStrokeView(context: Context) : View(context) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // FILL
        paint.style = Paint.Style.FILL
        paint.color = Color.RED
        canvas.drawCircle(200f, 200f, 100f, paint)

        // STROKE
        paint.style = Paint.Style.STROKE
        paint.color = Color.BLUE
        paint.strokeWidth = 10f
        canvas.drawCircle(500f, 200f, 100f, paint)

        // FILL_AND_STROKE
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = Color.GREEN
        paint.strokeWidth = 5f
        canvas.drawCircle(800f, 200f, 100f, paint)
    }

}

class StrokeWidthView(context: Context) : View(context) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.style = Paint.Style.STROKE
        paint.color = Color.BLACK
        paint.strokeWidth = 20f
        paint.strokeCap = Paint.Cap.ROUND // Options: BUTT, ROUND, SQUARE

        canvas.drawLine(100f, 100f, 600f, 100f, paint)
    }

}

class DashPathEffectView(context: Context) : View(context) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.style = Paint.Style.STROKE
        paint.color = Color.MAGENTA
        paint.strokeWidth = 10f
        paint.pathEffect = DashPathEffect(floatArrayOf(40f, 20f), 0f)

        path.reset()
        path.moveTo(100f, 200f)
        path.lineTo(800f, 200f)

        canvas.drawPath(path, paint)
    }

}

class LinearGradientView(context: Context) : View(context) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.shader = LinearGradient(
            100f, 100f, 500f, 500f,
            Color.RED, Color.BLUE,
            Shader.TileMode.CLAMP
        )
        canvas.drawRect(100f, 100f, 500f, 500f, paint)
    }

}

class RadialGradientView(context: Context) : View(context) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.shader = RadialGradient(
            400f, 400f, 300f,
            Color.YELLOW, Color.RED,
            Shader.TileMode.CLAMP
        )
        canvas.drawCircle(400f, 400f, 300f, paint)
    }

}

class SweepGradientView(context: Context) : View(context) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.shader = SweepGradient(
            400f, 400f,
            Color.GREEN, Color.BLUE
        )
        canvas.drawCircle(400f, 400f, 300f, paint)
    }

}

class ShadowView(context: Context) : View(context) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        setLayerType(LAYER_TYPE_SOFTWARE, null)  // Needed for shadows

        paint.color = Color.RED
        paint.setShadowLayer(20f, 10f, 10f, Color.GRAY)

        canvas.drawCircle(400f, 400f, 200f, paint)
    }

}

class FilterColorsView(context: Context) : View(context) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val colorMatrix = ColorMatrix(
            floatArrayOf(
                -1f, 0f, 0f, 0f, 255f,  // Red
                0f, 1f, 0f, 0f, 0f,    // Green
                0f, 0f, 1f, 0f, 0f,    // Blue
                0f, 0f, 0f, 1f, 0f     // Alpha
            )
        )
        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)

        canvas.drawRect(100f, 100f, 500f, 500f, paint)
    }

}

class PorterBluffBlendingView(context: Context) : View(context) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val dst = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888)
        val src = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888)

        val cDst = Canvas(dst)
        val cSrc = Canvas(src)

        val redPaint = Paint().apply { color = Color.RED }
        val bluePaint = Paint().apply { color = Color.BLUE }

        cDst.drawRect(0f, 0f, 400f, 400f, redPaint)
        cSrc.drawCircle(200f, 200f, 200f, bluePaint)

        canvas.drawBitmap(dst, 100f, 100f, null)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.MULTIPLY)
        canvas.drawBitmap(src, 100f, 100f, paint)
        paint.xfermode = null
    }
}

class ExtraPorterDuffModesView(context: Context) : View(context) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val dstBitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888)
        val srcBitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888)

        val canvasDst = Canvas(dstBitmap)
        val canvasSrc = Canvas(srcBitmap)

        // DST: Yellow square
        canvasDst.drawRect(0f, 0f, 400f, 400f, Paint().apply { color = Color.YELLOW })

        // SRC: Blue circle
        canvasSrc.drawCircle(200f, 200f, 200f, Paint().apply { color = Color.BLUE })

        // First draw the destination
        canvas.drawBitmap(dstBitmap, 100f, 100f, null)

        // Apply SRC_IN mode
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(srcBitmap, 100f, 100f, paint)

        // Reset for next operation
        paint.xfermode = null

        // other modes: SRC_OVER, DST_OUT, SRC_OUT, DST_IN etc.
    }
}

class ComposeShaderView(context: Context) : View(context) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val linearGradient = LinearGradient(
            0f, 0f, width.toFloat(), 0f,
            Color.RED, Color.BLUE, Shader.TileMode.CLAMP
        )

        val radialGradient = RadialGradient(
            width / 2f, height / 2f, width / 2f,
            Color.YELLOW, Color.GREEN, Shader.TileMode.CLAMP
        )

        val composedShader = ComposeShader(linearGradient, radialGradient, PorterDuff.Mode.SRC_OVER)
        paint.shader = composedShader

        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    }
}
