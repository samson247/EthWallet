package com.example.capstonewallet.Views.Adapters;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.example.capstonewallet.Database.ContactEntity;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Fragments.ContactFragment;
import java.util.ArrayList;

/**
 * Adapter class for contacts recycler view
 *
 * @author Sam Dodson
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {
    private Context context;
    private ArrayList<ContactEntity> contacts;
    private static final String TAG = "TransactionListAdapter";
    private FragmentManager fragmentManager;
    private ContactFragment fragment;

    /**
     * Setter for fragment manager to control on click fragments for each contact item
     * @param fragmentManager the fragment manager for this view
     */
    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    /**
     * Constructor for this class
     * @param context the context of the class
     * @param contacts the list of contacts to initialize recycler view with
     */
    public ContactsAdapter(Context context, ArrayList<ContactEntity> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    /**
     * Initializes view holder of recycler view
     * @param parent the parent view group
     * @param viewType the recycler view's view type
     * @return view holder for contacts adapter
     */
    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        ContactsViewHolder viewHolder = new ContactsViewHolder(view);
        return viewHolder;
    }

    /**
     * Method called on each change to recycler view to initialize each element
     * @param holder the view holder
     * @param position the position of the recycler view to update
     */
    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        holder.contactName.setText(contacts.get(position).getName());
        holder.address = contacts.get(position).getAddress();
        holder.getLayout().setBackground(null);
        holder.getLayout().setPadding(0,0,0,0);

        // Initializes the letter elements for the address book that have no address
        // Sets background and layout for each element
        if(holder.getAddress().equals("")) {
            holder.getLayout().setBackgroundColor(context.getColor(R.color.navy));
            holder.getLayout().setPadding(20,10,0,0);
            //TODO further fix height changes
            //ViewGroup.LayoutParams params = holder.getLayout().getLayoutParams();
            //int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
            //params.height = height;
            //params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            //holder.getLayout().setLayoutParams(params);
            holder.contactName.setTextColor(context.getColor(R.color.grey));
        }
        else {
            // Initializes all contact elements an sets background
            holder.getLayout().setBackgroundColor(context.getColor(R.color.grey));
            holder.contactName.setTextColor(context.getColor(R.color.navy));
        }

        // When contact is clicked the popup with info is displayed
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // New popup fragment is created
                if (fragment == null) {
                    if(!holder.getAddress().equals("")) {
                        fragment = new ContactFragment(holder.getName(), holder.getAddress());
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.slide_up, R.anim.fade_out);
                        fragmentTransaction.add(R.id.popupContainer, fragment, null);
                        fragmentTransaction.addToBackStack("ContactPopup");
                        fragmentTransaction.commit();
                    }
                }
                else if(!fragment.isVisible() && !holder.getAddress().equals("")) {
                    // Popup already exists but isn't currently visible
                    Log.d("yo123", "is in layout");
                    fragment = new ContactFragment(holder.getName(), holder.getAddress());
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.slide_up, R.anim.fade_out);
                    fragmentTransaction.add(R.id.popupContainer, fragment, null);
                    fragmentTransaction.addToBackStack("ContactPopup");
                    fragmentTransaction.commit();
                }
                else if(!holder.getAddress().equals("")){
                    // Switch contact when popup already exists
                    Log.d("yo123", "is in switch name");
                    fragment.setName(holder.getName());
                    fragment.setAddress(holder.getAddress());
                }
            }
        });
    }

    /**
     * Returns number of contacts in recycler view
     * @return the number of contacts
     */
    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public ContactFragment getFragment() {
        return this.fragment;
    }

    /**
     * View holder class for contacts
     *
     * @author Sam Dodson
     */
    public class ContactsViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private TextView contactName;
        private String address;

        /**
         * Constructor for this class
         * @param itemView the view context for this class
         */
        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.contactLayout);
            contactName = itemView.findViewById(R.id.contactName);
        }

        /**
         * Getter for name of contact
         * @return name of contact
         */
        public String getName() {
            return this.contactName.getText().toString();
        }

        /**
         * Getter for address of contact
         * @return address of contact
         */
        public String getAddress() {
            return this.address;
        }

        /**
         * Getter for layout of contact item
         * @return layout of contact item
         */
        public LinearLayout getLayout() {
            return this.linearLayout;
        }
    }
}

