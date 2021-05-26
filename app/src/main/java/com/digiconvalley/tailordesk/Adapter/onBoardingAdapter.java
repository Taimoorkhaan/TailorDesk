package com.digiconvalley.tailordesk.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digiconvalley.tailordesk.Model.OnboardingItem;
import com.digiconvalley.tailordesk.R;

import java.util.List;

public class onBoardingAdapter extends RecyclerView.Adapter<onBoardingAdapter.onboardingViewHolder> {
    List<OnboardingItem> onboardingItems;

    public onBoardingAdapter(List<OnboardingItem> onboardingItems) {
        this.onboardingItems = onboardingItems;
    }

    @NonNull
    @Override
    public onboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new onboardingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_onboarding, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull onboardingViewHolder holder, int position) {
        holder.setOnboardingData(onboardingItems.get(position));
    }

    @Override
    public int getItemCount() {
        return onboardingItems.size();
    }

    class onboardingViewHolder extends RecyclerView.ViewHolder {
        private TextView textHeading;
        private TextView textDescription;
        private ImageView imageOnboarding;

        public onboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            textHeading = itemView.findViewById(R.id.Heading2);
            textDescription = itemView.findViewById(R.id.descp2);
            imageOnboarding = itemView.findViewById(R.id.img2);
        }

        void setOnboardingData(OnboardingItem onboardingItem) {
            textHeading.setText(onboardingItem.getHeading());
            textDescription.setText(onboardingItem.getDescription());
            imageOnboarding.setImageResource(onboardingItem.getImage());
        }

    }
}
