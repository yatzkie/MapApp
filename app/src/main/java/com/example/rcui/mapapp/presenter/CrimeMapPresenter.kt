package com.example.rcui.mapapp.presenter

interface CrimeMapPresenter {

    val defaultLatitude: Double
    val defaultLongitude: Double

    fun getCrimes(time : String, lat: Double, lng: Double)
    fun destroyInstance()
    fun getCrimeAtIndex(index: Int?)
}