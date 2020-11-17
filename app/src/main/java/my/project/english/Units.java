package my.project.english;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import joinery.DataFrame;


public class Units extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;
    private ViewPager2 viewPager2Word;
    private String Units[];

    Dialog dialog;
    Dialog dialog2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal);

        // Развернуть игру на весь экран
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //открыть диалоговое окно в начале
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
                    Intent intent = new Intent(Units.this, Choice.class);
                    startActivity(intent);
                    finish();
                }
                catch (Exception E){

                }
                dialog.dismiss(); // закрыть диалоговое окно
            }
        });

        // Button Continue
        Button btn_continue = (Button)dialog.findViewById(R.id.button_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss(); // close dialog window
            }
        });

        dialog.show();

        //Button Help - open dialog window
        dialog2 = new Dialog(this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.activity_help);// путь к макету диалогового окна
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // прозрачный фон диалогового окна
        dialog2.setCancelable(false); // окно нельзя закрыть кнопкой наза

        ImageView buttonHelp = (ImageView) findViewById(R.id.imageQuestionMark);
        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dialog2.show();
                    // Button closing dialog window
                    TextView btnclose = (TextView)dialog.findViewById(R.id.btnclose);
                    btnclose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //обрабатываем нажатие кнопки
                            try {
                                dialog2.dismiss();
                            }
                            catch (Exception E){

                            }
                            dialog2.dismiss(); // закрыть диалоговое окно
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
                    Intent intent  = new Intent(Units.this, Choice2.class);
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
                    Intent intent  = new Intent(Units.this, Choice.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e) {

                }
            }
        });

        DataFrame df;
        List<String> l_units = new ArrayList<>();

        // list of units' names
        try {
            df = DataFrame.readCsv(getAssets().open("csv_page_2.csv"));
            l_units = (List<String>)df.col("Name");
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextView text_units_name = findViewById(R.id.text_units_name);
        int unitNumber = (int) getIntent().getSerializableExtra("unitNumber");
        text_units_name.setText(l_units.get(unitNumber)); //name of the unit

        // Displaying word - translation
        this.viewPager2Word = findViewById(R.id.viewPager2_word);

        WordTranslationStateAdapter adapter = new WordTranslationStateAdapter(this, getAssets(), unitNumber);
        this.viewPager2Word.setAdapter(adapter);
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
