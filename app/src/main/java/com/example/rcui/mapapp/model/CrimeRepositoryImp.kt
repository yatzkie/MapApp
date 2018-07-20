package com.example.rcui.mapapp.model

import com.example.rcui.mapapp.network.APIService
import io.reactivex.Observable

/**
 * Created by rcui on 19/07/2018.
 */
class CrimeRepositoryImp: CrimeRepository {

    private val apiService by lazy {
        APIService.create()
    }

    override fun getCrimes(time: String, lat: Double, lng: Double): Observable<ArrayList<Model.Crime>> {
        return apiService.getCrimes(time, lat, lng)
    }

}