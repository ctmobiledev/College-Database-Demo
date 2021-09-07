package ctatum.collegedatabase.datalist

import androidx.lifecycle.*
import ctatum.collegedatabase.models.CollegeDataModel
import ctatum.collegedatabase.models.CollegeDataRepository
import kotlinx.coroutines.launch

/*

THERE ARE TWO KINDS OF VIEW MODELS:

1. STANDARD
-----------

DECLARATION:
class HomeViewModel : ViewModel() {

USAGE:
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

2. WITH A REPOSITORY
--------------------

DECLARATION:
class ListCollegesViewModel(private val repository: CollegeDataRepository) : ViewModel() {

USAGE (IN AN ACTIVITY):
class ListCollegesActivity : AppCompatActivity() {
    private val listCollegesViewModel: ListCollegesViewModel by viewModels {
        ListCollegesViewModelFactory((application as CollegeDataApplication).repository)
    }

    >>> will need to refer to the parent activity to get this one.

 */

class EditCollegeDataViewModel(private val repository: CollegeDataRepository) : ViewModel() {

    lateinit var currentCollege: CollegeDataModel

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     * EACH METHOD NAME MUST CORRESPOND TO A NAME IN THE REPOSITORY OBJECT
     */
    fun updateCollege(college: CollegeDataModel) = viewModelScope.launch {
        println(">>> updateCollege, college = ${college.name}, hashCode = ${college.hashCode()}")
        repository.update(college)
    }

    //
    // Because this returns LiveData, it can be observed with an observable elsewhere
    // Remember: LiveData :: .observe(...)
    //
    fun getCollegeCount(): LiveData<Int> {
        return repository.getCollegeCount()            // >>> collegesCount
    }

}

// To use a ViewModelFactory, copy this and alter the "if modelClass" line to the appropriate class name

class EditCollegeDataViewModelFactory(private val repository: CollegeDataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        println(">>> EditCollegeDataViewModelFactory.create() fired")
        if (modelClass.isAssignableFrom(EditCollegeDataViewModel::class.java)) {
            println(">>> modelClass.isAssignableFrom = true")
            @Suppress("UNCHECKED_CAST")
            return EditCollegeDataViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

