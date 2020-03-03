package com.manegow.safeami.ui.map

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.manegow.safeami.R

class MapFragment : Fragment(), OnMapReadyCallback, PermissionListener {

    private lateinit var googleMap: GoogleMap
    private lateinit var mapViewModel: MapViewModel
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mapViewModel =
            ViewModelProviders.of(this).get(MapViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_map, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return root
    }

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map ?: return
        if (mapViewModel.isPermissionGiven(this.requireContext())) {
            googleMap.isMyLocationEnabled = true
            googleMap.uiSettings.isMyLocationButtonEnabled = true
            googleMap.uiSettings.isZoomControlsEnabled = true
            mapViewModel.getCurrentLocation(fusedLocationProviderClient, this.requireActivity(), googleMap)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CHECK_SETTINGS -> {
                if (resultCode == Activity.RESULT_OK) {
                    mapViewModel.getCurrentLocation(fusedLocationProviderClient, this.requireActivity(), googleMap)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        const val REQUEST_CHECK_SETTINGS = 43
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this.context, "Permission required for showing location", Toast.LENGTH_LONG)
            .show()
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        mapViewModel.getCurrentLocation(fusedLocationProviderClient, this.requireActivity(), googleMap)
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {
        token!!.continuePermissionRequest()
    }

}