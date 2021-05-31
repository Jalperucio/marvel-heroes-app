package com.hiberus.mobile.android.openbanktest.app.common.navigation

import android.app.Activity
import androidx.annotation.NonNull
import com.hiberus.mobile.android.openbanktest.app.marvel.MarvelActivity

/**
 * Class used to navigate through activities.
 */
object Navigator {

    /**
     * Opens no navigation main screen.
     *
     * @param activity An [Activity] needed to open the destiny activity.
     */
    fun navigateToMainActivity(@NonNull activity: Activity) {
        val intentToLaunch = MarvelActivity.getCallingIntent(activity)
        activity.startActivity(intentToLaunch)
        activity.finish()
    }
}
