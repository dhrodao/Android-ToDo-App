package es.urjc.alumnos.dhervas.androidmenu.listToDo

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.urjc.alumnos.dhervas.androidmenu.R
import es.urjc.alumnos.dhervas.androidmenu.addToDo.AddToDoDialogFragment
import es.urjc.alumnos.dhervas.androidmenu.model.ToDoItemDataModel
import es.urjc.alumnos.dhervas.androidmenu.util.ToDoRecyclerAdapter

class ToDoListFragment : Fragment() {
    private val fragmentTag : String = "ToDoListFragment"
    private val toDos = ArrayList<ToDoItemDataModel>()
    lateinit var todoAdapter : ToDoRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_do_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Test Data
        toDos.add(ToDoItemDataModel("Test 1", "SubTitle 1"))
        toDos.add(ToDoItemDataModel("Test 2", "SubTitle 2"))
        toDos.add(ToDoItemDataModel("Test 3", "SubTitle 3"))

        // Floating Button
        view.findViewById<View>(R.id.todo_floating_button)?.apply {
            setOnClickListener {
                Log.d(this@ToDoListFragment.fragmentTag, "onClick: AddTodoFloatingButton")

                // Display AddToDoFragmentDialog
                val dialogFragment = AddToDoDialogFragment()
                dialogFragment.show(parentFragmentManager, "AddToDoFragmentDialog")
            }
        }

        // RecyclerView
        todoAdapter = ToDoRecyclerAdapter(toDos)
        view.findViewById<RecyclerView>(R.id.todo_container)?.apply{
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(context)
        }

        // Dialog Fragment Result Listener
        setFragmentResultListener("submit") { requestKey, bundle ->
            when(requestKey){
                "submit" -> {
                    // Get Item
                    val item = bundle.getParcelable<ToDoItemDataModel>("item")

                    // Add Item to list
                    item?.let { todoAdapter.addItem(it) }
                }
            }
        }
    }
}