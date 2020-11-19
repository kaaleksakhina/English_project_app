package my.project.english;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import joinery.DataFrame;

public class Choice2 extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercises_2page);
        Dialog dialog;

        SharedPreferences save = getSharedPreferences("Save", MODE_PRIVATE);
        final int unit = save.getInt("Unit", 1);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Unit
        LinearLayout container3 = findViewById(R.id.container3);
        int unitNumber = 0;
        for(int i = 0; i < container3.getChildCount(); i++){
            View horizontalLine = container3.getChildAt(i);
            for(int j = 0; j < ((ViewGroup)horizontalLine).getChildCount(); j++){
                View child = ((ViewGroup) horizontalLine).getChildAt(j);
                if(child.getTag() != null && child.getTag().equals("unit")){
                    unitNumber++;
                    int finalUnitNumber = unitNumber;
                    child.setOnClickListener(v -> {
                        try {
                            if (finalUnitNumber <= unit) {
                                Intent intent = new Intent(Choice2.this, Units.class);
                                intent.putExtra("unitNumber", finalUnitNumber);
                                intent.putExtra("unit", unit);
                                startActivity(intent);
                                finish();
                            }
                        } catch (Exception e) {

                        }
                    });
                }
            }
        }

        // Button Help - open dialog window
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_help);// путь к макету диалогового окна
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // прозрачный фон диалогового окна
        dialog.setCancelable(false); // окно нельзя закрыть кнопкой назад

        ImageView buttonHelp = (ImageView) findViewById(R.id.imageQuestionMark);
        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dialog.show();
                    // Button closing dialog window
                    TextView btnclose = (TextView)dialog.findViewById(R.id.btnclose);
                    btnclose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //обрабатываем нажатие кнопки
                            try {
                                dialog.dismiss();
                            }
                            catch (Exception E){

                            }
                            dialog.dismiss(); // закрыть диалоговое окно
                        }
                    });
                }catch (Exception e) {

                }
            }
        });

        // Arrow Back
        ImageView arrowBack = (ImageView) findViewById(R.id.imageArrowBack);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent  = new Intent(Choice2.this, Choice.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e) {

                }
            }
        });

        // main button BEC
        ImageView btnBEC = (ImageView)findViewById(R.id.imageBEC);
        btnBEC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent  = new Intent(Choice2.this, Choice.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e) {

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        }
        else {
            backToast = Toast.makeText(getBaseContext(), "Press one more time to quit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}