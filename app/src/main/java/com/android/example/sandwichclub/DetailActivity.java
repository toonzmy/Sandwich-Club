package com.android.example.sandwichclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.example.sandwichclub.model.Sandwich;
import com.android.example.sandwichclub.utils.JsonUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView ingredientsIv;

    private TextView mDescriptionTextView;
    private TextView mOriginTextView;
    private TextView mIngredientsTextView;
    private  TextView mAlsoKnownTextView;

    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        mDescriptionTextView = findViewById(R.id.description_tv);
        mOriginTextView = findViewById(R.id.origin_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        mAlsoKnownTextView = findViewById(R.id.also_known_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.get()
                .load(sandwich.getImage())
                .placeholder(R.drawable.ic_launcher_background)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        mOriginTextView.setText(sandwich.getPlaceOfOrigin());
        mDescriptionTextView.setText(sandwich.getDescription());

        String ingredients = covertToList(sandwich.getIngredients());
        mIngredientsTextView.setText(ingredients);

        String alsoKnownAsNames = covertToList(sandwich.getAlsoKnownAs());
        mAlsoKnownTextView.setText(alsoKnownAsNames);

        //ingredientsIv.setIm
    }

    private String covertToList(List<String> stuff) {
        String merge = "";
        for (String s: stuff) {
            merge += "- " + s + "\n";
        }
        return merge;
    }
}
