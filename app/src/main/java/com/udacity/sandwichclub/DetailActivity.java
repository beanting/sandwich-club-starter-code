package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;


import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwiches) {
        TextView originTextView = findViewById(R.id.place_of_origin_tv);
        TextView alsoKnownAsLabelTextView = findViewById(R.id.also_known_as_label_tv);
        TextView alsoKnownAsTextView = findViewById(R.id.also_known_as_tv);
        TextView descriptionTextView = findViewById(R.id.description_tv);
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);
        CardView alsoKnowAsCardView = findViewById(R.id.also_know_as_cv);

        String originText = sandwiches.getPlaceOfOrigin();
        if (originText.isEmpty()){
            originTextView.setText(R.string.detail_error_message);
        } else {
            originTextView.setText(originText);
        }

        List<String> alsoKnownAsList = sandwiches.getAlsoKnownAs();
        if (alsoKnownAsList.size() == 0){
            alsoKnownAsLabelTextView.setVisibility(View.GONE);
            alsoKnownAsTextView.setVisibility(View.GONE);
            alsoKnowAsCardView.setVisibility(View.GONE);
        } else {
            alsoKnownAsLabelTextView.setVisibility(View.VISIBLE);
            alsoKnownAsTextView.setVisibility(View.VISIBLE);
            alsoKnowAsCardView.setVisibility(View.VISIBLE);

            StringBuilder otherNames = new StringBuilder();
            for (String otherName : alsoKnownAsList) {
                otherNames.append(otherName).append(", ");
            }
            otherNames.setLength(otherNames.length() - 2);
            alsoKnownAsTextView.setText(otherNames);
        }

        List<String> ingredientsList = sandwiches.getIngredients();
        if (ingredientsList.size() == 0){
            ingredientsTextView.setText(R.string.detail_error_message);
        } else {
            StringBuilder ingredients = new StringBuilder();

            for (String ingredient : ingredientsList) {
                ingredients.append(ingredient).append(", ");
            }
            ingredients.setLength(ingredients.length() - 2);
            ingredientsTextView.setText(ingredients);
        }

        String description = sandwiches.getDescription();
        if (description.isEmpty()) {
            descriptionTextView.setText(R.string.detail_error_message);
        } else {
            descriptionTextView.setText(description);
        }
    }
}
