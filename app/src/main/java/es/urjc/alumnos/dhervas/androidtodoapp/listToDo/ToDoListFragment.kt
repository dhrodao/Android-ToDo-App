package es.urjc.alumnos.dhervas.androidtodoapp.listToDo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.urjc.alumnos.dhervas.androidtodoapp.R
import es.urjc.alumnos.dhervas.androidtodoapp.addToDo.AddToDoDialogFragment
import es.urjc.alumnos.dhervas.androidtodoapp.model.ToDoItemDataModel
import es.urjc.alumnos.dhervas.androidtodoapp.util.DataManager
import es.urjc.alumnos.dhervas.androidtodoapp.util.SwipeToDeleteCallback
import es.urjc.alumnos.dhervas.androidtodoapp.util.ToDoRecyclerAdapter

class ToDoListFragment : Fragment() {
    private val fragmentTag : String = "ToDoListFragment"
    private val toDos = ArrayList<ToDoItemDataModel>()
    lateinit var todoAdapter : ToDoRecyclerAdapter

    private val filename = "todoitems.json"

    private lateinit var dataManager : DataManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_do_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Load data from JSON
        loadLocalData()

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
        todoAdapter = ToDoRecyclerAdapter(this, toDos)
        view.findViewById<RecyclerView>(R.id.todo_container)?.apply{
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(context)

            val swipeToDeleteCallback = SwipeToDeleteCallback(todoAdapter)
            val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
            itemTouchHelper.attachToRecyclerView(this)
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

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Set data manager
        dataManager = DataManager(context, filename)
    }

    override fun onStop() {
        super.onStop()

        Log.d(fragmentTag, "onStop")

        // Save ToDoList in external storage
        val itemList = todoAdapter.getAllItems()
        dataManager.saveToFile(itemList)
    }

    // Load saved data (in json file)
    private fun loadLocalData() {
        Log.d(fragmentTag, "loadLocalData")

        val items = dataManager.readFromFile()
        items?.let {
            toDos.addAll(it)
        }
    }
}