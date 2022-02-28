package es.urjc.alumnos.dhervas.androidmenu.addToDo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import es.urjc.alumnos.dhervas.androidmenu.R
import es.urjc.alumnos.dhervas.androidmenu.model.ToDoItemDataModel

class AddToDoDialogFragment : DialogFragment() {
    private val dialogTag : String = "AddToDoDialogFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, android.R.style.ThemeOverlay_Material_Dialog_Alert)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(dialogTag, "onCreateView")
        // Inflate Dialog View
        val view = inflater.inflate(R.layout.fragment_add_to_do_dialog, container, false)

        // Set dialog submit button listener
        view.findViewById<Button>(R.id.add_todo_button).setOnClickListener {
            //// Return data
            // Get text fields
            val title = view.findViewById<TextView>(R.id.add_todo_title_text).text.toString()
            val subTitle = view.findViewById<TextView>(R.id.add_todo_subtitle_text).text.toString()

            // Item to pass
            val item = ToDoItemDataModel(title, subTitle, false)

            // Bundle
            val bundle = Bundle()
            bundle.putParcelable("item", item)

            // Set fragment result
            setFragmentResult("submit", bundle)

            // Close dialog
            dismiss()
        }

        return view
    }
}