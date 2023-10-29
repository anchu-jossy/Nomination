package com.cube.cubeacademy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cube.cubeacademy.databinding.LayoutBackpressConfirmationBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// Declare a binding variable to access the layout components
private lateinit var binding: LayoutBackpressConfirmationBinding

class LeavePageDialogFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = LayoutBackpressConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set a click listener for the "Back" button
        binding.backButton.setOnClickListener {
            // Dismiss the dialog and finish the associated activity
            this.dismiss()
            activity?.finish()
        }

        // Set a click listener for the "Cancel" button
        binding.cancelButton.setOnClickListener {
            // Dismiss the dialog without further action
            this.dismiss()
        }
    }
}
