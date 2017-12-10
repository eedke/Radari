package mnmnm.radari;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

public class GetDataFromJSON extends AppCompatActivity {

    public String url = "http://radarizzh.mywebcommunity.org/radari.json";
    public RequestQueue requestQueue;
    public Context context;
    public GetDataFromJSON(Context context) {
        this.context = context;
    }

    public void getData(){
        requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    String date;
                    String PU;
                    String time1;
                    String time2;
                    String place;
                    SugarRadars radar;
                    for(int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        date = jsonObject.getString("date");
                        PU = jsonObject.getString("PU");
                        time1 = jsonObject.getString("time1");
                        time2 = jsonObject.getString("time2");
                        place = jsonObject.getString("place");
                        if(checkExisting(date, PU, time1)){
                            radar = new SugarRadars(date,PU, time1,time2,place);
                            radar.save();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
    }
    public boolean checkExisting(String date1, String kljuc1, String kljuc2){

        List<SugarRadars> allRadars = SugarRadars.listAll(SugarRadars.class);

        if(allRadars.isEmpty()){
            return true;
        }else{
            for(int i = 0;i<allRadars.size();i++) {
                if(allRadars.get(i).getDate().equals(date1)) {
                    if (allRadars.get(i).getPU().equals(kljuc1)) {
                        if (allRadars.get(i).getTime1().equals(kljuc2))
                            return false;
                    }
                }
            }
        }
        return true;
    }
}
/** SugarRadars sugarRadars38 = new SugarRadars("30-07-2017", "PU Grude", "08:00", "09:30", "Gorica"); // 08:00 – 09:30 h na M-6, u mjestu Gorica,
 sugarRadars38.save(); */