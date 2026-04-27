package com.example.edushare;

import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MapActivity extends AppCompatActivity {
    private MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load cấu hình OSM trước khi setContentView
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        setContentView(R.layout.activity_map);

        map = findViewById(R.id.mapView);
        if (map != null) {
            map.setMultiTouchControls(true); // Cho phép zoom bằng 2 ngón tay

            // Thiết lập vị trí trung tâm (Vd: Đại học Nông Lâm TP.HCM)
            GeoPoint startPoint = new GeoPoint(10.8710, 106.7932);
            map.getController().setZoom(17.0);
            map.getController().setCenter(startPoint);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (map != null) map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (map != null) map.onPause();
    }
}