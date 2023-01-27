package org.akozlenko.gitstars.ui.search.adapter

import androidx.recyclerview.widget.DiffUtil
import org.akozlenko.gitstars.model.RepositoryInfo

object RepositoryInfoDiffCallback : DiffUtil.ItemCallback<RepositoryInfo>() {
    override fun areItemsTheSame(oldItem: RepositoryInfo, newItem: RepositoryInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RepositoryInfo, newItem: RepositoryInfo): Boolean {
        return oldItem == newItem
    }
}
