package my.project.english;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Exercise2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex_2);
    }

    public void onBackPressed(){
        try {
            Intent intent  = new Intent(Exercise2.this, Choice.class);
            startActivity(intent); finish();
        }catch (Exception e) {

        }
    }
}