package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView alsoKnownAsTV;
    private TextView placeOfOriginTV;
    private TextView ingredientsTV;
    private ImageView imageIV;
    private TextView descriptionTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("DetailActivity", "EXTRA_POSITION:"+EXTRA_POSITION);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        placeOfOriginTV = findViewById(R.id.placeOfOrigin_tv);
        alsoKnownAsTV = findViewById(R.id.alsoKnownAs_tv);
        ingredientsTV = findViewById(R.id.ingredients_tv);
        descriptionTV = findViewById(R.id.description_tv);
        imageIV = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        //Log.d("EXTRA_POSITION new", String.valueOf(position));
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            Log.d("sandwich", "sandwich == null");
            closeOnError();
            return;
        }

        String mainName = sandwich.getMainName();
        Log.d("sandwich:", mainName);

        populateUI(sandwich);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        setTitle(sandwich.getMainName());
        alsoKnownAsTV.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        placeOfOriginTV.setText(sandwich.getPlaceOfOrigin());
        descriptionTV.setText(sandwich.getDescription());
        ingredientsTV.setText(TextUtils.join(", ", sandwich.getIngredients()));
        Picasso.with(this).load(sandwich.getImage()).into(imageIV);

    }
}
