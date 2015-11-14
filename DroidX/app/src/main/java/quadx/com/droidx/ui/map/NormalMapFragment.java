package quadx.com.droidx.ui.map;

import android.app.Fragment;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import quadx.com.droidx.R;

public class NormalMapFragment extends Fragment implements OnMapReadyCallback {

    private static final String MAP_TYPE = "mapType";

    private MapFragment mapFragment = MapFragment.newInstance();
    protected GoogleMap googleMap;
    private int mapType;

    protected int mapPaddingLeft = 50;
    protected int mapPaddingTop;
    protected int mapPaddingRight;
    protected int mapPaddingBottom;

    public static NormalMapFragment newInstance(int mapType) {
        NormalMapFragment fragment = new NormalMapFragment();
        Bundle args = new Bundle();
        args.putInt(MAP_TYPE, mapType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getFragmentManager().beginTransaction().add(R.id.mapContainer, mapFragment).commit();
        if (getArguments() != null) {
            mapType = getArguments().getInt(MAP_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_map, container, false);
        mapFragment.getMapAsync(this);
        return inflatedView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        initilizeGoogleMap();
    }

    protected void initilizeGoogleMap() {
        if (googleMap != null) {
            googleMap.setMyLocationEnabled(true);
            googleMap.setPadding(mapPaddingLeft, mapPaddingTop, mapPaddingRight, mapPaddingBottom);

            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if (location != null) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                        .zoom(17)                   // Sets the zoom
                        .build();                   // Creates a CameraPosition from the builder
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
            googleMap.setMapType(mapType);
        }
    }

    public void setMapType(int mapType) {
        this.mapType = mapType;
        googleMap.setMapType(mapType);
    }
}