package com.cube.cubeacademy.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cube.cubeacademy.databinding.ActivityMainBinding
import com.cube.cubeacademy.lib.adapters.NominationsRecyclerViewAdapter
import com.cube.cubeacademy.lib.di.Repository
import com.cube.cubeacademy.lib.models.Nomination
import com.cube.cubeacademy.lib.models.Nominee
import com.cube.cubeacademy.lib.viewmodels.CommonViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainActivityViewModel: CommonViewModel
    private var nomineeList: List<Nominee> = emptyList()
    private var nominationList: List<Nomination> = emptyList()

    @Inject
    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainActivityViewModel = ViewModelProvider(this)[CommonViewModel::class.java]
        populateUI()
        binding.createButton.setOnClickListener {
            this.finish()
            startActivity(Intent(this@MainActivity, CreateNominationActivity::class.java))
        }
    }

    // Function to populate the UI with data
    private fun populateUI() {
        mainActivityViewModel.getAllNominees()
        mainActivityViewModel.nomineeResponse.observe(this) { nomineeData ->
            nomineeList = nomineeData
            mainActivityViewModel.getAllNominations()
        }

        mainActivityViewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        mainActivityViewModel.nominationResponse.observe(this) { nominationsData ->
            nominationList = nominationsData

            // Combine nominations and nominees into pairs
            val pairList = nominationList.mapNotNull { nomination ->
                val nominee = nomineeList.find { it.nomineeId == nomination.nomineeId }
                nominee?.let { Pair(nomination, it) }
            }

            // Set up RecyclerView adapter
            val adapter = NominationsRecyclerViewAdapter(pairList)

            // Set RecyclerView layout manager and adapter
            binding.nominationsList.adapter = adapter
            binding.nominationsList.layoutManager = LinearLayoutManager(this)

            // Show/hide RecyclerView based on data availability
            binding.nominationsList.visibility =
                if (pairList.isNotEmpty()) View.VISIBLE else View.GONE
            binding.emptyContainer.visibility = if (pairList.isEmpty()) View.VISIBLE else View.GONE
        }
    }
}
