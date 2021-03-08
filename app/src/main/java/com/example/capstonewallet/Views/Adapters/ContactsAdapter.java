package com.example.capstonewallet.Views.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonewallet.AddContactFragment;
import com.example.capstonewallet.Models.Clients.TransactionClient;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Fragments.AddressBookFragment;
import com.example.capstonewallet.Views.Fragments.ContactFragment;
import com.example.capstonewallet.databinding.TransactionListItemBinding;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {
    private Context context;
    private ArrayList<String> names;
    //private ArrayList<ContactsData> transactionData;
    private TransactionListItemBinding binding;
    private static final String TAG = "TransactionListAdapter";
    private FragmentManager fragmentManager;
    private ContactFragment fragment;


    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public ContactsAdapter(Context context, ArrayList<String> names) {
        this.context = context;
        this.names = names;
        //this.fragment = fragment;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        ContactsViewHolder viewHolder = new ContactsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {

        // icon send or receive + timeStamp to date + received or sent + amount
        //holder.contactName.setText(transactionText.get(position));
        holder.contactName.setText(names.get(position));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //holder.contactName.setText("clicked");
                Log.d("yo123", "fragment: " + fragment);
                if (fragment == null) {
                    fragment = new ContactFragment(holder.getName(), "0x2323213");
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.popupContainer, fragment, null);
                    fragmentTransaction.addToBackStack("ContactPopup");
                    fragmentTransaction.commit();
                }
                else if(!fragment.isVisible()) {
                    Log.d("yo123", "is in layout");
                    fragment = new ContactFragment(holder.getName(), "0x2323213");
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.popupContainer, fragment, null);
                    fragmentTransaction.addToBackStack("ContactPopup");
                    fragmentTransaction.commit();
                }
                else {
                    Log.d("yo123", "is in switch name");
                    fragment.setName(holder.getName());
                }
                //fragment.setName(holder.contactName.toString());
                //fragment.setAddress("0x3483498394323");
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private TextView contactName;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            //itemView.setTag(TAG);
            linearLayout = itemView.findViewById(R.id.contactLayout);
            contactName = itemView.findViewById(R.id.contactName);
        }

        public String getName() {
            return this.contactName.getText().toString();
        }
    }
}

