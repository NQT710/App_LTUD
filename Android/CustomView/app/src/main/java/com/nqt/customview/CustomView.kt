package com.nqt.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout

class CustomView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var attributes : TypedArray
    private var textView: TextView
    private var imageView: ImageView
    init {
        inflate(context,R.layout.custom_view,this)
        attributes = context.obtainStyledAttributes(attrs,R.styleable.CustomView)
        textView = findViewById(R.id.text)
        imageView = findViewById(R.id.image)
        attributes.getString(R.styleable.CustomView_Text)?.let { setText(it) }
        setImage(attributes.getResourceId(R.styleable.CustomView_Image, R.color.teal_700))
        //imageView.setImageResource(attributes.getResourceId(R.styleable.CustomView_Image, R.color.teal_700))
    }
    fun setText(text: String) {
        textView.text = text
    }

    fun setImage(@DrawableRes resId: Int){
        imageView.setImageResource(resId)
    }
    fun getText(): String {
        return textView.text.toString()
    }
}
