package ctatum.collegedatabase.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var retrofit: Retrofit? = null

    // Define the base URL - must end with a "/" or it crashes
    // The query "?" is added by processing so don't add it here
    val BASE_URL =     "http://universities.hipolabs.com/"

    //*********************************************
    // Create the Retrofit instance
    //*********************************************

    // Add the converter
    // Build the Retrofit instance

    val retrofitInstance: Retrofit
        get() {
            if (retrofit == null) {
                retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!               // added '!!' - should never be null
        }
    //

}