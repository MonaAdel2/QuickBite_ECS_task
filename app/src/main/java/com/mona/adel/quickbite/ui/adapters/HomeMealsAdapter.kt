package com.mona.adel.quickbite.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mona.adel.quickbite.R
import com.mona.adel.quickbite.data.model.Meal

class HomeMealsAdapter(private val onAddButtonClick: (Meal) -> Unit) :
    RecyclerView.Adapter<HomeMealsAdapter.MyViewHolder>() {

    private var meals = listOf<Meal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
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
        holder.addBtn.setOnClickListener {
            onAddButtonClick(meal)
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

    }


    fun setData(data: List<Meal>) {
        meals = data // Receive the list of meals from the fragment
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        val mealName = item.findViewById<TextView>(R.id.tv_meal_name_home)
        val mealCategory = item.findViewById<TextView>(R.id.tv_meal_category_home)
        val mealPrice = item.findViewById<TextView>(R.id.tv_meal_price_home)
        val addBtn = item.findViewById<FloatingActionButton>(R.id.btn_add_to_cart_home)
        val mealImage = item.findViewById<ImageView>(R.id.img_meal_home)
    }

}