package com.example.rcui.mapapp.model

import io.reactivex.Observable

/**
 * Created by rcui on 19/07/2018.
 */
interface CrimeRepository {
    fun getCrimes(time: String, lat: Double, lng: Double): Observable<ArrayList<Model.Crime>>
}