package mnmnm.radari;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tvDatum, tvVrijeme, tvMjesto, tvPU;
    public String date, time1, time2, place, pu, kljuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle s = getIntent().getExtras();

        kljuc = s.getString("opcina");

        tvDatum = findViewById(R.id.tvDatum);
        tvVrijeme = findViewById(R.id.tvVrijeme);
        tvMjesto = findViewById(R.id.tvMjesto);
        tvPU = findViewById(R.id.tvPU);

        checkData(kljuc);
    }

    public boolean checkData(String kljuc){

        List<SugarRadars> allRadars = SugarRadars.listAll(SugarRadars.class);

        boolean rtrn = false;
        if(allRadars.isEmpty()){
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }else{
            for(int i = 0;i<allRadars.size();i++) {

                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String strDate = sdf.format(c.getTime());

                date = allRadars.get(i).getDate();
                String opcina = allRadars.get(i).getPU();
                if (date.equals(strDate)) {
                    if(opcina.equals(kljuc)) {
                        pu = allRadars.get(i).getPU();
                        time1 = allRadars.get(i).getTime1();
                        time2 = allRadars.get(i).getTime2();
                        place = allRadars.get(i).getPlace();
                        tvPU.setText(pu);
                        tvDatum.setText(date);
                        tvMjesto.setText(place);
                        tvVrijeme.setText(time1 + " - " + time2);
                        rtrn = true;
                        Toast.makeText(this, "Uspjesno ucitano!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        return rtrn;
    }
}
