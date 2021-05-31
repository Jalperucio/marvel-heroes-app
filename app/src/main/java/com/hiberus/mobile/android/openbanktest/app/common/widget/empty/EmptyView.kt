package com.hiberus.mobile.android.openbanktest.app.common.widget.empty

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.hiberus.mobile.android.openbanktest.app.R
import com.hiberus.mobile.android.openbanktest.app.databinding.ViewEmptyBinding

/**
 * Widget used to display an empty state to the user
 */
class EmptyView : RelativeLayout {

    private val binding = ViewEmptyBinding.inflate(LayoutInflater.from(context), this)

    var emptyListener: EmptyListener? = null

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
        LayoutInflater.from(context).inflate(R.layout.view_empty, this)
        binding.btEmptyViewRetryButton.setOnClickListener { emptyListener?.onCheckAgainClicked() }
    }
}