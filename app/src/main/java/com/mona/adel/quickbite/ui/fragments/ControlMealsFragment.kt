package com.mona.adel.quickbite.ui.fragments

import android.app.AlertDialog
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
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mona.adel.quickbite.R
import com.mona.adel.quickbite.data.LocalDataSourceImp
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.relations.MealDayCrossRef
import com.mona.adel.quickbite.data.repository.ControlMealsRepoImp
import com.mona.adel.quickbite.databinding.DialogAddMealBinding
import com.mona.adel.quickbite.databinding.FragmentControlMealsBinding
import com.mona.adel.quickbite.ui.adapters.AdminMealsAdapter
import com.mona.adel.quickbite.ui.adapters.AdminMealsAdapter2
import com.mona.adel.quickbite.ui.factories.ControlMealsViewModelFactory
import com.mona.adel.quickbite.ui.viewModels.ControlMealsViewModel


class ControlMealsFragment : Fragment() {

    private val TAG = "ControlMealsFragment"
    private lateinit var binding: FragmentControlMealsBinding

    private lateinit var addMealDialog: Dialog
    private lateinit var controlMealsViewModel: ControlMealsViewModel
    private lateinit var adminMealsAdapter: AdminMealsAdapter2

    private var dayForNewMeal = -1

    // Define the options and a list to store the selected ones
    private val options = arrayOf("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
    private val selectedItems = booleanArrayOf(false, false, false, false, false, false, false)
    private val selectedDaysIdList = mutableListOf<Int>()
    private val selectedDaysNamesList = mutableListOf<String>()

    private val selectedItems2 = booleanArrayOf(false, false, false, false, false, false, false)
    private val selectedDaysIdList2 = mutableListOf<Int>()
    private val selectedDaysNamesList2 = mutableListOf<String>()

    private lateinit var selectedOptionsTextView: TextView
    private lateinit var selectedOptionsTextView2: TextView

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

        getViewModelReady()



        addMealDialog = Dialog(requireActivity())

        binding.btnAddMealAdmin.setOnClickListener {
            showAddMealDialog()
        }

        controlMealsViewModel.newMeal.observe(requireActivity()){id->
            if (id != null){
//                addDayOfMeal(id)
            }else{
                Log.d(TAG, "onViewCreated: good game!!")
            }
        }

        controlMealsViewModel.getAllMeals()
        controlMealsViewModel.meals.observe(requireActivity()){meals->
            if (meals!=null){

                adminMealsAdapter = AdminMealsAdapter2(
                    { meal -> controlMealsViewModel.deleteMeal(meal) }
                )

                adminMealsAdapter.setData(meals)
                adminMealsAdapter.onItemClick={meal, position->
                    showEditMealDialog(meal, position)
                }
                
                binding.rvMealsControlAdmin.adapter = adminMealsAdapter
                binding.rvMealsControlAdmin.layoutManager = LinearLayoutManager(requireContext())
                
                
            }else{
                Log.d(TAG, "onViewCreated: the meals from database is null")
            }
        }


    }

    private fun showEditMealDialog(meal: Meal, position: Int) {
        createEditMealDialog(meal, position)
        addMealDialog.show()
    }

    private fun createEditMealDialog(meal: Meal, position: Int) {
        val binding = DialogAddMealBinding.inflate(layoutInflater)

        addMealDialog.setContentView(binding.root)

        addMealDialog.setCancelable(true)
        addMealDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // List of days
        val items = listOf("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_days_list, items)
        (binding.daysList.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        // Set meal details in the dialog
        binding.txtEtMealName.editText?.setText(meal.mealName)
        binding.txtEtMealCategory.editText?.setText(meal.category)
        binding.txtEtMealPrice.editText?.setText(meal.price.toString())

        // Display selected days as a string
        selectedOptionsTextView2 = binding.tvSelectedOptions
        selectedOptionsTextView2.text = getDaysAsString(meal)

        // Set checked days in a multi-select dialog or a similar implementation
        var selectedDays = mutableListOf<String>()
        if (meal.saturday) selectedDays.add("Saturday")
        if (meal.sunday) selectedDays.add("Sunday")
        if (meal.monday) selectedDays.add("Monday")
        if (meal.tuesday) selectedDays.add("Tuesday")
        if (meal.wednesday) selectedDays.add("Wednesday")
        if (meal.thursday) selectedDays.add("Thursday")
        if (meal.friday) selectedDays.add("Friday")

        // Show selected days when clicking the TextView
        selectedOptionsTextView2.setOnClickListener {
            showMultiSelectDialog2(selectedDays) // Pass only the selectedDays list
        }


        binding.btnAddMealAdmin.setOnClickListener {
            val name = binding.txtEtMealName.editText?.text.toString()
            val category = binding.txtEtMealCategory.editText?.text.toString()
            val price = binding.txtEtMealPrice.editText?.text.toString()

            if (name.isNotEmpty() && category.isNotEmpty() && price.isNotEmpty()) {
                val updatedMeal = Meal(
                    mealId = meal.mealId,
                    mealName = name,
                    category = category,
                    price = price.toDouble(),
                    saturday = selectedDays.contains("Saturday"),
                    sunday = selectedDays.contains("Sunday"),
                    monday = selectedDays.contains("Monday"),
                    tuesday = selectedDays.contains("Tuesday"),
                    wednesday = selectedDays.contains("Wednesday"),
                    thursday = selectedDays.contains("Thursday"),
                    friday = selectedDays.contains("Friday")
                )

                Log.d(TAG, "createEditMealDialog: ${updatedMeal}")
                updateMeal(updatedMeal) // Ensure this updates the database
                adminMealsAdapter.updateMeal(updatedMeal, position)
                Log.d(TAG, "createDialog: the meal will be updated")
            } else {
                Log.d(TAG, "createDialog: there is a field not filled yet.")
            }
            addMealDialog.dismiss()
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
        addMealDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val items = listOf("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_days_list, items)
        (binding.daysList.editText as? AutoCompleteTextView)?.setAdapter(adapter)


        binding.btnAddMealAdmin.setOnClickListener {
            val name = binding.txtEtMealName.editText?.text.toString()
            val category = binding.txtEtMealCategory.editText?.text.toString()
            val price = binding.txtEtMealPrice.editText?.text.toString()
            val day = (binding.daysList.editText as? AutoCompleteTextView)?.text.toString().lowercase().trim()
            dayForNewMeal = (items.indexOfFirst { it.lowercase() == day } + 1)

            if (name.isNotEmpty() && category.isNotEmpty() && price.isNotEmpty() && dayForNewMeal != -1){
                val newMeal = Meal(mealName = name, category = category, price = price.toDouble())

                selectedDaysNamesList.forEach { day ->
                    when (day.lowercase()) {
                        "saturday" -> newMeal.saturday = true
                        "sunday" -> newMeal.sunday = true
                        "monday" -> newMeal.monday = true
                        "tuesday" -> newMeal.tuesday = true
                        "wednesday" -> newMeal.wednesday = true
                        "thursday" -> newMeal.thursday = true
                        "friday" -> newMeal.friday = true
                    }
                }

                addMeal(newMeal)
                adminMealsAdapter.addMeal(newMeal)

                Log.d(TAG, "createDialog: the meal will be added")
            }else{
                Log.d(TAG, "createDialog: there is field not filled yet.")
            }
            addMealDialog.dismiss()
        }

        selectedOptionsTextView = binding.tvSelectedOptions

        // Set a click listener on the TextView to show the dialog
        selectedOptionsTextView.setOnClickListener {
            showMultiSelectDialog()
        }
    }

    private fun addMeal(newMeal: Meal) {
        controlMealsViewModel.insertMeal(newMeal)

    }

    private fun updateMeal(updatedMeal: Meal) {
        controlMealsViewModel.updateMeal(updatedMeal)

    }


    private fun getViewModelReady(){
        val factory = ControlMealsViewModelFactory(
            ControlMealsRepoImp(
                LocalDataSourceImp(requireContext()
                )
            )
        )
        controlMealsViewModel = ViewModelProvider(this, factory)[ControlMealsViewModel::class.java]
    }



    private fun showMultiSelectDialog() {
        // Create an AlertDialog to show the multi-select options
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select Days")

        // Set multi-choice items
        builder.setMultiChoiceItems(options, selectedItems) { dialog, which, isChecked ->
            selectedItems[which] = isChecked
        }

        // Set the positive button action
        builder.setPositiveButton("OK") { dialog, _ ->
            selectedDaysIdList.clear()

            for (i in options.indices) {
                if (selectedItems[i]) {
                    selectedDaysIdList.add(i + 1)
                    selectedDaysNamesList.add(options[i].lowercase())
                }
            }

            updateSelectedOptionsTextView()
        }

        // Set the negative button action
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }

        // Show the dialog
        val dialog = builder.create()
        dialog.show()
    }

    private fun showMultiSelectDialog2(selectedDays: MutableList<String>) {
        // Create an AlertDialog to show the multi-select options
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select Days")

        // Initialize selected items based on the passed selectedDays
        val selectedItems_ = BooleanArray(options.size) { false }
        selectedDays.forEach { day ->
            val index = options.indexOfFirst { it.equals(day, ignoreCase = true) }
            if (index != -1) {
                selectedItems_[index] = true // Mark this day as selected
            }
        }

        // Set multi-choice items
        builder.setMultiChoiceItems(options, selectedItems_) { dialog, which, isChecked ->
            selectedItems_[which] = isChecked
        }

        // Set the positive button action
        builder.setPositiveButton("OK") { dialog, _ ->
            selectedDays.clear() // Clear the existing days
            selectedDaysIdList2.clear()
            selectedDaysNamesList2.clear() // Clear the previous names list

            for (i in options.indices) {
                if (selectedItems_[i]) {
                    selectedDaysIdList2.add(i + 1) // Assuming the ID starts from 1
                    selectedDaysNamesList2.add(options[i].lowercase())
                    selectedDays.add(options[i]) // Update selectedDays directly
                }
            }

            updateSelectedOptionsTextView2()
        }

        // Set the negative button action
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }

        // Show the dialog
        val dialog = builder.create()
        dialog.show()
    }



    private fun updateSelectedOptionsTextView() {
        if (selectedDaysIdList.isEmpty()) {
            selectedOptionsTextView.text = "Select Days"
        } else {
            selectedOptionsTextView.text = selectedDaysNamesList.joinToString(", ")
        }
    }

    private fun updateSelectedOptionsTextView2() {
        if (selectedDaysIdList2.isEmpty()) {
            selectedOptionsTextView2.text = "Select Days"
        } else {
            selectedOptionsTextView2.text = selectedDaysNamesList2.joinToString(", ")
        }
    }

    private fun getDaysAsString(meal: Meal): String {
        val days = mutableListOf<String>()
        if (meal.saturday) days.add("Saturday")
        if (meal.sunday) days.add("Sunday")
        if (meal.monday) days.add("Monday")
        if (meal.tuesday) days.add("Tuesday")
        if (meal.wednesday) days.add("Wednesday")
        if (meal.thursday) days.add("Thursday")
        if (meal.friday) days.add("Friday")
        return days.joinToString(", ")
    }
}