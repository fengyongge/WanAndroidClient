package com.fengyongge.wanandroidclient.common.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.fengyongge.wanandroidclient.R


/**
 * describe
 * 按指定的长宽比，同比例截取图片中心展示
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class ScaleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    private var originWidth: Int
    private var originHeight: Int
    fun setOriginSize(originWidth: Int, originHeight: Int) {
        this.originWidth = originWidth
        this.originHeight = originHeight
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (originWidth > 0 && originHeight > 0) {
            var scale = originWidth * 1.0f / originHeight
            if (scale < 0.7f) {
                scale = 0.7f
            } else if (scale > 1.3f) {
                scale = 1.3f
            }
            val width = MeasureSpec.getSize(widthMeasureSpec)
            var height = MeasureSpec.getSize(heightMeasureSpec)
            if (width > 0) {
                height = (width * 1.0f / scale).toInt()
            }
            setMeasuredDimension(width, height)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.ScaleImageView)
        originWidth = ta.getInteger(R.styleable.ScaleImageView_origin_width, 0)
        originHeight = ta.getInteger(R.styleable.ScaleImageView_origin_height, 0)
        ta.recycle()
    }
}