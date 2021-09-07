package ctatum.collegedatabase.datalist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ctatum.collegedatabase.R
import ctatum.collegedatabase.edit.EditCollegeActivity
import ctatum.collegedatabase.models.CollegeDataModel
import java.io.Serializable                             // for passing custom objects with putExtra


class ListCollegesListAdapter : ListAdapter<CollegeDataModel,
        ListCollegesListAdapter.ListCollegesViewHolder>(CollegeComparator()) {

    //private val collegesArrayList: ArrayList<JSONObject> = ArrayList<JSONObject>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCollegesViewHolder {
        return ListCollegesViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ListCollegesViewHolder, position: Int) {
        val current = getItem(position)

        println(">>> onBindViewHolder: current = ${current.name}")
        println(">>> onBindViewHolder: current = ${current.country}")
        println(">>> onBindViewHolder: current = ${current.alpha_two_code}")
        println(">>> onBindViewHolder: current = ${current.stateProvince}")

        holder.bind(current)

        // In Currency Now we had a card to tap on, may implement that here
        holder.cardCollegeData.setOnClickListener {
            // "it" is actually the CollegeDataModel, serialized
            println(">>> CLICK ON ${current.name} with hashCode() = ${current.hashCode()}")
            //Toast.makeText(holder.collegeName.context, "Clicked on ${current.name}", Toast.LENGTH_SHORT).show()

            // BE SURE TO ADD putExtra WITH EITHER THE WHOLE OBJECT OR EACH OF THE PROPERTIES!

            val intent = Intent(holder.collegeName.context, EditCollegeActivity::class.java)
            intent.putExtra("college_extra", current as Serializable)
            holder.collegeName.context.startActivity(intent)
        }

    }

    /***
    // If implementing, be sure using the right list!!!
    override fun getItemCount(): Int {
        return collegesArrayList!!.size
    }
    ***/

    class ListCollegesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val cardCollegeData: CardView = itemView.findViewById(R.id.cardCollegeData)

        val collegeName: TextView = itemView.findViewById(R.id.outName)
        val collegeCountry: TextView = itemView.findViewById(R.id.outCountry)
        val collegeAlphaTwoCode: TextView = itemView.findViewById(R.id.outAlphaTwoCode)
        val collegeStateProvince: TextView = itemView.findViewById(R.id.outStateProvince)
        val collegeWebPagesJson: TextView = itemView.findViewById(R.id.outWebPagesJson)
        val collegeDomainsJson: TextView = itemView.findViewById(R.id.outDomainsJson)

        // REMINDER: THE BIND METHOD ONLY CONTROLS WHAT'S DISPLAYED FROM ROOM/SQLITE, NOT
        // WHAT HAS ARRIVED VIA RETROFIT/HTTP CLIENT. FOR THE FORMATTING OF DATA ON INSERTS,
        // CHECK ELSEWHERE.

        fun bind(college: CollegeDataModel?) {
            collegeName.text = college?.name
            collegeCountry.text = college?.country
            collegeStateProvince.text = college?.stateProvince
            collegeAlphaTwoCode.text = college?.alpha_two_code

            var flatWebPages = ""
            var flatDomains = ""

            // Adjust list formatting
            if (college?.web_pages.toString() == "[]") {
                collegeWebPagesJson.text = "<no web pages>"
            } else {
                // Remove the outer brackets to form a proper list
                val first = 1
                val last = college?.web_pages.toString().length - 1
                flatWebPages = college?.web_pages.toString().subSequence(first, last) as String
                collegeWebPagesJson.text = flatWebPages
            }
            //collegeWebPagesJson.text = college?.web_pages.toString()

            if (college?.domains.toString() == "[]") {
                collegeDomainsJson.text = "<no domains>"
            } else {
                // Remove the outer brackets to form a proper list
                val first = 1
                val last = college?.domains.toString().length - 1
                flatDomains = college?.domains.toString().subSequence(first, last) as String
                collegeDomainsJson.text = flatDomains
            }
            //collegeDomainsJson.text = college?.domains.toString()

            println(">>> bind: name = ${college?.name}")
            println(">>> bind: domains = $flatDomains")
            println(">>> bind: alphaTwoCode = ${college?.alpha_two_code}")
            println(">>> bind: webPages = $flatWebPages")
            println(">>> bind: country = ${college?.country}")
            println(">>> bind: stateProvince = ${college?.stateProvince}")
            println(">>> bind: logId = ${college?.logId}")
        }

        companion object {
            fun create(parent: ViewGroup): ListCollegesViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.college_list_item, parent, false)
                return ListCollegesViewHolder(view)
            }
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            println(">>> onClick, position = $position")
        }

    }

    // New: comparators (!)

    class CollegeComparator : DiffUtil.ItemCallback<CollegeDataModel>() {
        override fun areItemsTheSame(oldItem: CollegeDataModel, newItem: CollegeDataModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CollegeDataModel, newItem: CollegeDataModel): Boolean {
            return oldItem.name == newItem.name
        }
    }

}