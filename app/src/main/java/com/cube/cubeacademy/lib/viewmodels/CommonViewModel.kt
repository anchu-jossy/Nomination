package com.cube.cubeacademy.lib.viewmodels

import ApiException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cube.cubeacademy.lib.di.Repository
import com.cube.cubeacademy.lib.models.Nomination
import com.cube.cubeacademy.lib.models.Nominee
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommonViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {
    // LiveData to hold the response of Nominee data
    private val _nomineeResponse = MutableLiveData<List<Nominee>>()
    val nomineeResponse: LiveData<List<Nominee>> = _nomineeResponse

    // LiveData to hold any error messages
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    // LiveData to hold the response of Nomination data
    private val _nominationResponse = MutableLiveData<List<Nomination>>()
    val nominationResponse: LiveData<List<Nomination>> = _nominationResponse

    // LiveData to hold the response of a newly created Nomination
    private val _createdNominationResponse = MutableLiveData<Nomination>()
    val createdNominationResponse: LiveData<Nomination> = _createdNominationResponse

    /**
     * Fetches the list of nominees.
     */
    fun getAllNominees() {
        viewModelScope.launch {
            try {
                val response = repository.getAllNominees()
                _nomineeResponse.value = response
            } catch (e: ApiException) {
                _errorMessage.value = "API Error: ${e.message}"
                // Handle custom ApiException, e.g., show an error message
                e.printStackTrace()
            } catch (e: Exception) {
                _errorMessage.value = "Network Error: ${e.message}"
                e.printStackTrace()
            }
        }
    }

    /**
     * Fetches the list of nominations.
     */
    fun getAllNominations() {
        viewModelScope.launch {
            try {
                val response = repository.getAllNominations()
                _nominationResponse.value = response
            } catch (e: ApiException) {
                _errorMessage.value = "API Error: ${e.message}"
                // Handle custom ApiException, e.g., show an error message
                e.printStackTrace()
            } catch (e: Exception) {
                _errorMessage.value = "Network Error: ${e.message}"
                e.printStackTrace()
            }
        }
    }

    /**
     * Creates a new nomination for a given nominee with the specified process and reason.
     */
    fun createNomination(nomineeId: String, process: String, reason: String) {
        viewModelScope.launch {
            try {
                val response = repository.createNomination(nomineeId = nomineeId, process = process, reason = reason)
                _createdNominationResponse.value = response
            } catch (e: ApiException) {
                _errorMessage.value = "API Error: ${e.message}"
                // Handle custom ApiException, e.g., show an error message
                e.printStackTrace()
            } catch (e: Exception) {
                _errorMessage.value = "Network Error: ${e.message}"
                e.printStackTrace()
            }
        }
    }
}
