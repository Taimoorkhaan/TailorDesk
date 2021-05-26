package com.digiconvalley.tailordesk.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.digiconvalley.tailordesk.Activities.SignUp_Activity;
import com.digiconvalley.tailordesk.R;


public class SliderAdapter extends PagerAdapter {

    Context context;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_screen,container,false);

        ImageView logo = view.findViewById(R.id.logo);

        ImageView ind1 = view.findViewById(R.id.ind1);
        ImageView ind2 = view.findViewById(R.id.ind2);
        ImageView ind3 = view.findViewById(R.id.ind3);

        TextView title = view.findViewById(R.id.title);
        TextView decs = view.findViewById(R.id.decs);

        ImageView nexticon = view.findViewById(R.id.nexticon);
        nexticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SignUp_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        switch (position)
        {
            case 0:
                logo.setImageResource(R.drawable.ic_mask_group_3);
                ind1.setImageResource(R.drawable.selected);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.unselected);

                title.setText("Welcome to tailor desk");
                decs.setText("Tailor Desk is a free mobile app for tailoring shops and boutiques to manage orders, measurements, galleries payments and much more.");
                nexticon.setImageResource(R.drawable.ic_group_2);
                nexticon.setVisibility(View.GONE);
                break;

            case 1:
                logo.setImageResource(R.drawable.ic_mask_group_4);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.selected);
                ind3.setImageResource(R.drawable.unselected);

                title.setText("making life easier");
                decs.setText("Tailor Desk app is designed to help organized your jobs electronically thus to optimize performance.");
                nexticon.setImageResource(R.drawable.ic_group_2);
                nexticon.setVisibility(View.GONE);
                break;
            case 2:
                logo.setImageResource(R.drawable.ic_mask_group_5);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.selected);

                title.setText("making your customer happy");
                decs.setText("By going digital, you are more connected to your customers, more happy customers bring you more business.");
                nexticon.setImageResource(R.drawable.ic_group_2);
                nexticon.setVisibility(View.VISIBLE);
                break;
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
