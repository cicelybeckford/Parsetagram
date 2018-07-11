package codepath.com.parsetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;

public class LogoutActivity extends AppCompatActivity {

    private Button btnOfficialLogout;
    private ParseUser user = ParseUser.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        btnOfficialLogout = findViewById(R.id.btnOfficialLogout);

        btnOfficialLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.logOut();
                startActivity(new Intent(LogoutActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
