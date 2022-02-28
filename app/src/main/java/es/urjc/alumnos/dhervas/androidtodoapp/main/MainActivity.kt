package es.urjc.alumnos.dhervas.androidtodoapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.fragment.app.*
import com.google.android.material.appbar.MaterialToolbar
import es.urjc.alumnos.dhervas.androidtodoapp.R
import es.urjc.alumnos.dhervas.androidtodoapp.util.OnQueryTextListener
import es.urjc.alumnos.dhervas.androidtodoapp.listToDo.ToDoListFragment

class MainActivity : AppCompatActivity() {
    private val tag : String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(tag, "onCreate")

        // AppBar
        val searchButtonAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(searchButtonAppBar)

        // ToDoList Fragment
        if (savedInstanceState == null){
            supportFragmentManager.commit {
                add(R.id.fragment_container, ToDoListFragment())
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)

        // Get adapter from recycler
        val todoRecyclerAdapter = (supportFragmentManager.findFragmentById(R.id.fragment_container)
                as ToDoListFragment).todoAdapter

        // Add SearchView to the
        val searchBarView = menu.findItem(R.id.nav_search).actionView as SearchView // Find searchbar
        searchBarView.setOnQueryTextListener(OnQueryTextListener(this, todoRecyclerAdapter)) // Set listener

        return true
    }
}