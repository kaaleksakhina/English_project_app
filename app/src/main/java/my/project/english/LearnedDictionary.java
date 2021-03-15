package my.project.english;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LearnedDictionary extends AppCompatActivity {

    private RecyclerView wordsList;
    private LearnedDictionaryAdapter wordsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learned_dictionary);

        wordsList = findViewById(R.id.words);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        wordsList.setLayoutManager(layoutManager);

        wordsList.setHasFixedSize(true);

        wordsAdapter = new LearnedDictionaryAdapter(100);

        wordsList.setAdapter(wordsAdapter);

    }
}