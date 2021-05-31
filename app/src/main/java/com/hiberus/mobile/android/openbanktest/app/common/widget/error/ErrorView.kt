package com.hiberus.mobile.android.openbanktest.app.common.widget.error

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.hiberus.mobile.android.openbanktest.app.R
import com.hiberus.mobile.android.openbanktest.app.common.errorhandling.ErrorBundle
import com.hiberus.mobile.android.openbanktest.app.databinding.ViewErrorBinding
import timber.log.Timber

/**
 * Widget used to display an error state to the user
 */
class ErrorView : RelativeLayout {

    private val binding = ViewErrorBinding.inflate(LayoutInflater.from(context), this)

    var errorListener: ErrorListener? = null
    var errorBundle: ErrorBundle? = null

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
        LayoutInflater.from(context).inflate(R.layout.view_error, this)
        binding.btErrorViewRetryButton.setOnClickListener {
            errorBundle?.let { errorBundle ->
                errorListener?.apply {
                    onRetry(errorBundle)
                } ?: Timber.e("Error listener is null")
            } ?: Timber.e("Error bundle is null")
        }
    }

    fun setErrorMessage(message: String) {
        // Post a task to update the message
        binding.tvErrorViewMessage.post {
            binding.tvErrorViewMessage.text = message
        }
    }
}