package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    ActivityDetailBinding activityDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        activityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.ivImage);

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
        Sandwich sandwich = null;
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

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.ic_no_image2)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // Also Known As
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        if (alsoKnownAsList.isEmpty()) {
            activityDetailBinding.tvAlsoKnownAsLabel.setVisibility(View.GONE);
            activityDetailBinding.tvAlsoKnownAs.setVisibility(View.GONE);
        } else {
            String csList = alsoKnownAsList.get(0);

            for (int i = 1; i < alsoKnownAsList.size(); i++) {
                csList = csList + ", " + alsoKnownAsList.get(i);
            }
            activityDetailBinding.tvAlsoKnownAs.setText(csList);
        }

        // Description
        String description = sandwich.getDescription();
        if (description.length() == 0 || description.equals("")) {
            activityDetailBinding.tvDescription.setText("No Description Available");
        } else {
            activityDetailBinding.tvDescription.setText(description);
        }

        // Ingredients
        List<String> ingredientsList = sandwich.getIngredients();
        if (ingredientsList.isEmpty()) {
            activityDetailBinding.tvIngredients.setText("No Ingredients listed");
        } else {
            String plusSeparatedList = "+ " + ingredientsList.get(0);
            for (int i = 0; i < ingredientsList.size(); i++) {
                plusSeparatedList = plusSeparatedList + "\n+ " + ingredientsList.get(i);
            }
            activityDetailBinding.tvIngredients.setText(plusSeparatedList);
        }

        // Origin
        String placeOrigin = sandwich.getPlaceOfOrigin();
        if (!placeOrigin.equals("")) {
            activityDetailBinding.tvOrigin.setText(placeOrigin);
        }
    }
}
