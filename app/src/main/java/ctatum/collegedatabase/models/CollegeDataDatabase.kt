package ctatum.collegedatabase.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Template: https://developer.android.com/codelabs/android-room-with-a-view-kotlin#7
// Annotates class to be a Room Database with a table (entity) of the CollegeDataModel class

// SEEMS ANY DATABASE CHANGES ON AN ACTUAL DEVICE REQUIRE A CHANGE TO THE VERSION NUMBER
// EVEN IF THE APK HAS BEEN UNINSTALLED!

@Database(entities = arrayOf(CollegeDataModel::class), version = 4, exportSchema = false)
@TypeConverters(JsonTypeConverters::class)
abstract class CollegeDataDatabase : RoomDatabase() {

    abstract fun collegeDataDao(): CollegeDataDao

    private class CollegeDataDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var collegeDataDao = database.collegeDataDao()

                    /***
                    // Delete all content here.
                    collegeDataDao.deleteAllColleges()

                    // Add test colleges
                    // Ctrl+Space to get properties to fill out
                    var college_1 = CollegeDataModel(
                        country = "United States",
                        name = "University of Illinois",
                        alphaTwoCode = "US",
                        domainsJson = "",
                        logId = null,
                        stateProvince = "IL",
                        webPagesJson = ""
                    )

                    collegeDataDao.insert(college_1)
                    ***/

                    println(">>> CollegeDataDatabase - onCreate fired, one insert done")

                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: CollegeDataDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): CollegeDataDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CollegeDataDatabase::class.java,
                    "word_database"
                )
                    .addCallback(CollegeDataDatabaseCallback(scope))
                    .allowMainThreadQueries()           // the cheat - should be on its own bg thread
                    .fallbackToDestructiveMigration()   // blow away everything - okay for this test only
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}