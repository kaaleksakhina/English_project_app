package my.project.english;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LearnedDictionaryAdapter extends RecyclerView.Adapter<LearnedDictionaryAdapter.WordViewHolder>{
    private int numberItems;
    private static int viewHolderCount;

    public LearnedDictionaryAdapter (int numberOfItems) {
        numberItems = numberOfItems;
        viewHolderCount = 0;
    }
    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.learned_dictionary_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        WordViewHolder viewHolder = new WordViewHolder(view);
        viewHolder.viewHolderIndex.setText("ViewHolder index: " + viewHolderCount);

        viewHolderCount++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return numberItems;
    }

    class WordViewHolder extends RecyclerView.ViewHolder {

        TextView listItemWordView;
        TextView viewHolderIndex;

        public WordViewHolder(View itemView) {
            super(itemView);

            listItemWordView = itemView.findViewById(R.id.word_item);
            viewHolderIndex = itemView.findViewById(R.id.view_holder_number);
        }

        void bind(int word) {
            listItemWordView.setText(String.valueOf(word));
        }
    }
}
