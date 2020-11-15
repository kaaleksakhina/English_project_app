package my.project.english;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import joinery.DataFrame;

public class WordTranslationStateAdapter extends FragmentStateAdapter {

    private List<Word> words;

    public WordTranslationStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

        this.words = this.intDatas();
    }

    private List<Word> intDatas()  {
        Word emp1 = new Word("James Smith", "jamessmith@example.com", "Web Designer");
        Word emp2 = new Word("Elizabeth Johnson", "elizabethjohnson@example.com", "Project Manager");
        Word emp3 = new Word("Catherine Johnson", "catherinejohnson@example.com", "President of Sales");

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