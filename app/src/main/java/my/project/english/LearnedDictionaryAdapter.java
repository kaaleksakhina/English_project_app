package my.project.english;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import joinery.DataFrame;

import static android.content.Context.MODE_PRIVATE;

public class LearnedDictionaryAdapter extends RecyclerView.Adapter<LearnedDictionaryAdapter.WordViewHolder>{
    private final AssetManager assets;
    List<Word> words;
    Context context;
    public int[] array_learned;
    DataFrame df;

    public LearnedDictionaryAdapter (AssetManager assets, int unit, Context context) {
        this.assets = assets;
        this.context = context;
        array_learned = loadArrayLearned();
        this.words = this.intDatas(unit);
    }
    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.learned_dictionary_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        WordViewHolder viewHolder = new WordViewHolder(view);

        return viewHolder;
    }

    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        Word word = words.get(position);
        holder.wordItem.setText(word.getWord());
        holder.translationItem.setText(word.getTranslation());
    }

    @Override
    public int getItemCount() {
        return this.words.size();
    }

    private List<Word> intDatas(int unit)  {
        List<String> l_words = new ArrayList<>();
        List<String> l_translations = new ArrayList<>();


        // word - translation
        try {
            df = DataFrame.readCsv(assets.open("csv_page_1.csv"), ";");
            updateDFUnit(array_learned);
            DataFrame df_need = df.select((DataFrame.Predicate<Object>) values -> Long.class.cast(values.get(10)) == 1);
            l_words = (List<String>)df_need.col("Word");
            l_translations = (List<String>)df_need.col("Translation");

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Word> list = new ArrayList<Word>();
        for (int i = 0; i < l_words.size(); i++)
            list.add(new Word(l_words.get(i), l_translations.get(i)));
        return list;
    }

    class WordViewHolder extends RecyclerView.ViewHolder {

        TextView wordItem;
        TextView translationItem;

        public WordViewHolder(View itemView) {
            super(itemView);

            wordItem = itemView.findViewById(R.id.word_item);
            translationItem = itemView.findViewById(R.id.translation_item);
        }
    }

    public void updateDFUnit(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1) {
                df.set(i, 10, 1L);
            }
        }
    }

    public int[] loadArrayLearned() {
        int[] array = new int[170];
        SharedPreferences prefs = context.getSharedPreferences("wordsIdLearned", MODE_PRIVATE);
        for(int i = 0; i < array.length; i++)
            array[i] = prefs.getInt(String.valueOf(i), 0);
        return array;
    }
}
