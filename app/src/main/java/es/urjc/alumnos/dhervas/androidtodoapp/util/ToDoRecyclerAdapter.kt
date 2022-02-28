package es.urjc.alumnos.dhervas.androidtodoapp.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import es.urjc.alumnos.dhervas.androidtodoapp.R
import es.urjc.alumnos.dhervas.androidtodoapp.model.ToDoItemDataModel


class ToDoRecyclerAdapter(private val fragment : Fragment, private val dataSet : ArrayList<ToDoItemDataModel>) : RecyclerView.Adapter<ToDoRecyclerAdapter.ToDoItemViewHolder>(), Filterable {
    private val backupDataSet = ArrayList(dataSet)
    private val filter = ToDoTextFilter(this)
    private var recentlyRemovedItem : ToDoItemDataModel? = null
    private var recentlyRemovedItemPositionBackup : Int? = null
    private var recentlyRemovedItemPositionDataSet : Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoItemViewHolder {
        val todoView = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)

        return ToDoItemViewHolder(todoView)
    }

    override fun onBindViewHolder(holder: ToDoItemViewHolder, position: Int) {
        // Handler for when the isChecked status changes
        holder.itemView.findViewById<CheckBox>(R.id.item_checkbox).setOnCheckedChangeListener { _, b ->
            backupDataSet.elementAt(position).isChecked = b
        }

        holder.setData(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class ToDoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView : TextView = itemView.findViewById(R.id.item_title)
        private val subTitleView : TextView = itemView.findViewById(R.id.item_sub_title)
        private val checkBox : CheckBox = itemView.findViewById(R.id.item_checkbox)

        fun setData(todoItem : ToDoItemDataModel){
            titleView.text = todoItem.title
            subTitleView.text = todoItem.subTitle
            checkBox.isChecked = todoItem.isChecked
        }
    }

    override fun getFilter(): Filter {
        return filter
    }

    fun addItem(item : ToDoItemDataModel) {
        backupDataSet.add(item)
        dataSet.add(item)
        notifyItemInserted(dataSet.size - 1)
    }

    fun getAllItems() : ArrayList<ToDoItemDataModel> {
        return backupDataSet
    }

    fun deleteItem(position : Int) {
        // Save deleted item
        recentlyRemovedItem = dataSet[position]
        recentlyRemovedItemPositionBackup = backupDataSet.indexOf(recentlyRemovedItem)
        recentlyRemovedItemPositionDataSet = dataSet.indexOf(recentlyRemovedItem)

        // Delete item from lists
        dataSet.remove(recentlyRemovedItem)
        backupDataSet.removeAt(position)

        // Notify dataset changed
        notifyItemRemoved(recentlyRemovedItemPositionDataSet!!)

        // Show SnackBar and Undo message
        showUndoMessage()
    }

    private fun showUndoMessage() {
        fragment.view?.findViewById<View>(R.id.todo_list_fragment)?.let {
            val snackBar = Snackbar.make(it, R.string.snack_bar_text, Snackbar.LENGTH_LONG)
            snackBar.setAction(R.string.snack_bar_undo_text) { undoDelete() }
            snackBar.show()
        }
    }

    private fun undoDelete(){
        recentlyRemovedItem?.let {
            // Insert item
            backupDataSet.add(recentlyRemovedItemPositionBackup!!, recentlyRemovedItem)
            dataSet.add(recentlyRemovedItemPositionDataSet!!, recentlyRemovedItem!!)

            // Notify to refresh view
            notifyItemInserted(recentlyRemovedItemPositionDataSet!!)
        }
    }

    class ToDoTextFilter(private val parent : ToDoRecyclerAdapter) : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredArray : ArrayList<ToDoItemDataModel> = ArrayList()

            if (constraint == null || constraint.isEmpty()){
                filteredArray.addAll(parent.backupDataSet)
            } else {
                val pattern = constraint.toString().lowercase().trim() // Trim for deleting special chars (\n, \t, etc)
                for (item in parent.backupDataSet){
                    if(item.title.lowercase().contains(pattern)){
                        filteredArray.add(item)
                    }
                }
            }

            val result = FilterResults()
            result.values = filteredArray
            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            parent.dataSet.clear()
            parent.dataSet.addAll(results?.values as ArrayList<ToDoItemDataModel>)
            parent.notifyDataSetChanged()
        }

    }

}