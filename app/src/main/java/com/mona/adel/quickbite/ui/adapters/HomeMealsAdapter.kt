package com.mona.adel.quickbite.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mona.adel.quickbite.R
import com.mona.adel.quickbite.data.model.Meal

class HomeMealsAdapter: RecyclerView.Adapter<HomeMealsAdapter.MyViewHolder>() {

    private var meals = listOf<Meal>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return MyViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mealName.text = meals[position].mealName
        holder.mealCategory.text = meals[position].category
        holder.mealPrice.text = meals[position].price.toString()
        holder.addBtn.setOnClickListener {
            // send this meal to the cart // perform the check of #orders in the cart fragment
        }
    }


    fun setData(data: List<Meal>){
        meals = data
    }

    class MyViewHolder(item: View): RecyclerView.ViewHolder(item) {

        val mealName = item.findViewById<TextView>(R.id.tv_meal_name_home)
        val mealCategory = item.findViewById<TextView>(R.id.tv_meal_category_home)
        val mealPrice = item.findViewById<TextView>(R.id.tv_meal_price_home)
        val addBtn = item.findViewById<FloatingActionButton>(R.id.btn_add_to_cart_home)
    }

}