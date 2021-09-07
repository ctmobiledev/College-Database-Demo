package ctatum.collegedatabase.home

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import ctatum.collegedatabase.CollegeDataApplication
import ctatum.collegedatabase.R
import ctatum.collegedatabase.databinding.HomeFragmentBinding
import ctatum.collegedatabase.datalist.ListCollegesActivity
import ctatum.collegedatabase.datalist.ListCollegesViewModel
import ctatum.collegedatabase.datalist.ListCollegesViewModelFactory
import ctatum.collegedatabase.models.CollegeDataModel
import kotlinx.coroutines.*
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import ctatum.collegedatabase.api.CollegeDataApiMethods
import ctatum.collegedatabase.datalist.ListCollegesListAdapter


// THIS HAS BOTH KINDS OF VM DEFINITIONS:
// 1. The one using ViewModelProvider
// 2. The one using private val with a repository reference


class HomeFragment : Fragment() {

    // Globals

    // Same as in HomeActivity
    private val listCollegesViewModel: ListCollegesViewModel by viewModels {
        ListCollegesViewModelFactory((activity?.application as CollegeDataApplication).repository)
    }

    val adapter = ListCollegesListAdapter()

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    private var _binding: HomeFragmentBinding? = null       // Valid only between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private var collegeCount: Int = 0
    private var statusMessage = "Ready"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        // Get our VM, pass in the context from this level
        // Seems there's two ways to get a VM - this is one
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.vmContext = requireActivity().applicationContext
        viewModel.binding = binding

        listCollegesViewModel.getCollegeCount().observe(viewLifecycleOwner, {
            binding.txtStatusMsg.text = "Colleges Found: ${it.toString()}"
        })

        // Wire up the controls
        // Nav calls look like this
        binding.btnInstructions.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_homeFragment_to_instructionsFragment)
        }

        binding.btnListColleges.setOnClickListener {
            //Toast.makeText(activity?.applicationContext, "List Colleges", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity?.applicationContext, ListCollegesActivity::class.java)
            startActivity(intent)
        }

        binding.btnRefreshData.setOnClickListener {

            // Be sure something has been entered
            if (binding.editSearchString.text.toString().trim().length == 0) {
                Toast.makeText(activity?.applicationContext, "Please enter a search name.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Empty the table
            //println(">>> Emptying college ROOM table")
            listCollegesViewModel.clearDatabase()

            // Call to Retrofit (API) here, get colleges via web service
            println(">>> Getting colleges via API")
            val collegeDataApiMethods = CollegeDataApiMethods()
            collegeDataApiMethods.getCollegesViaApi(this, binding.editSearchString.text.toString())
            println(">>> items returned: ${collegeDataApiMethods.collegeList?.size}")

            // Replenish the table
            println(">>> Replenishing college ROOM table")
            collegeDataApiMethods.collegeList?.forEach {
                println(">>> it = ${it.name}, alpha_two_code = ${it.alpha_two_code}, web_pages = ${it.web_pages}")
                listCollegesViewModel.insert(it)
            }

            // Update the RCV in the UI
            // Requires ViewModel's LiveData to be cast to MutableLiveData or it defaults to "final"
            // meaning unchangeable. Also, this can't be run on a background thread so no coroutine here!
            listCollegesViewModel.allCollegesLiveData.value = collegeDataApiMethods.collegeList

            Toast.makeText(activity?.applicationContext, "Refresh Data", Toast.LENGTH_SHORT).show()

        }

        binding.btnAddOneRow.setOnClickListener {
            GlobalScope.launch {
                insertOneCollege()
            }
            // Indicate completion
            Toast.makeText(activity?.applicationContext, "Test college data added", Toast.LENGTH_SHORT).show()
        }

        binding.btnClearDatabase.setOnClickListener {
            GlobalScope.launch {
                clearDatabase()
            }
            Toast.makeText(activity?.applicationContext, "Database cleared", Toast.LENGTH_SHORT).show()
        }

        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.main.removeAllViews()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private fun clearDatabase() {
        listCollegesViewModel.clearDatabase()
    }

    private fun insertOneCollege() {

        val insertTimestamp = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now())

        println(">>> insertTimestamp =  $insertTimestamp")

        // Get input values from UI controls
        // Input type mask precludes need to parse-test input values
        // nulls subbed in for ArrayLists
        var newCollege: CollegeDataModel = CollegeDataModel(
            null,
            "United States",
            "TX",
            null,
            "US",
            "A university entry at $insertTimestamp",
            null
        )

        // Call save method in viewModel
        // ViewModel --calls--> DB Method --calls--> Repository Method
        // They should use the same name
        //

        listCollegesViewModel.insert(newCollege)

    }

}