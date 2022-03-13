package alpha.zechs.dexio.ui.dialog

import alpha.zechs.dexio.R
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window


class TaskDialog(
    context: Context,
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_task)
    }

}