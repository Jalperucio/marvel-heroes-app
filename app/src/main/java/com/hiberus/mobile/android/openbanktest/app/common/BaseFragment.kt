package com.hiberus.mobile.android.openbanktest.app.common

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.CallSuper
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.hiberus.mobile.android.openbanktest.app.R

/**
 * Base [Fragment] class for every fragment in this application.
 */
open class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // In initial versions of the lifecycle library, view models were not available in onViewCreated() and
        // OnActivityCreated() was used to call this set of functions. However, OnActivityCreated() is currently
        // deprecated and view models are now available at this point.
        initializeState(savedInstanceState)
        initializeViews(savedInstanceState)
        initializeContents(savedInstanceState)
    }

    /**
     * Initializes fragment state with [androidx.lifecycle.ViewModel]s and parameters passed through [Bundle].
     */
    @CallSuper
    protected open fun initializeState(savedInstanceState: Bundle?) {
    }

    /**
     * View initialization.
     */
    @CallSuper
    protected open fun initializeViews(savedInstanceState: Bundle?) {
    }

    /**
     * Initializes view contents.
     */
    @CallSuper
    protected open fun initializeContents(savedInstanceState: Bundle?) {
    }

    // region animations

    //This is basically an ugly fix to this bug: https://issuetracker.google.com/issues/37036000
    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        if (nextAnim == R.anim.enter_from_right) {
            val nextAnimation = AnimationUtils.loadAnimation(context, nextAnim)
            nextAnimation.setAnimationListener(object : Animation.AnimationListener {
                private var startZ = 0f
                override fun onAnimationStart(animation: Animation) {
                    view?.apply {
                        startZ = ViewCompat.getTranslationZ(this)
                        ViewCompat.setTranslationZ(this, 1f)
                    }
                }

                override fun onAnimationEnd(animation: Animation) {
                    // Short delay required to prevent flicker since other Fragment wasn't removed just yet.
                    view?.apply {
                        this.postDelayed({ ViewCompat.setTranslationZ(this, startZ) }, 350)
                    }
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
            return nextAnimation
        } else {
            return null
        }
    }

    // endregion animations
}
