package com.mona.adel.quickbite.ui.adapters

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mona.adel.quickbite.R
import com.mona.adel.quickbite.data.model.DayOfWeek
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.relations.MealWithDays

class AdminMealsAdapter(private val onDeleteMeal: (Meal) -> Unit,
                        private val onDaysOfMealRetrieved: (Int) -> Unit)
    : RecyclerView.Adapter<AdminMealsAdapter.MyViewHolder>() {

    private val TAG = "AdminMealsAdapter"
    var onItemClick: ((Meal, Int)->Unit)?=null

    private var meals = mutableListOf<Meal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_meal_control, parent, false)
        return MyViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val meal = meals[position]

        holder.mealName.text = meal.mealName
        holder.mealCategory.text = meal.category
        holder.mealPrice.text = meal.price.toString()

        // Set up the delete button
        holder.deleteBtn.setOnClickListener {
            onDeleteMeal(meal)
            meals = meals.toMutableList().apply { removeAt(position) }
            notifyItemRemoved(position)
        }

        // Set up the onClick listener
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(meal, position)
        }

        // Set up the meal day text to be empty initially
        holder.mealDay.text = ""

        // Retrieve the meal days asynchronously
        meal.mealId?.let { mealId ->
            // Fetch meal days using the ViewModel method
            onDaysOfMealRetrieved(mealId)

            // Use LiveData observation in your fragment to update the adapter when data is available
        }
    }


    private fun formatDaysText(days: List<DayOfWeek>): String{
            return days.joinToString(", ") { it.dayName }
    }

    fun updateMeal(updatedMeal: Meal, position: Int) {
        meals[position] = updatedMeal // Reassign the meal at the specific position
        notifyItemChanged(position)  // Notify the adapter that this specific item has changed
        notifyDataSetChanged()
    }

    fun addMeal(meal: Meal) {
        meals.add(meal) // Add the new meal to the list
        notifyItemInserted(meals.size - 1) // Notify adapter of new insertion
    }



    fun setData(data: List<Meal>){
        meals = data.toMutableList()
        notifyDataSetChanged()
    }

    fun updateMealDays(mealWithDays: MealWithDays) {
        val mealPosition = meals.indexOfFirst { it.mealId == mealWithDays.meal.mealId }
        Log.d(TAG, "updateMealDays: ${mealWithDays.days}")
        if (mealPosition != -1) {
            Handler(Looper.getMainLooper()).post {
                notifyItemChanged(mealPosition) // This ensures the update occurs after the layout pass
            }
        }
    }

    fun setMealsWithDays (){

    }


    class MyViewHolder(item: View): RecyclerView.ViewHolder(item) {

        val mealName = item.findViewById<TextView>(R.id.tv_meal_name_admin)
        val mealCategory = item.findViewById<TextView>(R.id.tv_meal_category)
        val mealPrice = item.findViewById<TextView>(R.id.tv_meal_price_admin)
        val mealDay = item.findViewById<TextView>(R.id.tv_meal_day_admin)
        val deleteBtn = item.findViewById<ImageView>(R.id.btn_delete_meal_admin)

    }

}