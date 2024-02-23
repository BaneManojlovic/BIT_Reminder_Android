package com.example.bitreminder.MapScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class MapViewModel: ViewModel() {

    // Properties
    private var _userCurrentLng = mutableStateOf(0.0)
    var userCurrentLng : MutableState<Double> = _userCurrentLng

    private var _userCurrentLat = mutableStateOf(0.0)
    var userCurrentLat : MutableState<Double> = _userCurrentLat

    val pickUp = LatLng(userCurrentLat.value, userCurrentLng.value)

    private var _locationPermissionGranted = MutableLiveData(false)
    var locationPermissionGranted : LiveData<Boolean> = _locationPermissionGranted

    // Set & Get Methods
    fun setUserCurrentGeoCoordinates(latLng: LatLng){
        _userCurrentLat.value = latLng.latitude
        _userCurrentLng.value = latLng.longitude
    }

    fun getUserCurrentGeoCoordinates() : LatLng {
        return LatLng(_userCurrentLat.value, _userCurrentLng.value)
    }

    fun permissionGranted(setGranted: Boolean){
        _locationPermissionGranted.value = setGranted
    }
}