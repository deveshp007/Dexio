package alpha.zechs.dexio.ui.dialog

import alpha.zechs.dexio.R
import alpha.zechs.dexio.model.Priority
import alpha.zechs.dexio.model.State
import alpha.zechs.dexio.model.Task
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.ListPopupWindow
import androidx.core.view.isInvisible
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class TaskDialog(
    context: Context,
    private val task: Task?,
    val onSubmitClickListener: (State, Task) -> Unit,
    val onDeleteClickListener: (Task) -> Unit
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_task)

        val dialogTitle = findViewById<TextView>(R.id.tv_title)
        val titleText = findViewById<TextInputLayout>(R.id.tf_title)
        val descriptionText = findViewById<TextInputLayout>(R.id.tf_description)
        val priorityMenu = findViewById<MaterialButton>(R.id.priority_menu)
        val submitButton = findViewById<MaterialButton>(R.id.btn_submit)
        val deleteButton = findViewById<MaterialButton>(R.id.btn_delete)

        setupPriorityMenu(priorityMenu)

        task?.let {
            titleText.editText?.setText(it.title)
            descriptionText.editText?.setText(it.description)
            priorityMenu.text = it.priority.name
            deleteButton.isInvisible = false
            deleteButton.setOnClickListener {
                onDeleteClickListener(task)
            }
        }

        dialogTitle.text = context.getString(
            if (task == null) R.string.add_task else R.string.update_task
        )

        submitButton.text = context.getString(
            if (task == null) R.string.add else R.string.update
        )

        submitButton.setOnClickListener {
            val taskTitle = titleText.editText?.text.toString()
            if (taskTitle == "") {
                Toast.makeText(
                    context,
                    context.getString(R.string.title_required), Toast.LENGTH_SHORT
                ).show()
            } else {
                val priority = enumValueOf<Priority>(priorityMenu.text.toString())
                val state = when (dialogTitle.text) {
                    context.getString(R.string.add_task) -> State.ADD
                    context.getString(R.string.update_task) -> State.UPDATE
                    else -> throw IllegalStateException("Invalid dialog state")
                }

                val task = Task(
                    title = taskTitle,
                    description = descriptionText.editText?.text.toString(),
                    priority = priority,
                    id = task?.id
                )

                onSubmitClickListener(state, task)
            }
        }
    }

    private fun setupPriorityMenu(priorityMenu: MaterialButton) {
        val listPriority = Priority.values().map { it.name }

        val listPopupPriority = ListPopupWindow(
            context, null,
            R.attr.listPopupWindowStyle
        )
        val adapter = ArrayAdapter(
            context,
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