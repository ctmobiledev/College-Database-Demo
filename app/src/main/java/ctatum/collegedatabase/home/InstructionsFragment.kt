package ctatum.collegedatabase.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ctatum.collegedatabase.databinding.HomeFragmentBinding
import ctatum.collegedatabase.databinding.InstructionsFragmentBinding
import android.R

import androidx.navigation.findNavController

class InstructionsFragment : Fragment() {

    companion object {
        fun newInstance() = InstructionsFragment()
    }

    private lateinit var viewModel: InstructionsViewModel

    private var _binding: InstructionsFragmentBinding? = null       // Valid only between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //return inflater.inflate(R.layout.home_fragment, container, false)

        _binding = InstructionsFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        // Wire up the controls
        binding.btnBack.setOnClickListener { view : View ->
            view.findNavController().navigateUp()
        }

        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()

        var fragment = InstructionsFragment()
        requireActivity().supportFragmentManager.beginTransaction().hide(fragment).commit()

        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(InstructionsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}