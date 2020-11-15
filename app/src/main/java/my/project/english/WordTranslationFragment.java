package my.project.english;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WordTranslationFragment extends Fragment {

    private static final String LOG_TAG = "AndroidExample";

    private Word word;

    private TextView TextViewWord;
    private TextView TextViewTranslation;
    private TextView TextViewExample;

    private static int counter = 0;

    public WordTranslationFragment() {

    }

    public WordTranslationFragment(Word word) {
        this.word = word;
    }

    // отображение разным цветом
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(
                R.layout.fragment_word__translation, container, false);

        counter++;
        if(counter % 2 == 0) {
            view.setBackgroundColor(Color.parseColor("#ebdef0"));
        } else  {
            view.setBackgroundColor(Color.parseColor("#e8f8f5"));
        }

        this.TextViewWord = view.findViewById(R.id.word);
        this.TextViewTranslation = view.findViewById(R.id.translation);
        this.TextViewExample = view.findViewById(R.id.examples);

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.i(LOG_TAG, "onSaveInstanceState: save employee data to Bundle");
        // Convert word object to Bundle.
        Bundle dataBundle = this.wordToBundle(this.word);
        outState.putAll(dataBundle);

        super.onSaveInstanceState(outState);
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onViewStateRestored");

        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);

        if(this.word == null)  {
            Log.i(LOG_TAG, "Get word data from savedInstanceState");
            // The state was saved by onSaveInstanceState(Bundle outState) method.
            this.word = this.bundleToWord(savedInstanceState);
        }
        this.showInGUI(this.word);
    }

    // Call where View ready.
    private void showInGUI(Word word)  {
        this.TextViewWord.setText(word.getWord());
        this.TextViewTranslation.setText(word.getTranslation());
        this.TextViewExample.setText(word.getExamples());
    }

    private Bundle wordToBundle(Word word)  {
        Bundle bundle = new Bundle();
        bundle.putString("word", word.getWord());
        bundle.putString("translation", word.getTranslation());
        bundle.putString("example", word.getExamples());

        return bundle;
    }

    private Word bundleToWord(Bundle savedInstanceState) {
        String word = savedInstanceState.getString("word");
        String translation = savedInstanceState.getString("translation");
        String example = savedInstanceState.getString("example");
        return new Word(word, translation, example);
    }

}