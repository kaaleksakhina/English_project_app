package my.project.english;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LearnedDictionary extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;
    Dialog dialog;

    private RecyclerView wordsList;
    private LearnedDictionaryAdapter wordsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learned_dictionary);

        SharedPreferences save = getSharedPreferences("Save", MODE_PRIVATE);
        final int unit = save.getInt("Unit", 1);
        SharedPreferences prefs = getSharedPreferences("wordsIdLearned", MODE_PRIVATE);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        wordsList = findViewById(R.id.words);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        wordsList.setLayoutManager(layoutManager);
        wordsList.setHasFixedSize(false);

        wordsAdapter = new LearnedDictionaryAdapter(getAssets(), unit, prefs);
        wordsList.setAdapter(wordsAdapter);

        //Button Help - open dialog window
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_window);// путь к макету диалогового окна
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // прозрачный фон диалогового окна
        dialog.setCancelable(false); // окно нельзя закрыть кнопкой наза
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
                            catch (Exception ignored){}
                            dialog.dismiss(); // закрыть диалоговое окно
                        }
                    });
                }catch (Exception ignored) {}
            }
        });

        // Arrow Back
        ImageView arrowBack = (ImageView) findViewById(R.id.imageArrowBack);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent  = new Intent(LearnedDictionary.this, ChoiceDictionary.class);
                    startActivity(intent);
                    finish();
                }catch (Exception ignored) {}
            }
        });

        // main button BEC
        ImageView btnBEC = (ImageView) findViewById(R.id.imageBEC);
        btnBEC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(LearnedDictionary.this, ChoiceMain.class);
                    startActivity(intent);
                    finish();
                } catch (Exception ignored) {

                }
            }
        });

    }

}