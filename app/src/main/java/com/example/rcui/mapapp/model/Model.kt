package com.example.rcui.mapapp.model

import android.os.Parcel
import android.os.Parcelable

object Model {

    data class Crime(val category: String?,
                     val locationType: String?,
                     val location: Location?,
                     val context: String?,
                     val outcomeStatus: OutcomeStatus?,
                     val persistentId: String?,
                     val id: Int,
                     val locationSubtype: String?,
                     val month: String?): Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readParcelable(Location::class.java.classLoader),
                parcel.readString(),
                parcel.readParcelable(OutcomeStatus::class.java.classLoader),
                parcel.readString(),
                parcel.readInt(),
                parcel.readString(),
                parcel.readString())

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(category)
            parcel.writeString(locationType)
            parcel.writeParcelable(location, flags)
            parcel.writeString(context)
            parcel.writeParcelable(outcomeStatus, flags)
            parcel.writeString(persistentId)
            parcel.writeInt(id)
            parcel.writeString(locationSubtype)
            parcel.writeString(month)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Crime> {
            override fun createFromParcel(parcel: Parcel): Crime {
                return Crime(parcel)
            }

            override fun newArray(size: Int): Array<Crime?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class Location(val latitude: Double,
                        val longitude: Double,
                        val street: Street?): Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readDouble(),
                parcel.readDouble(),
                parcel.readParcelable(Street::class.java.classLoader))

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeDouble(latitude)
            parcel.writeDouble(longitude)
            parcel.writeParcelable(street, flags)

        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Location> {
            override fun createFromParcel(parcel: Parcel): Location {
                return Location(parcel)
            }

            override fun newArray(size: Int): Array<Location?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class OutcomeStatus(val category : String?,
                             val date: String?): Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString())

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(category)
            parcel.writeString(date)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<OutcomeStatus> {
            override fun createFromParcel(parcel: Parcel): OutcomeStatus {
                return OutcomeStatus(parcel)
            }

            override fun newArray(size: Int): Array<OutcomeStatus?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class Street(val id: Int, val name: String?): Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readString())

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(id)
            parcel.writeString(name)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Street> {
            override fun createFromParcel(parcel: Parcel): Street {
                return Street(parcel)
            }

            override fun newArray(size: Int): Array<Street?> {
                return arrayOfNulls(size)
            }
        }
    }
}