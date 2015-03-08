package watch.ww.app.com.watch.util;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

public class GPSUtil {

	private static GPSCallBack callbck;
	private static LocationManager locationManager;
	public static Location curLocation;
	private static LocationListener netListener;
	private static LocationListener gpsListener;
	
	private static String lat = "0";
	private static String lng = "0";

	public static void initGPS(LocationManager lm,GPSCallBack cb) {
		locationManager = lm;
		callbck = cb;
		
		gpsListener = gpsListener != null ? gpsListener : new LocationListener() {
			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				if (LocationProvider.OUT_OF_SERVICE == arg1) {
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, netListener);
				}
			}
			@Override
			public void onProviderEnabled(String arg0) {
			}
			@Override
			public void onProviderDisabled(String arg0) {
			}
			@Override
			public void onLocationChanged(Location arg0) {
				curLocation = arg0;
				locationManager.removeUpdates(netListener);
				if(callbck != null)callbck.call(curLocation);
			}
		};
		
		netListener = netListener != null ? netListener : new LocationListener() {
			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			}
			@Override
			public void onProviderEnabled(String arg0) {
			}
			@Override
			public void onProviderDisabled(String arg0) {
			}
			@Override
			public void onLocationChanged(Location arg0) {
				curLocation = arg0;
				if(callbck != null)callbck.call(curLocation);
			}
		};
		if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000*2, 50, gpsListener);
		}
		if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, netListener);
		}
	}
	
	public static void stopGPS(){
		locationManager.removeUpdates(gpsListener);
		locationManager.removeUpdates(netListener);
	}
	
	public interface GPSCallBack {
		public void call(Location loc);
	}
	
	public static String[] getCurLoc(){
		if(curLocation != null) {
			lat = curLocation.getLatitude() + "";
			lng = curLocation.getLongitude() + "";
		}
		return new String[]{lat,lng};
	}
}
