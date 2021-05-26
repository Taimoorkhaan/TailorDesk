package com.digiconvalley.tailordesk.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.digiconvalley.tailordesk.Activities.SignUp_Activity;
import com.digiconvalley.tailordesk.Adapter.onBoardingAdapter;
import com.digiconvalley.tailordesk.Model.OnboardingItem;
import com.digiconvalley.tailordesk.R;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingScreen extends AppCompatActivity {

    private onBoardingAdapter boardingAdapter;
    private LinearLayout linearLayoutIndicators;
    private ImageView btnImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_screen);

        setUpOnboardingItems();
        linearLayoutIndicators = findViewById(R.id.layoutIndicator);
        btnImage = findViewById(R.id.btnNext);
        final ViewPager2 onBoardingViewPager = findViewById(R.id.onBoardingViewPager);
        onBoardingViewPager.setAdapter(boardingAdapter);
        setUpIndicators();
        setCurrentIndicator(0);

        onBoardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onBoardingViewPager.getCurrentItem() + 1 < boardingAdapter.getItemCount()) {
                    onBoardingViewPager.setCurrentItem(onBoardingViewPager.getCurrentItem() + 1);
                } else {
            Intent intent = new Intent(getApplicationContext(), SignUp_Activity.class);
            startActivity(intent);
            finish();
                }
            }
        });
    }

    private void setUpOnboardingItems() {
        List<OnboardingItem> onboardingItemList = new ArrayList<>();

        OnboardingItem screenOne = new OnboardingItem();
        screenOne.setHeading("Welcome to tailor desk");
        screenOne.setDescription("Tailor Desk is a free mobile app for tailoring shops and boutiques to manage orders, measurements, galleries payments and much more.");
        screenOne.setImage(R.drawable.ic_mask_group_3);

        OnboardingItem screenTwo = new OnboardingItem();
        screenTwo.setHeading("making life easier");
        screenTwo.setDescription("Tailor Desk app is designed to help organized your jobs electronically thus to optimize performance.");
        screenTwo.setImage(R.drawable.ic_mask_group_4);

        OnboardingItem screenThree = new OnboardingItem();
        screenThree.setHeading("making your customer happy");
        screenThree.setDescription("By going digital, you are more connected to your customers, more happy customers bring you more business.");
        screenThree.setImage(R.drawable.ic_mask_group_5);

        onboardingItemList.add(screenOne);
        onboardingItemList.add(screenTwo);
        onboardingItemList.add(screenThree);

        boardingAdapter = new onBoardingAdapter(onboardingItemList);
    }

    private void setUpIndicators() {
        ImageView[] indicators = new ImageView[boardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.unselected));
            indicators[i].setLayoutParams(layoutParams);
            linearLayoutIndicators.addView(indicators[i]);
        }
    }

    private void setCurrentIndicator(int index) {
        int ChildCount = linearLayoutIndicators.getChildCount();
        for (int i = 0; i < ChildCount; i++) {
            ImageView imageView = (ImageView) linearLayoutIndicators.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.unselected));
            }

        }
        if (index == boardingAdapter.getItemCount() - 1) {
            btnImage.setVisibility(View.VISIBLE);
        } else {
            btnImage.setVisibility(View.GONE);
        }

    }
}