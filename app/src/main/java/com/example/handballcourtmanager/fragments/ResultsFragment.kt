package com.example.handballcourtmanager.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.handballcourtmanager.R
import com.example.handballcourtmanager.databinding.FragmentResultsBinding

class ResultsFragment : Fragment() {

    private var binding:FragmentResultsBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_results,container,false)
        val view = binding!!.root
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.results_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.help_item-> findNavController().navigate(
                R.id.action_resultsFragment_to_helpFragment
            )

        }
        return super.onOptionsItemSelected(item)
    }

}