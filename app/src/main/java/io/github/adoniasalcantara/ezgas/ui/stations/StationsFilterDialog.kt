package io.github.adoniasalcantara.ezgas.ui.stations

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import io.github.adoniasalcantara.ezgas.R
import io.github.adoniasalcantara.ezgas.data.model.Filter
import io.github.adoniasalcantara.ezgas.data.model.FuelType
import io.github.adoniasalcantara.ezgas.data.model.SortCriteria
import io.github.adoniasalcantara.ezgas.databinding.DialogStationsFilterBinding
import io.github.adoniasalcantara.ezgas.ui.common.BaseDialogFragment
import io.github.adoniasalcantara.ezgas.util.format.formatToKilometers
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StationsFilterDialog : BaseDialogFragment(R.layout.dialog_stations_filter) {

    private val viewModel: StationsViewModel by sharedViewModel()
    private val binding: DialogStationsFilterBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.filter.observe(viewLifecycleOwner) {
            setUpFuelOptions(it.fuelType)
            setUpSortOptions(it.sortCriteria)
            setUpDistanceSlider(it.distance)
        }

        binding.close.setOnClickListener { dismiss() }
        binding.apply.setOnClickListener { apply() }
    }

    private fun setUpFuelOptions(fuelType: FuelType) {
        binding.fuelOptions.apply {
            addOnButtonCheckedListener { _, checkedId, _ ->
                val fuelText = FuelType.fromId(checkedId).text
                binding.selectedFuel.text = getString(fuelText)
            }

            check(fuelType.id)
        }
    }

    private fun setUpSortOptions(sortCriteria: SortCriteria) {
        binding.sortOptions.check(sortCriteria.id)
    }

    private fun setUpDistanceSlider(distance: Float) {
        binding.sliderDistance.apply {
            addOnChangeListener { _, value, _ ->
                binding.selectedDistance.text = value.toInt().formatToKilometers()
            }

            setLabelFormatter { value ->
                value.toInt().formatToKilometers()
            }

            value = distance / 1000
        }
    }

    private fun apply() {
        val filter = binding.run {
            val fuelType = FuelType.fromId(fuelOptions.checkedButtonId)
            val sortCriteria = SortCriteria.fromId(sortOptions.checkedButtonId)
            val distance = sliderDistance.value * 1000

            Filter(fuelType, sortCriteria, distance)
        }

        dismiss()
        viewModel.applyFilter(filter)
    }
}