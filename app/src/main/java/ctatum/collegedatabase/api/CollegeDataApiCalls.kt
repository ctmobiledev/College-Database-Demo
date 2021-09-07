package ctatum.collegedatabase.api

import ctatum.collegedatabase.models.CollegeDataModel
import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CollegeDataApiCalls {

    // Specify the request type and pass the relative URL

    // The full URL will be
    // BASE_URL = "http://universities.hipolabs.com/search"

    // Note: Any spaces in the parm for the Country must be changed to "+" signs

    // Referred to in usage by "service" qualifier
    // This must match the Call in the VM - if a JSON array, use List
    // NOTE: Unliked the DAO, this does not return them in order - will need a way to do that.

    // "service.getColleges("Illinois")" is what calls the "getColleges" method below
    // from CollegeDataApiMethods

    @GET("search")
    //fun getColleges(@Query("name") name: String): Call<JSONArray>
    fun getColleges(@Query("name") name: String): Call<List<CollegeDataModel>>
    //fun getColleges(@Query("country") country: String): Call<List<CollegeDataModel>>

    //abstract fun getColleges(): Call<CollegeDataModel>

}