package my.project.english;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
    Dialog dialog;

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

        //кнопка помощи - открыть диалоговое окно в начале
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.previewdialog);// путь к макету диалогового окна
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // прозрачный фон диалогового окна
        dialog.setCancelable(false); // окно нельзя закрыть кнопкой назад

        // кнопка, закрывающая диалоговое окно
        TextView btnclose = (TextView)dialog.findViewById(R.id.btnclose);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //обрабатываем нажатие кнопки
                try {
                    Intent intent = new Intent(Choice.this, Choice.class);
                    startActivity(intent);
                    finish();
                }
                catch (Exception E){

                }
                dialog.dismiss(); // закрыть диалоговое окно
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