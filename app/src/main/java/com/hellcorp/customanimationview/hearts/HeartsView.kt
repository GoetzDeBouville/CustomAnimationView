package com.hellcorp.customanimationview.hearts

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import com.hellcorp.customanimationview.R
import kotlin.math.min

class HeartsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes deffstyleAttr: Int = 0,
    @StyleRes deffstyleRes: Int = 0
) : View(
    context,
    attrs,
    deffstyleAttr,
    deffstyleRes
) {
    private val minViewSize = resources.getDimensionPixelSize(
        R.dimen.hearts_view_size
    )
    private val paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.heart_color1)
        style = Paint.Style.FILL
    }
    private val path = Path()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = min(
            MeasureSpec.getSize(widthMeasureSpec).coerceAtMost(minViewSize),
            MeasureSpec.getSize(heightMeasureSpec).coerceAtMost(minViewSize)
        )
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        path.reset()
        path.moveTo(width / 2f, height / 4f)
        path.cubicTo(
            width / 5f, 0f,
            0f, height / 2.5f,
            width / 2f, height * 3 / 4f
        )
        path.cubicTo(
            width.toFloat(), height / 2.5f,
            width * 4 / 5f, 0f,
            width / 2f, height / 4f
        )
        path.close()
        canvas.drawPath(path, paint)
    }
}
