package alpha.zechs.dexio.ui.dialog

import alpha.zechs.dexio.R
import alpha.zechs.dexio.model.Priority
import alpha.zechs.dexio.model.State
import alpha.zechs.dexio.model.Task
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class TaskDialog(
    context: Context,
    private val taskId: Int?,
    private val dialogInterface: DialogInterface
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

        dialogInterface.setupDialog(dialogTitle, priorityMenu, submitButton)

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
                    id = taskId
                )
                dialogInterface.onItemClick(state, task)
            }
        }
    }

}