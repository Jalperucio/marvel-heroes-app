package com.hiberus.mobile.android.openbanktest.app.common.widget.loading

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.hiberus.mobile.android.openbanktest.app.BuildConfig
import com.hiberus.mobile.android.openbanktest.app.R
import com.hiberus.mobile.android.openbanktest.app.databinding.ViewLoadingBinding

/**
 * Widget used to display an loading state to the user
 */
class LoadView : RelativeLayout {

    private val binding = ViewLoadingBinding.inflate(LayoutInflater.from(context), this)

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_loading, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        if (BuildConfig.DEBUG) {
            binding.tvLoadingViewMessage.visibility = View.VISIBLE
        }
    }

    fun updateLoadingMessage(message: String) {
        // Post a task to update the message
        binding.tvLoadingViewMessage.post {
            binding.tvLoadingViewMessage.text = message
        }
    }
}