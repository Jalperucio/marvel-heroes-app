package com.hiberus.mobile.android.openbanktest.app.marvel.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hiberus.mobile.android.openbanktest.app.R
import com.hiberus.mobile.android.openbanktest.app.common.BaseFragment
import com.hiberus.mobile.android.openbanktest.app.databinding.FragmentMarvelDetailBinding
import com.hiberus.mobile.android.openbanktest.app.marvel.viewmodel.MarvelViewModel
import com.hiberus.mobile.android.openbanktest.model.marvel.MarvelHero
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MarvelListDetailsFragment : BaseFragment() {

    private var _binding: FragmentMarvelDetailBinding? = null
    private val binding get() = _binding!!
    private val marvelViewModel: MarvelViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMarvelDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        activity?.setTitle(R.string.marvel_details_title)
    }

    override fun initializeContents(savedInstanceState: Bundle?) {
        super.initializeContents(savedInstanceState)

        // Link the fragment and the model view with "viewLifecycleOwner", so that observers
        // can be subscribed in onViewCreated() and can be automatically unsubscribed
        // in onDestroyView().
        // IMPORTANT: Never use "this" as lifecycle owner.
        // See: https://medium.com/@BladeCoder/architecture-components-pitfalls-part-1-9300dd969808
        marvelViewModel.getMarvelHeroLiveData().observe(viewLifecycleOwner,
            Observer<MarvelHero> {
                this.handleData(it)
            }
        )
    }

    private fun handleData(data: MarvelHero?) {
        if (data == null) {
            // TODO: Show error
        } else {
            // Map data to UI

            binding.tvMarvelRowDetailName.text = data.name
            binding.tvMarvelRowDetailDescription.text = data.description
            binding.tvMarvelRowDetailComics.text = getString(R.string.marvel_details_comics_label, data.comics.items.size)
            binding.tvMarvelRowDetailSeries.text = getString(R.string.marvel_details_series_label, data.series.items.size)
            binding.tvMarvelRowDetailStories.text = getString(R.string.marvel_details_stories_label, data.stories.items.size)
            binding.tvMarvelRowDetailEvents.text = getString(R.string.marvel_details_events_label, data.events.items.size)

            context?.let {
                Glide.with(it)
                    .load(data.thumbnail)
                    .apply(RequestOptions.circleCropTransform())
                    .into(binding.ivMarvelRowDetailImage)
            }

            data.urlDetail?.let { url ->
                binding.btMarvelRowDetailDetailBtn.setOnClickListener {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }
            } ?: run { binding.btMarvelRowDetailDetailBtn.visibility = View.GONE }

            data.urlWiki?.let { url ->
                binding.btMarvelRowDetailWikiBtn.setOnClickListener {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }
            } ?: run { binding.btMarvelRowDetailWikiBtn.visibility = View.GONE }

            data.urlComicLink?.let { url ->
                binding.btMarvelRowDetailComiclinkBtn.setOnClickListener {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }
            } ?: run { binding.btMarvelRowDetailComiclinkBtn.visibility = View.GONE }
        }
    }
}