package ctatum.collegedatabase.datalist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ctatum.collegedatabase.CollegeDataApplication
import ctatum.collegedatabase.R
import ctatum.collegedatabase.models.CollegeDataModel

class ListCollegesActivity : AppCompatActivity() {

    private val listCollegesViewModel: ListCollegesViewModel by viewModels {
        ListCollegesViewModelFactory((application as CollegeDataApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_colleges_activity)

        println(">>> ListCollegesActivity.onCreate fired")

        val recyclerView = findViewById<RecyclerView>(R.id.rcvColleges)
        val adapter = ListCollegesListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // populate the RCV ("owner = " wasn't needed)
        listCollegesViewModel.allCollegesLiveData.observe(this) { colleges ->
            colleges.let { adapter.submitList(it) }     // Update the cached copy of the words in the adapter.
        }

    }

    private fun collegeObjectTapped(result: CollegeDataModel) {          // #1 of 2
        Toast.makeText(this,
            "Row Tapped",
            Toast.LENGTH_SHORT).show()
    }

}