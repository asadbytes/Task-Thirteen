package com.asadbyte.taskthirteen

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TransformationsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create root ScrollView
        val scrollView = ScrollView(this).apply {
            isFillViewport = true
        }

        // Create main container with vertical orientation
        val mainContainer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setPadding(16.dpToPx(), 16.dpToPx(), 16.dpToPx(), 16.dpToPx())
        }

        // List of transformation examples to display
        val transformationExamples = listOf(
            "Original" to TransformationsView(this).apply { showOnlyOriginal = true },
            "Translation" to TransformationsView(this).apply { showOnlyTranslation = true },
            "Rotation" to TransformationsView(this).apply { showOnlyRotation = true },
            "Scaling" to TransformationsView(this).apply { showOnlyScaling = true },
            "Skew" to TransformationsView(this).apply { showOnlySkew = true },
            "Combined" to TransformationsView(this) // Shows all transformations
        )

        // Create two rows for our grid
        val row1 = createRow()
        val row2 = createRow()

        // Distribute views between rows
        transformationExamples.chunked(3).forEachIndexed { index, chunk ->
            val row = if (index == 0) row1 else row2
            chunk.forEach { (labelText, view) ->
                val container = createViewContainer(labelText, view)
                row.addView(container)
            }
        }

        mainContainer.addView(row1)
        mainContainer.addView(row2)
        scrollView.addView(mainContainer)
        setContentView(scrollView)
    }

    private fun createRow(): LinearLayout {
        return LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                weight = 1f
                topMargin = 8.dpToPx()
            }
            gravity = Gravity.CENTER
        }
    }

    private fun createViewContainer(labelText: String, view: View): LinearLayout {
        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                weight = 1f
                marginEnd = 8.dpToPx()
            }
            gravity = Gravity.CENTER
            setPadding(8.dpToPx(), 8.dpToPx(), 8.dpToPx(), 8.dpToPx())

            // Set fixed size for each view
            val size = 300.dpToPx()
            view.layoutParams = ViewGroup.LayoutParams(size, size)

            // Add label
            val label = TextView(this@TransformationsActivity).apply {
                text = labelText
                gravity = Gravity.CENTER
                setTextColor(Color.BLACK)
                textSize = 16f
                setPadding(0, 8.dpToPx(), 0, 0)
            }

            addView(view)
            addView(label)
        }
    }

    // Extension function to convert dp to pixels
    private fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()
}

class TransformationsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Control flags for the activity
    var showOnlyOriginal = false
    var showOnlyTranslation = false
    var showOnlyRotation = false
    var showOnlyScaling = false
    var showOnlySkew = false

    // Animation properties
    private var animProgress = 0f
    private val animator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 2000
        repeatCount = ValueAnimator.INFINITE
        repeatMode = ValueAnimator.REVERSE
        addUpdateListener {
            animProgress = it.animatedFraction
            invalidate()
        }
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
        style = Paint.Style.FILL
        strokeWidth = 2f
    }

    private val rect = RectF(-50f, -30f, 50f, 30f) // Centered rectangle

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animator.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator.cancel()
    }

    override fun onDraw(canvas: Canvas) {
        // Move origin to center for better visualization
        canvas.translate(width/2f, height/2f)

        // Draw axes for reference
        drawAxes(canvas)

        // Save the initial state
        canvas.save()

        // Apply transformations based on progress
        when {
            showOnlyOriginal -> drawOriginal(canvas)
            showOnlyTranslation -> demonstrateTranslation(canvas)
            showOnlyRotation -> demonstrateRotation(canvas)
            showOnlyScaling -> demonstrateScale(canvas)
            showOnlySkew -> demonstrateSkew(canvas)
            else -> demonstrateCombined(canvas)
        }

        // Restore to initial state
        canvas.restore()
    }

    private fun drawOriginal(canvas: Canvas) {
        canvas.save()
        drawTransformedShape(canvas, "Original")
        canvas.restore()
    }

    private fun demonstrateTranslation(canvas: Canvas) {
        canvas.save()
        val distance = 150f * animProgress
        canvas.translate(distance, distance)
        drawTransformedShape(canvas, "Translate")
        canvas.restore()
    }

    private fun demonstrateRotation(canvas: Canvas) {
        canvas.save()
        canvas.rotate(360f * animProgress)
        drawTransformedShape(canvas, "Rotate")
        canvas.restore()
    }

    private fun demonstrateScale(canvas: Canvas) {
        canvas.save()
        val scale = 0.5f + animProgress * 1.5f
        canvas.scale(scale, scale)
        drawTransformedShape(canvas, "Scale")
        canvas.restore()
    }

    private fun demonstrateSkew(canvas: Canvas) {
        canvas.save()
        val skew = animProgress * 0.5f
        canvas.skew(skew, skew * 0.5f)
        drawTransformedShape(canvas, "Skew")
        canvas.restore()
    }

    private fun demonstrateCombined(canvas: Canvas) {
        canvas.save()
        canvas.translate(100f * animProgress, 0f)
        canvas.rotate(180f * animProgress)
        canvas.scale(0.8f + animProgress * 0.5f, 0.8f + animProgress * 0.5f)
        canvas.skew(animProgress * 0.2f, 0f)
        drawTransformedShape(canvas, "Combined")
        canvas.restore()
    }

    private fun drawTransformedShape(canvas: Canvas, label: String) {
        // Draw rectangle
        paint.color = Color.BLUE
        canvas.drawRect(rect, paint)

        // Draw text
        //paint.color = Color.BLACK
        //canvas.drawText(label, rect.left, rect.bottom + 40f, paint)
    }

    private fun drawAxes(canvas: Canvas) {
        paint.color = Color.LTGRAY
        paint.style = Paint.Style.STROKE
        canvas.drawLine(-width/2f, 0f, width/2f, 0f, paint)
        canvas.drawLine(0f, -height/2f, 0f, height/2f, paint)
        paint.style = Paint.Style.FILL
    }
}