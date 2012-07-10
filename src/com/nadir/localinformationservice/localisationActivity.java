/*package com.nadir.vitryweather;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
 
public class localisationActivity extends Activity implements OnClickListener, LocationListener{
	private LocationManager lManager;
    private Location location;
    private String choix_source = "";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        //On sp�cifie que l'on va avoir besoin de g�rer l'affichage du cercle de chargement
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
 
        setContentView(R.layout.interfaceselectprovider);
 
        //On r�cup�re le service de localisation
        lManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        
        //Initialisation de l'�cran
        reinitialisationEcran();
 
        //On affecte un �couteur d'�v�nement aux boutons
        findViewById(R.id.choix_source).setOnClickListener(this);
        findViewById(R.id.obtenir_position).setOnClickListener(this);
        findViewById(R.id.afficherAdresse).setOnClickListener(this);
    }
 
        //M�thode d�clencher au clique sur un bouton
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.choix_source:
			choisirSource();
			break;
		case R.id.obtenir_position:
			obtenirPosition();
			break;
		case R.id.afficherAdresse:
			afficherAdresse();
			break;
		default:
			break;
		}
	}
 
	//R�initialisation de l'�cran
	private void reinitialisationEcran(){
		((TextView)findViewById(R.id.latitude)).setText("0.0");
		((TextView)findViewById(R.id.longitude)).setText("0.0");
		((TextView)findViewById(R.id.altitude)).setText("0.0");
		((TextView)findViewById(R.id.adresse)).setText("");
 
		findViewById(R.id.obtenir_position).setEnabled(false);
		findViewById(R.id.afficherAdresse).setEnabled(false);
	}
 
	private void choisirSource() {
		reinitialisationEcran();
 
		//On demande au service la liste des sources disponibles.
		List <String> providers = lManager.getProviders(true);
		final String[] sources = new String[providers.size()];
		int i =0;
		//on stock le nom de ces source dans un tableau de string
		for(String provider : providers)
			sources[i++] = provider;
 
		//On affiche la liste des sources dans une fen�tre de dialog
		//Pour plus d'infos sur AlertDialog, vous pouvez suivre le guide
		//http://developer.android.com/guide/topics/ui/dialogs.html
		new AlertDialog.Builder(localisationActivity.this)
		.setItems(sources, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				findViewById(R.id.obtenir_position).setEnabled(true);
				//on stock le choix de la source choisi
				choix_source = sources[which];
				//on ajoute dans la barre de titre de l'application le nom de la source utilis�
				setTitle(String.format("%s - %s", getString(R.string.app_name),
						choix_source));
			}
		})
		.create().show();
	}
 
	private void obtenirPosition() {
		//on d�marre le cercle de chargement
		setProgressBarIndeterminateVisibility(true);
 
		//On demande au service de localisation de nous notifier tout changement de position
		//sur la source (le provider) choisie, toute les minutes (60000millisecondes).
		//Le param�tre this sp�cifie que notre classe impl�mente LocationListener et recevra
		//les notifications.
		lManager.requestLocationUpdates(choix_source, 60000, 0, this);
	}
 
	private void afficherLocation() {
		//On affiche les informations de la position a l'�cran
		((TextView)findViewById(R.id.latitude)).setText(String.valueOf(location.getLatitude()));
		((TextView)findViewById(R.id.longitude)).setText(String.valueOf(location.getLongitude()));
		((TextView)findViewById(R.id.altitude)).setText(String.valueOf(location.getAltitude()));
	}
 
	private void afficherAdresse() {
		setProgressBarIndeterminateVisibility(true);
		// On met en place le passage entre les deux activit�s sur ce Listener 
       Intent intent = new Intent(localisationActivity.this,IteneraireActivity.class);
        intent.putExtra("latitude",location.getLatitude());
        intent.putExtra("longitude",location.getLongitude());
        ((TextView)findViewById(R.id.adresse)).setText(""+location.getLatitude()+" "+location.getLongitude());
        startActivity(intent); }
 
		//Le geocoder permet de r�cup�rer ou chercher des adresses
		//gr�ce � un mot cl� ou une position
		/*Geocoder geo = new Geocoder(GeolocActivity.this);
		try {
			//Ici on r�cup�re la premiere adresse trouv� gr�ce � la position que l'on a r�cup�r�
			List
<Address> adresses = geo.getFromLocation(location.getLatitude(),
					location.getLongitude(),1);
 
			if(adresses != null && adresses.size() == 1){
				Address adresse = adresses.get(0);
				//Si le geocoder a trouver une adresse, alors on l'affiche
				((TextView)findViewById(R.id.adresse)).setText(String.format("%s, %s %s",
						adresse.getAddressLine(0),
						adresse.getPostalCode(),
						adresse.getLocality()));
			}
			else {
				//sinon on affiche un message d'erreur
				((TextView)findViewById(R.id.adresse)).setText("L'adresse n'a pu �tre d�termin�e");
			}
		} catch (IOException e) {
			e.printStackTrace();
			((TextView)findViewById(R.id.adresse)).setText("L'adresse n'a pu �tre d�termin�e");
		}
		//on stop le cercle de chargement
		setProgressBarIndeterminateVisibility(false);
	}
 */
	/*public void onLocationChanged(Location location) {
		//Lorsque la position change...
		Log.i("Tuto g�olocalisation", "La position a chang�.");
		//... on stop le cercle de chargement
		setProgressBarIndeterminateVisibility(false);
		//... on active le bouton pour afficher l'adresse
		findViewById(R.id.afficherAdresse).setEnabled(true);
		//... on sauvegarde la position
		this.location = location;
		//... on l'affiche
		afficherLocation();
		//... et on sp�cifie au service que l'on ne souhaite plus avoir de mise � jour
		lManager.removeUpdates(this);
	}
 
	public void onProviderDisabled(String provider) {
		//Lorsque la source (GSP ou r�seau GSM) est d�sactiv�
		Log.i("Tuto g�olocalisation", "La source a �t� d�sactiv�");
		//...on affiche un Toast pour le signaler � l'utilisateur
		Toast.makeText(localisationActivity.this,
				String.format("La source \"%s\" a �t� d�sactiv�", provider),
				Toast.LENGTH_SHORT).show();
		//... et on sp�cifie au service que l'on ne souhaite plus avoir de mise � jour
		lManager.removeUpdates(this);
		//... on stop le cercle de chargement
		setProgressBarIndeterminateVisibility(false);
	}
 
	public void onProviderEnabled(String provider) {
		Log.i("Tuto g�olocalisation", "La source a �t� activ�.");
	}
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.i("Tuto g�olocalisation", "Le statut de la source a chang�.");
	}
 
}
*/

package com.nadir.localinformationservice;





import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
public class localisationActivity extends Activity {
private LocationManager locationManager;
private String locationProvider = LocationManager.GPS_PROVIDER;
private char[] latitude;
private char[] longitude; 
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
String locationContext = Context.LOCATION_SERVICE; 
locationManager = (LocationManager) getSystemService(locationContext);
if (locationManager != null && locationProvider != null) {
majCoordonnes(); 
locationManager.requestLocationUpdates(locationProvider, 6000,100, new MajListener());
}
}
public void majCoordonnes() {
StringBuilder stringBuilder = new StringBuilder();
stringBuilder.append("Fournisseurs :");
stringBuilder.append("\n");
stringBuilder.append(locationProvider);
stringBuilder.append(" : ");
Location location =
locationManager.getLastKnownLocation(locationProvider);
if (location != null) {
double latitude = location.getLatitude();
double longitude = location.getLongitude();
stringBuilder.append(latitude);
stringBuilder.append(", ");
stringBuilder.append(longitude);
} else {
stringBuilder.append("Non d�termin�e");
}
Log.d("MaPositionMaj", stringBuilder.toString());
Intent intent = new Intent(localisationActivity.this,MyMapActivity.class);
intent.putExtra("latitude",location.getLatitude());
intent.putExtra("longitude",location.getLongitude());


startActivity(intent); }


class MajListener implements android.location.LocationListener { 
public void onLocationChanged(Location location) { 
majCoordonnes();
}
private void majCoordonnes() {
	// TODO Auto-generated method stub
	
}
public void onProviderDisabled(String provider){
}
public void onProviderEnabled(String provider){
}
public void onStatusChanged(String provider, int status, Bundle extras){
}
};

}
