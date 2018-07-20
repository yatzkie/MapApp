package com.example.rcui.mapapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintSet
import android.support.design.widget.Snackbar
import android.support.transition.ChangeBounds
import android.support.transition.TransitionManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.OvershootInterpolator
import com.example.rcui.mapapp.model.Model
import com.example.rcui.mapapp.presenter.CrimeMapPresenter
import com.example.rcui.mapapp.presenter.CrimeMapPresenterImp
import com.example.rcui.mapapp.view.MapView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.twinkle94.monthyearpicker.picker.YearMonthPickerDialog
import kotlinx.android.synthetic.main.activity_map.*
import java.util.*
import java.text.SimpleDateFormat


/**
 * Created by rcui on 18/07/2018.
 */
class MapActivity : AppCompatActivity(), OnMapReadyCallback, MapView {

    private lateinit var googleMap : GoogleMap
    private lateinit var presenter: CrimeMapPresenter
    private var calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("yyyy-MM")
    private var delay = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        (fragment_map as SupportMapFragment).getMapAsync(this)
        input_timeline.setOnClickListener {
            showDatePicker()
        }
        presenter = CrimeMapPresenterImp(this)
        calendar.set(Calendar.YEAR, 2017)

    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        var defaultLocation = LatLng(presenter.defaultLatitude, presenter.defaultLongitude)
        var cameraPosition = CameraPosition.builder()
                .target(defaultLocation)
                .zoom(15f)
                .bearing( 0f)
                .tilt( 0f)
                .build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),null)
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMap.uiSettings.isZoomControlsEnabled = true

        googleMap.setOnCameraIdleListener {
            getCrimes()
        }
        googleMap.setOnMarkerClickListener {
            marker -> onMarkerClicked(marker)
        }
        showInputField()

    }

    private fun showInputField() {
        var handler = Handler()
        handler.postDelayed({
            val transition = ChangeBounds()
            transition.interpolator = AnticipateOvershootInterpolator(1f)
            transition.duration = BuildConfig.ANIMATION_DURATION
            TransitionManager.beginDelayedTransition(layout_root, transition)

            var set = ConstraintSet()
            set.clone(layout_root)
            set.connect(input_timeline.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            set.connect(input_timeline.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            set.connect(input_timeline.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            set.clear(input_timeline.id, ConstraintSet.BOTTOM)
            set.applyTo(layout_root)
        }, BuildConfig.ANIMATION_DELAY)

    }

    private fun getCrimes() {
        googleMap.clear()
        val latLng = googleMap.cameraPosition.target
        var timeline = dateFormat.format(calendar.time)
        presenter.getCrimes(timeline, latLng.latitude, latLng.longitude)
    }

    private fun onMarkerClicked(marker: Marker?): Boolean {
        presenter.getCrimeAtIndex(marker!!.title.toInt())
        return true
    }

    private fun showDatePicker() {
        var dialog = YearMonthPickerDialog(this,
                YearMonthPickerDialog.OnDateSetListener { year, month ->
                    run {
                        setTimeline(year, month)
                        getCrimes()
                        updateViews()
                    }
                },
                R.style.MyDialogTheme)
        dialog.show()
    }

    private fun updateViews() {
        var timeline = dateFormat.format(calendar.time)
        input_timeline.text = timeline
    }

    private fun setTimeline(year: Int, month: Int) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
    }

    override fun showCrimesOnMap(crimes: ArrayList<Model.Crime>) {
        val icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_pin)
        crimes.mapIndexed { index, crime ->
            if (crime.location != null)
                    MarkerOptions()
                            .position(LatLng(crime.location.latitude, crime.location.longitude))
                            .icon(icon)
                            .title(index.toString())
            else
                null
        }
        .forEach {
            if(it != null)
                googleMap.addMarker(it)
        }
    }

    override fun showNoCrimesFound() {
        showSnackbar("No Crimes found")
    }

    override fun showCrimeDetails(crime: Model.Crime?) {
        var intent = CrimeDetailsActivity.createIntent(this, crime)
        startActivity(intent)
    }

    override fun showError(message: String) {
        showSnackbar(message)
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(layout_root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroyInstance()
    }
}