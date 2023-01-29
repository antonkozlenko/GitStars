package org.akozlenko.gitstars.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import org.akozlenko.gitstars.databinding.FragmentSearchRepositoriesBinding
import org.akozlenko.gitstars.ui.search.adapters.results.SearchRepositoriesAdapter
import org.akozlenko.gitstars.ui.search.adapters.state.SearchStateAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchRepositoriesFragment : Fragment() {

    private val viewModel: SearchRepositoriesViewModel by viewModel()

    private lateinit var searchAdapter : SearchRepositoriesAdapter
    private lateinit var searchStateAdapter : SearchStateAdapter

    private var binding: FragmentSearchRepositoriesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSearchRepositoriesBinding.inflate(
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
        searchAdapter = SearchRepositoriesAdapter()
        searchStateAdapter = SearchStateAdapter {
            searchAdapter.retry()
        }
        initViews()
        // Load
        loadPageContent()
    }

    private fun initViews() {
        // Search results
        binding?.searchResults?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter.withLoadStateFooter(searchStateAdapter)
        }

    }

    private fun loadPageContent() {
        viewModel.loadPageContent().observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                searchAdapter.submitData(it)
            }
        }
    }

    companion object {
        fun newInstance() = SearchRepositoriesFragment()
    }

}
