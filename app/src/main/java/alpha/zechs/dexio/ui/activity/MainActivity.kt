package alpha.zechs.dexio.ui.activity

import alpha.zechs.dexio.databinding.ActivityMainBinding
import alpha.zechs.dexio.db.TodoDatabase
import alpha.zechs.dexio.ui.MainViewModel
import alpha.zechs.dexio.ui.MainViewModelProviderFactory
import alpha.zechs.dexio.ui.dialog.TaskDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints
import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private var _taskDialog: TaskDialog? = null
    private val taskDialog get() = _taskDialog!!

    override fun onCreate(savedInstanceState: Bundle?) {

        mainViewModel = ViewModelProvider(
            this,
            MainViewModelProviderFactory(TodoDatabase(this))
        )[MainViewModel::class.java]

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
    }

    private fun setupToolbar() {
        mainViewModel.today.observe(this@MainActivity) {
            binding.apply {
                tvDate.text = it.date.toString()
                tvDay.text = it.day
                tvMonthYear.text = it.monthYear
            }
        }
        binding.btnNewTask.setOnClickListener { showTaskDialog() }
    }

    private fun showTaskDialog() {
        if (_taskDialog == null) _taskDialog = TaskDialog(this)
        taskDialog.show()

        taskDialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(
                Constraints.LayoutParams.MATCH_PARENT,
                Constraints.LayoutParams.WRAP_CONTENT
            )
        }

    }
}