package com.hiberus.mobile.android.openbanktest.app.marvel.master

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hiberus.mobile.android.openbanktest.app.R
import com.hiberus.mobile.android.openbanktest.app.common.view.OnSingleClickListener
import com.hiberus.mobile.android.openbanktest.app.marvel.master.MarvelListAdapter.ViewHolder
import com.hiberus.mobile.android.openbanktest.model.marvel.MarvelHero
import java.lang.ref.WeakReference

class MarvelListAdapter(val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    interface MarvelHeroesAdapterListener {

        fun onItemClicked(position: Int)
    }

    var marvelHeroes: MutableList<MarvelHero> = arrayListOf()
    private var marvelHeroesAdapterListenerWeakReference: WeakReference<MarvelHeroesAdapterListener>? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val marvelHeroe = marvelHeroes[position]
        holder.nameText.text = marvelHeroe.name
        holder.descriptionText.text =
            if(marvelHeroe.description.isNotBlank()){ marvelHeroe.description } else "Not available description"

        Glide.with(context)
            .load(marvelHeroe.thumbnail)
            .centerInside()
            .apply(RequestOptions.circleCropTransform())
            .into(holder.avatarImage)

        // On click listener
        holder.itemView.setOnClickListener(
            OnSingleClickListener.wrap(View.OnClickListener {
                this@MarvelListAdapter.marvelHeroesAdapterListenerWeakReference?.get()?.onItemClicked(position)
            })
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_marvel, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return marvelHeroes.size
    }

    fun setMarvelHeroesAdapterListener(@NonNull marvelHeroesAdapterListener: MarvelHeroesAdapterListener) {
        this.marvelHeroesAdapterListenerWeakReference = WeakReference(marvelHeroesAdapterListener)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var avatarImage: ImageView = view.findViewById(R.id.iv_marvel_row_avatar_image)
        var nameText: TextView = view.findViewById(R.id.tv_marvel_row_name)
        var descriptionText: TextView = view.findViewById(R.id.tv_marvel_row_description)
    }
}