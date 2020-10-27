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
import retrofit2.HttpException
import java.io.IOException

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
                        handleError(result.error)
                    }

                    null -> {
                        refresh.isRefreshing = false
                        loading.showError {
                            image(R.drawable.img_no_favorites)
                            title(R.string.error_no_favorites_title)
                            message(R.string.error_no_favorites_message)
                        }
                    }
                }
            }
        }
    }

    private fun showStationDetails(station: Station) {
        navController.navigate(NavGraphDirections.startDetails(station))
    }

    private fun handleError(error: Throwable) {
        binding.loading.apply {
            when (error) {
                is HttpException -> showError {
                    image(R.drawable.img_unavailable)
                    title(R.string.error_unavailable)
                    message = getString(R.string.error_code, error.code())
                    retryCallback = viewModel::refresh
                }

                is IOException -> showError {
                    image(R.drawable.img_no_connection)
                    title(R.string.error_network_title)
                    message(R.string.error_network_message)
                    retryCallback = viewModel::refresh
                }

                else -> showError {
                    image(R.drawable.ic_error)
                    title(R.string.error)
                    message(R.string.error_unexpected)
                    retryCallback = viewModel::refresh
                }
            }
        }
    }
}