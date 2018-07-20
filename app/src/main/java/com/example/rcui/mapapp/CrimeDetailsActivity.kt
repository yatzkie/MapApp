package com.example.rcui.mapapp

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintSet
import android.support.transition.ChangeBounds
import android.support.transition.TransitionManager
import android.support.v7.app.AppCompatActivity
import android.view.animation.AnticipateOvershootInterpolator
import com.example.rcui.mapapp.databinding.ActivityCrimeDetailsBinding
import com.example.rcui.mapapp.databinding.ActivityCrimeDetailsInitialBinding
import com.example.rcui.mapapp.model.Model

/**
 * Created by rcui on 20/07/2018.
 */
class CrimeDetailsActivity: AppCompatActivity() {


    private var binding: ActivityCrimeDetailsInitialBinding? = null
    private var crime: Model.Crime? = null
    private val delay = 1000L
    companion object {
        private val KEY_CRIME: String = "crime"
        fun createIntent(context: Context, crime: Model.Crime?): Intent {
            var intent = Intent(context, CrimeDetailsActivity::class.java)
            intent.putExtra(KEY_CRIME, crime)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_crime_details_initial)

        if (intent.hasExtra(KEY_CRIME)) {
            crime = intent.getParcelableExtra(KEY_CRIME)
            binding?.crime = crime
            binding?.executePendingBindings()
        }

        startAnimation()
    }

    private fun startAnimation() {
        var handler = Handler()
        handler.postDelayed({
            val transition = ChangeBounds()
            transition.interpolator = AnticipateOvershootInterpolator(1f)
            transition.duration = BuildConfig.ANIMATION_DURATION
            TransitionManager.beginDelayedTransition(binding!!.layoutRoot, transition)

            var set = ConstraintSet()
            set.clone(this, R.layout.activity_crime_details)
            set.applyTo(binding!!.layoutRoot)
        }, BuildConfig.ANIMATION_DELAY)

    }
}