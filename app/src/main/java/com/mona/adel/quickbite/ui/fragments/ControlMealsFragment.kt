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
import androidx.lifecycle.ViewModelProvider
import com.mona.adel.quickbite.R
import com.mona.adel.quickbite.data.LocalDataSourceImp
import com.mona.adel.quickbite.data.model.Meal
import com.mona.adel.quickbite.data.relations.MealDayCrossRef
import com.mona.adel.quickbite.data.repository.ControlMealsRepoImp
import com.mona.adel.quickbite.databinding.DialogAddMealBinding
import com.mona.adel.quickbite.databinding.FragmentControlMealsBinding
import com.mona.adel.quickbite.ui.factories.ControlMealsViewModelFactory
import com.mona.adel.quickbite.ui.viewModels.ControlMealsViewModel


class ControlMealsFragment : Fragment() {

    private val TAG = "ControlMealsFragment"
    private lateinit var binding: FragmentControlMealsBinding

    private lateinit var addMealDialog: Dialog
    private lateinit var controlMealsViewModel: ControlMealsViewModel

    private var dayForNewMeal = -1

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
                Log.d(TAG, "createDialog: the meal will be added")
            }else{
                Log.d(TAG, "createDialog: there is field not filled yet.")
            }
            addMealDialog.dismiss()
        }
    }

    private fun addMeal(newMeal: Meal) {
        controlMealsViewModel.insertMeal(newMeal)

    }

    private fun addDayOfMeal(id: Long?){
        if (id!= null){
            controlMealsViewModel.insertDayOfNewMeal(MealDayCrossRef(id, dayForNewMeal))
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