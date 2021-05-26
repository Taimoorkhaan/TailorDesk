package com.digiconvalley.tailordesk.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.digiconvalley.tailordesk.R;

public class AddAmount_Activity extends AppCompatActivity {

    private EditText amount;
    private Button addBtn;
    private int bill,remainingAmount;
    private String deliveryDate,discountPrice;
    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_amount_);

        addBtn=findViewById(R.id.addbtn);
        amount=findViewById(R.id.add_amount);
        backArrow=findViewById(R.id.addAmountBack);


        if(getIntent()!=null && getIntent().hasExtra("Bill")){
            bill=getIntent().getIntExtra("Bill",0);
            remainingAmount=getIntent().getIntExtra("RemainingAmount",0);
            deliveryDate=getIntent().getStringExtra("DeliveryDate");
            discountPrice=getIntent().getStringExtra("DiscountPrice");

        }

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddAmount_Activity.this,CheckOutActivity.class);
                intent.putExtra("Amount","0");
                if(deliveryDate!=null)
                    intent.putExtra("DeliveryDate",deliveryDate.trim());
                if(discountPrice!=null)
                    intent.putExtra("DiscountPrice",discountPrice.trim());
                startActivity(intent);
                finish();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(amount.getText().toString().isEmpty()){
                    Toast.makeText(AddAmount_Activity.this, "Kindly Enter Received Amount", Toast.LENGTH_SHORT).show();
                   return;
                }

               /* if(Integer.parseInt(amount.getText().toString())>remainingAmount){
                    Toast.makeText(AddAmount_Activity.this, "Kindly Enter Equal or Less then Total Bill", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                Intent intent=new Intent(AddAmount_Activity.this,CheckOutActivity.class);
                intent.putExtra("Amount",amount.getText().toString());
                if(deliveryDate!=null)
                    intent.putExtra("DeliveryDate",deliveryDate.trim());
                if(discountPrice!=null)
                    intent.putExtra("DiscountPrice",discountPrice.trim());
                startActivity(intent);
                finish();

            }
        });
    }
}