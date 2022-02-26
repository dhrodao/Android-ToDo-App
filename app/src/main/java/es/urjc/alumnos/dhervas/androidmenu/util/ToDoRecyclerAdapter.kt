package es.urjc.alumnos.dhervas.androidmenu.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.urjc.alumnos.dhervas.androidmenu.R
import es.urjc.alumnos.dhervas.androidmenu.model.ToDoItemDataModel

class ToDoRecyclerAdapter(private val dataSet : ArrayList<ToDoItemDataModel>) : RecyclerView.Adapter<ToDoRecyclerAdapter.ToDoItemViewHolder>(), Filterable {
    private val backupDataSet = ArrayList(dataSet)
    private val filter = ToDoTextFilter(this)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoItemViewHolder {
        val todoView = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)

        return ToDoItemViewHolder(todoView)
    }

    override fun onBindViewHolder(holder: ToDoItemViewHolder, position: Int) {
        holder.setData(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class ToDoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView : TextView = itemView.findViewById(R.id.item_title)
        private val subTitleView : TextView = itemView.findViewById(R.id.item_sub_title)

        fun setData(todoItem : ToDoItemDataModel){
            titleView.text = todoItem.title
            subTitleView.text = todoItem.subTitle
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