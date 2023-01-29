package org.akozlenko.gitstars.ui.search.adapters.results

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import org.akozlenko.gitstars.model.RepositoryInfo
import org.akozlenko.gitstars.ui.search.adapters.results.RepositoryInfoDiffCallback
import org.akozlenko.gitstars.ui.search.adapters.results.RepositoryItemViewHolder

class SearchRepositoriesAdapter : PagingDataAdapter<
        RepositoryInfo,
        RepositoryItemViewHolder
        >(RepositoryInfoDiffCallback)
{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepositoryItemViewHolder {
        return RepositoryItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RepositoryItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
