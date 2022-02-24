package es.urjc.alumnos.dhervas.androidmenu

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {
    private val toDos = ArrayList<ToDoItemDataModel>()
    init {
        // Test Data
        toDos.add(ToDoItemDataModel("Title", "SubTitle"))
        toDos.add(ToDoItemDataModel("Title", "SubTitle"))
        toDos.add(ToDoItemDataModel("Title", "SubTitle"))
    }
    private val todoAdapter = ToDoRecyclerAdapter(toDos)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchButtonAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(searchButtonAppBar)

        findViewById<RecyclerView>(R.id.todo_container)?.apply{
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)

        val searchBarView = menu.findItem(R.id.nav_search).actionView as SearchView // Find searchbar
        searchBarView.setOnQueryTextListener(OnQueryTextListener(this, todoAdapter)) // Set
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