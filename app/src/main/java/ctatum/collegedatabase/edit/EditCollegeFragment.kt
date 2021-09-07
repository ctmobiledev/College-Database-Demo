package ctatum.collegedatabase.edit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import ctatum.collegedatabase.CollegeDataApplication
import ctatum.collegedatabase.databinding.EditCollegeFragmentBinding
import ctatum.collegedatabase.datalist.EditCollegeDataViewModel
import ctatum.collegedatabase.datalist.EditCollegeDataViewModelFactory
import ctatum.collegedatabase.datalist.ListCollegesViewModel
import ctatum.collegedatabase.datalist.ListCollegesViewModelFactory

class EditCollegeFragment : Fragment() {

    val editCollegeDataViewModel: EditCollegeDataViewModel by viewModels {
        EditCollegeDataViewModelFactory((activity?.application as CollegeDataApplication).repository)
    }

    companion object {
        fun newInstance() = EditCollegeFragment()
    }

    // One of these "private val" declarations must exist in a fragment for each VM class
    // And it must be a "val", not a RW "var"

    /***
    // MOVED TO THE ACTIVITY
    private val editCollegeViewModel: EditCollegeViewModel by viewModels {         // EditCollegeViewModel
        EditCollegeViewModelFactory((activity?.application as CollegeDataApplication).repository)
    }
    ***/

    private lateinit var viewModel: EditCollegeViewModel
    private lateinit var dataViewModel: EditCollegeDataViewModel

    private var _binding: EditCollegeFragmentBinding? = null       // Valid only between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = EditCollegeFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        // Get our VM, pass in the context from this level
        // Have a second VM?
        viewModel = ViewModelProvider(this).get(EditCollegeViewModel::class.java)
        viewModel.vmContext = requireActivity().applicationContext
        viewModel.binding = binding

        // Wire up the controls
        binding.btnSave.setOnClickListener {
            //viewModel.update(viewModel.currentCollege)
            // call the parent's View Model to access the update method
            println(">>> previously passed in by EditCollegeActivity.collegeInParm, hashcode = ${EditCollegeActivity.collegeInParm.hashCode()}")
            EditCollegeActivity.collegeInParm.stateProvince = binding.editStateProvince.text.toString()
            println(">>> updating stateProvince = ${EditCollegeActivity.collegeInParm.stateProvince}")
            editCollegeDataViewModel.updateCollege(EditCollegeActivity.collegeInParm)
            activity?.finish()
        }

        binding.btnCancel.setOnClickListener {
            activity?.finish()
        }

        /***
        binding.editCollegeName.setText(EditCollegeActivity.collegeInParm.name)
        binding.editStateProvince.setText(EditCollegeActivity.collegeInParm.stateProvince)
        binding.editWebPagesJson.setText(EditCollegeActivity.collegeInParm.webPagesJson)
        binding.editAlphaTwoCode.setText(EditCollegeActivity.collegeInParm.alphaTwoCode)
        binding.editDomainsJson.setText(EditCollegeActivity.collegeInParm.domainsJson)
         ***/

        //return inflater.inflate(R.layout.edit_college_fragment, container, false)

        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Supposedly created here, but "private var" does it first
        //viewModel = ViewModelProvider(this).get(EditCollegeViewModel::class.java)

        println(">>> EditCollegeActivity.collegeInParm.name = ${EditCollegeActivity.collegeInParm.name}")

        binding.editCollegeName.setText(EditCollegeActivity.collegeInParm.name)
        binding.editStateProvince.setText(EditCollegeActivity.collegeInParm.stateProvince)
        binding.editAlphaTwoCode.setText(EditCollegeActivity.collegeInParm.alpha_two_code)

        binding.editWebPagesJson.setText(stripBrackets(EditCollegeActivity.collegeInParm.web_pages.toString()))
        binding.editDomainsJson.setText(stripBrackets(EditCollegeActivity.collegeInParm.domains.toString()))
        /***
        ***/

    }

    fun stripBrackets(arg: String?): String? {
        val first = 1
        val last = arg?.length?.minus(1)
        return last?.let { arg?.substring(first, it) }
    }

}