package view.fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
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

import app.Constants;
import es.dmoral.toasty.Toasty;

public class HospitalLocationFragment extends Fragment implements OnMapReadyCallback {

    public Context context;
    private GoogleMap map;
    private CameraPosition googlePlex;
    private Location userCurrentLocation;
    private SupportMapFragment mapFragment;

    private CardView markers;
    private ImageView hospitalMarker, userMarker;

    private Boolean locationPermissionsGranted = false;

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
        initMap();
        setListeners();

        return view;
    }

    private void initializeComponents(View view) {

        hospitalMarker = view.findViewById(R.id.hospitalLocationFragment_hospitalMarker);
        userMarker = view.findViewById(R.id.hospitalLocationFragment_userMarker);
        markers = view.findViewById(R.id.hospitalLocationFragment_markersCardView);

        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.hospitalLocationFragment_map);

        getLocationPermission();

    }

    private void initMap() {

        if (locationPermissionsGranted) {
            mapFragment.getMapAsync(this);

        } else {
            Toasty.error(getContext(), "No GPS permission").show();
        }
    }

    private void setListeners() {
        hospitalMarker.setOnClickListener(v -> {

            googlePlex = CameraPosition.builder()
                    .target(new LatLng(29.978204, 30.949905))
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
                Toasty.error(getContext(), "We cant get your current location").show();
            }
        });
    }

    private void getLocationPermission() {

        String[] permissions = {Constants.FINE_LOCATION, Constants.COURSE_LOCATION};

        if (ContextCompat.checkSelfPermission(getContext(),
                Constants.FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getContext(),
                Constants.COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionsGranted = true;
            initMap();
        } else {
            ActivityCompat.requestPermissions(getActivity(), permissions, Constants.LOCATION_PERMISSION_REQUEST_CODE);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        // Map Initialize
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.clear();

        // Camera Position
        googlePlex = CameraPosition.builder()
                .target(new LatLng(29.978204, 30.949905))
                .zoom(13f)
                .bearing(0)
                .tilt(45)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 3000, null);

        // O6U Marker
        map.addMarker(new MarkerOptions()
                .position(new LatLng(29.978204, 30.949905))
                .title("O6U")
                .snippet("October 6 University Hospital")
                .draggable(false)
                .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.icon_hospital_map)));

        // User Marker
        try {
            if (locationPermissionsGranted) {

                FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

                final Task location = fusedLocationProviderClient.getLastLocation();

                location.addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        userCurrentLocation = (Location) task.getResult();

                        if (userCurrentLocation != null) {

                            // map.setMyLocationEnabled(true);
                            //map.getUiSettings().setMyLocationButtonEnabled(true);
                            map.getUiSettings().setCompassEnabled(false);

                            map.addMarker(new MarkerOptions()
                                    .position(new LatLng(userCurrentLocation.getLatitude(), userCurrentLocation.getLongitude()))
                                    .title("YOU")
                                    .snippet("Your Location")
                                    .draggable(false)
                                    .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.icon_user_map)));

                            markers.setVisibility(View.VISIBLE);
                        } else {
                            getLocationPermission();
                        }

                    } else {
                        Toasty.error(getContext(), "Enable to get your location").show();
                    }
                });
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

}
