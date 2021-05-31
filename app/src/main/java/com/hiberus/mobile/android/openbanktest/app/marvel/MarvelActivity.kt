package com.hiberus.mobile.android.openbanktest.app.marvel

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.hiberus.mobile.android.openbanktest.app.R
import com.hiberus.mobile.android.openbanktest.app.R.id
import com.hiberus.mobile.android.openbanktest.app.databinding.ActivityContentMainBinding
import com.hiberus.mobile.android.openbanktest.app.databinding.ActivityMainBinding
import com.hiberus.mobile.android.openbanktest.app.marvel.master.MarvelListFragmentDirections
import com.hiberus.mobile.android.openbanktest.app.marvel.master.MarvelListNavigationCommand.GoToDetailsView
import com.hiberus.mobile.android.openbanktest.app.marvel.viewmodel.MarvelNavigationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.ref.WeakReference

class MarvelActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    companion object {

        fun getCallingIntent(context: Context): Intent {
            return Intent(context, MarvelActivity::class.java)
        }
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var contentBinding: ActivityContentMainBinding
    private val marvelNavigationViewModel: MarvelNavigationViewModel by viewModel()
    private var exitSnackBarWeakReference: WeakReference<Snackbar>? = null

    // region Life cycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        contentBinding = binding.activityContentMain
        setContentView(binding.root)

        initializeViews(savedInstanceState)
        initializeContents(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancel coroutines
        cancel()
    }
    // endregion

    // region Initialization
    private fun initializeViews(@Suppress("UNUSED_PARAMETER") savedInstanceState: Bundle?) {
        // Initialize toolbar
        setSupportActionBar(binding.tbMainToolbar)

        // Initialize toolbar to show up button
        // IMPORTANT: findNavController will fail in onCreate() of an activity with a FragmentContainerView (recommended
        // way) instead of a fragment (previous way).
        // See: https://developer.android.com/guide/navigation/navigation-getting-started?authuser=1#navigate
        val navHostFragment = supportFragmentManager.findFragmentById(id.fcv_marvel_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        setupWithNavController(binding.tbMainToolbar, navController)
    }

    private fun initializeContents(@Suppress("UNUSED_PARAMETER") savedInstanceState: Bundle?) {
        marvelNavigationViewModel.getMarvelNavigationLiveEvents().observe(this, Observer { eventWrapper ->
            eventWrapper.getContentIfNotHandled()?.let { command -> // Only proceed if the event has never been handled
                when (command) {
                    GoToDetailsView -> {
                        val action = MarvelListFragmentDirections.actionMarvelListFragmentToMarvelListDetailsFragment()
                        findNavController(id.fcv_marvel_host_fragment).navigate(action)
                    }
                }
            }
        })
    }
    // endregion

    //region Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_about -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("About")
                builder.setMessage(getString(R.string.about_text))
                builder.setPositiveButton(R.string.back) { dialog, which ->
                    dialog.dismiss()
                }
                builder.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    //endregion

    // region Back button
    override fun onBackPressed() {
        val navController = findNavController(id.fcv_marvel_host_fragment)
        val start = navController.currentDestination?.id
        // TODO: Improve this way of knowing that navController is at a top destination
        if (start == id.marvelListFragment) {
            exitSnackBarWeakReference?.let { exitSnackBarWeakReference ->
                exitSnackBarWeakReference.get()?.let { exitSnackBar ->
                    if (exitSnackBar.isShown) {
                        finish()
                    } else {
                        showExitSnackBar()
                    }
                } ?: showExitSnackBar()
            } ?: showExitSnackBar()
        } else {
            super.onBackPressed()
        }
    }

    private fun showExitSnackBar() {
        val exitSnackBar =
            Snackbar.make(contentBinding.clMarvelMainContentLayout, R.string.press_back_again, Snackbar.LENGTH_SHORT)
        exitSnackBarWeakReference = WeakReference(exitSnackBar)
        exitSnackBar.show()
    }
    // endregion

}