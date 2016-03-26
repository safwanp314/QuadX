package quadx.com.droidx.ui.map;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quadx.com.droidx.R;
import quadx.com.droidx.ui.MainActivity;

/**
 * Created by SAFWAN on 2/1/16.
 */
public class MapActivity extends MainActivity implements OnMapReadyCallback {

    private static final Logger DEBUG_LOGGER = LoggerFactory.getLogger("quadx.com.droidx.debug");

    private MapFragment mapFragment = MapFragment.newInstance();
    private static GoogleMap googleMap;
    private static int mapType = GoogleMap.MAP_TYPE_NORMAL;

    protected static int mapPaddingLeft = 50;
    protected static int mapPaddingTop = 0;
    protected static int mapPaddingRight = 0;
    protected static int mapPaddingBottom = 0;

    private static LocationManager locationManager;

    private static LatLng camLatLng;
    private static final float DEFAULT_CAM_ZOOM = 17;
    private static float camZoom = DEFAULT_CAM_ZOOM;

    protected void setViewResources() {
        super.setViewResources();
        mapFragment.getMapAsync(this);
        getFragmentManager().beginTransaction().add(R.id.mapContainer, mapFragment).show(mapFragment).commit();
        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 5000L, new CustomLocationListener());
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000L, 5000L, new CustomLocationListener());
        }
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.activity_map;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapActivity.googleMap = googleMap;
        initilizeGoogleMap();
    }

    private static void initilizeGoogleMap() {
        if (googleMap != null) {
            googleMap.setMyLocationEnabled(true);
            googleMap.setPadding(mapPaddingLeft, mapPaddingTop, mapPaddingRight, mapPaddingBottom);
            googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                    DEBUG_LOGGER.debug("Set Camera Position " + cameraPosition.target.latitude + ", " + cameraPosition.target.longitude);
                    MapActivity.camLatLng = cameraPosition.target;
                    MapActivity.camZoom = cameraPosition.zoom;
                }
            });
            if(MapActivity.camLatLng != null) {
                moveCamera(MapActivity.camLatLng, MapActivity.camZoom);
            }
        }
    }

    private static void moveCamera(LatLng camLatLng, float camZoom) {

        if (camLatLng != null) {
            MapActivity.camLatLng = camLatLng;
            MapActivity.camZoom = camZoom;

            DEBUG_LOGGER.debug("Moving Camera to " + camLatLng.latitude + ", " + camLatLng.longitude);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(camLatLng)          // Sets the center of the map to location user
                    .zoom(camZoom)              // Sets the zoom
                    .build();                   // Creates a CameraPosition from the builder
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
        googleMap.setMapType(mapType);
    }

    private static void animateCamera(LatLng camLatLng, float camZoom) {

        if (camLatLng != null) {
            MapActivity.camLatLng = camLatLng;
            MapActivity.camZoom = camZoom;

            DEBUG_LOGGER.debug("Animating Camera to " + camLatLng.latitude + ", " + camLatLng.longitude);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(camLatLng)          // Sets the center of the map to location user
                    .zoom(camZoom)              // Sets the zoom
                    .build();                   // Creates a CameraPosition from the builder
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
        googleMap.setMapType(mapType);
    }

    public static void setMapType(String mapTypeStr) {
        if (mapTypeStr.equals("Normal")) {
            mapType = GoogleMap.MAP_TYPE_NORMAL;
        } else if (mapTypeStr.equals("Satellite")) {
            mapType = GoogleMap.MAP_TYPE_SATELLITE;
        }
        googleMap.setMapType(mapType);
    }

    private class CustomLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            DEBUG_LOGGER.debug("Location Changed to " + location.getLatitude() + ", " + location.getLongitude());
            animateCamera(new LatLng(location.getLatitude(), location.getLongitude()), DEFAULT_CAM_ZOOM);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    }
}