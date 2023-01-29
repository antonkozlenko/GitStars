package org.akozlenko.gitstars.ui.search.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import org.akozlenko.gitstars.model.RepositoryInfo

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
