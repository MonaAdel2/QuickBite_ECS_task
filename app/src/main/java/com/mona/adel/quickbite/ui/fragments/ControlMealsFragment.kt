package com.mona.adel.quickbite.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mona.adel.quickbite.R
import com.mona.adel.quickbite.data.LocalDataSourceImp
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.repository.ControlMealsRepoImp
import com.mona.adel.quickbite.databinding.DialogMealDataBinding
import com.mona.adel.quickbite.databinding.FragmentControlMealsBinding
import com.mona.adel.quickbite.ui.adapters.AdminMealsAdapter
import com.mona.adel.quickbite.ui.factories.ControlMealsViewModelFactory
import com.mona.adel.quickbite.ui.viewModels.ControlMealsViewModel


class ControlMealsFragment : Fragment() {

    private val TAG = "ControlMealsFragment"
    private lateinit var binding: FragmentControlMealsBinding

    private lateinit var mealDialog: Dialog
    private lateinit var controlMealsViewModel: ControlMealsViewModel
    private lateinit var adminMealsAdapter: AdminMealsAdapter

    private val categoriesOptions = listOf("Desserts", "Drinks", "Chicken" ,"Soups", "Salads", "Seafood", "Pasta", "Bakery")

    // Define the days' options and a list to store the selected days
    private val daysOptions = arrayOf("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
    private val selectedDays = booleanArrayOf(false, false, false, false, false, false, false)
    private val selectedDaysNamesList = mutableListOf<String>()

    private lateinit var tvDialogSelectedDays: TextView

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

        mealDialog = Dialog(requireActivity()) // Create a dialog object

        binding.btnAddMealAdmin.setOnClickListener {
            showAddMealDialog()
        }

        controlMealsViewModel.getAllMeals() // Retrieve all the meals from database

        // Observe the retrieved meals
        controlMealsViewModel.meals.observe(requireActivity()){meals->
            if (meals!=null){

                if (meals.isEmpty()){
                    binding.tvNoMealsAdmin.visibility = View.VISIBLE

                }else{
                    binding.tvNoMealsAdmin.visibility = View.GONE

                    adminMealsAdapter = AdminMealsAdapter(
                        { meal -> controlMealsViewModel.deleteMeal(meal) }
                    )

                    adminMealsAdapter.setData(meals) // Send the meals to the adapter
                    adminMealsAdapter.onItemClick={meal, position-> // Callback of clicking the meal item
                        showEditMealDialog(meal, position)
                    }

                    // Setup the RecyclerView
                    binding.rvMealsControlAdmin.adapter = adminMealsAdapter
                    binding.rvMealsControlAdmin.layoutManager = LinearLayoutManager(requireContext())
                }
                
                
            }else{
                Log.d(TAG, "onViewCreated: the meals from database is null")
            }
        }


    }


    // --------------------- Add Meal: -----------------------
    private fun showAddMealDialog() {
        createAddMealDialog()
        mealDialog.show()
    }

    private fun createAddMealDialog(){
        // Clear the lists
        selectedDaysNamesList.clear()
        selectedDays.fill(false)

        val binding = DialogMealDataBinding.inflate(layoutInflater) // Use ViewBinding

        mealDialog.setContentView(binding.root)
        mealDialog.setCancelable(true)
        mealDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Set exposed dropDown menu for categories
        val adapter = ArrayAdapter(requireContext(), R.layout.item_categories_list, categoriesOptions)
        (binding.categoryListOptions.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        // Update the dialog labels
        binding.tvTitleMealDialog.text = "Add new Meal"
        binding.btnSubmitMealAdmin.text = "Add"


        binding.btnSubmitMealAdmin.setOnClickListener {
            // Collect the data from the edit text fields
            val name = binding.txtEtMealName.editText?.text.toString()
            val category = (binding.categoryListOptions.editText as? AutoCompleteTextView)?.text.toString()
            val price = binding.txtEtMealPrice.editText?.text.toString()

            // Check that the required fields are filled
            if (name.isNotEmpty() && category.isNotEmpty() && price.isNotEmpty()) {
                // Create a new meal object and pass it for addMeal method
                val newMeal = Meal(mealName = name, category = category, price = price.toDouble(), days = selectedDaysNamesList)

                addMeal(newMeal)
                adminMealsAdapter.addMeal(newMeal) // To update the UI with the new meal

            } else {
                Log.d(TAG, "createAddMealDialog: there is a field not filled yet.")
            }

            mealDialog.dismiss() // Close the dialog after pressing "Add"
        }


        tvDialogSelectedDays = binding.tvSelectedOptions

        // Set a click listener on the TextView to show the dialog
        tvDialogSelectedDays.setOnClickListener {
            showMultiSelectDialog()
        }
    }

    private fun addMeal(newMeal: Meal) {
        controlMealsViewModel.insertMeal(newMeal)

    }

    private fun showMultiSelectDialog() {
        // Create an AlertDialog to show the multi-select options
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select Days")

        // Set multi-choice items
        builder.setMultiChoiceItems(daysOptions, selectedDays) { dialog, which, isChecked ->
            selectedDays[which] = isChecked
        }

        // Set the positive button action
        builder.setPositiveButton("OK") { dialog, _ ->

            for (i in daysOptions.indices) {
                if (selectedDays[i]) {
                    selectedDaysNamesList.add(daysOptions[i].lowercase())
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

    private fun updateSelectedOptionsTextView() {
        if (selectedDaysNamesList.isEmpty()) {
            tvDialogSelectedDays.text = "Click to Select Days"
        } else {
            tvDialogSelectedDays.text = selectedDaysNamesList.joinToString(", ")
                                    { day -> day.replaceFirstChar { it.uppercase() } }
        }
    }



    // --------------------- Update Meal: ----------------------
    private fun updateMeal(updatedMeal: Meal) {
        controlMealsViewModel.updateMeal(updatedMeal)

    }

    private fun showEditMealDialog(meal: Meal, position: Int) {
        createEditMealDialog(meal, position)
        mealDialog.show()
    }

    private fun createEditMealDialog(meal: Meal, position: Int) {

        val binding = DialogMealDataBinding.inflate(layoutInflater)
        mealDialog.setContentView(binding.root)
        mealDialog.setCancelable(true)
        mealDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Set exposed dropDown menu for categories
        val adapter = ArrayAdapter(requireContext(), R.layout.item_categories_list, categoriesOptions)
        (binding.categoryListOptions.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        // set the dialog labels
        binding.tvTitleMealDialog.text = "Update Meal's details"
        binding.btnSubmitMealAdmin.text = "Update"

        // Set meal details in the dialog
        binding.txtEtMealName.editText?.setText(meal.mealName)
        (binding.categoryListOptions.editText as? AutoCompleteTextView)?.setText(meal.category, false)
        binding.txtEtMealPrice.editText?.setText(meal.price.toString())

        // Display selected days list as a string
        tvDialogSelectedDays = binding.tvSelectedOptions
        tvDialogSelectedDays.text = meal.days
            .joinToString(", ") { day -> day.replaceFirstChar { it.uppercase() } }


        // Initialize the selectedDaysNamesList for the current meal
        val selectedDaysNamesList = meal.days.toMutableList()

        // Show selected days when clicking the TextView
        tvDialogSelectedDays.setOnClickListener {
            showMultiSelectDialog(selectedDaysNamesList) // Pass the list to the dialog
        }

        binding.btnSubmitMealAdmin.setOnClickListener {
            // Collect the data from the edit text fields
            val name = binding.txtEtMealName.editText?.text.toString()
            val category = (binding.categoryListOptions.editText as? AutoCompleteTextView)?.text.toString()
            val price = binding.txtEtMealPrice.editText?.text.toString()

            // Check that the required fields are filled
            if (name.isNotEmpty() && category.isNotEmpty() && price.isNotEmpty()) {
                // Create a meal object and pass it for updateMeal method
                val updatedMeal = Meal(
                    mealId = meal.mealId,
                    mealName = name,
                    category = category,
                    price = price.toDouble(),
                    days = selectedDaysNamesList
                )

                updateMeal(updatedMeal)
                adminMealsAdapter.updateMeal(updatedMeal, position)  // To update the UI with the updated meal
            } else {
                Log.d(TAG, "createAddMealDialog: there is a field not filled yet.")
            }
            mealDialog.dismiss() // Close the dialog after pressing "Add"
        }
    }

    private fun showMultiSelectDialog(selectedDays: MutableList<String>) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select Days")

        // Initialize selectedItems2 as a local variable
        val selectedItems2 = BooleanArray(daysOptions.size) { false }

        // Set selected items based on the passed selectedDays
        selectedDays.forEach { day ->
            val index = daysOptions.indexOfFirst { it.equals(day, ignoreCase = true) }
            if (index != -1) {
                selectedItems2[index] = true // Mark this day as selected
            }
        }

        // Set multi-choice items
        builder.setMultiChoiceItems(daysOptions, selectedItems2) { _, which, isChecked ->
            selectedItems2[which] = isChecked
        }

        // Set the positive button action
        builder.setPositiveButton("OK") { _, _ ->
            selectedDays.clear() // Clear the existing days

            for (i in daysOptions.indices) {
                if (selectedItems2[i]) {
                    selectedDays.add(daysOptions[i]) // Update selectedDays directly
                }
            }

            updateSelectedDaysTextView(selectedDays) // Pass the updated list to function
        }

        // Set the negative button action
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }

        // Show the dialog
        val dialog = builder.create()
        dialog.show()
    }

    private fun updateSelectedDaysTextView(selectedDaysNames: MutableList<String>) {
        if (selectedDaysNames.isEmpty()) {
            tvDialogSelectedDays.text = "Click to Select Days"
        } else {
            tvDialogSelectedDays.text = selectedDaysNames.joinToString(", ")
                                    { day -> day.replaceFirstChar { it.uppercase() } }
        }
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







}