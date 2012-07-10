package com.nadir.localinformationservice;
 

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.maps.MapActivity;

 
    public class VitryhubActivity extends MapActivity {
    
    	//private MapView mapView;
    	//Declaration de variables de class:
    	TextView now_a_293 ;
       	TextView now_r_293 ;
        TextView next_a_293;
        TextView next_r_293;
        TextView now_a_132 ;
        TextView now_r_132 ;
        TextView next_a_132;
        TextView next_r_132;
           
        TextView indiceauj , indicedem , polluantauj , polluantdem , niveauauj , niveaudem ;
    
    
     private Handler handler = new Handler() {

    	public void handleMessage(android.os.Message msg) {

    	     if(msg.what == 0) {
 	        
 	         setTpsAttente(getTpsAttente());

    	                       }
    	     
    	      if(msg.what == 1){
    		  setPollution(getPollution());
    	                       }

    	                                                  };

    	                                        };
	//private Object webView1;
    
    
	
        public void onCreate(Bundle savedInstanceState) {
        	
             super.onCreate(savedInstanceState);
             setContentView(R.layout.interfaceapplicationvitryhub);
             
               OnClickListener btnlistener= new OnClickListener() {
			
			      public void onClick(View actuelView) {
			    	 // try
			  		{
			  		//InetAddress thisIp = InetAddress.getLocalHost();
			  		
			  		//Toast.makeText(VitryhubActivity.this, (CharSequence) thisIp, Toast.LENGTH_LONG).show();
			  		//System.out.println("IP:"+thisIp.getHostAddress());
			  		
			    	 // Toast.makeText(VitryhubActivity.this, (CharSequence) thisIp, Toast.LENGTH_LONG).show();
				    Intent intent=new Intent(VitryhubActivity.this,localisationActivity.class) ;
				
				
				       startActivity(intent);
			  		}
				  		//catch(UnknownHostException e)
				  		//{
				  		//e.printStackTrace();
				  		//}
			                                            }
                                                                 };
                 super.onCreate(savedInstanceState);

                 ImageButton localiser = (ImageButton)this.findViewById(R.id.mapacces);
                 localiser.setOnClickListener(btnlistener);
              
        /*mapView = (MapView) this.findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);*/
        
       
                now_a_293 = (TextView)this.findViewById(R.id.now_A_293);  
                now_r_293 = (TextView)this.findViewById(R.id.now_R_293);
                next_a_293 = (TextView)this.findViewById(R.id.next_A_293);
                next_r_293 = (TextView)this.findViewById(R.id.next_R_293);
        
        
               now_a_132 = (TextView)this.findViewById(R.id.now_A_132);  
               now_r_132 = (TextView)this.findViewById(R.id.now_R_132);
               next_a_132 = (TextView)this.findViewById(R.id.next_A_132);
               next_r_132 = (TextView)this.findViewById(R.id.next_R_132);
        
               indiceauj= (TextView)this.findViewById(R.id.indiceauj); 
               indicedem= (TextView)this.findViewById(R.id.indicedem);  
               polluantauj= (TextView)this.findViewById(R.id.polluantauj); 
               polluantdem= (TextView)this.findViewById(R.id.polluantdem); 
               niveauauj= (TextView)this.findViewById(R.id.niveauauj); 
               niveaudem= (TextView)this.findViewById(R.id.niveaudem);

        
        
        
		
        //traiter nos requetes dans des threads:
               
              new Thread(new Runnable() {
            	  
        	   public void run() {
        		traitementBus();
                traiementPollution();
        	                     }

        	                   }).start();
        
       /* new Thread(new Runnable() {
        	public void run() {
        		setTpsAttente(now_a_293, next_a_293, getTpsAttente("293", "A"));
                setTpsAttente(now_r_293, next_r_293, getTpsAttente("293", "R"));
        	    setTpsAttente(now_a_132, next_a_132, getTpsAttente("132", "A"));
                setTpsAttente(now_r_132, next_r_132, getTpsAttente("132", "R"));
        	}

        	}).start();*/
            	 
        
        
        
        
       
	
	
	
        // Instanciation du WebView...
        WebView wvSite = (WebView)findViewById(R.id.webmeteo);
        WebView wvSite1 = (WebView)findViewById(R.id.webView1);
       // ScrollView scrol1=(ScrollView)findViewById(R.id.scrollView1);
       // WebView wvSite2 = (WebView)findViewById(R.id.webview);
       
        //...on active JavaScript...
       WebSettings webSettings = wvSite.getSettings();
       WebSettings webSettings1 = wvSite1.getSettings();
       webSettings1.setBuiltInZoomControls(true);
       webSettings.setJavaScriptEnabled(true);
        
        //...et on charge la page
       wvSite.loadUrl("http://weathersticker.wunderground.com/weathersticker/cgi-bin/banner/ban/wxBanner?bannertype=wu_clean2day_metric_cond&airportcode=LFPO&ForcedCity=Vitry-sur-Seine&ForcedState=&wmo=07149&language=FR");
       wvSite1.loadUrl("http://feedget.infotrafic.com/img.php?nom=idf&type=L&widget_rand=91857");
      // wvSite.loadUrl("http://www.ratp.fr/horaires/fr/ratp/bus/prochains_passages/PP/B293/293_925_1003/A");
    
    }

	private void traiementPollution() {
		
		 new Thread(new Runnable() {

	        	public void run() {
	        		handler.sendEmptyMessage(1);
	                              }

	        	                  }).start();
		
	                                  }

	private void traitementBus() {
		
		Thread busThread =
		 new Thread(new Runnable() {

	        	public void run() {
	        		
	        			while(true){
	        			handler.sendEmptyMessage(0);
	        			try {
							Thread.sleep(5000);
						     } catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						                                       }
	        			            }
	        		
	        	                  }

	        	                  });
		
		         busThread.start();
	
		
		
	                               }


	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	//envoyer la requete a mon service web bus.php
	public String getTpsAttente(){
		
		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(); //On crée la liste qui contiendra tous nos paramètres
	        RequestServer requete = new RequestServer("http://192.168.1.14/projet/bus.php",nameValuePairs);
	        return requete.sendRequest();
	                              }
	
	public void setTpsAttente(String jsonString){
		   JSONObject jObject;
			try {
				jObject = new JSONObject(jsonString);
				JSONObject response = jObject.getJSONObject("response");
				
				
				JSONObject a_293 = response.getJSONObject("293").getJSONObject("A");
				JSONObject r_293 = response.getJSONObject("293").getJSONObject("R");
				

				JSONObject a_132 = response.getJSONObject("132").getJSONObject("A");
				JSONObject r_132 = response.getJSONObject("132").getJSONObject("R");
				
				now_a_293.setText(a_293.getString("now"));
				next_a_293.setText(a_293.getString("next"));
				now_r_293.setText(r_293.getString("now"));
				next_r_293.setText(r_293.getString("next"));
				
				now_a_132.setText(a_132.getString("now"));
				next_a_132.setText(a_132.getString("next"));
				now_r_132.setText(r_132.getString("now"));
				next_r_132.setText(r_132.getString("next"));
		    	
			} catch (JSONException e) {
//				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	public String getPollution(){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		RequestServer requete = new RequestServer("http://192.168.1.14/projet/pollution.php",nameValuePairs);
		return requete.sendRequest();
	} 
	public void setPollution(String jsonString){
		JSONObject jObject;
		try {
			jObject = new JSONObject(jsonString);
			JSONObject response = jObject.getJSONObject("response");
			JSONObject today = response.getJSONObject("today");
			JSONObject tomorrow = response.getJSONObject("tomorrow");
	    	
			indiceauj.setText(today.getString("indice"));
			polluantauj.setText(today.getString("polluant"));
			niveauauj.setText(today.getString("niveau"));
			
			indicedem.setText(tomorrow.getString("indice"));
			polluantdem.setText(tomorrow.getString("polluant"));
			niveaudem.setText(tomorrow.getString("niveau"));
			
			
		} catch (JSONException e) {
//			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}