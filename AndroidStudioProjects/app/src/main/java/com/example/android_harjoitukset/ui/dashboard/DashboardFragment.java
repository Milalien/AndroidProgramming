package com.example.android_harjoitukset.ui.dashboard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.android_harjoitukset.databinding.FragmentDashboardBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment implements LocationListener {

    private FragmentDashboardBinding binding;

    public static final String TAG = "Dashboard";

    private TextInputEditText locationLatitudeInfo;
    private TextInputEditText locationLongitudeInfo;
    private TextInputEditText locationAddressInfo;

    private Button showMapBtn;
    LocationManager locationManager;
    LocationListener myListener;
    Location lastLocation;
    Address address;
    String currentLocation;

    Geocoder geocoder;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        locationLatitudeInfo = binding.fieldLatitude;
        locationLongitudeInfo = binding.fieldLongitude;
        locationAddressInfo = binding.fieldAddress;
        showMapBtn = binding.showMapButton;
        showMapBtn.setOnClickListener(v -> showAddressInMap());

        startLocationUpdates();

        return root;
    }


    @Override
    public void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            /* Log.e(TAG, "Permission denied");
            String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_FINE_LOCATION);*/
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
        lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }


    @Override
    public void onStop() {
        super.onStop();
        locationManager.removeUpdates(this);
    }

    private void showAddressInMap() {

        Uri gmmIntentUri = Uri.parse("geo:" + lastLocation.getLatitude() + "," + lastLocation.getLongitude());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }


    private void startLocationUpdates() {
        Log.i(TAG, "Locationupdates started");
        if (locationManager == null) {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        }
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            /* Log.e(TAG, "Permission denied");
            String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_FINE_LOCATION);*/
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
        lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (lastLocation != null) {
            locationLatitudeInfo.setText(Double.toString(lastLocation.getLatitude()));
            locationLongitudeInfo.setText(Double.toString(lastLocation.getLongitude()));
            locationAddressInfo.setText(getAddress(lastLocation));
        }
    }

    @SuppressLint("SetTextI18n")
    public void onLocationChanged(Location location) {

        String msg = "Updated location: " +
                location.getLatitude() + ", "
                + location.getLongitude();
        Log.i(TAG, msg);
        lastLocation = location;

        locationLatitudeInfo.setText(Double.toString(lastLocation.getLatitude()));
        locationLongitudeInfo.setText(Double.toString(lastLocation.getLongitude()));
        locationAddressInfo.setText(getAddress(lastLocation));
        Log.i(TAG, "New address is: " + getAddress(lastLocation));
    }

    private String getAddress(Location location) {
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        currentLocation = "Address not available";

        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            assert addresses != null;
            address = addresses.get(0);
            currentLocation = address.getAddressLine(0);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());

        }
        return currentLocation;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}