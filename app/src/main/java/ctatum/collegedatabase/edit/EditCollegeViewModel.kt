package ctatum.collegedatabase.edit

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ctatum.collegedatabase.databinding.EditCollegeFragmentBinding
import ctatum.collegedatabase.datalist.ListCollegesViewModel
import ctatum.collegedatabase.models.CollegeDataModel
import ctatum.collegedatabase.models.CollegeDataRepository
import kotlinx.coroutines.launch

class EditCollegeViewModel : ViewModel() {

    // The only things this VM is good for

    lateinit var vmContext: Context
    lateinit var binding: EditCollegeFragmentBinding           // the module that gets the data

    var currentCollege = CollegeDataModel(
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )

}
