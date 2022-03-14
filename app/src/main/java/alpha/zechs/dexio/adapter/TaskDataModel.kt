package alpha.zechs.dexio.adapter

import alpha.zechs.dexio.model.Priority
import alpha.zechs.dexio.model.TypeTask
import androidx.annotation.Keep


sealed class TaskDataModel {

    @Keep
    data class Header(
        val title: String
    ) : TaskDataModel()

    @Keep
    data class Task(
        val id: Int,
        var title: String,
        var description: String,
        var priority: Priority
    ) : TaskDataModel() {
        fun toTask() = alpha.zechs.dexio.model.Task(title, description, priority, id)
        val taskType = if (priority == Priority.DONE) TypeTask.DONE else TypeTask.TODO
    }
}