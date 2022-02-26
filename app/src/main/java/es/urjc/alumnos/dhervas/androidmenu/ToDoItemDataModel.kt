package es.urjc.alumnos.dhervas.androidmenu

import android.os.Parcel
import android.os.Parcelable

data class ToDoItemDataModel(val title: String, val subTitle: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(subTitle)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ToDoItemDataModel> {
        override fun createFromParcel(parcel: Parcel): ToDoItemDataModel {
            return ToDoItemDataModel(parcel)
        }

        override fun newArray(size: Int): Array<ToDoItemDataModel?> {
            return arrayOfNulls(size)
        }
    }
}