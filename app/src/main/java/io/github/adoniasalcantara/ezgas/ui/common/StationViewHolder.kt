package io.github.adoniasalcantara.ezgas.ui.common

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.github.adoniasalcantara.ezgas.R
import io.github.adoniasalcantara.ezgas.data.model.FuelType
import io.github.adoniasalcantara.ezgas.data.model.Station
import io.github.adoniasalcantara.ezgas.databinding.LayoutStationItemBinding
import io.github.adoniasalcantara.ezgas.util.format.formatToBRLSuperscript
import io.github.adoniasalcantara.ezgas.util.format.formatToKilometers
import io.github.adoniasalcantara.ezgas.util.format.formatToRelativeTimeFromNow

class StationViewHolder(
    private val binding: LayoutStationItemBinding,
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
        binding.address.text = station.place.fullAddress()
        binding.distance.text = station.place.distance?.formatToKilometers()
    }

    private fun setUpBrand() {
        val resource = context.getString(R.string.assets_url, station.brand.id)
        binding.brand.contentDescription = station.brand.name

        Picasso.get()
            .load(resource)
            .placeholder(R.drawable.ic_brand_placeholder)
            .into(binding.brand)
    }

    private fun setUpPrice(fuelType: FuelType) {
        val color = context.getColor(fuelType.color)
        val fuel = station.fuels[fuelType]
        val price = fuel
            ?.price
            ?.formatToBRLSuperscript()
            ?: context.getString(R.string.fuel_empty_price)

        binding.price.apply {
            text = price
            setTextColor(color)
        }

        binding.lastUpdate.text = fuel
            ?.updatedAt
            ?.formatToRelativeTimeFromNow(context)
    }
}