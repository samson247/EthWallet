package com.example.capstonewallet.Views.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.capstonewallet.R;
import com.example.capstonewallet.databinding.TransactionListItemBinding;
import java.util.ArrayList;

/**
 * Adapter class for news article recycler view
 *
 * @author Sam Dodson
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{
    private Context context;
    private ArrayList<String> newsText = new ArrayList<>();
    private ArrayList<String> newsUrl = new ArrayList<>();
    private TransactionListItemBinding binding;
    private static final String TAG = "StockNewsAdapter";

    /**
     * Constructor for this class
     * @param context the context of this class's view
     * @param newsText titles of news articles
     * @param newsUrl urls of news articles
     */
    public NewsAdapter(Context context, ArrayList<String> newsText, ArrayList<String> newsUrl) {
        this.context = context;
        this.newsText = newsText;
        this.newsUrl = newsUrl;
    }

    /**
     * Called when recycler view is created to inflate view
     * @param parent the parent view group
     * @param viewType the view type for recycler view
     * @return the view holder for this recycler view
     */
    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        NewsAdapter.NewsViewHolder viewHolder = new NewsAdapter.NewsViewHolder(view);
        return viewHolder;
    }

    /**
     * Called for each element in recycler to initialize data
     * @param holder the view holder for the recycler view
     * @param position the position of an element in the list
     */
    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {
        holder.newsText.setText(this.newsText.get(position));
        holder.url = this.newsUrl.get(position);
        holder.setIsRecyclable(false);

        // Opens URL when article is clicked
        holder.newsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newsLinkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(holder.url));
                context.startActivity(newsLinkIntent);
            }
        });
    }

    /**
     * Getter for number of elements in recycler view
     * @return the number of elements in recycler view
     */
    @Override
    public int getItemCount() {

        return newsText.size();
    }

    /**
     * View holder class for the news recycler
     *
     * @author Sam Dodson
     */
    public class NewsViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout relativeLayout;
        private TextView newsText;
        private String url;

        /**
         * Constructor for this class
         * @param itemView the view for an individual item in the recycler
         */
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.news_layout);
            newsText = itemView.findViewById(R.id.news_text);
        }
    }
}
