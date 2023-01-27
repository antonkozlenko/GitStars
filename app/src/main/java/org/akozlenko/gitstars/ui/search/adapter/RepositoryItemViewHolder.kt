package org.akozlenko.gitstars.ui.search.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.akozlenko.gitstars.databinding.ItemRepositoryInfoBinding
import org.akozlenko.gitstars.model.RepositoryInfo

class RepositoryItemViewHolder(
    itemBinding: ItemRepositoryInfoBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    private val repoNameView = itemBinding.repositoryName
    private val repoDescriptionView = itemBinding.repositoryDescription
    private val repoStarsView = itemBinding.repositoryStarsValue
    private val usernameView = itemBinding.repositoryOwnerUsername
    private val usernameIcon = itemBinding.usernameAvatar

    private lateinit var repoItem: RepositoryInfo

    fun bind(item: RepositoryInfo?) {
        Log.e("SearchVH", "BIND -> $item")
        item?.let {
            repoItem = it
            // Update views
            with(repoItem) {
                repoNameView.text = name
                repoDescriptionView.text = description ?: "N/A"
                repoStarsView.text = stars.toString()
                usernameView.text = owner.username
                // Load avatar
                Glide.with(itemView)
                    .load(owner.avatarUrl)
                    .into(usernameIcon)
            }
        }
    }

    companion object {

        fun create(parent: ViewGroup): RepositoryItemViewHolder {
            val view = ItemRepositoryInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return RepositoryItemViewHolder(view)
        }

    }
}
