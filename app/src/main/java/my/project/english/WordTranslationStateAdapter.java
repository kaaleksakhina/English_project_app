package my.project.english;

import android.content.res.AssetManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import joinery.DataFrame;

public class WordTranslationStateAdapter extends FragmentStateAdapter {

    private final AssetManager assets;
    private List<Word> words;

    public WordTranslationStateAdapter(@NonNull FragmentActivity fragmentActivity, AssetManager assets) {
        super(fragmentActivity);

        this.assets = assets;
        this.words = this.intDatas();
    }

    private List<Word> intDatas()  {
        Word emp1 = new Word("James Smith", "jamessmith@example.com", "Web Designer");
        Word emp2 = new Word("Elizabeth Johnson", "elizabethjohnson@example.com", "Project Manager");
        Word emp3 = new Word("Catherine Johnson", "catherinejohnson@example.com", "President of Sales");

        DataFrame df;
        List<String> words = new ArrayList<>();
        List<String> translations = new ArrayList<>();
        List<String> examples = new ArrayList<>();

        // word - translation
        try {
            df = DataFrame.readCsv(assets.open("csv_page_1.csv"));
            DataFrame df_unit_1 = df.select((DataFrame.Predicate<Object>) values -> Long.class.cast(values.get(0)) == 1);
            words = (List<String>)df_unit_1.col("Word");
            translations = (List<String>)df_unit_1.col("Translation");
            examples = (List<String>)df_unit_1.col("Examples of sentences");
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Word> list = new ArrayList<Word>();
        list.add(emp1);
        list.add(emp2);
        list.add(emp3);
        return list;
    }


    @NonNull
    @Override
    public Fragment createFragment(int translation) {
        Word word = this.words.get(translation);
        return new WordTranslationFragment(word);
    }


    @Override
    public int getItemCount() {
        return this.words.size();
    }
}