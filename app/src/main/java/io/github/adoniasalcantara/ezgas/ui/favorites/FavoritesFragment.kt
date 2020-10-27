package io.github.adoniasalcantara.ezgas.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.color.MaterialColors
import io.github.adoniasalcantara.ezgas.NavGraphDirections
import io.github.adoniasalcantara.ezgas.R
import io.github.adoniasalcantara.ezgas.data.model.Station
import io.github.adoniasalcantara.ezgas.data.repository.Resource
import io.github.adoniasalcantara.ezgas.databinding.LayoutStationListBinding
import io.github.adoniasalcantara.ezgas.ui.common.SpacingDecoration
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment(R.layout.layout_station_list) {

    private val viewModel: FavoritesViewModel by viewModel()
    private val navController by lazy { findNavController() }
    private val binding: LayoutStationListBinding by viewBinding()
    private val adapter: FavoritesAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpList()
        setUpRefresh()
        setUpSubscribers()
    }

    private fun setUpList() {
        adapter.onStationClick = ::showStationDetails

        binding.stationList.let {
            it.adapter = adapter
            it.addItemDecoration(SpacingDecoration(requireContext(), R.dimen.spacing_medium))
            it.setHasFixedSize(true)
        }
    }

    private fun setUpRefresh() {
        binding.refresh.let {
            val color = MaterialColors.getColor(it, R.attr.colorPrimary)
            it.setColorSchemeColors(color)
            it.setOnRefreshListener { viewModel.refresh() }
        }
    }

    private fun setUpSubscribers() {
        viewModel.fuelType.observe(viewLifecycleOwner) {
            adapter.fuelType = it
        }

        viewModel.favorites.observe(viewLifecycleOwner) { result ->
            binding.apply {
                when (result) {
                    is Resource.Loading -> if (!refresh.isRefreshing) {
                        loading.showLoading()
                    }

                    is Resource.Success -> {
                        refresh.isRefreshing = false
                        loading.hide()
                        adapter.submit(result.data)
                    }

                    is Resource.Error -> {
                        refresh.isRefreshing = false
                        loading.hide()
                        handleError(result.error)
                    }
                }
            }
        }
    }

    private fun showStationDetails(station: Station) {
        navController.navigate(NavGraphDirections.startDetails(station))
    }

    private fun handleError(error: Throwable) {
        // TODO
    }
}