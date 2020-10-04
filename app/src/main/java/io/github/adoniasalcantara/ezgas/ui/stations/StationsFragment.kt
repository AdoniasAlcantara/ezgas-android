package io.github.adoniasalcantara.ezgas.ui.stations

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import io.github.adoniasalcantara.ezgas.R
import io.github.adoniasalcantara.ezgas.data.model.Station
import io.github.adoniasalcantara.ezgas.databinding.LayoutStationListBinding
import io.github.adoniasalcantara.ezgas.ui.common.SpacingDecoration
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StationsFragment : Fragment(R.layout.layout_station_list) {

    private val viewModel: StationsViewModel by sharedViewModel()
    private val binding: LayoutStationListBinding by viewBinding()
    private val adapter: StationsAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.stationList.let {
            adapter.onStationClick = ::showStationDetails
            it.adapter = adapter
            it.addItemDecoration(SpacingDecoration(requireContext(), R.dimen.spacing_medium))
            it.setHasFixedSize(true)
        }

        setUpSubscribers()

        /*val dummyLocation = Location("").apply {
            latitude = -14.672079
            longitude = -39.374967
        }

        viewModel.searchNearbyStations(dummyLocation)*/
    }

    private fun setUpSubscribers() {
        viewModel.filter.observe(viewLifecycleOwner) {
            adapter.fuelType = it.fuelType
        }

        viewModel.stations.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun showStationDetails(station: Station) {
        // TODO navigate to station details
    }
}
