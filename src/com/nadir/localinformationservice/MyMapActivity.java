package com.nadir.localinformationservice;

import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MyMapActivity extends MapActivity
{

	private MapView	mapView;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interfacelocationetiteneraire); 
      //On récupère l’Intent que l’on a reçu
        Intent thisIntent = getIntent();
        //On récupère les deux extra grâce à leurs id
        Double latitude = thisIntent.getExtras().getDouble("latitude");
        Double longitude = thisIntent.getExtras().getDouble("longitude");
        int intentLatitude = (int) (latitude * 1E6) ;
        int intentLongitude = (int)(longitude *1E6) ;
        mapView = (MapView) this.findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        List<Overlay> mapOverlays = mapView.getOverlays();  

                Drawable drawable = this.getResources().getDrawable(R.drawable.marker);  

                CustomItemizedOverlay itemizedOverlay =   

                     new CustomItemizedOverlay(drawable, this);  

                  
                 GeoPoint point = new GeoPoint(intentLatitude, intentLongitude);  
                 

                OverlayItem overlayitem =   

                     new OverlayItem(point, "Hello", "I'm in Athens, Greece!");  

                   

                itemizedOverlay.addOverlay(overlayitem);  

                mapOverlays.add(itemizedOverlay);  

                   

                MapController mapController = mapView.getController();  

                   

                mapController.animateTo(point);  

              mapController.setZoom(18);
        
    }

	@Override
	protected boolean isRouteDisplayed()
	{
		return false;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_S)
		{
			mapView.setSatellite(!mapView.isSatellite());
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


}
