package ctatum.collegedatabase.models

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CollegeDataDao {

    // Flow object added Aug 2021
    // Use the one from JetBrains, not the Java one
    // Table name is now the string name in the DAO, not the Model

    @Query("SELECT * FROM college_data_table ORDER BY name")
    fun getAllCollegesToList(): Flow<List<CollegeDataModel>>

    @Query("SELECT * FROM college_data_table ORDER BY name")
    fun getAllCollegesToListDesc(): Flow<List<CollegeDataModel>>

    @Query("SELECT * FROM college_data_table ORDER BY name")
    fun getAllCollegesToLiveData(): Flow<List<CollegeDataModel>>

    @Query("SELECT * FROM college_data_table ORDER BY name")
    fun getAllCollegesToLiveDataDesc(): Flow<List<CollegeDataModel>>

    @Query("SELECT * FROM college_data_table WHERE logId = :sarg")
    fun getCollegeWithLogId(sarg: Long): Flow<List<CollegeDataModel>>

    @Query("SELECT COUNT(*) FROM college_data_table")
    fun getCollegeCount(): LiveData<Int>

    @Query("SELECT * FROM college_data_table ORDER BY name")
    fun getCollegeList(): List<CollegeDataModel>


    // "vararg" only needed for 2+ rows/objects
    // these don't return anything so no LiveData markers are needed

    @Insert
    fun insert(newModel: CollegeDataModel)

    @Update
    fun updateCollege(college: CollegeDataModel)

    @Delete
    fun delete(delModel: CollegeDataModel)

    @Query("DELETE FROM college_data_table")
    fun deleteAllColleges()

}