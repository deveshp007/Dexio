package alpha.zechs.dexio.ui

import alpha.zechs.dexio.db.TodoDatabase
import alpha.zechs.dexio.model.Priority
import alpha.zechs.dexio.model.Task
import alpha.zechs.dexio.model.Today
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(
    private val todoDatabase: TodoDatabase
) : ViewModel() {

    private val time = System.currentTimeMillis()
    private val dateFormat = SimpleDateFormat("dd", Locale.getDefault())
    private val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
    private val monthYearFormat = SimpleDateFormat("MMM, yyyy", Locale.getDefault())

    val today: MutableLiveData<Today> by lazy { MutableLiveData<Today>() }

    init {
        today.value = Today(
            date = dateFormat.format(time).toInt(),
            day = dayFormat.format(time),
            monthYear = monthYearFormat.format(time)
        )
    }

    fun addTask(task: Task) = viewModelScope.launch {
        todoDatabase.getTaskDao().upsertMovie(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        todoDatabase.getTaskDao().deleteTask(task)
    }

    fun getTask(id: Int) = todoDatabase.getTaskDao().getTask(id)

    fun getTasks() = todoDatabase.getTaskDao().getTasks()

    fun setPriority(priority: Priority, id: Int) = viewModelScope.launch {
        todoDatabase.getTaskDao().setPriority(priority, id)
    }

}