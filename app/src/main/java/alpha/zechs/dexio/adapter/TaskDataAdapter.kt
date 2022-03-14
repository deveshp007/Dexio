package alpha.zechs.dexio.adapter

import alpha.zechs.dexio.R
import alpha.zechs.dexio.databinding.ItemHeaderBinding
import alpha.zechs.dexio.databinding.ItemTaskBinding
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class TaskDataAdapter(
    private val context: Context,
    val checkTodoListener: (TaskDataModel.Task) -> Unit,
    val itemTodoListener: (TaskDataModel.Task) -> Unit
) : RecyclerView.Adapter<TaskDataViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<TaskDataModel>() {

        override fun areItemsTheSame(
            oldItem: TaskDataModel,
            newItem: TaskDataModel
        ): Boolean = when {
            oldItem is TaskDataModel.Header && newItem
                    is TaskDataModel.Header && oldItem.title == newItem.title
            -> true
            oldItem is TaskDataModel.Task && newItem
                    is TaskDataModel.Task && oldItem.id == newItem.id
            -> true
            else -> false
        }

        override fun areContentsTheSame(
            oldItem: TaskDataModel, newItem: TaskDataModel
        ) = oldItem == newItem

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): TaskDataViewHolder {
        return when (viewType) {
            R.layout.item_task -> TaskDataViewHolder.TaskViewHolder(
                ItemTaskBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                ), this, context
            )
            R.layout.item_header -> TaskDataViewHolder.HeaderViewHolder(
                ItemHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                ), this, context
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: TaskDataViewHolder, position: Int) {
        return when (val task = differ.currentList[position]) {
            is TaskDataModel.Task -> (holder as TaskDataViewHolder.TaskViewHolder).bind(task)
            is TaskDataModel.Header -> (holder as TaskDataViewHolder.HeaderViewHolder).bind(task)
        }
    }

    override fun getItemCount() = differ.currentList.size

    override fun getItemViewType(position: Int) = when (differ.currentList[position]) {
        is TaskDataModel.Header -> R.layout.item_header
        is TaskDataModel.Task -> R.layout.item_task
    }
}