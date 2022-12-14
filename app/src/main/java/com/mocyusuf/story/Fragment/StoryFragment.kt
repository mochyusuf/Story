package com.mocyusuf.story.Fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.mocyusuf.story.Activity.AddStory
import com.mocyusuf.story.Adapter.LoadingStateAdapter
import com.mocyusuf.story.Adapter.StoryAdapter
import com.mocyusuf.story.R
import com.mocyusuf.story.Utils.PrefsManager
import com.mocyusuf.story.ViewModel.HomeViewModel
import com.mocyusuf.story.databinding.FragmentStoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalPagingApi
class StoryFragment : Fragment() {
    private var _binding: FragmentStoryBinding? = null
    private val binding get() = _binding

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var prefsManager: PrefsManager
    private lateinit var storyAdapter: StoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStoryBinding.inflate(LayoutInflater.from(requireActivity()))
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefsManager = PrefsManager(requireContext())
        storyAdapter = StoryAdapter()
        (activity as AppCompatActivity).apply {
            setSupportActionBar(binding?.toolbar)
            supportActionBar?.title = resources.getString(R.string.storyapp)
        }
        binding?.toolbar?.apply {
            setTitleTextColor(Color.WHITE)
            setSubtitleTextColor(Color.WHITE)
        }
        setHasOptionsMenu(true)
        setPagingData()
        getData()
        binding?.fabAddStory?.setOnClickListener {
            startActivity(Intent(requireContext(), AddStory::class.java))
        }
    }

    private fun getData() {
        viewModel.getStories(prefsManager.token).observe(viewLifecycleOwner) {
            storyAdapter.submitData(lifecycle, it)
        }
    }

    private fun setPagingData() {
        binding?.apply {
            rvStory.setHasFixedSize(true)
            rvStory.layoutManager = LinearLayoutManager(requireContext())
            rvStory.adapter = storyAdapter.withLoadStateHeaderAndFooter(
                header = LoadingStateAdapter { storyAdapter.retry() },
                footer = LoadingStateAdapter { storyAdapter.retry() }
            )
            swipeRefresh.setOnRefreshListener {
                storyAdapter.refresh()
                swipeRefresh.isRefreshing = false
                rvStory.visibility = View.GONE
            }
            btnTry.setOnClickListener {
                storyAdapter.retry()
            }
        }

        storyAdapter.addLoadStateListener {
            binding?.apply {
                progressBar.isVisible = it.source.refresh is LoadState.Loading
                rvStory.isVisible = it.source.refresh is LoadState.NotLoading
                tvError.isVisible = it.source.refresh is LoadState.Error
                btnTry.isVisible = it.source.refresh is LoadState.Error

                //not found
                if (it.source.refresh is LoadState.NotLoading &&
                    it.append.endOfPaginationReached &&
                    storyAdapter.itemCount < 1
                ) {
                    tvNotFound.isVisible = true
                    tvError.isVisible = true
                    rvStory.isVisible = false
                } else {
                    tvNotFound.isVisible = false
                    tvError.isVisible = false
                    rvStory.isVisible = true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}