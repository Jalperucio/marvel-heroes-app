package com.hiberus.mobile.android.openbanktest.app.marvel.master

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hiberus.mobile.android.openbanktest.app.R
import com.hiberus.mobile.android.openbanktest.app.common.BaseFragment
import com.hiberus.mobile.android.openbanktest.app.common.errorhandling.AppAction
import com.hiberus.mobile.android.openbanktest.app.common.errorhandling.AppAction.GET_MARVEL_HEROES
import com.hiberus.mobile.android.openbanktest.app.common.errorhandling.AppError.NO_INTERNET
import com.hiberus.mobile.android.openbanktest.app.common.errorhandling.AppError.TIMEOUT
import com.hiberus.mobile.android.openbanktest.app.common.errorhandling.ErrorBundle
import com.hiberus.mobile.android.openbanktest.app.common.errorhandling.ErrorDialogFragment
import com.hiberus.mobile.android.openbanktest.app.common.errorhandling.ErrorUtils
import com.hiberus.mobile.android.openbanktest.app.common.model.ResourceState.Error
import com.hiberus.mobile.android.openbanktest.app.common.model.ResourceState.Loading
import com.hiberus.mobile.android.openbanktest.app.common.model.ResourceState.Success
import com.hiberus.mobile.android.openbanktest.app.common.widget.empty.EmptyListener
import com.hiberus.mobile.android.openbanktest.app.common.widget.error.ErrorListener
import com.hiberus.mobile.android.openbanktest.app.databinding.FragmentMarvelListBinding
import com.hiberus.mobile.android.openbanktest.app.marvel.master.MarvelListAdapter.MarvelHeroesAdapterListener
import com.hiberus.mobile.android.openbanktest.app.marvel.master.MarvelListNavigationCommand.GoToDetailsView
import com.hiberus.mobile.android.openbanktest.app.marvel.viewmodel.MarvelNavigationViewModel
import com.hiberus.mobile.android.openbanktest.app.marvel.viewmodel.MarvelState
import com.hiberus.mobile.android.openbanktest.app.marvel.viewmodel.MarvelViewModel
import com.hiberus.mobile.android.openbanktest.model.marvel.MarvelHero
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class MarvelListFragment : BaseFragment() {

    companion object {

        private const val ERROR_DIALOG_REQUEST_CODE = 0
    }

    private var _binding: FragmentMarvelListBinding? = null
    private val binding get() = _binding!!
    private val marvelListAdapter: MarvelListAdapter by inject()
    private val marvelNavigationViewModel: MarvelNavigationViewModel by sharedViewModel()
    private val marvelViewModel: MarvelViewModel by sharedViewModel()
    private val adapterListener = object : MarvelHeroesAdapterListener {
        override fun onItemClicked(position: Int) {
            Timber.d("Selected position $position")
            // Select item in data view model
            marvelViewModel.select(position)
            // Send command in navigation view model
            marvelNavigationViewModel.send(GoToDetailsView)
        }
    }
    private var isLoadingMore = false

    // region Lifecycle methods
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMarvelListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        activity?.setTitle(R.string.marvel_master_title)
    }

    override fun initializeViews(savedInstanceState: Bundle?) {
        super.initializeViews(savedInstanceState)
        setupMarvelHeroesRecycler()
        setupViewListeners()
    }

    override fun initializeContents(savedInstanceState: Bundle?) {
        super.initializeContents(savedInstanceState)

        binding.srlMarvelHeroes.setOnRefreshListener {
            binding.srlMarvelHeroes.isRefreshing = false
            marvelViewModel.offset = 0
            clearListView()
            marvelViewModel.fetchMarvelHeroes(true)
        }

        // Link the fragment and the model view with "viewLifecycleOwner", so that observers
        // can be subscribed in onViewCreated() and can be automatically unsubscribed
        // in onDestroyView().
        // IMPORTANT: Never use "this" as lifecycle owner.
        marvelViewModel.getMarvelLiveData().observe(viewLifecycleOwner,
            Observer<MarvelState> {
                if (it != null) this.handleDataState(it)
            }
        )

        // Check if the view model has data
        if (marvelViewModel.getMarvelLiveData().value == null) {
            // Fetch data only if the view model doesn't have data
            clearListView()
            marvelViewModel.fetchMarvelHeroes()
        }
    }

    private fun setupMarvelHeroesRecycler() {
        binding.rvMarvel.layoutManager = LinearLayoutManager(this.context)
        marvelListAdapter.setMarvelHeroesAdapterListener(this.adapterListener)
        binding.rvMarvel.adapter = marvelListAdapter
        binding.rvMarvel.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && marvelViewModel.offset > 0) {
                    onRecyclerScrolled()
                }
            }
        })
    }
    //endregion

    //region Handling state
    private fun handleDataState(marvelState: MarvelState) {
        when (marvelState) {
            is Loading -> setupScreenForLoadingState()
            is Success -> setupScreenForSuccess(marvelState.data)
            is Error -> setupScreenForError(marvelState.errorBundle)
        }
    }

    private fun setupScreenForLoadingState() {
        binding.lvMarvelLoadingView.visibility = View.VISIBLE
        binding.evMarvelEmptyView.visibility = View.GONE
        binding.evMarvelErrorView.visibility = View.GONE
    }

    private fun setupScreenForSuccess(data: List<MarvelHero>?) {
        binding.evMarvelErrorView.visibility = View.GONE
        binding.lvMarvelLoadingView.visibility = View.GONE
        if (data != null && data.isNotEmpty()) {
            updateListView(data)
        } else {
            binding.evMarvelEmptyView.visibility = View.VISIBLE
        }
        isLoadingMore = false
    }


    private fun clearListView() {
        marvelListAdapter.marvelHeroes = arrayListOf()
        marvelListAdapter.notifyDataSetChanged()
    }

    private fun updateListView(marvelHeroes: List<MarvelHero>) {
        marvelListAdapter.marvelHeroes.addAll(marvelHeroes as MutableList)
        marvelListAdapter.notifyDataSetChanged()
    }

    private fun setupScreenForError(errorBundle: ErrorBundle) {
        binding.lvMarvelLoadingView.visibility = View.GONE
        binding.evMarvelEmptyView.visibility = View.GONE

        // Example of using a custom error view as part of the fragment view
        showErrorView(errorBundle)

        isLoadingMore = false
    }
    //endregion

    //region Error and empty views
    private fun setupViewListeners() {
        binding.evMarvelEmptyView.emptyListener = emptyListener
        binding.evMarvelErrorView.errorListener = errorListener
    }

    private val emptyListener = object : EmptyListener {
        override fun onCheckAgainClicked() {
            marvelViewModel.offset = 0
            clearListView()
            marvelViewModel.fetchMarvelHeroes(true)
        }
    }

    private val errorListener = object : ErrorListener {
        override fun onRetry(errorBundle: ErrorBundle) {
            retry(errorBundle.appAction)
        }
    }

    private fun retry(appAction: AppAction) {
        when (appAction) {
            GET_MARVEL_HEROES -> {
                marvelViewModel.offset = 0
                clearListView()
                marvelViewModel.fetchMarvelHeroes(true)
            }
            else -> Timber.e("Unknown action code")
        }
    }

    private fun showErrorView(errorBundle: ErrorBundle) {
        activity?.let { activity ->
            binding.evMarvelErrorView.visibility = View.VISIBLE
            binding.evMarvelErrorView.errorBundle = errorBundle
            binding.evMarvelErrorView.setErrorMessage(ErrorUtils.buildErrorMessageForDialog(activity, errorBundle).message)
        } ?: Timber.e("Activity is null")
    }

    //region endless scroll
    private fun onRecyclerScrolled() {
        if (!isLoadingMore) {
            Timber.d("Fetching more data")
            marvelViewModel.fetchMarvelHeroes(true)
            isLoadingMore = true
        }
    }
    //endregion
}
