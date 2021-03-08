package com.example.capstonewallet.Views.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonewallet.Models.Clients.TransactionClient;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Fragments.ContactFragment;
import com.example.capstonewallet.databinding.TransactionListItemBinding;

import java.util.ArrayList;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.TransactionListViewHolder>{
    private Context context;
    private ArrayList<String[]> transactionText;
    ArrayList<TransactionClient.TransactionData> transactionData;
    private TransactionListItemBinding binding;
    private static final String TAG = "TransactionListAdapter";
    private FragmentManager fragmentManager;


    public TransactionListAdapter(Context context, ArrayList<String[]> transactionText) {
        this.context = context;
        this.transactionText = transactionText;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
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
        Log.d("yo123", transactionText.get(position)[0]);
        holder.transactionDate.setText(transactionText.get(position)[0]);
        holder.transactionType.setText(transactionText.get(position)[1]);
        holder.transactionAMT.setText(transactionText.get(position)[2]);
        holder.icon.setColorFilter(R.color.navy);
        if((position % 2) == 0) {
            holder.icon.setColorFilter(R.color.navy);
        }
        else {
            holder.icon.setColorFilter(R.color.bt_text_blue);
        }

        if(position == 0) {
            holder.icon.setVisibility(View.INVISIBLE);
        }
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //holder.transactionText.setText("clicked");
                //TODO create fragment class to display txn data and add back arrow to pop fragment
                /*fragment = new ContactFragment(holder.getName(), "0x2323213");
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.popupContainer, fragment, null);
                fragmentTransaction.addToBackStack("ContactPopup");
                fragmentTransaction.commit();*/

            }
        });
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
        private ImageView icon;

        public TransactionListViewHolder(@NonNull View itemView) {
            super(itemView);
            //itemView.setTag(TAG);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_layout);
            transactionDate = (TextView) itemView.findViewById(R.id.transactionTextDate);
            transactionType = (TextView) itemView.findViewById(R.id.transactionTextType);
            transactionAMT = itemView.findViewById(R.id.transactionTextAmount);
            icon = itemView.findViewById(R.id.transactionIcon);
        }
    }
}
