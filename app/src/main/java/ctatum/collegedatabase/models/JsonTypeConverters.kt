package ctatum.collegedatabase.models

import androidx.room.TypeConverter
import java.lang.StringBuilder
import java.util.*

class JsonTypeConverters {

    // REMINDER: THIS ONLY APPLIES TO *ROOM*, NOT TO RETROFIT. SINCE SQLITE ONLY HAS
    // PRIMITIVES AS DATA TYPES, CONVERTERS ARE NEEDED TO CHANGE COMPLEX DATA TYPES
    // INTO PRIMITIVES.

    // RULES:
    // 1. The data type after "to" and "from" MUST ALREADY EXIST - ideally it should be an
    //    existing Java or Kotlin primitive like String, Int, or Float.
    // 2. For lists, use ArrayList in the method names only, not List.
    // 3. In any associated data models using the data type (in this case, ArrayList),
    //    when initializing any objects, use null if no value present - otherwise, you'll
    //    need to create one with actual values:
    /*
            var newCollege: CollegeDataModel = CollegeDataModel(
            null,
            "United States",
            "TX",
            null,
            "US",
            "A university entry at $insertTimestamp",
            null
        )
     */
    //     The two nulls above are both JSONArrays in the response returned:
    //     ["ill.edu", "ill.com",...]

    //****************************************
    // This is the one actually fired
    //****************************************
    @TypeConverter
    fun fromArrayList(list: ArrayList<String>?): String? {
        println(">>> fromArrayList fired, size of list is ${list?.size}")
        println(">>> list parm is ${list?.toString()}")
        var outString = StringBuilder("")
        //outString.append("[")
        if (list != null) {
            for (n in 0 until list.size) {
                println(">>> @TypeConverter list[$n] = ${list[n]}")
                outString.append("\"${list[n]}\"")
                if (n < list.size-1) {
                    outString.append(", ")
                }
            }
        }
        //outString.append("]")
        println(">>> fromArrayList result = ${outString.toString()}")
        return outString.toString()
    }

    //****************************************
    // Not sure when this one is called...?
    // Maybe for insertions?
    //****************************************
    @TypeConverter
    fun toArrayList(string: String?): ArrayList<String>? {
        println(">>> toArrayList fired")
        var outList: ArrayList<String>? = ArrayList<String>()
        if (string != null) {
            outList?.add(string)
        }
        return outList
    }


}