package alpha.zechs.dexio.model

import androidx.annotation.Keep
import androidx.room.Entity

@Entity(
    tableName = "tasks",
    primaryKeys = ["id"]
)
@Keep
data class Task(
    var id: Int,
    var title: String,
    var description: String,
    var priority: Priority
)