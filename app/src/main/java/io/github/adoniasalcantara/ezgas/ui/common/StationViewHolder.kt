package io.github.adoniasalcantara.ezgas.ui.common

import androidx.recyclerview.widget.RecyclerView
import io.github.adoniasalcantara.ezgas.R
import io.github.adoniasalcantara.ezgas.data.model.FuelType
import io.github.adoniasalcantara.ezgas.data.model.Station
import io.github.adoniasalcantara.ezgas.databinding.LayoutStationItemBinding
import io.github.adoniasalcantara.ezgas.util.AssetsCache
import io.github.adoniasalcantara.ezgas.util.format.formatToBRLSuperscript
import io.github.adoniasalcantara.ezgas.util.format.formatToKilometers
import io.github.adoniasalcantara.ezgas.util.format.formatToRelativeTimeFromNow

class StationViewHolder(
    private val binding: LayoutStationItemBinding,
    private val assets: AssetsCache,
    onStationClick: (Station) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private val context = binding.root.context
    private lateinit var station: Station

    init {
        binding.root.setOnClickListener { onStationClick(station) }
    }

    fun bind(station: Station, fuelType: FuelType) {
        this.station = station
        setUpStation()
        setUpBrand()
        setUpPrice(fuelType)
    }

    private fun setUpStation() {
        binding.company.text = station.company
        binding.address.text = station.place.run { address ?: "$city, $state" }
        binding.distance.text = station.place.distance?.let {
            (it / 1000).formatToKilometers()
        }
    }

    private fun setUpBrand() {
        val bitmap = assets.getBitmapOrDefault(
            path = "brands/${station.brand.id}.png",
            default = R.drawable.ic_white_flag
        )

        binding.brand.apply {
            contentDescription = station.brand.name
            setImageBitmap(bitmap)
        }
    }

    private fun setUpPrice(fuelType: FuelType) {
        val color = context.getColor(fuelType.color)
        val fuel = station.fuels.find { it.type == fuelType }
        val price = fuel?.salePrice
            ?.formatToBRLSuperscript()
            ?: context.getString(R.string.fuel_empty_price)

        binding.price.apply {
            text = price
            setTextColor(color)
        }

        binding.lastUpdate.text = fuel?.updatedAt?.formatToRelativeTimeFromNow(context)
    }
}