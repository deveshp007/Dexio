package alpha.zechs.dexio.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    var title: String,
    var description: String,
    var priority: Priority,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)