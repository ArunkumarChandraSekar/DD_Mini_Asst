package com.ex.dd_mini_asst.utils

import android.graphics.*
import android.graphics.drawable.Drawable


class SegmentProgressDrawable( fgColor : Int,  bgColor : Int): Drawable()  {
    private val NUMSEGMENTS = 4
    private val mForeground = fgColor
    private val mBackground = bgColor
    private val mPaint: Paint = Paint()
    private val mSegment = RectF()

    override fun onLevelChange(level: Int): Boolean {
        invalidateSelf()
        return true
    }
    override fun draw(canvas: Canvas) {
        val level = level / 10000f
        val b: Rect = bounds
        val gapWidth: Float = b.height() / 2f
        val segmentWidth: Float = (b.width() - (NUMSEGMENTS - 1) * gapWidth) / NUMSEGMENTS
        mSegment[0f, 0f, segmentWidth] = b.height().toFloat()
        mPaint.color = mForeground

        for (i in 0 until NUMSEGMENTS) {
            val loLevel = i / NUMSEGMENTS.toFloat()
            val hiLevel = (i + 1) / NUMSEGMENTS.toFloat()
            if (level in loLevel..hiLevel) {
                val middle = mSegment.left + NUMSEGMENTS * segmentWidth * (level - loLevel)
                canvas.drawRect(mSegment.left, mSegment.top, middle, mSegment.bottom, mPaint)
                mPaint.color = mBackground
                canvas.drawRect(middle, mSegment.top, mSegment.right, mSegment.bottom, mPaint)
            } else {
                canvas.drawRect(mSegment, mPaint)
            }
            mSegment.offset(mSegment.width() + gapWidth, 0f)
        }
    }

    override fun setAlpha(alpha: Int) {

    }

    override fun setColorFilter(colorFilter: ColorFilter?) {

    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT;
    }
}