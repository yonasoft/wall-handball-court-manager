package com.yonasoft.handballcourtmanager.fragments.matches.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.yonasoft.handballcourtmanager.R
import com.yonasoft.handballcourtmanager.databinding.FragmentCreateMatchDialogBinding
import com.yonasoft.handballcourtmanager.fragments.matches.viewmodel.MatchesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateMatchDialogFragment:DialogFragment(){

    private var binding: FragmentCreateMatchDialogBinding?=null
    private val viewModel: MatchesViewModel by viewModels()

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
        val view=binding!!.root

        binding!!.viewModel = viewModel

        //Creates spinner items aka the numbers that will show in the spinner
        val spinnerItems = List(33){ i ->i }
        val arrayAdapter:ArrayAdapter<Int> = ArrayAdapter(this.requireContext(),android.R.layout.simple_spinner_dropdown_item,spinnerItems)

        //Adapter that applies spinner for each individual spinner for each match type
        binding!!.spinnerSingles.adapter = arrayAdapter
        binding!!.spinnerDoubles.adapter = arrayAdapter
        binding!!.spinnerTriangles.adapter = arrayAdapter

        //Creates x number of matches based on the what is selected in the spinner for each.
        // Each match type has it's own spinner so number of matches can be different for each type
        //Dismisses dialog when done
        binding!!.btnOk.setOnClickListener{
            viewModel.numOfSinglesToAdd.value = binding!!.spinnerSingles.selectedItem as Int
            viewModel.numOfDoublesToAdd.value = binding!!.spinnerDoubles.selectedItem as Int
            viewModel.numOfTrianglesToAdd.value = binding!!.spinnerTriangles.selectedItem as Int
            viewModel.addMatches()
            dismiss()
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}