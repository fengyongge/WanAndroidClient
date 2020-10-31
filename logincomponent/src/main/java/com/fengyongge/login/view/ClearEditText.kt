package com.fengyongge.login.view

import android.R
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.appcompat.widget.AppCompatEditText

/**
 * describe
 * 输入文本框 右边有自带的删除按钮 当有输入时，显示删除按钮，当无输入时，不显示删除按钮。
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class ClearEditText @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = R.attr.editTextStyle
) : AppCompatEditText(context!!, attrs, defStyle), OnFocusChangeListener, TextWatcher {

    private var mClearDrawable: Drawable? = null
    private fun init() {
        mClearDrawable = compoundDrawables[2]
        mClearDrawable?.apply {
            mClearDrawable = resources.getDrawable(R.drawable.ic_delete)
            setBounds(
                0,
                0,
                getIntrinsicWidth(),
                getIntrinsicHeight()
            )
        }
        setClearIconVisible(false)
        onFocusChangeListener = this
        addTextChangedListener(this)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (compoundDrawables[2] != null) {
                val touchable =
                    event.x > width - totalPaddingRight && event.x < width - paddingRight
                if (touchable) {
                    this.setText("")
                }
            }
        }
        return super.onTouchEvent(event)
    }


    override fun onFocusChange(v: View, hasFocus: Boolean) {
        hasFoucs = hasFocus
        if (hasFocus) {
            setClearIconVisible(text!!.length > 0)
        } else {
            setClearIconVisible(false)
        }
    }


    protected fun setClearIconVisible(visible: Boolean) {
        val right = if (visible) mClearDrawable else null
        setCompoundDrawables(
            compoundDrawables[0],
            compoundDrawables[1],
            right,
            compoundDrawables[3]
        )
    }


    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
        if (hasFoucs) {
            setClearIconVisible(s.length > 0)
        }
    }

    override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun afterTextChanged(s: Editable) {}

    companion object {
        private var hasFoucs = false
    }

    init {
        init()
    }
}