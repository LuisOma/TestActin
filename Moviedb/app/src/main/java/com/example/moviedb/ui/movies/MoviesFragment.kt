package com.example.moviedb.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.constants.Constants
import com.example.moviedb.data.entities.Movie
import com.example.moviedb.databinding.MoviesFragmentBinding
import com.example.moviedb.ui.adapters.MovieAdapter
import com.example.moviedb.ui.moviedetail.MovieDetailFragment
import com.example.moviedb.utils.Resource
import com.example.moviedb.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment(), MovieAdapter.itemClickListener {

    private var binding: MoviesFragmentBinding by autoCleared()
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MoviesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObservers()
    }

    private fun setupView() {
        adapter = MovieAdapter(requireContext(), arrayListOf()).apply {
            setItemListener(this@MoviesFragment)
        }
        binding.moviesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.moviesRv.setHasFixedSize(true)
        binding.moviesRv.adapter = adapter

        binding.moviesRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    binding.fabScroll.show()
                } else {
                    binding.fabScroll.hide()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        binding.fabScroll.setOnClickListener { view ->
            binding.moviesRv.post {
                binding.moviesRv.smoothScrollToPosition(0)
            }
        }

        binding.countrySearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun setupObservers() {
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.addItems(ArrayList(it.data))
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }

    override fun onItemClicked(movie: Movie) {
        showEditDialog(movie.id)
    }

    private fun showEditDialog(idMovie: Int) {
        val fm: FragmentManager = parentFragmentManager
        val editNameDialogFragment: MovieDetailFragment =
            MovieDetailFragment.newInstance(idMovie)
        editNameDialogFragment.show(fm, "fragment_detail")
    }
}
