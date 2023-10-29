package com.cube.cubeacademy.lib.di

import ApiException
import android.util.Log
import com.cube.cubeacademy.lib.api.ApiService
import com.cube.cubeacademy.lib.models.Nomination
import com.cube.cubeacademy.lib.models.Nominee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository class for handling API requests and data retrieval.
 *
 * This class provides methods for fetching nominations, nominees, and creating nominations.
 *
 * @param api The ApiService instance used for making API requests.
 */
class Repository(val api: ApiService) {

    /**
     * Fetch a list of all nominations.
     *
     * @return A list of nominations if the API request is successful, otherwise an empty list.
     * @throws ApiException when an API request fails with an error code.
     */
    suspend fun getAllNominations(): List<Nomination> {
        return withContext(Dispatchers.Default) {
            try {
                val response = api.getAllNominations()
                if (response.errorCode != null) {
                    if (response.errorMessage != null) {
                        Log.e("API Error", response.errorMessage)
                    } else {
                        Log.e(
                            "API Error",
                            "An error occurred with error code: ${response.errorCode}"
                        )
                    }
                    throw ApiException("API request failed with error code: ${response.errorCode}")
                }

                response.data ?: emptyList()

            } catch (e: Exception) {
                throw e
            }
        }
    }

    /**
     * Fetch a list of all nominees.
     *
     * @return A list of nominees if the API request is successful, otherwise an empty list.
     * @throws ApiException when an API request fails with an error code.
     */
    suspend fun getAllNominees(): List<Nominee> {
        return withContext(Dispatchers.Default) {
            try {
                val response = api.getAllNominees()
                if (response.errorCode != null) {
                    // Handle API error
                    if (response.errorMessage != null) {
                        // Display or log the error message
                        Log.e("API Error", response.errorMessage)
                    } else {
                        // Handle the error without a specific message
                        Log.e(
                            "API Error",
                            "An error occurred with error code: ${response.errorCode}"
                        )
                    }
                    // Handle the error as appropriate, e.g., throw a custom exception
                    throw ApiException("API request failed with error code: ${response.errorCode}")
                }

                // If there are no errors, return the data for your application logic
                response.data ?: emptyList() // Return an empty list if data is null

            } catch (e: Exception) {
                // Handle network or other exceptions
                throw e
            }
        }
    }

    /**
     * Create a nomination with the specified nominee ID, reason, and process.
     *
     * @param nomineeId The ID of the nominee for the nomination.
     * @param reason The reason for the nomination.
     * @param process The process value for the nomination.
     * @return The created nomination if the API request is successful, otherwise null.
     * @throws ApiException when an API request fails with an error code.
     */
    suspend fun createNomination(nomineeId: String, reason: String, process: String): Nomination? {
        return withContext(Dispatchers.Default) {
            try {
                val response =
                    api.createNomination(nomineeId = nomineeId, reason = reason, process = process)
                if (response.errorCode != null) {
                    if (response.errorMessage != null) {
                        Log.e("API Error", response.errorMessage)
                    } else {
                        Log.e(
                            "API Error",
                            "An error occurred with error code: ${response.errorCode}"
                        )
                    }
                    throw ApiException("API request failed with error code: ${response.errorCode}")
                }
                response.data

            } catch (e: Exception) {
                throw e
            }
        }
    }
}
