package com.cube.cubeacademy.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cube.cubeacademy.MainApplication
import com.cube.cubeacademy.databinding.ActivityNominationSubmittedBinding

class NominationSubmittedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNominationSubmittedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflating the layout for this activity
        binding = ActivityNominationSubmittedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Populating the user interface
        populateUI()
    }

    /**
     * Populates the user interface with appropriate data and sets click listeners.
     */
    private fun populateUI() {
        // Check a flag in shared preferences to determine the visibility of the back button
        binding.backButton.visibility = if (MainApplication.getFlagFromSharedPreferences(context = this)) View.GONE else View.VISIBLE

        // Set a click listener for the "Create Nomination" button
        binding.createButton.setOnClickListener {
            // Save a flag to shared preferences to hide back button after user's first submission and start the CreateNominationActivity
            MainApplication.saveFlagToSharedPreferences(context = this, flag = true)
            this.finish()

            startActivity(Intent(this, CreateNominationActivity::class.java))
        }
        binding.backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))

        }
    }
}
