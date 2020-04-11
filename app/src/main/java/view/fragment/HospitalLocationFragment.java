package view.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.s7k.doctroid.R;

import java.util.Objects;

import customView.CustomToast;
import customView.CustomToastType;
import utilities.InternetUtilities;
import utilities.Utilities;

public class HospitalLocationFragment extends Fragment implements OnMapReadyCallback {

    public Context context;

    private LatLng hospitalLatLng = new LatLng(29.9787019, 30.9501475);
    private GoogleMap map;
    private CameraPosition googlePlex;
    private Location userCurrentLocation;
    private SupportMapFragment mapFragment;

    private CardView markers;
    private ImageView hospitalMarker, userMarker;

    public HospitalLocationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hospital_location_fragment, container, false);
        context = getActivity().getApplicationContext();

        initializeComponents(view);
        setListeners();

        return view;
    }

    private void initializeComponents(View view) {

        hospitalMarker = view.findViewById(R.id.hospitalLocationFragment_hospitalMarker);
        userMarker = view.findViewById(R.id.hospitalLocationFragment_userMarker);
        markers = view.findViewById(R.id.hospitalLocationFragment_markersCardView);

        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.hospitalLocationFragment_map);

        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    private void setListeners() {
        hospitalMarker.setOnClickListener(v -> {

            googlePlex = CameraPosition.builder()
                    .target(hospitalLatLng)
                    .zoom(16f)
                    .bearing(0)
                    .tilt(45)
                    .build();

            map.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 2000, null);
        });

        userMarker.setOnClickListener(v -> {

            if (userCurrentLocation != null) {
                googlePlex = CameraPosition.builder()
                        .target(new LatLng(userCurrentLocation.getLatitude(), userCurrentLocation.getLongitude()))
                        .zoom(16f)
                        .bearing(0)
                        .tilt(45)
                        .build();

                map.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 2000, null);
            } else {
                CustomToast.Companion.darkColor(getContext(), CustomToastType.ERROR, "Can't get your current location.");
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        // Map Initialize
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.clear();

        // Camera Position
        googlePlex = CameraPosition.builder()
                .target(hospitalLatLng)
                .zoom(13f)
                .bearing(0)
                .tilt(45)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 3000, null);

        // O6U Marker
        map.addMarker(new MarkerOptions()
                .position(hospitalLatLng)
                .title("O6U")
                .snippet("October 6 University Hospital")
                .draggable(false)
                .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.icon_hospital_map)));

        // User Marker
        try {
            if (Utilities.checkLocationPermission(getContext())) {
                if (InternetUtilities.isLocationEnabled(Objects.requireNonNull(getContext()))) {
                    FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
                    Task location = fusedLocationProviderClient.getLastLocation();

                    location.addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            userCurrentLocation = (Location) task.getResult();
                            if (userCurrentLocation != null) {

                                // map.setMyLocationEnabled(true);  -- Blue dot
                                // map.getUiSettings().setMyLocationButtonEnabled(true); -- my location button
                                map.getUiSettings().setCompassEnabled(false);

                                map.addMarker(new MarkerOptions()
                                        .position(new LatLng(userCurrentLocation.getLatitude(), userCurrentLocation.getLongitude()))
                                        .title("YOU")
                                        .snippet("Your Location")
                                        .draggable(false)
                                        .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.icon_user_map)));

                                markers.setVisibility(View.VISIBLE);
                            }

                        }
                    });
                } else {
                    CustomToast.Companion.darkColor(getContext(), CustomToastType.WARNING, "Please enable GPS to get your current location.");
                }
                } else {
                checkLocationPermission();
            }

        } catch (SecurityException e) {
            Log.v("getDeviceLocation", "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapFragment.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapFragment != null) {
            mapFragment.onDestroy();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mapFragment.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapFragment.onLowMemory();
    }

    private void checkLocationPermission() {
        int permission = ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                CustomToast.Companion.darkColor(getContext(), CustomToastType.ERROR, "Permission denied.");
            }
        }
    }

}