package com.techspaceke.cookit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.techspaceke.cookit.R;
import com.techspaceke.cookit.models.Categories;
import com.techspaceke.cookit.models.Recipes;

import java.util.ArrayList;
import java.util.List;

public class PopularRecipeListAdapter extends BaseAdapter {
    String TAG = PopularRecipeListAdapter.class.getSimpleName();
    private List<Categories> mCategories = new ArrayList<>();
    private Context mContext;

    public PopularRecipeListAdapter(Context context, List<Categories> categories) {
        mContext = context;
        mCategories = categories;
    }

    @Override
    public int getCount() {
        return mCategories.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        final Categories categories = mCategories.get(position);
        if (view == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            view = layoutInflater.inflate(R.layout.category_item, null);
            final ImageView imageView = view.findViewById(R.id.category_thumb);
            final TextView title = view.findViewById(R.id.category_name);
            final ViewHolder viewHolder = new ViewHolder(title, imageView);
            view.setTag(viewHolder);
        }
        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        ImageView mCategoryThumb = view.findViewById(R.id.category_thumb);
        Picasso.get().load(categories.getStrCategoryThumb()).into(viewHolder.imageViewThumb);
        viewHolder.nameTextView.setText(categories.getStrCategory());

        return view;
    }

    private class ViewHolder implements View.OnClickListener {
        private final TextView nameTextView;
        private final ImageView imageViewThumb;

        public ViewHolder(TextView nameTextView,ImageView imageViewCoverArt) {
            this.nameTextView = nameTextView;
            this.imageViewThumb = imageViewCoverArt;
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(mContext, "Error fetching CAtegories", Toast.LENGTH_LONG).show();

        }
    }
}
