package com.cube.cubeacademy.lib.models

/**
 * A generic data wrapper class that can hold data of type [T], an error code, and an error message.
 *
 * @param T The type of data this wrapper holds.
 * @property data The data object of type [T], which can be null.
 * @property errorCode An optional error code if there's an error (can be null).
 * @property errorMessage An optional error message if there's an error (can be null).
 */
data class DataWrapper<T>(

    val data: T? = null,
    val errorCode: Int? = null,
    val errorMessage: String? = null

)

