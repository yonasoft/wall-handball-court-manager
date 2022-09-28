package com.yonasoft.handballcourtmanager.fragments.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.yonasoft.handballcourtmanager.R
import com.yonasoft.handballcourtmanager.databinding.FragmentHelpBinding

//Help fragment for help and faq related to this app
class HelpFragment : Fragment() {

    private var binding: FragmentHelpBinding ? =null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_help, container, false)
        val view = binding!!.root

        binding!!.queueDesc.visibility = View.GONE
        binding!!.matchDesc.visibility = View.GONE
        binding!!.resultsDesc.visibility = View.GONE

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.tvQueueTitle.setOnClickListener {
            if (binding!!.queueDesc.visibility == View.GONE) {
                binding!!.queueDesc.visibility = View.VISIBLE
            } else {
                binding!!.queueDesc.visibility = View.GONE
            }
        }
        binding!!.queueDrop.setOnClickListener{
            if (binding!!.queueDesc.visibility == View.GONE) {
                binding!!.queueDesc.visibility = View.VISIBLE
            } else {
                binding!!.queueDesc.visibility = View.GONE
            }
        }

        binding!!.tvMatchTitle.setOnClickListener {
            if (binding!!.matchDesc.visibility == View.GONE) {
                binding!!.matchDesc.visibility = View.VISIBLE
            } else {
                binding!!.matchDesc.visibility = View.GONE
            }
        }
        binding!!.matchDrop.setOnClickListener{
            if (binding!!.matchDesc.visibility == View.GONE) {
                binding!!.matchDesc.visibility = View.VISIBLE
            } else {
                binding!!.matchDesc.visibility = View.GONE
            }
        }

        binding!!.tvResultsTitle.setOnClickListener {
            if (binding!!.resultsDesc.visibility == View.GONE) {
                binding!!.resultsDesc.visibility = View.VISIBLE
            } else {
                binding!!.resultsDesc.visibility = View.GONE
            }
        }
        binding!!.resultsDrop.setOnClickListener{
            if (binding!!.resultsDesc.visibility == View.GONE) {
                binding!!.resultsDesc.visibility = View.VISIBLE
            } else {
                binding!!.resultsDesc.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}