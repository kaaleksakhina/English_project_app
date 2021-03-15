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

public class ChoiceMain extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal_choice);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button ch1 = (Button)findViewById(R.id.ch1);
        Button ch2 = (Button)findViewById(R.id.ch2);
        ch1.setText(R.string.ex_1);
        ch2.setText(R.string.ex_2);

        // button to dictionary
        ch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(ChoiceMain.this, ChoiceDictionary.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {

                }
            }
        });

        //button to practice
        ch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(ChoiceMain.this, ChoicePractice.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {

                }
            }
        });

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
                    Intent intent  = new Intent(ChoiceMain.this, MainActivity.class);
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