package com.techspaceke.cookit.ui;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.techspaceke.cookit.R;
import com.techspaceke.cookit.models.Recipes;

import org.parceler.Parcels;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private static final String TAG = RecipeDetailFragment.class.getSimpleName();
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
//        ButterKnife.bind(this,view);
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
        String instructions = mRecipes.getStrInstructions();
        String[] in = TextUtils.split(instructions,".\n");
        String inst = TextUtils.join(", \n", in);
        mInstructionsTextView.setText(inst);

        String gIng = "getStrIngredient";
        List<String> ing = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            ing.add(gIng+i);
            Log.e(TAG, ing.get(0));
        }
        List<String> getStrIng = new ArrayList<>();
        Map<String, String> ingMap = new HashMap<>();
        ingMap.put("getStrIngredient1",mRecipes.getStrIngredient1());
        ingMap.put("getStrIngredient2",mRecipes.getStrIngredient2());
        ingMap.put("getStrIngredient3",mRecipes.getStrIngredient3());
        ingMap.put("getStrIngredient4",mRecipes.getStrIngredient4());
        ingMap.put("getStrIngredient5",mRecipes.getStrIngredient5());
        ingMap.put("getStrIngredient6",mRecipes.getStrIngredient6());
        ingMap.put("getStrIngredient7",mRecipes.getStrIngredient7());
        ingMap.put("getStrIngredient8",mRecipes.getStrIngredient8());
        ingMap.put("getStrIngredient9",mRecipes.getStrIngredient9());
        ingMap.put("getStrIngredient10",mRecipes.getStrIngredient10());
        ingMap.put("getStrIngredient11",mRecipes.getStrIngredient11());
        ingMap.put("getStrIngredient12",mRecipes.getStrIngredient12());
        ingMap.put("getStrIngredient13",mRecipes.getStrIngredient13());
        ingMap.put("getStrIngredient14",mRecipes.getStrIngredient14());
        ingMap.put("getStrIngredient15",mRecipes.getStrIngredient15());
        ingMap.put("getStrIngredient16",mRecipes.getStrIngredient16());

        for (int i = 0; i < 16; i++) {
            String ingredientItem = ingMap.get(ing.get(i));
            if (ingredientItem != null && !(ingredientItem.isEmpty())){
                getStrIng.add(ingredientItem);
                Log.e(TAG, ingMap.get(ing.get(i)));
            }
//            Log.e(TAG, ingMap.get(ing.get(i)));
//            Log.e(TAG, ing.get(i));
        }

        ArrayAdapter<String> ingredientAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,getStrIng);
        mIngredientsListView.setAdapter(ingredientAdapter);

        Integer itemHeight = mIngredientsListView.getHeight();
        mIngredientsListView.setLayoutParams(new LinearLayout.LayoutParams(getActivity().getWindow().getWindowManager().getDefaultDisplay().getWidth(),100*getStrIng.size()));

        return view;
    }

}
class Dog {
   private String name;

   public Dog(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }
}

