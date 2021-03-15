package com.example.capstonewallet.Views.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Fragments.ContactFragment;
import com.example.capstonewallet.databinding.TransactionListItemBinding;

import java.util.ArrayList;

public class StockNewsAdapter extends RecyclerView.Adapter<StockNewsAdapter.StockNewsViewHolder>{
    private Context context;
    private ArrayList<String> newsText = new ArrayList<>();
    private ArrayList<String> newsUrl = new ArrayList<>();
    private TransactionListItemBinding binding;
    private static final String TAG = "StockNewsAdapter";
    RecyclerView.RecycledViewPool viewPool;


    public StockNewsAdapter(Context context, ArrayList<String> newsText, ArrayList<String> newsUrl) {
        this.context = context;
        this.newsText = newsText;
        this.newsUrl = newsUrl;
        //viewPool = new RecyclerView.RecycledViewPool();
    }

    @NonNull
    @Override
    public StockNewsAdapter.StockNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        StockNewsAdapter.StockNewsViewHolder viewHolder = new StockNewsAdapter.StockNewsViewHolder(view);
        //RecyclerView recyclerView = (RecyclerView) parent.findViewById(R.id.news_recycler);
        //viewHolder.relativeLayout.setRecycledViewPool(viewPool);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StockNewsAdapter.StockNewsViewHolder holder, int position) {
        holder.newsText.setText(this.newsText.get(position));
        holder.url = this.newsUrl.get(position);

        //holder.newsImage.setImageBitmap(this.newsImage.get(position));
        holder.newsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newsLinkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(holder.url));
                context.startActivity(newsLinkIntent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return newsText.size();
    }

    public class StockNewsViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout relativeLayout;
        private TextView newsText;
        private String url;

        public StockNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            //itemView.setTag(TAG);
            relativeLayout = itemView.findViewById(R.id.news_layout);
            newsText = itemView.findViewById(R.id.news_text);
        }
    }
}
