package ctatum.collegedatabase.api

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ctatum.collegedatabase.datalist.ListCollegesViewModel
import ctatum.collegedatabase.models.CollegeDataModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CollegeDataApiMethods {

    var collegeList: MutableList<CollegeDataModel>? = null
    private lateinit var collegeDataModel: CollegeDataModel
    private lateinit var listCollegesViewModel: ListCollegesViewModel

    fun getCollegesViaApi(fragment: Fragment, searchString: String) {

        listCollegesViewModel = ViewModelProvider(fragment).get(ListCollegesViewModel::class.java)

        // https://www.geeksforgeeks.org/how-to-extract-data-from-json-array-in-android-using-retrofit-library/
        // https://stackoverflow.com/questions/42623437/parse-json-array-response-using-retrofit-gson

        // builder may need to go here
        val service = RetrofitClient.retrofitInstance.create(CollegeDataApiCalls::class.java)

        //
        // on below line we are calling a method to get all the courses from API.
        // CHANGE SO THIS RETURNS AN ARRAY LIST
        // NOTE: spaces do not need to be corrected with "+"; it's done automatically by the API
        //
        val call: Call<List<CollegeDataModel>> = service.getColleges(searchString)
        //val call: Call<List<CollegeDataModel>> = service.getColleges("United States")

        // on below line we are calling method to enqueue and calling
        // all the data from array list.

        call.enqueue(object : Callback<List<CollegeDataModel>?> {

            // Dropped these in from error message suggestion

            override fun onResponse(
                call: Call<List<CollegeDataModel>?>,
                response: Response<List<CollegeDataModel>?>             // List<CollegeDataModel
            ) {

                println(">>> response.body: ${response.body()}")

                println(">>> response: ${response.message()}")

                //println(">>> response.body.toString(): ${response.body().toString()}")

                // THIS IS WHERE NULLS ARE ODDLY RETURNED FOR EVERY FIELD EXCEPT COLLEGE NAME AND COUNTRY

                collegeList = response.body() as MutableList<CollegeDataModel>
                //collegeList = response.body()?.sortedWith(compareBy { it.name }) as MutableList<CollegeDataModel>

                println(">>> **************************************************")
                println(">>> getCollegesViaApi:")
                println(">>> **************************************************")
                collegeList?.forEach {
                    println(">>> College: ${it.name}")
                    println(">>>    AlphaTwoCode: ${it.alpha_two_code}")
                    println(">>>    Web Pages: ${it.web_pages.toString()}")
                    println(">>>    Domains: ${it.domains.toString()}")
                    listCollegesViewModel.insert(it)
                    //println(">>> ...insert attempted")
                }
                println(">>> **************************************************")

                listCollegesViewModel.allCollegesLiveData.value = collegeList

            }

            override fun onFailure(call: Call<List<CollegeDataModel>?>, t: Throwable) {

                println(">>> CollegeDataApiMethods - onFailure fired")
                println(">>> t = ${t.localizedMessage}")
                println(">>> t = ${t.stackTrace}")

            }

        })

    }

}