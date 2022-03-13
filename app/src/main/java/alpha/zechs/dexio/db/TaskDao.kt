package alpha.zechs.dexio.db

import alpha.zechs.dexio.model.Priority
import alpha.zechs.dexio.model.Task
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMovie(media: Task): Long

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT EXISTS(SELECT * FROM tasks WHERE id = :id)")
    fun getTask(id: Int): LiveData<Boolean>

    @Query("SELECT * FROM tasks")
    fun getTasks(): LiveData<List<Task>>

    @Query("UPDATE tasks SET priority = :priority WHERE id = :id")
    suspend fun setPriority(priority: Priority, id: Int)

}