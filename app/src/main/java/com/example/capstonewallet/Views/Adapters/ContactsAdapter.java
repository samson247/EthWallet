package com.example.capstonewallet.Views.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonewallet.AddContactFragment;
import com.example.capstonewallet.Database.ContactEntity;
import com.example.capstonewallet.Models.Clients.TransactionClient;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Fragments.AddressBookFragment;
import com.example.capstonewallet.Views.Fragments.ContactFragment;
import com.example.capstonewallet.databinding.TransactionListItemBinding;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> implements SearchView.OnQueryTextListener {
    private Context context;
    private ArrayList<String> names;
    private ArrayList<String> addresses;
    private ArrayList<ContactEntity> contacts;
    //private ArrayList<ContactsData> transactionData;
    private TransactionListItemBinding binding;
    private static final String TAG = "TransactionListAdapter";
    private FragmentManager fragmentManager;
    private ContactFragment fragment;


    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public ContactsAdapter(Context context, ArrayList<ContactEntity> contacts) {
        this.context = context;
        this.contacts = contacts;
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
        //Log.d("yo123", names.get(position) + " " + addresses.get(position));
        holder.contactName.setText(contacts.get(position).getName());
        holder.address = contacts.get(position).getAddress();
        Log.d("yo123", position + " " + holder.contactName.getText().toString() + " " + holder.address);
        if(holder.getAddress().equals("")) {
            holder.getLayout().setBackgroundColor(context.getColor(R.color.navy));
            holder.getLayout().setPadding(20,0,0,0);
            ViewGroup.LayoutParams params = holder.getLayout().getLayoutParams();
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
            params.height = height;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.getLayout().setLayoutParams(params);
            //holder.getLayout().setBackground(null);
            holder.contactName.setTextColor(context.getColor(R.color.grey));
        }
        else {
            holder.getLayout().setBackground(context.getDrawable(R.drawable.border_5));
            //holder.contactName.setText(contacts.get(position).getName());
            holder.contactName.setTextColor(context.getColor(R.color.navy));
        }
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //holder.contactName.setText("clicked");
                Log.d("yo123", "fragment: " + fragment);
                if (fragment == null) {
                    if(!holder.getAddress().equals("")) {
                        fragment = new ContactFragment(holder.getName(), holder.getAddress());
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(R.id.popupContainer, fragment, null);
                        fragmentTransaction.addToBackStack("ContactPopup");
                        fragmentTransaction.commit();
                    }
                }
                else if(!fragment.isVisible() && holder.getAddress() != "") {
                    Log.d("yo123", "is in layout");
                    fragment = new ContactFragment(holder.getName(), holder.getAddress());
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.popupContainer, fragment, null);
                    fragmentTransaction.addToBackStack("ContactPopup");
                    fragmentTransaction.commit();
                }
                else if(holder.getAddress() != ""){
                    Log.d("yo123", "is in switch name");
                    fragment.setName(holder.getName());
                    fragment.setAddress(holder.getAddress());
                }
                //fragment.setName(holder.contactName.toString());
                //fragment.setAddress("0x3483498394323");
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        filterSearch(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filterSearch(newText);
        return false;
    }

    public void filterSearch(String text) {
        Log.d("yo123", text);
        ArrayList<String> namesOriginal = names;
        //names.clear();
        if(text.isEmpty()){
            //names.addAll(namesOriginal);
        } else{
            text = text.toLowerCase();
            int index = 0;
            while(index < names.size()){
                if(!names.get(index).toLowerCase().contains(text)){
                    names.remove(index);
                    Log.d("yo123", "adding filtered name " + names.get(index));
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private TextView contactName;
        private String address;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            //itemView.setTag(TAG);
            linearLayout = itemView.findViewById(R.id.contactLayout);
            contactName = itemView.findViewById(R.id.contactName);
        }

        public String getName() {
            return this.contactName.getText().toString();
        }

        public String getAddress() {
            return this.address;
        }

        public LinearLayout getLayout() {
            return this.linearLayout;
        }
    }
}

