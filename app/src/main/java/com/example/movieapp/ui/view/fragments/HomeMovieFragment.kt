package com.example.movieapp.ui.view.fragments


import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.HomeMovieFragmentBinding
import com.example.movieapp.ui.view.adapter.MoviePagingPopularAdapter
import com.example.movieapp.ui.view.adapter.MovieSearchPagingAdapter
import com.example.movieapp.ui.view.adapter.MoviePagingTopRatedAdapter
import com.example.movieapp.ui.view.adapter.ReposLoadStateAdapter
import com.example.movieapp.ui.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeMovieFragment : Fragment() {

    companion object {
        fun newInstance() = HomeMovieFragment()
    }

    private lateinit var homeMovieFragmentBinding: HomeMovieFragmentBinding

    private val moviesViewModel: MoviesViewModel by viewModels()

    private lateinit var layoutManager : LinearLayoutManager

    private lateinit var moviePagingTopRatedAdapter: MoviePagingTopRatedAdapter
    private lateinit var moviePagingPopularAdapter: MoviePagingPopularAdapter

    private lateinit var movieSearchPagingAdapter: MovieSearchPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeMovieFragmentBinding = HomeMovieFragmentBinding.inflate(inflater,container ,false)
        return homeMovieFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerViewTopRated()
        initRecyclerViewPopular()
        initRecyclerViewShearch()

        loadDataTopRated()
        loadDataPopular()

        moviesViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            homeMovieFragmentBinding.progressBar.isVisible = it
        })

        setHasOptionsMenu(true)
    }



    private fun initRecyclerViewShearch() {

        movieSearchPagingAdapter = MovieSearchPagingAdapter()
        homeMovieFragmentBinding.retryButton.setOnClickListener { movieSearchPagingAdapter.retry() }
        homeMovieFragmentBinding.rvMoviesSearch.apply {
            layoutManager=LinearLayoutManager(context, RecyclerView.VERTICAL,false)
            adapter = movieSearchPagingAdapter.withLoadStateHeaderAndFooter(
                header = ReposLoadStateAdapter { movieSearchPagingAdapter.retry() },
                footer = ReposLoadStateAdapter { movieSearchPagingAdapter.retry() }
            )

            movieSearchPagingAdapter.addLoadStateListener { loadState ->
                // show empty list
                val isListEmpty = loadState.refresh is LoadState.NotLoading && movieSearchPagingAdapter.itemCount == 0
                // showEmptyList(isListEmpty)

                // Only show the list if refresh succeeds.
                homeMovieFragmentBinding.rvMoviesSearch.isVisible = loadState.source.refresh is LoadState.NotLoading
                // Show loading spinner during initial load or refresh.
                homeMovieFragmentBinding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                // Show the retry state if initial load or refresh fails.
                homeMovieFragmentBinding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

                // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        context,
                        "\uD83D\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }


    }


    private fun initRecyclerViewTopRated() {

        moviePagingTopRatedAdapter = MoviePagingTopRatedAdapter()


        homeMovieFragmentBinding.rvMoviesTopRated.apply {
            layoutManager= LinearLayoutManager(context, RecyclerView.HORIZONTAL,false)
        adapter = moviePagingTopRatedAdapter

        }

    }


    private fun initRecyclerViewPopular() {
        moviePagingPopularAdapter =MoviePagingPopularAdapter()
        homeMovieFragmentBinding.retryButton.setOnClickListener { moviePagingPopularAdapter.retry() }
        homeMovieFragmentBinding.rvMoviesPopularity.apply {

            layoutManager= LinearLayoutManager(context, RecyclerView.VERTICAL,false)

            adapter = moviePagingPopularAdapter.withLoadStateHeaderAndFooter(
                header = ReposLoadStateAdapter { moviePagingPopularAdapter.retry() },
                footer = ReposLoadStateAdapter { moviePagingPopularAdapter.retry() }
            )

           moviePagingPopularAdapter.addLoadStateListener { loadState ->

                // show empty list
                val isListEmpty = loadState.refresh is LoadState.NotLoading && moviePagingPopularAdapter.itemCount == 0
               // showEmptyList(isListEmpty)

                // Only show the list if refresh succeeds.
                homeMovieFragmentBinding.rvMoviesPopularity.isVisible = loadState.source.refresh is LoadState.NotLoading
                // Show loading spinner during initial load or refresh.
                homeMovieFragmentBinding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                // Show the retry state if initial load or refresh fails.
                homeMovieFragmentBinding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

                // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        context,
                        "\uD83D\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }


        }
    }

    private fun loadDataTopRated() {

        lifecycleScope.launch {
            moviesViewModel.topRatedMovies().collect {
                moviePagingTopRatedAdapter.submitData(it)

            }

        }

    }
    private fun loadDataPopular() {

        lifecycleScope.launch {
            moviesViewModel.popularMovies().collect {
                Log.d("aaa", "load: $it")
                moviePagingPopularAdapter.submitData(it)

            }

        }
    }


    private fun loadDataSearch(query:String) {

        lifecycleScope.launch {
            moviesViewModel.searchMovie(query).collect {
                movieSearchPagingAdapter.submitData(it)

            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home,menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true

            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    homeMovieFragmentBinding.tvTitleTopRated.isVisible =false
                    homeMovieFragmentBinding.rvMoviesTopRated.isVisible =false
                    homeMovieFragmentBinding.tvTitlePopularity.isVisible =false
                    homeMovieFragmentBinding.rvMoviesPopularity.isVisible =false

                    homeMovieFragmentBinding.rvMoviesSearch.isVisible =true
                  //  homeMovieFragmentBinding.rvMoviesSearch.scrollToPosition(0)


                    loadDataSearch(query)

                    //  binding.recyclerView.scrollToPosition(0)
                    // viewModel.searchPhotos(query)
                  //  searchView.clearFocus()

                }


                    return true
            }
        })

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                homeMovieFragmentBinding.tvTitleTopRated.isVisible =true
                homeMovieFragmentBinding.rvMoviesTopRated.isVisible =true
                homeMovieFragmentBinding.tvTitlePopularity.isVisible =true
                homeMovieFragmentBinding.rvMoviesPopularity.isVisible =true
                homeMovieFragmentBinding.rvMoviesSearch.isVisible =false
                return true // Return true to collapse action view
            }

            override    fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                homeMovieFragmentBinding.tvTitleTopRated.isVisible =true
                homeMovieFragmentBinding.rvMoviesTopRated.isVisible =true
                homeMovieFragmentBinding.tvTitlePopularity.isVisible =true
                homeMovieFragmentBinding.rvMoviesPopularity.isVisible =true
                homeMovieFragmentBinding.rvMoviesSearch.isVisible =false

                return true // Return true to expand action view
            }
        })

    }



}