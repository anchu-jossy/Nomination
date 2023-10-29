package com.cube.cubeacademy.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.cube.cubeacademy.R
import com.cube.cubeacademy.databinding.ActivityCreateNominationBinding
import com.cube.cubeacademy.fragment.LeavePageDialogFragment
import com.cube.cubeacademy.lib.models.Nominee
import com.cube.cubeacademy.lib.viewmodels.CommonViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activity for creating a new nomination.
 */
@AndroidEntryPoint
class CreateNominationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateNominationBinding
    private var nameList: List<String> = emptyList()
    private lateinit var createNominationViewModel: CommonViewModel
    private var nomineeList: List<Nominee> = emptyList()
    private var selectedRadioText: String = ""
    private val orOperation = "OR"
    private val andOperation = "AND"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNominationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNominationViewModel = ViewModelProvider(this)[CommonViewModel::class.java]

        populateUI()

        // Back button click handler
        binding.backButton.setOnClickListener {
            if (isFormValid(orOperation)) {
                val fragment = LeavePageDialogFragment()
                fragment.show(supportFragmentManager, "leavePageDialog")
            } else {
                this.finish()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

        // Submit button initialization
        binding.submitButton.isEnabled = isFormValid(andOperation)


        // Submit button click handler
        binding.submitButton.setOnClickListener {

            val nomination = getUserSelectedNominee()
            createNominationViewModel.createNomination(
                nomineeId = nomination?.nomineeId ?: "",
                process = mapEmoticonToAPIValue(selectedRadioText),
                reason = binding.editReasoning.text.toString()
            )

        }

        // Observe created nomination response
        createNominationViewModel.createdNominationResponse.observe(this) {
            startActivity(
                Intent(
                    this, NominationSubmittedActivity::class.java
                )
            )

        }
    }

    // Get the selected nominee
    private fun getUserSelectedNominee(): Nominee? {
        val splitNames = binding.spinner.selectedItem.toString().split(" ")
        val firstName = splitNames[0] // Extract the first name
        val lastName = splitNames[1] // Extract the last name
        return nomineeList.find { it.firstName == firstName && it.lastName == lastName }
    }

    // Map emoji's to its corresponding API value .Sorry I haven't  got any
    // emojis mentioned in the figma file so mapped with this coloured ones
    private fun mapEmoticonToAPIValue(emoticon: String): String {
        return when (emoticon) {
            getString(R.string.very_unfair) -> getString(R.string.very_unfair_value)
            getString(R.string.unfair) -> getString(R.string.unfair_value)
            getString(R.string.not_sure) -> getString(R.string.not_sure_value)
            getString(R.string.fair) -> getString(R.string.fair_value)
            getString(R.string.very_fair) -> getString(R.string.very_fair_value)
            else -> getString(R.string.very_unfair_value) // Default value if no match is found
        }
    }

    // Set up radio button click listener
    private fun setUpRadioButton() {
        binding.layoutRadioButton.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            selectedRadioText = selectedRadioButton.text.toString()
            binding.submitButton.isEnabled = isFormValid(andOperation)
        }
    }

    // Format text and add asterisk
    private fun formatText() {
        colorText(binding.tvReasoning, getString(R.string.reasoning), 0, 1)
        colorText(binding.tvCubename, getString(R.string.cubes_name), 0, 1)
        colorText(binding.tvIsHow, getString(R.string.process_title), 24, 42)

    }

    // Add an asterisk to the text
    private fun colorText(view: TextView, text: String, start: Int, end: Int) {
        val builder = SpannableStringBuilder()
        builder.append(text)
        builder.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.pink)), start, end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        view.text = builder
    }

    // Check if the form is valid based on the selected operation (OR/AND)
    private fun isFormValid(opr: String): Boolean {
        val selectedItem = binding.spinner.selectedItem
        val isReasoningValid = binding.editReasoning.text.toString().isNotEmpty()
        val isSpinnerValid =
            selectedItem != null && selectedItem.toString() != getString(R.string.select_option)

        return when (opr) {
            "OR" -> isReasoningValid || isSpinnerValid
            "AND" -> isReasoningValid && isSpinnerValid
            else -> true
        }
    }

    // Populate the user interface
    private fun populateUI() {
        formatText()
        setUpRadioButton()
        setUpListeners()
        createNominationViewModel.getAllNominees()
        setUpDataObservers()
    }

    // Set up listeners for the spinner and edit text
    private fun setUpListeners() {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                binding.submitButton.isEnabled = isFormValid(andOperation)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.editReasoning.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                binding.submitButton.isEnabled = isFormValid(andOperation)
            }
        })
    }

    // Set up data observers for error messages and nominee responses
    private fun setUpDataObservers() {
        createNominationViewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
        createNominationViewModel.nomineeResponse.observe(this) { it ->
            nomineeList = it
            nameList =
                listOf(getString(R.string.select_option)) + it.map { it.firstName + " " + it.lastName }
            val adapter = ArrayAdapter(this, R.layout.custom_spinner, nameList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
        }
    }
}
