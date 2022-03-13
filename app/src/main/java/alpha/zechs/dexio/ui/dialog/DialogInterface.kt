package alpha.zechs.dexio.ui.dialog

import alpha.zechs.dexio.model.State
import alpha.zechs.dexio.model.Task
import android.widget.TextView
import com.google.android.material.button.MaterialButton

interface DialogInterface {

    fun onItemClick(
        state: State,
        task: Task
    )

    fun setupDialog(
        dialogTitle: TextView,
        priorityMenu: MaterialButton,
        submitButton: MaterialButton
    )
}