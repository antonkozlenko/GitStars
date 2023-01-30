package org.akozlenko.gitstars.ui.search.adapters.state

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import org.akozlenko.gitstars.databinding.ItemLoadStateBinding

class SearchStateViewHolder private constructor(
    itemBinding: ItemLoadStateBinding,
    retryCall: () -> Unit
) : RecyclerView.ViewHolder(itemBinding.root) {

    private val loadProgress = itemBinding.loaderProgress
    private val retryButton = itemBinding.loaderRetryBtn
    private val failedText = itemBinding.loadFailedText

    init {
        // Set RETRY listener
        retryButton.setOnClickListener {
            retryCall()
        }
    }

    fun bind(loadState: LoadState) {
        when (loadState) {
            is LoadState.Loading -> {
                // Hide failed state views
                retryButton.isVisible = false
                failedText.isVisible = false
                // Show progress
                loadProgress.isVisible = true
            }
            is LoadState.Error -> {
                // Hide progress
                loadProgress.isVisible = false
                // Show failed state views
                retryButton.isVisible = true
                failedText.isVisible = true
            }
            is LoadState.NotLoading -> {
                // Hide failed state views
                retryButton.isVisible = false
                failedText.isVisible = false
                // Hide progress
                loadProgress.isVisible = false
            }
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            retryCall: () -> Unit,
        ): SearchStateViewHolder {
            val view = ItemLoadStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return SearchStateViewHolder(
                itemBinding = view,
                retryCall = retryCall
            )
        }

    }
}
