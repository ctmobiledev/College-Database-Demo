package ctatum.collegedatabase.models

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import ctatum.collegedatabase.api.CollegeDataApiMethods
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO

class CollegeDataRepository(private val collegeDataDao: CollegeDataDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    var allColleges: Flow<List<CollegeDataModel>> = collegeDataDao.getAllCollegesToList()
    var allCollegesMutableList: MutableList<CollegeDataModel> = mutableListOf<CollegeDataModel>()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.

    /**
     * EACH METHOD NAME MUST CORRESPOND TO A NAME IN THE VIEWMODEL OBJECT
     */

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(collegeDataModel: CollegeDataModel) {
        GlobalScope.launch {
            collegeDataDao.insert(collegeDataModel)         // getting an Int returned hasn't worked: coroutines
        }

        // This also works
        /***
        Thread {
            collegeDataDao.insert(collegeDataModel)
        }.start()
        ***/
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(collegeDataModel: CollegeDataModel) {
        GlobalScope.launch {
            collegeDataDao.updateCollege(collegeDataModel)
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getCollegeCount(): LiveData<Int> {
        return collegeDataDao.getCollegeCount()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun clearDatabase() {
        GlobalScope.launch {
            collegeDataDao.deleteAllColleges()
        }
    }

    //*****************************************
    // DO NOT DELETE THIS YET; MAY USE SOON.
    //*****************************************
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getCollegesForExport(): MutableList<CollegeDataModel> {
        GlobalScope.launch {
            allColleges.toList().forEach { oneCollege ->
                oneCollege.forEach {
                    allCollegesMutableList.add(it)
                }
            }
        }
        println(">>> allCollegesMutableList = ${allCollegesMutableList.toString()}")
        return allCollegesMutableList
    }

}