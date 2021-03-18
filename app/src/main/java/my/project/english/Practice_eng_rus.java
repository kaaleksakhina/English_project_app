package my.project.english;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import joinery.DataFrame;

public class Practice_eng_rus extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;
    public Integer count = 0;
    public Integer count_right = 0;
    public ArrayList<Integer> prog = new ArrayList<Integer>();
    public String right_answer, word;
    public int[] array_learned;

    Dialog dialog, dialog2, end;

    DataFrame df, df_unit = null;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal_practice);
        Random random = new Random();
        for (int i = 0; i < 10; i++) prog.add(0);

        final int[] progress = {R.id.point1, R.id.point2, R.id.point3, R.id.point4, R.id.point5, R.id.point6,
                R.id.point7, R.id.point8, R.id.point9, R.id.point10};

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

        HashSet<TextView> tVOptions = (HashSet<TextView>) Stream.of(option1, option2, option3, option4).collect(Collectors.toSet());

        final Animation a = AnimationUtils.loadAnimation(Practice_eng_rus.this, R.anim.alpha);

        SharedPreferences save = getSharedPreferences("Save", MODE_PRIVATE);
        final int unit = save.getInt("Unit", 1);

        array_learned = loadArrayLearned();

        try {
            df = DataFrame.readCsv(getAssets().open("csv_page_1.csv"));
            updateDFUnit(array_learned);
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
                    TextView btnclose = (TextView) dialog2.findViewById(R.id.btnclose);
                    btnclose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //обрабатываем нажатие кнопки
                            try {
                                dialog2.dismiss();
                            } catch (Exception ignored) {
                                ignored.printStackTrace();
                            }
                            dialog2.dismiss(); // закрыть диалоговое окно
                        }
                    });
                } catch (Exception ignored) {

                }
            }
        });

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.window_end_practice);// путь к макету диалогового окна
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // прозрачный фон диалогового окна
        dialog.setCancelable(false); // окно нельзя закрыть кнопкой назад

        TextView textView = dialog.findViewById(R.id.textdescription);
        TextView textView2 = dialog.findViewById(R.id.textdescription2);
        TextView points = dialog.findViewById(R.id.points);

        textView.setText(R.string.done_practice);
        textView2.setText(R.string.done_practice2);
        textView2.setTextSize(14);

        // Button Continue in Dialog window
        Button btn_continue = (Button) dialog.findViewById(R.id.button_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Practice_eng_rus.this, ChoicePractice.class);
                    startActivity(intent);
                    finish();
                } catch (Exception ignored) {

                }
            }
        });

        end = new Dialog(this);
        end.requestWindowFeature(Window.FEATURE_NO_TITLE);
        end.setContentView(R.layout.dialog_window);
        end.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        end.setCancelable(false); // окно нельзя закрыть кнопкой назад
        TextView textdescription = (TextView) end.findViewById(R.id.textdescription);
        textdescription.setText(R.string.end_words);

        TextView btnclose = (TextView) end.findViewById(R.id.btnclose);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //обрабатываем нажатие кнопки
                try {
                    Intent intent = new Intent(Practice_eng_rus.this, ChoicePractice.class);
                    startActivity(intent);
                    end.dismiss();
                    finish();
                } catch (Exception ignored) {
                }
            }
        });

        // Arrow Back
        ImageView arrowBack = (ImageView) findViewById(R.id.imageArrowBack);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Practice_eng_rus.this, ChoicePractice.class);
                    startActivity(intent);
                    finish();
                } catch (Exception ignored) {

                }
            }
        });

        // main button BEC
        ImageView btnBEC = (ImageView) findViewById(R.id.imageBEC);
        btnBEC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Practice_eng_rus.this, ChoiceMain.class);
                    startActivity(intent);
                    finish();
                } catch (Exception ignored) {

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

        for (TextView currentOption : tVOptions) {
            HashSet<TextView> tVOptionsCopy = (HashSet<TextView>) tVOptions.clone();
            tVOptionsCopy.remove(currentOption);

            currentOption.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        for (TextView otherOption : tVOptionsCopy) {
                            otherOption.setEnabled(false);
                        }
                        if (currentOption.getText().toString().equals(right_answer)) {
                            currentOption.setBackgroundResource(R.drawable.right_choice_green);
                        } else {
                            currentOption.setBackgroundResource(R.drawable.wrong_choice_red);
                            for (TextView otherOption : tVOptionsCopy) {
                                if (otherOption.getText().toString().equals(right_answer))
                                    otherOption.setBackgroundResource(R.drawable.the_real_answer);
                            }
                        }
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        wait_for();
                        if (currentOption.getText().toString().equals(right_answer)) {
                            if (count < 10) {
                                count++;
                                count_right++;
                            }
                            prog.set(count - 1, 1);
                            make_true(word);
                            for (int i = 0; i < 10; i++) {
                                TextView tv = findViewById(progress[i]);
                                tv.setBackgroundResource(R.drawable.style_points);
                            }
                            for (int i = 0; i < count; i++) {
                                TextView tv = findViewById(progress[i]);
                                if (prog.get(i) == 1) {
                                    tv.setBackgroundResource(R.drawable.style_points_teal);
                                } else {
                                    tv.setBackgroundResource(R.drawable.style_points_red);
                                }
                            }
                        } else {
                            count++;
                            for (int i = 0; i < 10; i++) {
                                TextView tv = findViewById(progress[i]);
                                tv.setBackgroundResource(R.drawable.style_points);
                            }
                            for (int i = 0; i < count; i++) {
                                TextView tv = findViewById(progress[i]);
                                if (prog.get(i) == 1) {
                                    tv.setBackgroundResource(R.drawable.style_points_teal);
                                } else {
                                    tv.setBackgroundResource(R.drawable.style_points_red);
                                }
                            }
                        }
                        if (count == 10) {
                            points.setText(count_right.toString());
                            updateDF(array_learned);
                            dialog.show();

                        } else {
                            for (int option : Options) {
                                TextView op = (TextView) findViewById(option);
                                op.setBackgroundResource(R.drawable.button_stroke_black95_press_white);
                                op.setEnabled(true);
                            }

                            ArrayList<String> Choices = getChoices();
                            if (Choices.size() == 0) {
                                updateDF(array_learned);
                                end.show();
                            }
                            else {
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
                    }
                    return true;
                }
            });
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

    // list of 4 translations and a word
    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<String> getChoices() {
        Random random = new Random();
        ArrayList<String> Choices = new ArrayList<>(); // список переводов

        DataFrame df_unlearned = df_unit.select((DataFrame.Predicate<Object>) values -> Long.class.cast(values.get(10)) == 0);
        if (df_unlearned.length() == 0) return Choices;

        int l_ids_size = ((List<Long>)df_unlearned.col("id_word")).size();
        List<String> l_words_unlearned = (List<String>)df_unlearned.col("Word");

        List<String> l_words = (List<String>)df.col("Word");
        List<String> l_translations = (List<String>)df.col("Translation");

        int rand_id = random.nextInt(l_ids_size);
        String right_word = l_words_unlearned.get(rand_id);
        String right_translation = l_translations.get(l_words.indexOf(right_word));
        String part_of_speech = (String) df.get(l_words.indexOf(right_word), 2);

        DataFrame actual_df = df.select((DataFrame.Predicate<Object>) values -> String.class.cast(values.get(2)).equals(part_of_speech));
        List<String> actual_translations = (List<String>)actual_df.col("Translation");

        Integer[] rand = getRand(actual_translations.size(), actual_translations.indexOf(right_translation));

        for (Integer i : rand) {
            Choices.add(actual_translations.get(i));
        }
        Collections.shuffle(Choices);

        Choices.add(right_word);
        Choices.add(right_translation);
        return Choices;

    }

    // list of 4 random numbers for words
    public Integer[] getRand (int translations, int id) {
        Random random = new Random();
        Integer[] rand = new Integer[] {0,0,0,0};
        int r = 0;

        // 4 random id of words
        for (int i = 0; i < 3; i++) {
            r = random.nextInt(translations);
            while (r == id){
                 r = random.nextInt(translations);
            }
            if (0 < i) {
                for (int j = i - 1; j >= 0; j--) {
                    while (rand[j] == r && rand[j] != 0) {
                        r = random.nextInt(translations);
                    }
                }
            }
            rand[i] = r;
        }
        rand[3] = id;
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
        List<String> l_words = (List<String>)df.col("Word");
        Integer ind = l_words.indexOf(word);
        df_unit.set(ind, 10, 1L);
        df.set(ind, 10, 1L);
        array_learned[ind] = 1;
    }

    public void updateDFUnit(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1) {
                df.set(i, 10, 1L);
            }
        }
    }
    public void updateDF(int[] array){
        saveArrayLearned(array);
    }

    public int[] loadArrayLearned() {
        int[] array = new int[170];
        SharedPreferences prefs = getSharedPreferences("wordsIdLearned", MODE_PRIVATE);
        for(int i = 0; i < array.length; i++)
            array[i] = prefs.getInt(String.valueOf(i), 0);
        return array;
    }

    public void saveArrayLearned(int[] array) {
        SharedPreferences prefs = getSharedPreferences("wordsIdLearned", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        for(int i = 0; i < array.length; i++)
            editor.putInt(String.valueOf(i), array[i]);
        editor.commit();
    }
}