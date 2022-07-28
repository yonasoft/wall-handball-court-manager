package com.example.handballcourtmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.handballcourtmanager.R
import com.example.handballcourtmanager.databinding.FragmentCreateMatchDialogBinding
import com.example.handballcourtmanager.viewmodel.MatchesViewModel

class CreateMatchDialogFragment:DialogFragment(){

    lateinit var binding: FragmentCreateMatchDialogBinding
    private val viewModel:MatchesViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(layoutInflater,
            R.layout.fragment_create_match_dialog,container,false)
        val view=binding.root
        binding.viewModel = viewModel
        val spinnerItems = List(33){ i ->i }
        val arrayAdapter:ArrayAdapter<Int> = ArrayAdapter(this.context!!,android.R.layout.simple_spinner_dropdown_item,spinnerItems)
        binding.spinnerSingles.adapter = arrayAdapter
        binding.spinnerDoubles.adapter = arrayAdapter
        binding.spinnerTriangles.adapter = arrayAdapter



        binding.btnOk.setOnClickListener{
            viewModel.numOfSinglesToAdd.value = binding.spinnerSingles.selectedItem as Int
            viewModel.numOfDoublesToAdd.value = binding.spinnerDoubles.selectedItem as Int
            viewModel.numOfTrianglesToAdd.value = binding.spinnerTriangles.selectedItem as Int
            viewModel.addMatches()
            dismiss()
        }

        return view
    }


}