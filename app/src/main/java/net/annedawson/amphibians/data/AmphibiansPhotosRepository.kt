package net.annedawson.amphibians.data

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


import net.annedawson.amphibians.model.AmphibiansPhoto
import net.annedawson.amphibians.network.AmphibiansApiService

/**
 * Repository that fetch amphibians photos list from amphibiansApi.
 */
interface AmphibiansPhotosRepository {
    /** Fetches list of AmphibiansPhoto from AmphibiansApiService */
    suspend fun getAmphibiansPhotos(): List<AmphibiansPhoto>
    // the above is an abstract function that needs implementing
    // by overriding - see below
}

/**
 * Network Implementation of Repository that fetch
 * amphibians photos list from amphibiansApi.
 */
class NetworkAmphibiansPhotosRepository(
    private val amphibiansApiService: AmphibiansApiService
) : AmphibiansPhotosRepository {
    /** Fetches list of AmphibiansPhoto from amphibiansApi*/
    override suspend fun getAmphibiansPhotos(): List<AmphibiansPhoto> = amphibiansApiService.getPhotos()
}
