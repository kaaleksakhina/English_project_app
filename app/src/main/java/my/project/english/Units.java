package my.project.english;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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


public class Units extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;
    Dialog dialog, dialog2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal_dictionary);

        int unitNumber = (int) getIntent().getSerializableExtra("unitNumber");
        SharedPreferences save = getSharedPreferences("Save", MODE_PRIVATE);
        final boolean click = save.getBoolean("Click", false);

        // Развернуть игру на весь экран
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (unitNumber == 1 && !click) {
            SharedPreferences.Editor editor = save.edit();
            editor.putBoolean("Click", true);
            editor.apply();

            //открыть диалоговое окно в начале
            dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.window_end_practice);// путь к макету диалогового окна
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // прозрачный фон диалогового окна
            dialog.setCancelable(false); // окно нельзя закрыть кнопкой назад

            TextView textView = dialog.findViewById(R.id.textdescription);
            textView.setText(R.string.first_mes);

            // Button Continue in Dialog window
            Button btn_continue = (Button) dialog.findViewById(R.id.button_continue);
            btn_continue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss(); // close dialog window
                }
            });
            dialog.show();
        }

        // Button End
        Button button_end = (Button) findViewById(R.id.button_end);
        button_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences save = getSharedPreferences("Save", MODE_PRIVATE);
                final int unit = save.getInt("Unit", 1);

                if (unit == 1 && unitNumber == 1){
                    SharedPreferences.Editor editor = save.edit();
                    editor.putInt("Unit", 2);
                    editor.apply();
                }
                else if (unit != 1 && unitNumber == unit && unit < 24){
                    SharedPreferences.Editor editor = save.edit();
                    editor.putInt("Unit", unit + 1);
                    editor.apply();
                }
                Intent intent = new Intent(Units.this, ChoiceUnits.class);
                startActivity(intent);
                finish();
            }
        });

        /// Button Help - open dialog window
        dialog2 = new Dialog(this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.dialog_window);// путь к макету диалогового окна
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // прозрачный фон диалогового окна
        dialog2.setCancelable(false); // окно нельзя закрыть кнопкой назад
        TextView textdescription = (TextView) dialog2.findViewById(R.id.textdescription);
        textdescription.setText(R.string.help_description);

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
                            catch (Exception ignored){

                            }
                            dialog2.dismiss(); // закрыть диалоговое окно
                        }
                    });
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
                    Intent intent  = new Intent(Units.this, ChoiceUnits.class);
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
                    Intent intent  = new Intent(Units.this, ChoiceMain.class);
                    startActivity(intent);
                    finish();
                }catch (Exception ignored) {

                }
            }
        });

        // Displaying word - translation
        ViewPager2 viewPager2Word = findViewById(R.id.viewPager2_word);

        WordTranslationStateAdapter adapter = new WordTranslationStateAdapter(this, getAssets(), unitNumber);
        viewPager2Word.setAdapter(adapter);

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
