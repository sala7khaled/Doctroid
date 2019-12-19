package view.fragment;

import android.Manifest;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.s7k.doctroid.R;

import app.Constants;
import es.dmoral.toasty.Toasty;

import static app.Constants.LOCATION_PERMISSION_REQUEST_CODE;

public class HospitalLocationFragment extends Fragment implements OnMapReadyCallback {

    public Context context;
    public GoogleMap mMap;
    MapView mMapView;

    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    public HospitalLocationFragment() {
        // Required empty public constructor
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

        getLocationPermission();

        if (mLocationPermissionsGranted) {
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.hospitalLocationFragment_map);

            mapFragment.getMapAsync(this);
        }

    }

    private void setListeners() {

    }

    private void getLocationPermission() {
        Log.v("LocationPermission", "getLocationPermission: getting location permissions");

        String[] permissions = {Constants.FINE_LOCATION, Constants.COURSE_LOCATION};

        if (ContextCompat.checkSelfPermission(getContext(),
                Constants.FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getContext(),
                Constants.COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mLocationPermissionsGranted = true;

        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    permissions,
                    Constants.LOCATION_PERMISSION_REQUEST_CODE);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.clear();

        CameraPosition googlePlex = CameraPosition.builder()
                .target(new LatLng(29.978204, 30.949905))
                .zoom(14f)
                .bearing(0)
                .tilt(45)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null);

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(29.978204, 30.949905))
                .title("O6U")
                .snippet("6th of October University Hospital")
                .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.icon_hospital_map)));

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        try {
            if (mLocationPermissionsGranted) {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();

                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
                                    .title("YOU")
                                    .snippet("Your Location")
                                    .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.icon_user_map)));

                        } else {
                            Toasty.error(getContext(), "Enable to get your location").show();
                        }
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
