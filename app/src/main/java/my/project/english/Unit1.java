package my.project.english;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import joinery.DataFrame;

public class Unit1 extends Activity {

    Dialog dialog;
    Dialog dialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal);

        // Развернуть игру на весь экран
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView text_units = findViewById(R.id.text_units);
        text_units.setText(R.string.unit1n); //name of the unit

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
                    Intent intent = new Intent(Unit1.this, Choice.class);
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
                    Intent intent  = new Intent(Unit1.this, Choice2.class);
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
                    Intent intent  = new Intent(Unit1.this, Choice.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e) {

                }
            }
        });


        DataFrame df;
        List<String> words = new ArrayList<>();
        List<String> translations = new ArrayList<>();

        try {
            df = DataFrame.readCsv(getAssets().open("csv_page_1.csv"));
            DataFrame df_unit_1 = df.select((DataFrame.Predicate<Object>) values -> Long.class.cast(values.get(0)) == 1);
            words = (List<String>)df_unit_1.col("Word");
            translations = (List<String>)df_unit_1.col("Translation");
        } catch (IOException e) {
            e.printStackTrace();
        }


        LinearLayout linLayout = (LinearLayout) findViewById(R.id.container3);
            LayoutInflater llInflater = getLayoutInflater();

            for (int i = 0; i < words.size(); i++) {

                View item = llInflater.inflate(R.layout.listitem, linLayout, false);
                TextView word = (TextView) item.findViewById(R.id.words);
                word.setText(words.get(i));
                word.setTextColor(Color.rgb(100,100,100));

                TextView translation = (TextView) item.findViewById(R.id.translations);
                translation.setText(translations.get(i));
                translation.setTextColor(Color.rgb(100,100,100));

                item.getLayoutParams().width = WindowManager.LayoutParams.WRAP_CONTENT;
                linLayout.addView(item);
            }
    }

    public void onBackPressed(){
        try {
            Intent intent  = new Intent(Unit1.this, Choice.class);
            startActivity(intent); finish();
        }catch (Exception e) {

        }
    }
}