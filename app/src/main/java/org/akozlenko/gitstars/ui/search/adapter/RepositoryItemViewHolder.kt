package org.akozlenko.gitstars.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.akozlenko.gitstars.R
import org.akozlenko.gitstars.databinding.ItemRepositoryInfoBinding
import org.akozlenko.gitstars.model.RepositoryInfo

class RepositoryItemViewHolder(
    itemBinding: ItemRepositoryInfoBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    private val repoNameView = itemBinding.repositoryName
    private val repoDescriptionView = itemBinding.repositoryDescription
    private val repoStarsView = itemBinding.repositoryStarsValue
    private val usernameView = itemBinding.repositoryOwnerUsername
    private val usernameIcon = itemBinding.ownerAvatar

    private lateinit var repoItem: RepositoryInfo

    fun bind(item: RepositoryInfo?) {
        item?.let {
            repoItem = it
            // Update views
            with(repoItem) {
                // Repository name
                repoNameView.text = name
                // Description
                if (description?.isNotBlank() == true) {
                    repoDescriptionView.text = description
                } else {
                    repoDescriptionView.setText(R.string.missing_description_text)
                }
                // Repository stars
                repoStarsView.text = stars.toString()
                // Owner's username
                usernameView.text = owner.username
                // Owner's avatar
                Glide.with(itemView)
                    .load(owner.avatarUrl)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
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
