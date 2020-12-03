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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import joinery.DataFrame;

public class Practice extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;
    public int count = 0;
    public String right_answer, word;

    Dialog dialog, dialog2;

    DataFrame df, df_unit = null;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal_practice);
        Random random = new Random();


        final int[] progress = { R.id.point1,  R.id.point2, R.id.point3, R.id.point4, R.id.point5, R.id.point6,
                R.id.point7,  R.id.point8,  R.id.point9,  R.id.point10};

        final int Options[] = {
                R.id.option1,
                R.id.option2,
                R.id.option3,
                R.id.option4
        };

        final TextView word_final = (TextView) findViewById(R.id.textView1);
        final TextView option1 = (TextView) findViewById(Options[0]);
        final TextView option2 = (TextView) findViewById(Options[1]);
        final TextView option3 = (TextView) findViewById(Options[2]);
        final TextView option4 = (TextView) findViewById(Options[3]);

        final Animation a = AnimationUtils.loadAnimation(Practice.this, R.anim.alpha);

        SharedPreferences save = getSharedPreferences("Save", MODE_PRIVATE);
        final int unit = save.getInt("Unit", 1);

        try {
            df = DataFrame.readCsv(getAssets().open("csv_page_1.csv"), ";");
            df_unit = df.select((DataFrame.Predicate<Object>) values -> Long.class.cast(values.get(0)) <= unit);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Open full screen
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /// Button Help - open dialog window
        dialog2 = new Dialog(this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.dialog_window);// way to dialog window layout
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // transparent background for dialog window
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
                            catch (Exception ignored) {
                                ignored.printStackTrace();
                            }
                            dialog2.dismiss(); // закрыть диалоговое окно
                        }
                    });
                }catch (Exception ignored) {

                }
            }
        });

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.previewdialog);// путь к макету диалогового окна
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // прозрачный фон диалогового окна
        dialog.setCancelable(false); // окно нельзя закрыть кнопкой назад

        TextView textView = dialog.findViewById(R.id.textdescription);
        textView.setText(R.string.done_practice);

        // Button Continue in Dialog window
        Button btn_continue = (Button) dialog.findViewById(R.id.button_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent  = new Intent(Practice.this, Choice.class);
                    startActivity(intent);
                    finish();
                }catch (Exception ignored) {

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
                }catch (Exception ignored) {

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
                }catch (Exception ignored) {

                }
            }
        });

        ArrayList<String> Choices = getChoices();
        word = Choices.get(4);
        right_answer = Choices.get(5);

        // set the word and options on the places
        word_final.setText(word);
        option1.setText(Choices.get(0));
        option2.setText(Choices.get(1));
        option3.setText(Choices.get(2));
        option4.setText(Choices.get(3));
        //Choices.remove(5);

        // tap on the option1
        option1.setOnTouchListener(new View.OnTouchListener() {
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

                        if (option2.getText().toString().equals(right_answer)) option2.setBackgroundResource(R.drawable.the_real_answer);
                        if (option3.getText().toString().equals(right_answer)) option3.setBackgroundResource(R.drawable.the_real_answer);
                        if (option4.getText().toString().equals(right_answer)) option4.setBackgroundResource(R.drawable.the_real_answer);
                    }
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    wait_for();
                    if (option1.getText().toString().equals(right_answer)) {
                        if (count < 10) {
                            count++;
                        }
                        make_true(word);
                        for (int i = 0; i < 10; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        for (int i = 0; i < count; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_teal);
                        }
                    }
                    else {
                        if (count > 0) {
                            count = count - 1;
                        }
                        for (int i = 0; i < 10; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        for (int i = 0; i < count; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_teal);
                        }
                    }
                    if (count == 10) {
                        dialog.show();
                        updateDF();
                    }
                    else {
                        for (int option : Options) {
                            TextView op = (TextView) findViewById(option);
                            op.setBackgroundResource(R.drawable.button_stroke_black95_press_white);
                            op.setEnabled(true);
                        }

                        ArrayList<String> Choices = getChoices();

                        right_answer = Choices.get(5);
                        word = Choices.get(4);
                        word_final.setText(word);

                        for (int i = 0; i < Options.length; i++) {
                            TextView op = (TextView) findViewById(Options[i]);
                            op.setText(Choices.get(i));
                            op.setAnimation(a);
                        }
                    }
                }
                return true;
            }
        });

        // tap on the option2
        option2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    option1.setEnabled(false);
                    option3.setEnabled(false);
                    option4.setEnabled(false);
                    if (option2.getText().toString().equals(right_answer)) {
                        option2.setBackgroundResource(R.drawable.right_choice_green);
                    }
                    else {
                        option2.setBackgroundResource(R.drawable.wrong_choice_red);

                        if (option1.getText().toString().equals(right_answer)) option1.setBackgroundResource(R.drawable.the_real_answer);
                        if (option3.getText().toString().equals(right_answer)) option3.setBackgroundResource(R.drawable.the_real_answer);
                        if (option4.getText().toString().equals(right_answer)) option4.setBackgroundResource(R.drawable.the_real_answer);
                    }
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    wait_for();
                    if (option2.getText().toString().equals(right_answer)) {
                        if (count < 10) {
                            count++;
                        }
                        make_true(word);
                        for (int i = 0; i < 10; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        for (int i = 0; i < count; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_teal);
                        }
                    }
                    else {
                        if (count > 0) {
                            count = count - 1;
                        }
                        for (int i = 0; i < 10; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        for (int i = 0; i < count; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_teal);
                        }
                    }
                    if (count == 10) {
                        dialog.show();
                        updateDF();
                    }
                    else {
                        for (int option : Options) {
                            TextView op = (TextView) findViewById(option);
                            op.setBackgroundResource(R.drawable.button_stroke_black95_press_white);
                            op.setEnabled(true);
                        }

                        ArrayList<String> Choices = getChoices();

                        right_answer = Choices.get(5);
                        word = Choices.get(4);
                        word_final.setText(word);

                        for (int i = 0; i < Options.length; i++) {
                            TextView op = (TextView) findViewById(Options[i]);
                            op.setText(Choices.get(i));
                            op.setAnimation(a);
                        }
                    }
                }
                return true;
            }
        });

        // tap on the option3
        option3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    option1.setEnabled(false);
                    option2.setEnabled(false);
                    option4.setEnabled(false);
                    if (option3.getText().toString().equals(right_answer)) {
                        option3.setBackgroundResource(R.drawable.right_choice_green);
                    }
                    else {
                        option3.setBackgroundResource(R.drawable.wrong_choice_red);

                        if (option1.getText().toString().equals(right_answer)) option1.setBackgroundResource(R.drawable.the_real_answer);
                        if (option2.getText().toString().equals(right_answer)) option2.setBackgroundResource(R.drawable.the_real_answer);
                        if (option4.getText().toString().equals(right_answer)) option4.setBackgroundResource(R.drawable.the_real_answer);
                    }
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    wait_for();
                    if (option3.getText().toString().equals(right_answer)) {
                        if (count < 10) {
                            count++;
                        }
                        make_true(word);
                        for (int i = 0; i < 10; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        for (int i = 0; i < count; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_teal);
                        }
                    }
                    else {
                        if (count > 0) {
                            count = count - 1;
                        }
                        for (int i = 0; i < 10; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        for (int i = 0; i < count; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_teal);
                        }
                    }
                    if (count == 10) {
                        dialog.show();
                        updateDF();
                    }
                    else {
                        for (int option : Options) {
                            TextView op = (TextView) findViewById(option);
                            op.setBackgroundResource(R.drawable.button_stroke_black95_press_white);
                            op.setEnabled(true);
                        }

                        ArrayList<String> Choices = getChoices();

                        right_answer = Choices.get(5);
                        word = Choices.get(4);
                        word_final.setText(word);

                        for (int i = 0; i < Options.length; i++) {
                            TextView op = (TextView) findViewById(Options[i]);
                            op.setText(Choices.get(i));
                            op.setAnimation(a);
                        }
                    }
                }
                return true;
            }
        });

        // tap on the option4
        option4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    option1.setEnabled(false);
                    option2.setEnabled(false);
                    option3.setEnabled(false);
                    if (option4.getText().toString().equals(right_answer)) {
                        option4.setBackgroundResource(R.drawable.right_choice_green);
                    }
                    else {
                        option4.setBackgroundResource(R.drawable.wrong_choice_red);
                        if (option1.getText().toString().equals(right_answer)) option1.setBackgroundResource(R.drawable.the_real_answer);
                        if (option2.getText().toString().equals(right_answer)) option2.setBackgroundResource(R.drawable.the_real_answer);
                        if (option3.getText().toString().equals(right_answer)) option3.setBackgroundResource(R.drawable.the_real_answer);
                    }
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    wait_for();
                    if (option4.getText().toString().equals(right_answer)) {
                        if (count < 10) {
                            count++;
                        }
                        make_true(word);
                        for (int i = 0; i < 10; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        for (int i = 0; i < count; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_teal);
                        }
                    }
                    else {
                        if (count > 0) {
                            count = count - 1;
                        }
                        for (int i = 0; i < 10; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        for (int i = 0; i < count; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_teal);
                        }
                    }
                    if (count == 10) {
                        dialog.show();
                        updateDF();
                    }
                    else {
                        for (int option : Options) {
                            TextView op = (TextView) findViewById(option);
                            op.setBackgroundResource(R.drawable.button_stroke_black95_press_white);
                            op.setEnabled(true);
                        }

                        ArrayList<String> Choices = getChoices();

                        right_answer = Choices.get(5);
                        word = Choices.get(4);
                        word_final.setText(word);

                        for (int i = 0; i < Options.length; i++) {
                            TextView op = (TextView) findViewById(Options[i]);
                            op.setText(Choices.get(i));
                            op.setAnimation(a);
                        }
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
    public ArrayList<String> getChoices () {
        ArrayList<String> Choices = new ArrayList<>(); // список переводов
        Integer[] rand = null;

        DataFrame df_need = df_unit.select((DataFrame.Predicate<Object>) values -> Long.class.cast(values.get(8)) == 0);
        List<String> l_words = (List<String>)df_need.col("Word");
        List<String> l_translations = (List<String>)df_need.col("Translation");
        List<Boolean> l_learned = (List<Boolean>)df_need.col("Learned");

        rand = getRand(l_translations);

        String word = l_words.get(rand[0]);
        String right_translation = l_translations.get(rand[0]);

        for (Integer integer : rand) {
            Choices.add(l_translations.get(integer));
        }
        Collections.shuffle(Choices);

        Choices.add(word);
        Choices.add(right_translation);
        return Choices;

    }

    // list of 4 random numbers for words
    public Integer[] getRand (List<String> l_translations) {
        Random random = new Random();
        Integer[] rand = new Integer[] {0,0,0,0};

        // 4 random id of words
        for (int i = 0; i < 4; i++) {
            rand[i] = random.nextInt(l_translations.size());
        }
        for (int i = 0; i < 3; i++) {
            for (int j = i + 1; j < 4; j++) {
                while (rand[i].equals(rand[j])) {
                    rand[i] = random.nextInt(l_translations.size());
                }
            }
        }
        return rand;
    }

    public void wait_for() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void make_true (String word) { // if the word is learned - change to true
        List<String> l_words = (List<String>)df_unit.col("Word");

        int ind = l_words.indexOf(word);
        df_unit.set(ind, 8, 1);
    }

    public void updateDF(){
        df.update(df_unit);
        try {
            df.writeCsv("csv_page_1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

