package alpha.zechs.dexio.db

import alpha.zechs.dexio.model.Priority
import alpha.zechs.dexio.model.Task
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTask(media: Task): Long

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks")
    fun getTasks(): LiveData<List<Task>>

    @Query("UPDATE tasks SET priority = :priority WHERE id = :id")
    suspend fun setPriority(priority: Priority, id: Int)

    @Query(
        "UPDATE tasks " +
                "SET  title = :title, description = :description, priority = :priority" +
                " WHERE id = :id"
    )
    suspend fun updateTask(id: Int, title: String, description: String, priority: Priority)
}