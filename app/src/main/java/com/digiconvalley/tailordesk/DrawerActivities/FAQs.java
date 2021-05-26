package com.digiconvalley.tailordesk.DrawerActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.digiconvalley.tailordesk.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FAQs extends AppCompatActivity {
    private ImageView backBtn;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_qs);


        expandableListView = findViewById(R.id.expandable_View);
        Map<String, List<String>> item = new LinkedHashMap<>();

        backBtn=findViewById(R.id.back_button_faq);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ArrayList<String> one = new ArrayList<>();
        one.add("Privacy of our customer is our #1 priority. We never share your information with anyone else. It is securely stored on our servers and never be shared with anyone.");



        item.put("1. What is TailorDesk?",one);

        ArrayList<String> two = new ArrayList<>();
        two.add("Privacy of our customer is our #1 priority. We never share your information with anyone else. It is securely stored on our servers and never be shared with anyone.");


        item.put("2. Is TailorDesk free for use?",two);

        ArrayList<String> three = new ArrayList<>();
        three.add("Privacy of our customer is our #1 priority. We never share your information with anyone else. It is securely stored on our servers and never be shared with anyone.");


        item.put("3. Is my data secure?",three);

        ArrayList<String> four = new ArrayList<>();
        four.add("Privacy of our customer is our #1 priority. We never share your information with anyone else. It is securely stored on our servers and never be shared with anyone.");


        item.put("4. If I lose my phone, will I loose all my data?",four);

        ArrayList<String> five = new ArrayList<>();
        five.add("Privacy of our customer is our #1 priority. We never share your information with anyone else. It is securely stored on our servers and never be shared with anyone.");


        item.put("5. Do you share data with someone?",five);

        expandableListAdapter = new com.digiconvalley.tailordesk.Adapter.ExpandableListAdapter(item);
        expandableListView.setAdapter(expandableListAdapter);

    }
}