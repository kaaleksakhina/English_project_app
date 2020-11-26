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

import java.util.ArrayList;
import java.util.List;

public class WordTranslationFragment extends Fragment {
    private static final String LOG_TAG = "AndroidExample";

    private Word word;

    private TextView TextViewWord;
    private TextView TextViewTranslation;
    private TextView TextViewExample1;
    private TextView TextViewExample2;
    private TextView TextViewExample3;

    public WordTranslationFragment() { }

    public WordTranslationFragment(Word word) {
        this.word = word;
    }

    // отображение разным цветом
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(
                R.layout.fragment_word__translation, container, false);

        view.setBackgroundColor(Color.parseColor("#FFFFFFFF"));

        this.TextViewWord = view.findViewById(R.id.word);
        this.TextViewTranslation = view.findViewById(R.id.translation);
        this.TextViewExample1 = view.findViewById(R.id.example1);
        this.TextViewExample2 = view.findViewById(R.id.example2);
        this.TextViewExample3 = view.findViewById(R.id.example3);

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.i(LOG_TAG, "onSaveInstanceState: save word data to Bundle");
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
        this.TextViewExample1.setText(word.getExamples().get(0));
        this.TextViewExample2.setText(word.getExamples().get(1));
        if (word.getExamples().get(2) != "") {
            this.TextViewExample3.setText(word.getExamples().get(2));
            this.TextViewExample3.setTextSize(14);
        }

        this.TextViewWord.setAllCaps(true);

    }

    private Bundle wordToBundle(Word word)  {
        Bundle bundle = new Bundle();
        bundle.putString("word", word.getWord());
        bundle.putString("translation", word.getTranslation());
        bundle.putString("example1", word.getExamples().get(0));
        bundle.putString("example2", word.getExamples().get(1));
        bundle.putString("example3", word.getExamples().get(2));

        return bundle;
    }

    private Word bundleToWord(Bundle savedInstanceState) {
        String word = savedInstanceState.getString("word");
        String translation = savedInstanceState.getString("translation");
        String example1 = savedInstanceState.getString("example");
        String example2 = savedInstanceState.getString("example2");
        String example3 = savedInstanceState.getString("example3");
        String collocations = savedInstanceState.getString("collocations");
        List <String> examples= new ArrayList<>();
        examples.add(example1);
        examples.add(example2);
        examples.add(example3);
        return new Word(word, translation, examples, collocations);
    }

}