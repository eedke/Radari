package mnmnm.radari;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orm.SugarContext;

public class StartingActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnStarting1, btnStarting2, btnStarting3, btnStarting4, btnStartingRefresh;
    TextView tvStarting1, tvStartingRefreshed;
    public GetDataFromJSON g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SugarContext.init(this);
        setContentView(R.layout.activity_starting);

        btnStarting1 = findViewById(R.id.btnStarting1);
        btnStarting2 = findViewById(R.id.btnStarting2);
        btnStarting3 = findViewById(R.id.btnStarting3);
        btnStarting4 = findViewById(R.id.btnStarting4);
        btnStartingRefresh = findViewById(R.id.btnStartingRefresh);

        tvStarting1 = (TextView) findViewById(R.id.tvStarting1);
        tvStartingRefreshed = (TextView) findViewById(R.id.tvStartingRefreshed);

        btnStarting1.setOnClickListener(this);
        btnStarting2.setOnClickListener(this);
        btnStarting3.setOnClickListener(this);
        btnStarting4.setOnClickListener(this);
        btnStartingRefresh.setOnClickListener(this);

        g = new GetDataFromJSON(StartingActivity.this);

    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(StartingActivity.this, MainActivity.class);

        switch (v.getId()){
            case R.id.btnStarting1:
                i.putExtra("opcina", "PU Grude");
                startActivity(i);
                break;
            case R.id.btnStarting2:
                i.putExtra("opcina", "PU Ljubuski");
                startActivity(i);
                break;
            case R.id.btnStarting3:
                i.putExtra("opcina", "PU Posusje");
                startActivity(i);
                break;
            case R.id.btnStarting4:
                i.putExtra("opcina", "PU Siroki Brijeg");
                startActivity(i);
                break;
            case R.id.btnStartingRefresh:
                g.getData();
                break;
        }
    }
}
