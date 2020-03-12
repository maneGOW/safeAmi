package com.manegow.safeami.util.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.manegow.safeami.database.Friends
import com.manegow.safeami.databinding.MapUserScrollLayoutBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1


class MapUserAdapter(val userTouchedListener: UserTouchedListener) :
    ListAdapter<UserDataItem, RecyclerView.ViewHolder>(UserDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addUserSubmitList(list: List<Friends>?) {
        adapterScope.launch {
            val users = when (list) {
                null -> listOf(UserDataItem.Header)
                else -> list.map { UserDataItem.UserItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(users)
            }
        }
    }

    class ViewHolder private constructor(val binding: MapUserScrollLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: UserTouchedListener, item: Friends) {
            binding.txtUsername.text = item.friendName
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MapUserScrollLayoutBinding.inflate(layoutInflater)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown ViewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder ->{
                val friendItem = getItem(position) as UserDataItem.UserItem
                holder.bind(userTouchedListener, friendItem.friend)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UserDataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is UserDataItem.UserItem -> ITEM_VIEW_TYPE_ITEM
        }
    }
}

class UserDiffCallback : DiffUtil.ItemCallback<UserDataItem>() {
    override fun areItemsTheSame(oldItem: UserDataItem, newItem: UserDataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserDataItem, newItem: UserDataItem): Boolean {
        return oldItem == newItem
    }
}

class UserTouchedListener(val clickListener: (friendId: String) -> Unit) {
    fun onClick(friend: Friends) = clickListener(friend.friendId.toString())
}

sealed class UserDataItem {
    data class UserItem(val friend: Friends) : UserDataItem() {
        override val id = friend.friendId
    }

    object Header : UserDataItem() {
        override val id = Long.MIN_VALUE
    }

    abstract val id: Long
}