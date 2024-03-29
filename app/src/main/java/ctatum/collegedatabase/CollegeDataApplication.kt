package ctatum.collegedatabase

import android.app.Application
import ctatum.collegedatabase.models.CollegeDataDatabase
import ctatum.collegedatabase.models.CollegeDataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class CollegeDataApplication : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { CollegeDataDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { CollegeDataRepository(database.collegeDataDao()) }

}