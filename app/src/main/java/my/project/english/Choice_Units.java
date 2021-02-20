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

public class Choice_Units extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_units);
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
                                Intent intent = new Intent(Choice_Units.this, Units.class);
                                intent.putExtra("unitNumber", finalUnitNumber);
                                startActivity(intent);
                                finish();
                            }
                        } catch (Exception ignored) {

                        }
                    });
                }
            }
        }

        // Button Help - open dialog window
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_window);// путь к макету диалогового окна
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // прозрачный фон диалогового окна
        dialog.setCancelable(false); // окно нельзя закрыть кнопкой назад
        TextView textdescription = (TextView) dialog.findViewById(R.id.textdescription);
        textdescription.setText(R.string.help_description);

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
                    Intent intent  = new Intent(Choice_Units.this, Choice_dic_prac.class);
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
                    Intent intent  = new Intent(Choice_Units.this, Choice_dic_prac.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e) {

                }
            }
        });

        final int text[] = {
                R.id.textView1,
                R.id.textView2,
                R.id.textView3,
                R.id.textView4,
                R.id.textView5,
                R.id.textView6,
                R.id.textView7,
                R.id.textView8,
                R.id.textView9,
                R.id.textView10,
                R.id.textView11,
                R.id.textView12,
                R.id.textView13,
                R.id.textView14,
                R.id.textView15,
                R.id.textView16,
                R.id.textView17,
                R.id.textView18,
                R.id.textView19,
                R.id.textView20,
                R.id.textView21,
                R.id.textView22,
                R.id.textView23,
                R.id.textView24
        };

        final int images[] = {
                R.id.imageView1,
                R.id.imageView2,
                R.id.imageView3,
                R.id.imageView4,
                R.id.imageView5,
                R.id.imageView6,
                R.id.imageView7,
                R.id.imageView8,
                R.id.imageView9,
                R.id.imageView10,
                R.id.imageView11,
                R.id.imageView12,
                R.id.imageView13,
                R.id.imageView14,
                R.id.imageView15,
                R.id.imageView16,
                R.id.imageView17,
                R.id.imageView18,
                R.id.imageView19,
                R.id.imageView20,
                R.id.imageView21,
                R.id.imageView22,
                R.id.imageView23,
                R.id.imageView24
        };

        for (int i = unit; i < images.length; i++) {
            ImageView im = (ImageView) findViewById(images[i]);
            im.setColorFilter(R.color.black95);
            TextView tv = (TextView) findViewById(text[i]);
            tv.setBackgroundResource(R.color.black95);
            tv.setTextColor(Integer.valueOf("b35f00", 16));
        }
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