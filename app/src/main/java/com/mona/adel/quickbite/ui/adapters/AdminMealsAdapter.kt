package com.mona.adel.quickbite.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mona.adel.quickbite.R
import com.mona.adel.quickbite.data.model.Meal

class AdminMealsAdapter(private val onDeleteMeal: (Meal) -> Unit) : RecyclerView.Adapter<AdminMealsAdapter.MyViewHolder>() {

    private val TAG = "AdminMealsAdapter"
    var onItemClick: ((Meal, Int) -> Unit)? = null

    private var meals = mutableListOf<Meal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.item_meal_control, parent, false)
        return MyViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val meal = meals[position]

        holder.mealName.text = meal.mealName
        holder.mealCategory.text = meal.category
        holder.mealPrice.text = "Price ${meal.price.toString()} L.E"

        holder.mealDay.text = meal.days
            .joinToString(", ") { day -> day.replaceFirstChar { it.uppercase() } }


        // Set up the delete button of the meal
        holder.deleteBtn.setOnClickListener {
            onDeleteMeal(meal)
            meals = meals.toMutableList().apply { removeAt(position) }
            notifyItemRemoved(position)
        }

        // Set the meal image according to the meal's category
        when (meal.category) {
            "Desserts" -> holder.mealImage.setImageResource(R.drawable.desserts)
            "Drinks" -> holder.mealImage.setImageResource(R.drawable.drinks)
            "Soups" -> holder.mealImage.setImageResource(R.drawable.soups)
            "Seafood" -> holder.mealImage.setImageResource(R.drawable.seafood)
            "Pasta" -> holder.mealImage.setImageResource(R.drawable.pasta)
            "Bakery" -> holder.mealImage.setImageResource(R.drawable.bakery)
            "Salads" -> holder.mealImage.setImageResource(R.drawable.salads)
            "Chicken" -> holder.mealImage.setImageResource(R.drawable.chicken)
        }

        // Set up the onClick listener for updating the meal
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(meal, position)
        }

    }


    fun updateMeal(updatedMeal: Meal, position: Int) {
        meals[position] = updatedMeal
        notifyItemChanged(position)  // Notify the adapter that this specific item has changed
        notifyDataSetChanged()
    }

    fun addMeal(meal: Meal) {
        meals.add(meal)
        notifyItemInserted(meals.size - 1) // Notify adapter of new insertion
    }

    fun setData(data: List<Meal>) {
        meals = data.toMutableList()
        notifyDataSetChanged()
    }


    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        val mealName = item.findViewById<TextView>(R.id.tv_meal_name_admin)
        val mealCategory = item.findViewById<TextView>(R.id.tv_meal_category)
        val mealPrice = item.findViewById<TextView>(R.id.tv_meal_price_admin)
        val mealDay = item.findViewById<TextView>(R.id.tv_meal_day_admin)
        val deleteBtn = item.findViewById<ImageView>(R.id.btn_delete_meal_admin)
        val mealImage = item.findViewById<ImageView>(R.id.img_meal_admin)

    }

}