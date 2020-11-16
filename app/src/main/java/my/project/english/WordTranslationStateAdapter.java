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
        DataFrame df;
        List<String> l_words = new ArrayList<>();
        List<String> l_translations = new ArrayList<>();
        List<String> l_examples = new ArrayList<>();

        // word - translation
        try {
            df = DataFrame.readCsv(assets.open("csv_page_1.csv"));
            DataFrame df_unit_1 = df.select((DataFrame.Predicate<Object>) values -> Long.class.cast(values.get(0)) == 1);
            l_words = (List<String>)df_unit_1.col("Word");
            l_translations = (List<String>)df_unit_1.col("Translation");
            l_examples = (List<String>)df_unit_1.col("Examples of sentences");
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Word> list = new ArrayList<Word>();

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