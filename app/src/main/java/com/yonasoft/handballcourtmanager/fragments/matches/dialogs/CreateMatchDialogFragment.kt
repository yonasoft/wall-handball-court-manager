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
class CreateMatchDialogFragment : DialogFragment() {

    private val viewModel: MatchesViewModel by viewModels()

    private var _binding: FragmentCreateMatchDialogBinding? = null
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_match_dialog, container, false)
        return binding.root.apply {
            setupBinding()
            setupSpinner()
            setupOkButton()
        }
    }

    private fun setupBinding() {
        binding.viewModel = viewModel
    }

    private fun setupSpinner() {
        val spinnerItems = List(33) { i -> i }
        val spinnerAdapter: ArrayAdapter<Int> = ArrayAdapter(this@CreateMatchDialogFragment.requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItems)

        binding.spinnerSingles.adapter = spinnerAdapter
        binding.spinnerDoubles.adapter = spinnerAdapter
        binding.spinnerTriangles.adapter = spinnerAdapter
    }

    private fun setupOkButton() {
        binding.btnOk.setOnClickListener {
            viewModel.apply {
                numOfSinglesToAdd.value = binding.spinnerSingles.selectedItem as Int
                numOfDoublesToAdd.value = binding.spinnerDoubles.selectedItem as Int
                numOfTrianglesToAdd.value = binding.spinnerTriangles.selectedItem as Int
                addMatches()
            }
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
