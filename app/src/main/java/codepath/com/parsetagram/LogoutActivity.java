package codepath.com.parsetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;

public class LogoutActivity extends AppCompatActivity {

    private Button btnOfficialLogout;
    private ParseUser user = ParseUser.getCurrentUser();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        bottomNavigationView = findViewById(R.id.bottom_navigation_logout);
        btnOfficialLogout = findViewById(R.id.btnOfficialLogout);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        goHome();
                        return true;
                    case R.id.action_post:
                        //newPost();
                        return true;
                    case R.id.action_user:
                        //logOut();
                        return true;
                }
                return true;
            }
        });

        btnOfficialLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.logOut();
                startActivity(new Intent(LogoutActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void goHome() {
        Intent intent = new Intent(this,FeedActivity.class);
        startActivity(intent);
        finish();
    }
}
