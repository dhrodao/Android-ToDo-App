package es.urjc.alumnos.dhervas.androidtodoapp.util

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.urjc.alumnos.dhervas.androidtodoapp.model.ToDoItemDataModel
import java.io.*

class DataManager(private val context : Context, private val filename : String) {
    private val tag : String = "DataManager"
    private val gson = Gson()

    fun saveToFile(item : ArrayList<ToDoItemDataModel>) {
        val file = File(context.getExternalFilesDir(null), filename)
        val fileOutputStream = file.outputStream()
        val outputStreamWriter = OutputStreamWriter(fileOutputStream)

        // Write
        val jsonItem : String? = gson.toJson(item)
        outputStreamWriter.write(jsonItem)

        // Close writer
        outputStreamWriter.close()
        fileOutputStream.close()
    }

    fun readFromFile() : ArrayList<ToDoItemDataModel>? {
        val file : File
        var fileInputStream : FileInputStream? = null
        var inputStreamReader : InputStreamReader? = null
        var arrayList : ArrayList<ToDoItemDataModel>? = null

        try {
            file = File(context.getExternalFilesDir(null), filename)
            fileInputStream = file.inputStream()
            inputStreamReader = InputStreamReader(fileInputStream)

            val jsonText = inputStreamReader.readText()
            Log.d(tag, jsonText)

            val objectType = object : TypeToken<ArrayList<ToDoItemDataModel>>(){}.type
            arrayList = gson.fromJson(jsonText, objectType)
        }catch (e : FileNotFoundException){
            Log.d(tag, "file not found!")
        }finally {
            // Close reader if open
            inputStreamReader?.close()
            fileInputStream?.close()
        }

        return arrayList
    }
}