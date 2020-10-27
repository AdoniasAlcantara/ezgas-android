package io.github.adoniasalcantara.ezgas.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.adoniasalcantara.ezgas.data.model.FuelType
import io.github.adoniasalcantara.ezgas.data.model.Station
import io.github.adoniasalcantara.ezgas.databinding.LayoutStationItemBinding
import io.github.adoniasalcantara.ezgas.ui.common.StationViewHolder
import io.github.adoniasalcantara.ezgas.util.AssetsCache

class FavoritesAdapter(private val assets: AssetsCache) : RecyclerView.Adapter<StationViewHolder>() {

    private var stations = emptyList<Station>()
    lateinit var fuelType: FuelType
    lateinit var onStationClick: (Station) -> Unit

    override fun getItemCount() = stations.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val binding = LayoutStationItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return StationViewHolder(binding, assets, onStationClick)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.bind(stations[position], fuelType)
    }

    fun submit(stations: List<Station>) {
        this.stations = stations
        notifyDataSetChanged()
    }
}