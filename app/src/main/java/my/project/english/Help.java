package my.project.english;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    public void onBackPressed(){
        try {
            Intent intent  = new Intent(Help.this, Choice.class);
            startActivity(intent); finish();
        }catch (Exception e) {

        }
    }
}