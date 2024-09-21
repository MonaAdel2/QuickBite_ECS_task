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
import com.mona.adel.quickbite.ui.factories.ControlMealsViewModelFactory
import com.mona.adel.quickbite.ui.viewModels.ControlMealsViewModel


class ControlMealsFragment : Fragment() {

    private val TAG = "ControlMealsFragment"
    private lateinit var binding: FragmentControlMealsBinding

    private lateinit var addMealDialog: Dialog
    private lateinit var controlMealsViewModel: ControlMealsViewModel
    private lateinit var adminMealsAdapter: AdminMealsAdapter

    private var dayForNewMeal = -1

    // Define the options and a list to store the selected ones
    private val options = arrayOf("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
    private val selectedItems = booleanArrayOf(false, false, false, false, false, false, false)
    private val selectedDaysIdList = mutableListOf<Int>()
    private val selectedDaysNamesList = mutableListOf<String>()

    private lateinit var selectedOptionsTextView: TextView

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
                addDayOfMeal(id)
            }else{
                Log.d(TAG, "onViewCreated: good game!!")
            }
        }

        controlMealsViewModel.getAllMeals()
        controlMealsViewModel.meals.observe(requireActivity()){meals->
            if (meals!=null){

                adminMealsAdapter = AdminMealsAdapter(
                    { meal -> controlMealsViewModel.deleteMeal(meal) },
                    { mealId -> controlMealsViewModel.getDaysByMeal(mealId) }
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

//        controlMealsViewModel.days.observe(viewLifecycleOwner) { mealWithDays ->
//            mealWithDays?.let {
//                adminMealsAdapter.updateMealDays(it)
//            }
//        }

        controlMealsViewModel.allMealsWithDays.observe(viewLifecycleOwner) { list ->
            list?.let {

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

        val items = listOf("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_days_list, items)
        (binding.daysList.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.txtEtMealName.editText?.setText(meal.mealName)
        binding.txtEtMealCategory.editText?.setText(meal.category)
//        (binding.daysList.editText as? AutoCompleteTextView)?.setText(meal.category)
        binding.txtEtMealPrice.editText?.setText(meal.price.toString())



        binding.btnAddMealAdmin.setOnClickListener {
            val name = binding.txtEtMealName.editText?.text.toString()
            val category = binding.txtEtMealCategory.editText?.text.toString()
            val price = binding.txtEtMealPrice.editText?.text.toString()
            val day = (binding.daysList.editText as? AutoCompleteTextView)?.text.toString().lowercase().trim()
            dayForNewMeal = (items.indexOfFirst { it.lowercase() == day } + 1)

            if (name.isNotEmpty() && category.isNotEmpty() && price.isNotEmpty() && dayForNewMeal != -1){
                val updatedMeal = Meal(mealId = meal.mealId, mealName = name, category = category, price = price.toDouble())

                Log.d(TAG, "createEditMealDialog: ${updatedMeal}")
                updateMeal(updatedMeal)
                adminMealsAdapter.updateMeal(updatedMeal , position)

                Log.d(TAG, "createDialog: the meal will be updated")
            }else{
                Log.d(TAG, "createDialog: there is field not filled yet.")
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

    private fun addDayOfMeal(id:Long){
        for (day in selectedDaysIdList){
            controlMealsViewModel.insertDayOfNewMeal(MealDayCrossRef(id, day))
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
                    selectedDaysNamesList.add(options[i])
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
        if (selectedDaysIdList.isEmpty()) {
            selectedOptionsTextView.text = "Select Days"
        } else {
            selectedOptionsTextView.text = selectedDaysNamesList.joinToString(", ")
        }
    }
}