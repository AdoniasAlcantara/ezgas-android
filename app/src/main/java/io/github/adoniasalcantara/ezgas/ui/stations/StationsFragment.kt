package io.github.adoniasalcantara.ezgas.ui.stations

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.paging.LoadState
import androidx.paging.PagingData
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.color.MaterialColors
import com.google.android.material.snackbar.Snackbar
import io.github.adoniasalcantara.ezgas.R
import io.github.adoniasalcantara.ezgas.data.model.Station
import io.github.adoniasalcantara.ezgas.databinding.LayoutStationListBinding
import io.github.adoniasalcantara.ezgas.ui.common.SpacingDecoration
import io.github.adoniasalcantara.ezgas.util.location.LocationLiveData
import io.github.adoniasalcantara.ezgas.util.location.LocationSettingsResolver
import io.github.adoniasalcantara.ezgas.util.location.LocationUpdate
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf
import retrofit2.HttpException
import java.io.IOException

class StationsFragment : Fragment(R.layout.layout_station_list) {

    private val viewModel: StationsViewModel by sharedViewModel()
    private val binding: LayoutStationListBinding by viewBinding()
    private val adapter: StationsAdapter by inject()
    private val locationUpdates: LocationLiveData by inject()
    private val locationResolver: LocationSettingsResolver = get { parametersOf(this) }

    private val snackbar by lazy {
        Snackbar.make(requireView(), R.string.misc_awaiting_location, Snackbar.LENGTH_INDEFINITE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Make sure the location is enabled at startup
        savedInstanceState ?: locationResolver.resolve(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpList()
        setUpRefresh()
        setUpSubscribers()
    }

    private fun setUpList() {
        binding.stationList.let {
            adapter.onStationClick = ::showStationDetails
            it.adapter = adapter
            it.addItemDecoration(SpacingDecoration(requireContext(), R.dimen.spacing_medium))
            it.setHasFixedSize(true)
        }
    }

    private fun setUpRefresh() {
        binding.refresh.let {
            val color = MaterialColors.getColor(it, R.attr.colorPrimary)
            it.setColorSchemeColors(color)
            it.setOnRefreshListener { refreshNearbyStations() }
        }
    }

    private fun setUpSubscribers() {
        viewModel.filter.observe(viewLifecycleOwner) {
            // Clear list whenever the filter changes
            adapter.fuelType = it.fuelType
            adapter.submitData(viewLifecycleOwner.lifecycle, PagingData.empty())
        }

        viewModel.stations.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        viewModel.isLocationPending.observe(viewLifecycleOwner) { isPending ->
            if (isPending) snackbar.show() else snackbar.dismiss()
        }

        locationUpdates.observe(viewLifecycleOwner) { update ->
            viewModel.apply {
                if (isLocationPending.value!! && update is LocationUpdate.Available) {
                    searchNearbyStations(update.location)
                }
            }
        }

        adapter.loadStateFlow.asLiveData().observe(viewLifecycleOwner) { states ->
            binding.apply {
                when (val state = states.refresh) {
                    is LoadState.NotLoading -> {
                        refresh.isRefreshing = false
                        loading.hide()
                    }

                    is LoadState.Loading -> if (adapter.isEmpty()) {
                        loading.showLoading()
                    }

                    is LoadState.Error -> {
                        refresh.isRefreshing = false
                        handleError(state.error)
                    }
                }
            }
        }
    }

    private fun refreshNearbyStations() {
        val lastUpdate = locationUpdates.value

        if (lastUpdate is LocationUpdate.Available) {
            viewModel.searchNearbyStations(lastUpdate.location)
        } else {
            viewModel.notifyPendingLocation()
            locationResolver.resolve(requireContext())
        }
    }

    private fun showStationDetails(station: Station) {
        // TODO navigate to station details
    }

    private fun handleError(error: Throwable) {
        binding.loading.apply {
            when (error) {
                is HttpException -> if (error.code() == 404) {
                    showError {
                        image(R.drawable.img_no_results)
                        title(R.string.error_no_results_title)
                        message(R.string.error_no_results_message)
                        retryText(R.string.misc_refresh)
                        retryCallback = ::refreshNearbyStations
                    }
                } else {
                    showError {
                        image(R.drawable.img_unavailable)
                        title(R.string.error_unavailable)
                        message = getString(R.string.error_code, error.code())
                        retryCallback = ::refreshNearbyStations
                    }
                }

                is IOException -> showError {
                    image(R.drawable.img_no_connection)
                    title(R.string.error_network_title)
                    message(R.string.error_network_message)
                    retryCallback = ::refreshNearbyStations
                }

                else -> showError {
                    image(R.drawable.ic_error)
                    title(R.string.error)
                    message(R.string.error_unexpected)
                    retryCallback = ::refreshNearbyStations
                }
            }
        }
    }
}
