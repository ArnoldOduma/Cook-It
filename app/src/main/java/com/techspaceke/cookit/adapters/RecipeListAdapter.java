package com.techspaceke.cookit.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.techspaceke.cookit.R;
import com.techspaceke.cookit.models.Recipes;
import com.techspaceke.cookit.ui.RecipeDetailActivity;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;



public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    String TAG = RecipeListAdapter.class.getSimpleName();


    private List<Recipes> mRecipes = new ArrayList<>();
    private Context mContext;

    public RecipeListAdapter(Context context, List<Recipes> recipes){
        mContext = context;
        mRecipes = recipes;
    }

    @Override
    public RecipeListAdapter.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false);
        RecipeViewHolder viewHolder = new RecipeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListAdapter.RecipeViewHolder holder, int position) {
        holder.bindRecipe(mRecipes.get(position));
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        @BindView(R.id.recipeImageView) ImageView mRecipeImageView;
        @BindView(R.id.recipeCategoryTextView) TextView mRecipeCategoryTextView;
        @BindView(R.id.recipeAreaTextView) TextView mRecipeAreaTextView;
        @BindView(R.id.recipeNameTextView) TextView mRecipeNameTextView;


        private Context mContext;

        public RecipeViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindRecipe(Recipes recipe) {

            Picasso.get().load(recipe.getStrMealThumb()).into(mRecipeImageView);
            mRecipeNameTextView.setText(recipe.getStrMeal());
            mRecipeCategoryTextView.setText(recipe.getStrCategory());
            mRecipeAreaTextView.setText(recipe.getStrArea());
//            String video = recipe.getStrYoutube();
//            WebView videoWeb;
//            videoWeb = (WebView) itemView.findViewById(R.id.recipeVideoView);
//            videoWeb.getSettings().setJavaScriptEnabled(true);
//            videoWeb.setWebChromeClient(new WebChromeClient(){});
//
//            Vector<You>

        }


        @Override
        public void onClick(View view) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, RecipeDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("recipe", Parcels.wrap(mRecipes));
            mContext.startActivity(intent);
        }
    }
}
