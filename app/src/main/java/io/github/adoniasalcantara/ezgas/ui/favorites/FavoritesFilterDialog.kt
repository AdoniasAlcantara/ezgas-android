package io.github.adoniasalcantara.ezgas.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import io.github.adoniasalcantara.ezgas.R
import io.github.adoniasalcantara.ezgas.data.model.FuelType
import io.github.adoniasalcantara.ezgas.databinding.DialogFavoritesFilterBinding
import io.github.adoniasalcantara.ezgas.ui.common.BaseDialogFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FavoritesFilterDialog : BaseDialogFragment(R.layout.dialog_favorites_filter) {

    private val viewModel: FavoritesViewModel by sharedViewModel()
    private val binding: DialogFavoritesFilterBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.fuelType.observe(viewLifecycleOwner, ::setUpFuelOptions)

        binding.close.setOnClickListener { dismiss() }

        binding.apply.setOnClickListener {
            dismiss()
            val fuelType = FuelType.fromId(binding.fuelOptions.checkedButtonId)
            viewModel.applyFuelType(fuelType)
        }
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
}