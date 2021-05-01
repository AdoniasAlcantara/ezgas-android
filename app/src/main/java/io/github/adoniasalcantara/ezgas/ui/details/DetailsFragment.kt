package io.github.adoniasalcantara.ezgas.ui.details

import android.graphics.Bitmap
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.model.cameraPosition
import io.github.adoniasalcantara.ezgas.R
import io.github.adoniasalcantara.ezgas.data.model.Fuel
import io.github.adoniasalcantara.ezgas.data.model.FuelType
import io.github.adoniasalcantara.ezgas.databinding.FragmentDetailsBinding
import io.github.adoniasalcantara.ezgas.databinding.LayoutFuelBinding
import io.github.adoniasalcantara.ezgas.ui.common.TransitionListenerAdapter
import io.github.adoniasalcantara.ezgas.util.AssetsCache
import io.github.adoniasalcantara.ezgas.util.format.formatToBRLSuperscript
import io.github.adoniasalcantara.ezgas.util.format.formatToKilometers
import io.github.adoniasalcantara.ezgas.util.format.formatToRelativeTimeFromNow
import io.github.adoniasalcantara.ezgas.util.startDirections
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsViewModel by viewModel { parametersOf(args.station.id) }
    private val navController by lazy { findNavController() }
    private val binding: FragmentDetailsBinding by viewBinding()
    private val assets: AssetsCache by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpMap()
        setUpMotion()
        setUpStation()
        setUpControls()
        setUpSubscribers()
    }

    private fun setUpMap() = lifecycleScope.launch {
        val map = (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).awaitMap()
        val position = args.station.place.position.run { LatLng(latitude, longitude) }

        val cameraPosition = cameraPosition {
            target(position)
            zoom(ZOOM_BLOCK)
        }

        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        map.addMarker {
            val markerSize = resources.getDimensionPixelSize(R.dimen.marker_size)

            val bitmap = assets.getBitmapOrDefault(
                path = "brands/${args.station.brand.id}.png",
                default = R.drawable.ic_brand_placeholder
            ).run {
                Bitmap.createScaledBitmap(this, markerSize, markerSize, false)
            }

            val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap)
            icon(bitmapDescriptor)
            position(position)
        }
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
        binding.address.text = station.place.shortAddress()
        binding.brand.text = station.brand.name

        binding.cityState.text = getString(
            R.string.details_city_state,
            station.place.city,
            station.place.state
        )

        binding.direction.text = station.place.distance
            ?.formatToKilometers()
            ?: getString(R.string.details_go)
    }

    private fun setUpControls() {
        val bounceAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.bounce)

        binding.favorite.setOnCheckedChangeListener { button, isChecked ->
            if (button.isPressed) {
                button.startAnimation(bounceAnimation)
                showFavoriteChanged(isChecked)
                viewModel.setFavorite(isChecked)
            }
        }

        binding.direction.setOnClickListener {
            val (latitude, longitude) = args.station.place.position
            startDirections(requireContext(), latitude, longitude)
        }

        binding.back.setOnClickListener { navController.popBackStack() }
    }

    private fun setUpSubscribers() {
        viewModel.isFavorite.observe(viewLifecycleOwner) {
            binding.favorite.isChecked = it
        }

        viewModel.fuels.observe(viewLifecycleOwner, ::addFuels)
    }

    private fun addFuels(fuels: Map<FuelType, Fuel>) {
        fuels.forEach { (fuelType, fuel) ->
            val priceColor = requireContext().getColor(fuelType.color)

            LayoutFuelBinding.inflate(layoutInflater, binding.contents, true).also {
                it.lastUpdate.text = fuel.updatedAt.formatToRelativeTimeFromNow(requireContext())
                it.price.text = fuel.price.formatToBRLSuperscript()
                it.fuel.setText(fuelType.text)
                it.price.setTextColor(priceColor)
            }
        }
    }

    private fun showFavoriteChanged(isFavorite: Boolean) {
        val message = if (isFavorite) {
            R.string.details_added_favorite
        } else {
            R.string.details_removed_favorite
        }

        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private companion object {
        const val ZOOM_BLOCK = 19f
    }
}