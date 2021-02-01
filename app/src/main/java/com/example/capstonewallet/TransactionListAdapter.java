package com.example.capstonewallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonewallet.databinding.TransactionListItemBinding;

import java.util.ArrayList;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.TransactionListViewHolder>{
    private Context context;
    private ArrayList<String> transactionText = new ArrayList<>();
    private TransactionListItemBinding binding;
    private static final String TAG = "TransactionListAdapter";


    public TransactionListAdapter(Context context, ArrayList<String> transactionText) {
        this.context = context;
        this.transactionText = transactionText;
    }

    @NonNull
    @Override
    public TransactionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_list_item, parent, false);
        TransactionListViewHolder viewHolder = new TransactionListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionListViewHolder holder, int position) {
        holder.transactionText.setText(transactionText.get(position));
    }

    @Override
    public int getItemCount() {
        return transactionText.size();
    }

    public class TransactionListViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout relativeLayout;
        private TextView transactionText;

        public TransactionListViewHolder(@NonNull View itemView) {
            super(itemView);
            //itemView.setTag(TAG);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_layout);
            transactionText = (TextView) itemView.findViewById(R.id.transaction_text);
        }
    }
}
