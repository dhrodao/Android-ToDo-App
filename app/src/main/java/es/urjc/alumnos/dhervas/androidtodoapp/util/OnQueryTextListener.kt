package es.urjc.alumnos.dhervas.androidtodoapp.util

import android.content.Context
import android.widget.SearchView
import android.widget.Toast

class OnQueryTextListener(private val context : Context, private val adapter : ToDoRecyclerAdapter) : SearchView.OnQueryTextListener{
    override fun onQueryTextSubmit(text: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(text: String?): Boolean {
        adapter.filter.filter(text)
        return true
    }
}