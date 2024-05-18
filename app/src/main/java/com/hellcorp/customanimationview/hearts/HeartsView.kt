package com.hellcorp.customanimationview.hearts

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
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
    init {
        animateAppearance()
        translationAnimation()
    }

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

    private fun startPulsingAnimation() {
        val scaleX = ObjectAnimator.ofFloat(this, PROPERTY_SCALE_X, 1f, 1.2f, 1f)
        val scaleY = ObjectAnimator.ofFloat(this, PROPERTY_SCALE_Y, 1f, 1.2f, 1f)
        val animatorSet = AnimatorSet().apply {
            playTogether(scaleX, scaleY)
            duration = 1000
            reverse()
        }
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator.addUpdateListener {
            animatorSet.reverse()
        }
        valueAnimator.start()
    }

    private fun translationAnimation() {
        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 2000
            addUpdateListener { animation ->
                val value = animation.animatedValue as Float
                val x = (Math.sin(value * Math.PI * 2) * 400).toFloat()
                val y = -1000f * value
                translationX = x
                translationY = y
            }
            fadingOut()
            start()
        }
    }

    private fun animateAppearance() {
        val scaleX = ObjectAnimator.ofFloat(this, PROPERTY_SCALE_X, 0f, 1f)
        val scaleY = ObjectAnimator.ofFloat(this, PROPERTY_SCALE_Y, 0f, 1f)

        val animatorSet = AnimatorSet().apply {
            playTogether(scaleX, scaleY)
            duration = 700

            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) = Unit
                override fun onAnimationEnd(animation: Animator) {
                    startPulsingAnimation()
                }

                override fun onAnimationCancel(animation: Animator) = Unit
                override fun onAnimationRepeat(animation: Animator) = Unit
            })
        }
        animatorSet.start()
    }

    private fun fadingOut() {
        ObjectAnimator.ofFloat(this, PROPERTY_ALPHA, 1f, 0f).apply {
            duration = 2000
            start()
        }

    }

    companion object {
        const val PROPERTY_SCALE_X = "scaleX"
        const val PROPERTY_SCALE_Y = "scaleY"
        const val PROPERTY_TRANSLATION_X = "translationX"
        const val PROPERTY_TRANSLATION_Y = "translationY"
        const val PROPERTY_ALPHA = "alpha"
    }
}
