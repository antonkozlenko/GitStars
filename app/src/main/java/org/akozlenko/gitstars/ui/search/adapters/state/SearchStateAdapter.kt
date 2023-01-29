package org.akozlenko.gitstars.ui.search.adapters.state

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class SearchStateAdapter(private val retryCall: () -> Unit) : LoadStateAdapter<SearchStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): SearchStateViewHolder {
        return SearchStateViewHolder.create(
            parent = parent,
            retryCall = retryCall
        )
    }

    override fun onBindViewHolder(holder: SearchStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}
