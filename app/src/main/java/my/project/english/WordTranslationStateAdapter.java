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
    public int counter;

    public WordTranslationStateAdapter(@NonNull FragmentActivity fragmentActivity, AssetManager assets, int unit) {
        super(fragmentActivity);

        this.assets = assets;
        this.words = this.intDatas(unit);
    }

    public int getCounter(){
        return counter;
    }

    private List<Word> intDatas(int unit)  {
        DataFrame df;
        List<String> l_words = new ArrayList<>();
        List<String> l_translations = new ArrayList<>();
        List<String> l_examples = new ArrayList<>();

        // word - translation - examples, collocations
        try {
            df = DataFrame.readCsv(assets.open("csv_page_1.csv"));
            DataFrame df_unit_1 = df.select((DataFrame.Predicate<Object>) values -> Long.class.cast(values.get(0)) == unit);
            l_words = (List<String>)df_unit_1.col("Word");
            l_translations = (List<String>)df_unit_1.col("Translation");
            l_examples = (List<String>)df_unit_1.col("First examples");
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Word> list = new ArrayList<Word>();
        for (int i = 0; i < l_words.size(); i++) {
            Word w = new Word(l_words.get(i), l_translations.get(i), l_examples.get(i));
            list.add(w);
        }
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