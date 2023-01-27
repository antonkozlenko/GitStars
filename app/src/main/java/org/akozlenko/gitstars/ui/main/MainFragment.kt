package org.akozlenko.gitstars.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.akozlenko.gitstars.databinding.FragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()

    private var binding: FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMainBinding.inflate(
            inflater,
            container,
            false
        ).also {
            // Init binding
            binding = it
        }.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Reset binding
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        // Load
        loadPageContent()
    }

    private fun initViews() {
        // TODO
    }

    private fun initObservers() {
        // TODO
    }

    private fun loadPageContent() {
        viewModel.loadPageContent()
    }

    companion object {
        fun newInstance() = MainFragment()
    }

}
