package alpha.zechs.dexio.ui.activity

import alpha.zechs.dexio.R
import alpha.zechs.dexio.databinding.ActivityMainBinding
import alpha.zechs.dexio.db.TodoDatabase
import alpha.zechs.dexio.model.Priority
import alpha.zechs.dexio.model.State
import alpha.zechs.dexio.model.Task
import alpha.zechs.dexio.ui.MainViewModel
import alpha.zechs.dexio.ui.MainViewModelProviderFactory
import alpha.zechs.dexio.ui.dialog.DialogInterface
import alpha.zechs.dexio.ui.dialog.TaskDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ListPopupWindow
import androidx.constraintlayout.widget.Constraints
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton


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
        mainViewModel.getToday()

        mainViewModel.today.observe(this) {
            binding.apply {
                tvDate.text = it.date.toString()
                tvDay.text = it.day
                tvMonthYear.text = it.monthYear
            }
        }

        binding.btnNewTask.setOnClickListener {
            showTaskDialog(null, getString(R.string.add_task))
        }
    }

    private fun showTaskDialog(taskId: Int?, title: String) {

        val dialogInterface = object : DialogInterface {
            override fun onItemClick(state: State, task: Task) {
                when (state) {
                    State.ADD -> mainViewModel.addTask(task)
                    State.UPDATE -> mainViewModel.updateTask(task)
                }
                taskDialog.dismiss()
                _taskDialog = null
            }

            override fun setupDialog(
                dialogTitle: TextView,
                priorityMenu: MaterialButton,
                submitButton: MaterialButton
            ) {
                dialogTitle.text = title
                submitButton.text = when (title) {
                    getString(R.string.add_task) -> "Add"
                    getString(R.string.update_task) -> "Update"
                    else -> throw IllegalStateException("Invalid dialog state")
                }
                setupPriorityMenu(priorityMenu)
            }
        }

        if (_taskDialog == null) {
            _taskDialog = TaskDialog(this, taskId, dialogInterface)
        }
        taskDialog.show()

        taskDialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(
                Constraints.LayoutParams.MATCH_PARENT,
                Constraints.LayoutParams.WRAP_CONTENT
            )
        }

        taskDialog.setOnDismissListener {
            _taskDialog = null
        }
    }

    private fun setupPriorityMenu(priorityMenu: MaterialButton) {
        val listPriority = Priority.values().map { it.name }

        val listPopupPriority = ListPopupWindow(
            this, null,
            R.attr.listPopupWindowStyle
        )
        val adapter = ArrayAdapter(
            this,
            R.layout.item_dropdown,
            listPriority
        )

        listPopupPriority.apply {
            isModal = true
            anchorView = priorityMenu
            setAdapter(adapter)

            listPopupPriority.setOnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
                priorityMenu.text = listPriority[position]
                listPopupPriority.dismiss()
            }
        }

        priorityMenu.setOnClickListener { listPopupPriority.show() }
    }
}