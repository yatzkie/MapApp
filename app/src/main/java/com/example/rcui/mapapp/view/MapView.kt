package com.example.rcui.mapapp.view

import com.example.rcui.mapapp.model.Model

/**
 * Created by ronaldphillipcui on 19/07/2018.
 */
interface MapView {

    fun showCrimesOnMap(crimes: ArrayList<Model.Crime>)
    fun showNoCrimesFound()
    fun showError(message: String)
    fun showCrimeDetails(crime: Model.Crime?)
}