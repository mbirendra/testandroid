package com.birendra.lensdayshop.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Lensdays(
    @PrimaryKey
    val _id:String="",
    var pname:String?=null,
    var pprice:Int = 0,
    var pdesc:String?=null,
    var pimage:String?=null,
    var availableStock:Int = 0,
    var pBrand:String?=null,
    var discount:Int = 0,
    var newPrice:Int = 0,
    var discountedAmount:Int = 0,
    var category:String? = null,
    var onSale:Boolean?=null
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(pname)
        parcel.writeInt(pprice)
        parcel.writeString(pdesc)
        parcel.writeString(pimage)
        parcel.writeInt(availableStock)
        parcel.writeString(pBrand)
        parcel.writeInt(discount)
        parcel.writeInt(newPrice)
        parcel.writeInt(discountedAmount)
        parcel.writeString(category)
        parcel.writeValue(onSale)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Lensdays> {
        override fun createFromParcel(parcel: Parcel): Lensdays {
            return Lensdays(parcel)
        }

        override fun newArray(size: Int): Array<Lensdays?> {
            return arrayOfNulls(size)
        }
    }
}