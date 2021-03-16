package my.project.english;

import android.content.Context;
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

public class LearnedDictionaryAdapter extends RecyclerView.Adapter<LearnedDictionaryAdapter.WordViewHolder>{
    private final AssetManager assets;
    List<Word> word;

    public LearnedDictionaryAdapter ( AssetManager assets, int unit) {
        this.assets = assets;
        this.word = this.intDatas(unit);
    }
    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.learned_dictionary_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        WordViewHolder viewHolder = new WordViewHolder(view);
        viewHolder.viewHolderIndex.setText(word.get(1).getWord());

        return viewHolder;
    }

    public void onBindViewHolder(@NonNull WordViewHolder holder, int id) {
        holder.bind(id);
    }

    @Override
    public int getItemCount() {
        return this.word.size();
    }

    private List<Word> intDatas(int unit)  {
        DataFrame df;
        List<String> l_words = new ArrayList<>();
        List<String> l_translations = new ArrayList<>();

        // word - translation - examples, collocations
        try {
            df = DataFrame.readCsv(assets.open("csv_page_1.csv"), ";");
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

        TextView listItemWordView;
        TextView viewHolderIndex;

        public WordViewHolder(View itemView) {
            super(itemView);

            listItemWordView = itemView.findViewById(R.id.word_item);
            viewHolderIndex = itemView.findViewById(R.id.translation_item);
        }

        void bind(int id) {
            listItemWordView.setText(word.get(id).getWord());
        }
    }
}
