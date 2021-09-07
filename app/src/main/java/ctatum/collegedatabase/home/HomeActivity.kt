package ctatum.collegedatabase.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import ctatum.collegedatabase.CollegeDataApplication
import ctatum.collegedatabase.R
import ctatum.collegedatabase.databinding.HomeActivityBinding
import ctatum.collegedatabase.databinding.HomeFragmentBinding
import ctatum.collegedatabase.datalist.ListCollegesViewModel
import ctatum.collegedatabase.datalist.ListCollegesViewModelFactory

class HomeActivity : AppCompatActivity() {

    private val listCollegesViewModel: ListCollegesViewModel by viewModels {
        ListCollegesViewModelFactory((application as CollegeDataApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.home_activity)

        /***
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
                .commitNow()
        }
        ***/

    }

}