package alpha.zechs.dexio.ui

import alpha.zechs.dexio.db.TodoDatabase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class MainViewModelProviderFactory(
    private val todoDatabase: TodoDatabase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(todoDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }

}