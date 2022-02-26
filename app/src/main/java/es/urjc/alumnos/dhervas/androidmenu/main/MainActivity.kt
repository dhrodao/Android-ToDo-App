package es.urjc.alumnos.dhervas.androidmenu.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.*
import com.google.android.material.appbar.MaterialToolbar
import es.urjc.alumnos.dhervas.androidmenu.*
import es.urjc.alumnos.dhervas.androidmenu.addToDo.AddToDoDialogFragment
import es.urjc.alumnos.dhervas.androidmenu.model.ToDoItemDataModel
import es.urjc.alumnos.dhervas.androidmenu.util.OnQueryTextListener
import es.urjc.alumnos.dhervas.androidmenu.listToDo.ToDoListFragment
import es.urjc.alumnos.dhervas.androidmenu.util.ToDoRecyclerAdapter

class MainActivity : AppCompatActivity() {
    private val tag : String = "MainActivity"
    private val toDos = ArrayList<ToDoItemDataModel>()
    private val todoAdapter = ToDoRecyclerAdapter(toDos)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // AppBar
        val searchButtonAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(searchButtonAppBar)


        // Floating Button
        findViewById<View>(R.id.todo_floating_button)?.apply {
            setOnClickListener {
                Log.d(this@MainActivity.tag, "onClick: AddTodoFloatingButton")

                // Display AddToDoFragmentDialog
                val dialogFragment = AddToDoDialogFragment()
                dialogFragment.show(supportFragmentManager, "AddToDoFragmentDialog")
            }
        }

        // ToDoList Fragment
        if (savedInstanceState == null){
            supportFragmentManager.commit {
                add(R.id.fragment_container, ToDoListFragment())
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)

        // Get ToDoListFragment
        val todoRecyclerAdapter = (supportFragmentManager.findFragmentById(R.id.fragment_container)
                as ToDoListFragment).todoAdapter

        // Add SearchView
        val searchBarView = menu.findItem(R.id.nav_search).actionView as SearchView // Find searchbar
        searchBarView.setOnQueryTextListener(OnQueryTextListener(this, todoRecyclerAdapter)) // Set

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_search -> {
                Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}