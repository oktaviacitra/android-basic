package com.example.contacts;

import android.content.Context;
import android.view.*;
import android.widget.*;
import java.util.*;

public class ContactAdapter extends ArrayAdapter<Contact> {
    public ContactAdapter(Context context, List<Contact> contacts) {
        super(context, 0, contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_contact, parent, false);
        }
        TextView name = convertView.findViewById(R.id.contact_name);
        TextView phone = convertView.findViewById(R.id.contact_phone);
        name.setText(contact.getName());
        phone.setText(contact.getPhoneNumber());
        return convertView;
    }
}
