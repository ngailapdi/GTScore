package com.example.ngailapdi.gtscore;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ContactFragment extends Fragment {
    @Nullable
    private ArrayList<String> array = new ArrayList<String>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View display = inflater.inflate(R.layout.fragment_contact,container,false);
        ListView list = (ListView) display.findViewById(R.id.list_contact);
        createList();
        list.setAdapter(new MyListAdapter(this.getActivity(),R.layout.list_invite,array));
        return display;

    }

    private void createList()
    {
        for(int i =0; i < 10; i++)
        {
            array.add("row"+i);
        }
    }
}

    class MyListAdapter extends ArrayAdapter<String>
    {
        int layout;
        protected MyListAdapter(Context context, int resources, List<String> objects)
        {
            super(context, resources, objects);
            layout = resources;
        }

        @Override
        public View getView (final int position, View convertView, ViewGroup parent)
        {
            ViewHolder main = null;
            if (convertView == null)
            {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout,parent,false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.txt = (TextView) convertView.findViewById(R.id.txt);
                viewHolder.button = (Button) convertView.findViewById(R.id.bt);
                viewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),"Is clicked" +position, Toast.LENGTH_SHORT ).show();
                    }
                });
                convertView.setTag(viewHolder);
            }
            else{
                main = (ViewHolder) convertView.getTag();
                main.txt.setText(getItem(position));

            }
            return convertView;



    }

    public class ViewHolder{
        TextView txt;
        Button button;

    }

}
