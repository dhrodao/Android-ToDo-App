package es.urjc.alumnos.dhervas.androidtodoapp.util

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeToDeleteCallback(private val adapter: ToDoRecyclerAdapter) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // Swipe Left to delete
        if (direction == ItemTouchHelper.LEFT){
            // Delete item from TodoList
            val position = viewHolder.adapterPosition
            adapter.deleteItem(position)
        }
    }
}