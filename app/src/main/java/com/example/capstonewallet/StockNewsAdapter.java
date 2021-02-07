package com.example.capstonewallet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonewallet.databinding.TransactionListItemBinding;

import java.net.URL;
import java.util.ArrayList;

public class StockNewsAdapter extends RecyclerView.Adapter<StockNewsAdapter.StockNewsViewHolder>{
    private Context context;
    private ArrayList<String> newsText = new ArrayList<>();
    private ArrayList<Bitmap> newsImage = new ArrayList<>();
    private TransactionListItemBinding binding;
    private static final String TAG = "StockNewsAdapter";
    RecyclerView.RecycledViewPool viewPool;


    public StockNewsAdapter(Context context, ArrayList<String> newsText, ArrayList<Bitmap> newsImage) {
        this.context = context;
        this.newsText = newsText;
        this.newsImage = newsImage;
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

        holder.newsImage.setImageBitmap(this.newsImage.get(position));
    }

    @Override
    public int getItemCount() {

        return newsText.size();
    }

    public class StockNewsViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout relativeLayout;
        private TextView newsText;
        private ImageView newsImage;

        public StockNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            //itemView.setTag(TAG);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.news_layout);
            newsText = (TextView) itemView.findViewById(R.id.news_text);
            newsImage = (ImageView) itemView.findViewById(R.id.news_image);
        }
    }
}
