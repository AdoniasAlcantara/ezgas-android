package io.github.adoniasalcantara.ezgas.ui.favorites

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import io.github.adoniasalcantara.ezgas.R
import io.github.adoniasalcantara.ezgas.data.model.FuelType
import io.github.adoniasalcantara.ezgas.databinding.DialogFavoritesFilterBinding
import io.github.adoniasalcantara.ezgas.ui.common.BaseDialogFragment

class FavoritesFilterDialog : BaseDialogFragment(R.layout.dialog_favorites_filter) {

    private val binding: DialogFavoritesFilterBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // TODO get fuel type from view model
        val fuelType = FuelType.GASOLINE

        binding.apply {
            fuelOptions.let {
                it.addOnButtonCheckedListener { _, checkedId, _ ->
                    val fuelText = FuelType.fromId(checkedId).text
                    binding.selectedFuel.text = getString(fuelText)
                }

                it.check(fuelType.id)
            }

            close.setOnClickListener { dismiss() }

            apply.setOnClickListener {
                dismiss()

                val selectedFuelType = FuelType.fromId(fuelOptions.checkedButtonId)
                // TODO apply fuel type on view model
            }
        }
    }
}