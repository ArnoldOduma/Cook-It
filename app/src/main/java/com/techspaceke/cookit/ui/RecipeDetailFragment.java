package com.techspaceke.cookit.ui;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.techspaceke.cookit.R;
import com.techspaceke.cookit.models.Recipes;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailFragment extends Fragment {
    @BindView(R.id.titleTextView) TextView mTitleTextView;
    @BindView(R.id.ingredientsListView) ListView mIngredientsListView;
    @BindView(R.id.instructionsTextView) TextView mInstructionsTextView;
    @BindView(R.id.recipeImageView) ImageView mRecipeImageView;

    private Recipes mRecipes;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    public static RecipeDetailFragment newInstance (Recipes recipes) {
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("recipe", Parcels.wrap(recipes));
        recipeDetailFragment.setArguments(args);
        return recipeDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecipes = Parcels.unwrap(getArguments().getParcelable("recipe"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reipe_detail, container, false);
        ButterKnife.bind(this,view);

        Picasso.get().load(mRecipes.getStrMealThumb()).into(mRecipeImageView);
        mTitleTextView.setText(mRecipes.getStrMeal());
        mInstructionsTextView.setText(mRecipes.getStrInstructions());

        return view;
    }

}
