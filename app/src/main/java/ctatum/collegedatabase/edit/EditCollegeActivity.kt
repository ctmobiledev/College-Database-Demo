package ctatum.collegedatabase.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import ctatum.collegedatabase.CollegeDataApplication
import ctatum.collegedatabase.R
import ctatum.collegedatabase.databinding.EditCollegeFragmentBinding
import ctatum.collegedatabase.datalist.EditCollegeDataViewModel
import ctatum.collegedatabase.datalist.EditCollegeDataViewModelFactory
import ctatum.collegedatabase.datalist.ListCollegesViewModel
import ctatum.collegedatabase.datalist.ListCollegesViewModelFactory
import ctatum.collegedatabase.models.CollegeDataModel

class EditCollegeActivity : AppCompatActivity() {

    companion object {

        var collegeInParm: CollegeDataModel = CollegeDataModel(
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )

    }

    private lateinit var viewModel: EditCollegeViewModel

    private var _binding: EditCollegeFragmentBinding? = null       // Valid only between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.edit_activity)

        // Get our VM, pass in the context from this level
        viewModel = ViewModelProvider(this).get(EditCollegeViewModel::class.java)

        collegeInParm = intent.getSerializableExtra("college_extra") as CollegeDataModel

        println(">>> collegeInParm.hashCode() = ${collegeInParm.hashCode()}")
        println(">>> collegeInParm.name = ${collegeInParm.name}")
        println(">>> collegeInParm.domainsJson = ${collegeInParm.domains}")
        println(">>> collegeInParm.alphaTwoCode = ${collegeInParm.alpha_two_code}")
        println(">>> collegeInParm.webPagesJson = ${collegeInParm.web_pages}")
        println(">>> collegeInParm.country = ${collegeInParm.country}")
        println(">>> collegeInParm.stateProvince = ${collegeInParm.stateProvince}")
        println(">>> collegeInParm.logId = ${collegeInParm.logId}")

        viewModel.currentCollege = collegeInParm

        println(">>> viewModel.currentCollege.hashCode() = ${viewModel.currentCollege}")

    }

}