package ctatum.collegedatabase.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable                             // for passing custom objects with putExtra

@Entity(tableName = "college_data_table")
@TypeConverters(JsonTypeConverters::class)
data class CollegeDataModel(
    @PrimaryKey(autoGenerate = true) var logId: Long?,
    @ColumnInfo(name = "country") var country: String?,
    @ColumnInfo(name = "state_province") var stateProvince: String?,
    @ColumnInfo(name = "web_pages") var web_pages: ArrayList<String>?,      // See JsonTypeConverters class for translation to String
    @ColumnInfo(name = "alpha_two_code") var alpha_two_code: String?,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "domains") var domains: ArrayList<String>?           // See JsonTypeConverters class for translation to String
) : Serializable