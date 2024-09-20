package com.mona.adel.quickbite.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.mona.adel.quickbite.R
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.databinding.DialogAddMealBinding
import com.mona.adel.quickbite.databinding.FragmentControlMealsBinding


class ControlMealsFragment : Fragment() {

    private val TAG = "ControlMealsFragment"
    private lateinit var binding: FragmentControlMealsBinding

    private lateinit var addMealDialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentControlMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        addMealDialog = Dialog(requireContext())

        binding.btnAddMealAdmin.setOnClickListener {

            showAddMealDialog()
        }
    }

    private fun showAddMealDialog() {
        createDialog()
        addMealDialog.show()
    }

    private fun createDialog(){
        val binding = DialogAddMealBinding.inflate(layoutInflater)

        addMealDialog.setContentView(binding.root)

        addMealDialog.setCancelable(true)
//        addMealDialog.setCanceledOnTouchOutside(true)
        addMealDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")
//        val adapter = ArrayAdapter(requireContext(), R.layout.item_days_list, items)
//        (binding.daysList.editText as? AutoCompleteTextView)?.setAdapter(adapter)


        binding.btnAddMealAdmin.setOnClickListener {
            val name = binding.txtEtMealName.editText?.text.toString()
            val category = binding.txtEtMealCategory.editText?.text.toString()
            val price = binding.txtEtMealPrice.editText?.text.toString()
            // send day of the meal

            if (name.isNotEmpty() && category.isNotEmpty() && price.isNotEmpty()){
                val newMeal = Meal(mealName = name, category = category, price = price.toDouble())
                addMeal(newMeal)
                Log.d(TAG, "createDialog: the meal will be added")
            }
        }
    }

    private fun addMeal(newMeal: Meal) {

        // send the meal to database
    }

}