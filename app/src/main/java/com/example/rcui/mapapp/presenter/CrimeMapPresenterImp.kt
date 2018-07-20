package com.example.rcui.mapapp.presenter

import com.example.rcui.mapapp.model.CrimeRepository
import com.example.rcui.mapapp.model.CrimeRepositoryImp
import com.example.rcui.mapapp.model.Model
import com.example.rcui.mapapp.view.MapView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CrimeMapPresenterImp(private val view: MapView) : CrimeMapPresenter {

    override val defaultLatitude: Double = 52.629729
    override val defaultLongitude: Double = -1.131592
    private val repository: CrimeRepository = CrimeRepositoryImp()

    private var disposable: Disposable? = null

    private var crimes: ArrayList<Model.Crime>? = null

    override fun getCrimes(time: String, lat: Double, lng: Double) {
        destroyInstance()
        disposable = repository.getCrimes(time, lat, lng)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    crimes ->
                    run {
                        if (crimes.size > 0) {
                            this.crimes = crimes
                            view.showCrimesOnMap(crimes)
                        }
                        else
                            view.showNoCrimesFound()
                    }
                }, {
                    error -> view.showError(error.message.toString())
                })
    }

    override fun getCrimeAtIndex(index: Int?) {
        if (index != null)
            view.showCrimeDetails(crimes?.get(index))
    }

    override fun destroyInstance() {
        if(disposable != null && !disposable!!.isDisposed)
            disposable!!.dispose()
    }

}