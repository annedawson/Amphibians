package net.annedawson.amphibians.ui.screens

/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import net.annedawson.amphibians.AmphibiansApplication
import net.annedawson.amphibians.data.AmphibiansPhotosRepository
import net.annedawson.amphibians.model.AmphibiansPhoto
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen.
 * At any one time it can only be one of the three values
 * Success, Error, Loading
 */
sealed interface AmphibiansUiState {
    //data class Success(val photos:  AmphibiansPhoto) : AmphibiansUiState

    //The Success data class represents a successful state in the Amphibians app,
    // where the list of photos has been successfully retrieved from the API.
    // It has a single property called photos, which is a list of AmphibiansPhoto objects.
    data class Success(val photos: List<AmphibiansPhoto>) : AmphibiansUiState
    // Represents a successful retrieval of Amphibians photos.
    // The original starter code stored the retrieved photos
    // as a long String of JSON key, value pairs and displayed
    // that in the HomeScreen.
    // Later the code was amended in AmphibiansViewModel.kt
    // to read the data into a list of AmphibiansPhoto.
    // The length of the list is displayed in a string using string formatting.
    // In this the final version of the app, the list of actual Amphibians photos and details
    // will be displayed in a list.


    //The Error object represents an error state in the Amphibians app,
    // where there was an error retrieving the photos from the API.
    object Error : AmphibiansUiState
    // Signifies an error during the photo fetching process.
    // Doesn't hold additional data, but its presence indicates a problem.
    object Loading : AmphibiansUiState
    // Indicates that photos are currently being fetched.
    // Used to display loading indicators or messages to the user.
}

// you can't instantiate the AmphibiansUiState interface,
// but you can instantiate a AmphibiansUiState.Success object e.g.
// AmphibiansUiState.Success(amphibiansPhotosRepository.getAmphibiansPhotos()),
// or a AmphibiansUiState.Error or a AmphibiansUiState.Loading object

class AmphibiansViewModel(private val amphibiansPhotosRepository: AmphibiansPhotosRepository) : ViewModel() {
    // This is dependency injection, injecting the repository dependency

    /** The mutable State that stores the status of the most recent request */
    var amphibiansUiState: AmphibiansUiState by mutableStateOf(AmphibiansUiState.Loading)
        private set

    /**
     * Call getAmphibiansPhotos() on init so we can display status immediately.
     */
    init {
        getAmphibiansPhotos()
    }

    /**
     * Gets Amphibians photos information from the Amphibians API Retrofit service
     * and updates the
     * [AmphibiansPhoto] [List] [MutableList].
     */
    fun getAmphibiansPhotos() {
        viewModelScope.launch {
            amphibiansUiState = AmphibiansUiState.Loading
            amphibiansUiState = try { // possible network access exception
                //val result = amphibiansPhotosRepository.getAmphibiansPhotos()[0]
                //AmphibiansUiState.Success(amphibiansPhotosRepository.getAmphibiansPhotos()[0])
                /*
                In the line of code below, in the case of a successful response,
                you receive Amphibians photo information from the server.

                In order to store the data, add a constructor parameter
                to the Success data class for the property.
                 */
                AmphibiansUiState.Success(amphibiansPhotosRepository.getAmphibiansPhotos())
            } catch (e: IOException) {
                AmphibiansUiState.Error
            } catch (e: HttpException) {
                AmphibiansUiState.Error
            }
        }
    }

    /**
     * Factory for [AmphibiansViewModel]
     * that takes [AmphibiansPhotosRepository] as a dependency
     */

    // This is the definition of Factory. It is used in AmphibiansPhotosApp
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AmphibiansApplication)
                val amphibiansPhotosRepository = application.container.amphibiansPhotosRepository
                AmphibiansViewModel(amphibiansPhotosRepository = amphibiansPhotosRepository)
            }
            // this is how the ViewModel is created in AmphibiansPhotosApp using Factory


        }
    }

}
