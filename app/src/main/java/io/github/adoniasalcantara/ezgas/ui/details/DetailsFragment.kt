package io.github.adoniasalcantara.ezgas.ui.details

import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import io.github.adoniasalcantara.ezgas.R
import io.github.adoniasalcantara.ezgas.data.model.Fuel
import io.github.adoniasalcantara.ezgas.data.model.FuelType
import io.github.adoniasalcantara.ezgas.databinding.FragmentDetailsBinding
import io.github.adoniasalcantara.ezgas.databinding.LayoutFuelBinding
import io.github.adoniasalcantara.ezgas.ui.common.TransitionListenerAdapter
import io.github.adoniasalcantara.ezgas.util.format.formatToBRLSuperscript
import io.github.adoniasalcantara.ezgas.util.format.formatToRelativeTimeFromNow
import java.math.BigDecimal
import java.time.OffsetDateTime

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args: DetailsFragmentArgs by navArgs()
    private val binding: FragmentDetailsBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpMotion()
        setUpStation()
        setUpSubscribers()
    }

    private fun setUpMotion() {
        val sheet = ContextCompat.getDrawable(requireContext(), R.drawable.bg_bottom_sheet)
            ?.mutate() as LayerDrawable

        val sheetHandle = sheet.findDrawableByLayerId(R.id.handle)!!
        val sheetPadding = binding.bottomSheet.paddingTop

        binding.bottomSheet.apply {
            isScrollEnabled = false
            background = sheet
        }

        binding.root.addTransitionListener(object : TransitionListenerAdapter {
            // Workaround to prevent ScrollView from competing for the touch event with
            // the MotionLayout. Scroll is enabled only when the transition reaches the top.
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                binding.bottomSheet.isScrollEnabled = currentId == R.id.top
            }

            // Smooth transition to show/hide sheet handle
            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                if (startId == R.id.middle && endId == R.id.top) {
                    val newSheetPadding = ((1 - progress) * sheetPadding).toInt()
                    sheetHandle.alpha = ((1 - progress) * 255).toInt()
                    binding.bottomSheet.setPadding(0, newSheetPadding, 0, 0)
                }
            }
        })
    }

    private fun setUpStation() {
        val station = args.station

        binding.company.text = station.company
        binding.address.text = station.place.address
        binding.cityState.text = getString(
            R.string.station_city_state,
            station.place.city,
            station.place.state
        )
    }

    private fun setUpSubscribers() {
        val fuel = Fuel(
            type = FuelType.GASOLINE,
            salePrice = BigDecimal.valueOf(4_70, 2),
            purchasePrice = null,
            updatedAt = OffsetDateTime.now(),
            updatedBy = "EzGas"
        )

        addFuel(fuel)
        addFuel(fuel.copy(type = FuelType.ETHANOL))
        addFuel(fuel.copy(type = FuelType.DIESEL))
        addFuel(fuel.copy(type = FuelType.DIESEL_S10))
    }

    private fun addFuel(fuel: Fuel) {
        val priceColor = requireContext().getColor(fuel.type.color)

        LayoutFuelBinding.inflate(layoutInflater, binding.contents, true).let {
            it.lastUpdate.text = fuel.updatedAt.formatToRelativeTimeFromNow(requireContext())
            it.price.text = fuel.salePrice.formatToBRLSuperscript()
            it.fuel.setText(fuel.type.text)
            it.price.setTextColor(priceColor)
        }
    }
}