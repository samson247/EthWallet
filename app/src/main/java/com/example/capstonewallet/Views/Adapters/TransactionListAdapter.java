package com.example.capstonewallet.Views.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonewallet.AddContactFragment;
import com.example.capstonewallet.Models.Clients.TransactionClient;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Fragments.ContactFragment;
import com.example.capstonewallet.Views.Fragments.TransactionInfoFragment;
import com.example.capstonewallet.databinding.TransactionListItemBinding;

import java.util.ArrayList;

import jnr.ffi.annotations.In;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.TransactionListViewHolder> {
    private Context context;
    private ArrayList<String[]> transactionText;
    ArrayList<TransactionClient.TransactionData> transactionData;
    private TransactionListItemBinding binding;
    private static final String TAG = "TransactionListAdapter";
    private TransactionInfoFragment transactionInfoFragment;
    private FragmentManager fragmentManager;


    public TransactionListAdapter(Context context, ArrayList<String[]> transactionText) {
        this.context = context;
        this.transactionText = transactionText;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void setTransactionData(ArrayList<TransactionClient.TransactionData> transactionData) {
        this.transactionData = transactionData;
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

        // icon send or receive + timeStamp to date + received or sent + amount
        holder.transactionDate.setText(transactionText.get(position)[0]);
        holder.transactionType.setText(transactionText.get(position)[1]);
        holder.transactionAMT.setText(transactionText.get(position)[2]);
        holder.iconSent.setVisibility(View.VISIBLE);
        holder.iconReceived.setVisibility(View.INVISIBLE);
        //holder.icon.setColorFilter(R.color.navy);
        if(holder.transactionType.getText().toString().contains("received")) {
            //Drawable drawable = context.getResources().getDrawable(R.drawable.received, null);
            //holder.icon.setImageDrawable(drawable);
            holder.iconSent.setVisibility(View.INVISIBLE);
            holder.iconReceived.setVisibility(View.VISIBLE);
        }
        if(position == 0) {
            holder.iconSent.setVisibility(View.INVISIBLE);
            holder.iconReceived.setVisibility(View.INVISIBLE);
            holder.relativeLayout.setClickable(false);
        }
        else {
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TransactionInfoFragment fragment = new TransactionInfoFragment(transactionData.get(position));
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.transactionPopup, fragment, null);
                    fragmentTransaction.addToBackStack("TransactionInfo");
                    fragmentTransaction.commit();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return transactionText.size();
    }

    public class TransactionListViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout relativeLayout;
        private TextView transactionDate;
        private TextView transactionType;
        private TextView transactionAMT;
        private ImageView iconSent;
        private ImageView iconReceived;

        public TransactionListViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_layout);
            transactionDate = (TextView) itemView.findViewById(R.id.transactionTextDate);
            transactionType = (TextView) itemView.findViewById(R.id.transactionTextType);
            transactionAMT = itemView.findViewById(R.id.transactionTextAmount);
            iconSent = itemView.findViewById(R.id.iconSent);
            iconReceived = itemView.findViewById(R.id.iconReceived);
        }
    }
}
