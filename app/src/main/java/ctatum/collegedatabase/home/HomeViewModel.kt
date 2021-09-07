package ctatum.collegedatabase.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ctatum.collegedatabase.api.CollegeDataApiCalls
import ctatum.collegedatabase.api.RetrofitClient
import ctatum.collegedatabase.databinding.HomeFragmentBinding
import ctatum.collegedatabase.datalist.ListCollegesListAdapter
import ctatum.collegedatabase.models.CollegeDataModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.recyclerview.widget.LinearLayoutManager

// THIS MAY NEED TO BE MOVED TO LIST COLLEGES LIST ETC. TO HAVE CONTACT WITH THE RCV OBJECT

class HomeViewModel : ViewModel() {

    lateinit var vmContext: Context
    lateinit var binding: HomeFragmentBinding           // the module that gets the data

    private val NUMBER_OF_RESULTS = 32
    private val refresh = true

    private var collegeDataArrayList: ArrayList<CollegeDataModel>? = null
    private lateinit var collegeDataModel: CollegeDataModel


    // LiveData gives us updated events when they change.
    val liveCollegesMutableList: MutableLiveData<MutableList<CollegeDataModel>> = MutableLiveData(
        mutableListOf())


}