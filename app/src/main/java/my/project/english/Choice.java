package my.project.english;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Choice extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exersises);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // кнопка для перехода в 1 упражнение
        TextView textView1 = (TextView)findViewById(R.id.textView1);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Choice.this, Exercise1.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {

                }
            }
        });

        // кнопка для перехода вo 2 упражнение
        TextView textView2 = (TextView)findViewById(R.id.textView2);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Choice.this, Exercise2.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {

                }
            }
        });

        // кнопка помощи
        ImageView imageQuestionMark = (ImageView)findViewById(R.id.imageQuestionMark);
        imageQuestionMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Choice.this, Help.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {

                }
            }
        });
        
    }
    // возврат назад
    public void onBackPressed(){
        try {
            Intent intent  = new Intent(Choice.this, MainActivity.class);
            startActivity(intent); finish();
        }catch (Exception e) {

            }
    }
}