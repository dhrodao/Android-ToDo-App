package es.urjc.alumnos.dhervas.androidtodoapp.model

import android.os.Parcel
import android.os.Parcelable

data class ToDoItemDataModel(var title: String, var subTitle: String, var isChecked : Boolean) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(subTitle)
        parcel.writeByte(if (isChecked) 1 else 0)
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