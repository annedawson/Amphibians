package net.annedawson.amphibians.network

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



import  net.annedawson.amphibians.model.MarsPhoto
import retrofit2.http.GET

/**
 * A public interface that exposes the [getPhotos] method
 */
interface MarsApiService {
    /**
     * Returns a [List] of [MarsPhoto] and
     * this method can be called from a Coroutine.
     * The @GET annotation indicates that
     * the "photos" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("photos") // to be concatenated to the baseUrl
    // to get the full URL of the JSON resource.
    // the GET interface adds "photos" to
    // the BASE_URL to get the absolute URL of the photos data
    // the function just returns the list of photos

    suspend fun getPhotos(): List<MarsPhoto>
    // overridden in the repository
}
