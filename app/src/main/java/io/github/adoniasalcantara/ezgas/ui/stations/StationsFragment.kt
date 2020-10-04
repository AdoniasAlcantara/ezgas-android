package io.github.adoniasalcantara.ezgas.ui.stations

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.color.MaterialColors
import io.github.adoniasalcantara.ezgas.R
import io.github.adoniasalcantara.ezgas.data.model.Station
import io.github.adoniasalcantara.ezgas.databinding.LayoutStationListBinding
import io.github.adoniasalcantara.ezgas.ui.common.SpacingDecoration
import io.github.adoniasalcantara.ezgas.util.location.LocationLiveData
import io.github.adoniasalcantara.ezgas.util.location.LocationUpdate
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StationsFragment : Fragment(R.layout.layout_station_list) {

    private val viewModel: StationsViewModel by sharedViewModel()
    private val binding: LayoutStationListBinding by viewBinding()
    private val adapter: StationsAdapter by inject()
    private val locationUpdates: LocationLiveData by inject()

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
            it.setOnRefreshListener {
                val lastUpdate = locationUpdates.value

                if (lastUpdate is LocationUpdate.Available) {
                    viewModel.searchNearbyStations(lastUpdate.location)
                } else {
                    viewModel.notifyPendingLocation()
                    // TODO ask user to change location settings as needed
                }
            }
        }
    }

    private fun setUpSubscribers() {
        viewModel.filter.observe(viewLifecycleOwner) {
            adapter.fuelType = it.fuelType
        }

        viewModel.stations.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        viewModel.isLocationPending.observe(viewLifecycleOwner) {
            // TODO show a loading spinner when location is pending
        }

        locationUpdates.observe(viewLifecycleOwner) { update ->
            val isLocationPending = viewModel.isLocationPending.value!!

            if (isLocationPending && update is LocationUpdate.Available) {
                viewModel.searchNearbyStations(update.location)
            }
        }
    }

    private fun showStationDetails(station: Station) {
        // TODO navigate to station details
    }
}
