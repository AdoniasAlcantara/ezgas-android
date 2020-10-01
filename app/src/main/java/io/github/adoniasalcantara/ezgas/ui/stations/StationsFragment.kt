package io.github.adoniasalcantara.ezgas.ui.stations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import io.github.adoniasalcantara.ezgas.R
import io.github.adoniasalcantara.ezgas.data.model.*
import io.github.adoniasalcantara.ezgas.databinding.LayoutStationItemBinding
import io.github.adoniasalcantara.ezgas.databinding.LayoutStationListBinding
import io.github.adoniasalcantara.ezgas.ui.common.SpacingDecoration
import io.github.adoniasalcantara.ezgas.ui.common.StationViewHolder
import io.github.adoniasalcantara.ezgas.util.AssetsCache
import java.math.BigDecimal
import java.time.OffsetDateTime

class StationsFragment : Fragment(R.layout.layout_station_list) {

    private val binding: LayoutStationListBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.stationList.apply {
            adapter = dummyAdapter
            addItemDecoration(SpacingDecoration(context, R.dimen.spacing_medium))
            setHasFixedSize(true)
        }
    }

    private val dummyAdapter by lazy {
        val stations = (1..20).map { value ->
            val brand = Brand(id = 11, name = "Kaboom Oil")

            val place = Place(
                latitude = -14.672075,
                longitude = -39.374909,
                distance = 10_000f,
                address = "0, Fool's St, Somewhere",
                city = "Itajuípe",
                state = "Bahia"
            )

            val fuels = setOf(
                Fuel(
                    type = FuelType.GASOLINE,
                    salePrice = BigDecimal.valueOf(4_70, 2),
                    purchasePrice = null,
                    updatedAt = OffsetDateTime.now(),
                    updatedBy = "EzGas"
                )
            )

            Station(
                id = value,
                company = "Super Fas Gas Inc.",
                brand = brand,
                place = place,
                fuels = fuels
            )
        }

        val listener = { station: Station -> println(station) }

        object : RecyclerView.Adapter<StationViewHolder>() {

            override fun getItemCount() = stations.count()

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
                val binding = LayoutStationItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )

                return StationViewHolder(binding, AssetsCache(parent.context, 4096), listener)
            }

            override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
                holder.bind(stations[position], FuelType.GASOLINE)
            }
        }
    }
}
