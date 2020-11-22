package my.project.english;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import joinery.DataFrame;

public class Practice extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;
    public int count = 0;

    Dialog dialog2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal_practice);
        Random random = new Random();

        final int[] progress = { R.id.point1,  R.id.point2, R.id.point3, R.id.point4, R.id.point5, R.id.point6,
                R.id.point7,  R.id.point8,  R.id.point9,  R.id.point10};

        final TextView word_final = (TextView) findViewById(R.id.textView1);
        final TextView option1 = (TextView) findViewById(R.id.option1);
        final TextView option2 = (TextView) findViewById(R.id.option2);
        final TextView option3 = (TextView) findViewById(R.id.option3);
        final TextView option4 = (TextView) findViewById(R.id.option4);

        final Animation a = AnimationUtils.loadAnimation(Practice.this, R.anim.alpha);

        SharedPreferences save = getSharedPreferences("Save", MODE_PRIVATE);
        final int unit = save.getInt("Unit", 1);

        // Развернуть игру на весь экран
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /// Button Help - open dialog window
        dialog2 = new Dialog(this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.activity_help);// путь к макету диалогового окна
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // прозрачный фон диалогового окна
        dialog2.setCancelable(false); // окно нельзя закрыть кнопкой назад

        ImageView buttonHelp = (ImageView) findViewById(R.id.imageQuestionMark);
        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dialog2.show();
                    // Button closing dialog window
                    TextView btnclose = (TextView)dialog2.findViewById(R.id.btnclose);
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
                    Intent intent  = new Intent(Practice.this, Choice.class);
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
                    Intent intent  = new Intent(Practice.this, Choice.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e) {

                }
            }
        });

        List<String> Choices = getChoices(unit);
        String right_answer = Choices.get(5);
        String word = Choices.get(4);

        // set options on the places
        word_final.setText(Choices.get(4));
        option1.setText(Choices.get(0));
        option2.setText(Choices.get(1));
        option3.setText(Choices.get(2));
        option4.setText(Choices.get(3));


        // tap on the option1
        option1.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    option2.setEnabled(false);
                    option3.setEnabled(false);
                    option4.setEnabled(false);
                    if (option1.getText().toString().equals(right_answer)) {
                        option1.setBackgroundResource(R.drawable.right_choice_green);
                    }
                    else {
                        option1.setBackgroundResource(R.drawable.wrong_choice_red);
                    }
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (option1.getText().toString().equals(right_answer)) {
                        if (count < 10) {
                            count++;
                        }
                        for (int i = 0; i < 10; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        for (int i = 0; i < count; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_orange);
                        }
                    }
                    else {
                        if(count > 0) {
                            count = count - 1;
                        }
                        for (int i  = 0; i < 9; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        for (int i = 0; i < count; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_orange);
                        }
                    }
                    if (count == 10) {
                        // exit
                    }
                    else {
                        option1.setBackgroundResource(R.drawable.button_stroke_black95_press_white);
                        List<String> Choices = getChoices(unit);
                        String right_answer = Choices.get(5);
                        String word = Choices.get(4);

                        // set options on the places
                        option1.setText(Choices.get(0));
                        option1.setAnimation(a);

                        option2.setText(Choices.get(1));
                        option2.setAnimation(a);

                        option3.setText(Choices.get(2));
                        option3.setAnimation(a);

                        option4.setText(Choices.get(3));
                        option4.setAnimation(a);
                    }
                }
                return true;
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

    // list of 4 translations and a word
    public List<String> getChoices (int unit) {
        List<String> Choices = new ArrayList<>(); // список переводов
        DataFrame df;
        List<String> l_words = new ArrayList<>();
        List<String> l_translations = new ArrayList<>();

        try {
            df = DataFrame.readCsv(getAssets().open("csv_page_1.csv"));
            DataFrame df_unit_1 = df.select((DataFrame.Predicate<Object>) values -> Long.class.cast(values.get(0)) == unit);
            l_words = (List<String>)df_unit_1.col("Word");
            l_translations = (List<String>)df_unit_1.col("Translation");
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Integer> rand = getRand(l_words);

        String word = l_words.get(rand.get(0));
        String right_translation = l_translations.get(rand.get(0));

        for (int i = 0; i < Choices.size(); i++) {
            Choices.add(l_translations.get(rand.get(i)));
        }
        Collections.shuffle(Choices);

        Choices.add(word);
        Choices.add(right_translation);
        return Choices;

    }

    // list of 4 random numbers for words
    public List<Integer> getRand (List<String> l_words) {
        Random random = new Random();
        List<Integer> rand = new ArrayList<>();

        // 4 random id of words
        for (int i = 0; i < 4; i++) {
            rand.add(random.nextInt(l_words.size()));
        }
        for (int i = 0; i < 3; i++) {
            for (int j = i + 1; j < 4; j++) {
                while (rand.get(i).equals(rand.get(j))) {
                    rand.add(i, random.nextInt(l_words.size()));
                }
            }
        }

        return rand;
    }
}

