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
        List<String> l_collocations = new ArrayList<>();
        List< List<String> > l_examples = new ArrayList<>();

        // word - translation - examples, collocations
        try {
            df = DataFrame.readCsv(assets.open("csv_page_1.csv"), ";");
            DataFrame df_unit = df.select((DataFrame.Predicate<Object>) values -> Long.class.cast(values.get(0)) == unit);
            l_words = (List<String>)df_unit.col("Word");
            l_translations = (List<String>)df_unit.col("Translation");
            l_examples.add(df_unit.col("First examples"));
            l_examples.add(df_unit.col("Second examples"));
            l_examples.add(df_unit.col("Third examples"));
            l_collocations= (List<String>)df_unit.col("Сollocations");

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Word> list = new ArrayList<Word>();
        for (int i = 0; i < l_words.size(); i++) {
            List <String> examples = new ArrayList<>();
            for (int j = 0; j < l_examples.size(); j++) examples.add(l_examples.get(j).get(i));
            Word w = new Word(l_words.get(i), l_translations.get(i), examples, l_collocations.get(i));
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