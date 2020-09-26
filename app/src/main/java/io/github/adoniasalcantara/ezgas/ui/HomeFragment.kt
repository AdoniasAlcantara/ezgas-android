package io.github.adoniasalcantara.ezgas.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.tabs.TabLayoutMediator
import io.github.adoniasalcantara.ezgas.R
import io.github.adoniasalcantara.ezgas.databinding.FragmentHomeBinding
import io.github.adoniasalcantara.ezgas.ui.HomeFragmentDirections.Companion.startFavoritesFilter
import io.github.adoniasalcantara.ezgas.ui.HomeFragmentDirections.Companion.startStationsFilter
import io.github.adoniasalcantara.ezgas.ui.favorites.FavoritesFragment
import io.github.adoniasalcantara.ezgas.ui.stations.StationsFragment

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val navController by lazy { findNavController() }
    private val binding: FragmentHomeBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpToolbar()
        setUpPagerWithTabs()
        setUpFab()
    }

    private fun setUpToolbar() {
        binding.toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.report_problem) {
                // TODO navigate to report screen
                true
            }

            false
        }
    }

    private fun setUpPagerWithTabs() {
        val titles = arrayOf(R.string.home_stations, R.string.home_favorites)
        val constructors = arrayOf(::StationsFragment, ::FavoritesFragment)

        binding.pager.adapter = object : FragmentStateAdapter(this) {

            override fun getItemCount() = 2

            override fun createFragment(position: Int) = constructors[position].invoke()
        }

        TabLayoutMediator(binding.tabs, binding.pager) { tab, index ->
            tab.setText(titles[index])
        }.attach()
    }

    private fun setUpFab() {
        // Show filter dialog associated with the currently selected page
        binding.fabFilter.setOnClickListener {
            when (binding.pager.currentItem) {
                PAGE_STATIONS -> navController.navigate(startStationsFilter())
                PAGE_FAVORITES -> navController.navigate(startFavoritesFilter())
            }
        }

        // Shrink FAB when app bar is collapsed
        binding.appBar.addOnOffsetChangedListener(OnOffsetChangedListener { _, offset ->
            val isAppBarCollapsed = offset == 0

            binding.fabFilter.apply {
                when {
                    !isExtended && isAppBarCollapsed -> extend()
                    isExtended && !isAppBarCollapsed -> shrink()
                }
            }
        })
    }

    private companion object {
        const val PAGE_STATIONS = 0
        const val PAGE_FAVORITES = 1
    }
}