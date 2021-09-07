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

class ListCollegesViewModel(private val repository: CollegeDataRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    var allCollegesLiveData: MutableLiveData<List<CollegeDataModel>> =
        repository.allColleges.asLiveData() as MutableLiveData<List<CollegeDataModel>>

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     * EACH METHOD NAME MUST CORRESPOND TO A NAME IN THE REPOSITORY OBJECT
     */
    fun insert(college: CollegeDataModel) = viewModelScope.launch {
        repository.insert(college)
    }

    fun clearDatabase() = viewModelScope.launch {
        repository.clearDatabase()
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

class ListCollegesViewModelFactory(private val repository: CollegeDataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        println(">>> ListCollegesViewModelFactory.create() fired")
        if (modelClass.isAssignableFrom(ListCollegesViewModel::class.java)) {
            println(">>> modelClass.isAssignableFrom = true")
            @Suppress("UNCHECKED_CAST")
            return ListCollegesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

