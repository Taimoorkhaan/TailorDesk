/*
package com.digiconvalley.tailordesk.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.digiconvalley.tailordesk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import me.philio.pinentry.PinEntryView;

import static android.widget.Toast.LENGTH_SHORT;

public class Verify_Pin_Activity2 extends AppCompatActivity {

    private ImageView backArrow;
    private TextView resend;
    private TextView modifyNumber,resisterNoText;

    private Button verify;
    private String verifyCodeBySystem,token;
    private String userPhoneNumber;
    private ProgressDialog progressDialog;
    private JSONObject jsonObject,lawyerInfo;
    private Boolean userExist,mTimer;

    private CountDownTimer cTimer;
    private ProgressDialog progressDialog2;
    private TextView timer,timer1;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    private String userPhoneNo;
    private String countryCode;
    private SharedPreferences pref ;
    private SharedPreferences.Editor editor;
    private PhoneAuthProvider phoneAuthProvider;

    private EditText editText1, editText2, editText3, editText4, editText5, editText6;
    private EditText[] editTexts;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout6);



        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
         editor = pref.edit();

        if(getIntent()!=null){
            userPhoneNumber=getIntent().getStringExtra("User_PhoneNo");
            userPhoneNo=getIntent().getStringExtra("User_PhoneNo");
            countryCode=getIntent().getStringExtra("User_PhoneCountryCode");
            userPhoneNo=countryCode+userPhoneNo;
        }

        backArrow = findViewById(R.id.back_arrow);
        pinEntryView = findViewById(R.id.pin_entry_simple);
        resisterNoText=findViewById(R.id.registerNoText);
        resend=findViewById(R.id.resend);
        modifyNumber=findViewById(R.id.modify_number);

        timer=findViewById(R.id.timer);
        timer1=findViewById(R.id.timer1);

        progressDialog=new ProgressDialog(this);

        resisterNoText.setText("OTP SENT AT YOUR REGISTERED MOBILE \n NUMBER ( "+userPhoneNo+" )");


        startTimer();

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelTimer();
                startTimer();
                progressDialog2=new ProgressDialog(Verify_Pin_Activity.this);
                progressDialog2.setMessage("Sending Again OTP....");
                progressDialog2.show();

                pinEntryView.clearText();

                phoneauth();

            }
        });

        modifyNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Verify_Pin_Activity.this,Signup_Activity.class);
                intent.putExtra("PhoneNo",userPhoneNumber);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        pinEntryView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
  if(s.toString().length()==6) {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifyCodeBySystem, s.toString());
                    if(!mTimer)
                    signInTheUserCredential(credential);
                    else
                        Toast.makeText(Verify_Pin_Activity.this, "Time out Click on Resend", LENGTH_SHORT).show();
                }

            }
        });






        phoneAuthProvider.getInstance().verifyPhoneNumber(
                userPhoneNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,// Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);


        verify = findViewById(R.id.verify_btn);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pinEntryView.getText().toString().length()==6)
                    CheckUser();
                    // verifyCode(pinEntryView.getText().toString());
                else
                    Toast.makeText(Verify_Pin_Activity.this, "Please Enter Complete Code", LENGTH_SHORT).show();
            }
        });
    }

   public PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

       @Override
       public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
           String code =phoneAuthCredential.getSmsCode();
           if(code!=null){
               verifyCode(code);
           }
       }

       @Override
       public void onVerificationFailed(FirebaseException e) {
           Toast.makeText(Verify_Pin_Activity.this, e.getMessage(), LENGTH_SHORT).show();
       }

       @Override
       public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
           super.onCodeSent(s, forceResendingToken);

           verifyCodeBySystem = s;
           resendToken=forceResendingToken;
       }
   };


    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifyCodeBySystem,code);
      if(!mTimer)
        signInTheUserCredential(credential);
      else
          Toast.makeText(Verify_Pin_Activity.this, "Time out Click on Resend", LENGTH_SHORT).show();
    }

    private void signInTheUserCredential(PhoneAuthCredential credential) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithCredential(credential)
                .addOnCompleteListener(Verify_Pin_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            CheckUser();
   Intent intent =new Intent(getApplicationContext(),User_basic_info_Activity.class);
                            editor.putString("Loged","yes");
                            editor.commit();
                            editor.apply();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }else Toast.makeText(Verify_Pin_Activity.this, task.getException().getMessage(), LENGTH_SHORT).show();
                    }
                });

    }



    public void phoneauth(){
        phoneAuthProvider.getInstance().verifyPhoneNumber(
                userPhoneNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,resendToken);

        progressDialog2.dismiss();
    }
    public void CheckUser( ){

        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        String url;
        if (Build.VERSION.SDK_INT < 24) {

            //url = "https://olawakeel.conveyor.cloud/api/generalapi/LawyerExist?PhoneNo="+userPhoneNo;
            url = StaticData.baseLiveUrl+"/api/generalapi/LawyerExist?PhoneNo="+userPhoneNo.replace(" ","");
        }
        else{
            Toast.makeText(this, "Base Url"+StaticData.baseLiveUrl, Toast.LENGTH_LONG).show();
          url = StaticData.baseLiveUrl+"/api/generalapi/LawyerExist?PhoneNo="+userPhoneNo.replace(" ","");


        }

        StringRequest req= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Verify_Pin_Activity.this, ""+response.toString(), Toast.LENGTH_LONG).show();
                if(response!=null) {
                    try {
                         jsonObject=new JSONObject(response);
                       userExist = (Boolean) jsonObject.get("does_exist");
                       if(userExist){
                           lawyerInfo =jsonObject.getJSONObject("lawyer");
                           lawyerBasicInfo=new LawyerBasicInfo();
                           lawyerBasicInfo.setLawyerName(lawyerInfo.getString("lawyerName"));
                           lawyerBasicInfo.setLawyerId(lawyerInfo.getInt("LawyerId"));
                           lawyerBasicInfo.setOnlineStatus(lawyerInfo.getString("OnlineStatus"));
                           lawyerBasicInfo.setOnlineStatus(lawyerInfo.getString("LawyerPic"));
                       }
                     //   Toast.makeText(Verify_Pin_Activity.this, ""+userExist, LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    if(!userExist) {

                        Intent intent = new Intent(getApplicationContext(), User_basic_info_Activity.class);
                        intent.putExtra("UserPhoneNo",userPhoneNo);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                    else if(userExist){
                        Gson gson=new Gson();
                        String lawyerinfo = gson.toJson(lawyerBasicInfo);

                        editor.putString("Loged", "yes");
                        editor.putString("LawyerBasicInfo",lawyerinfo);
                        editor.commit();
                        editor.apply();

                        LawyerData(lawyerBasicInfo.getLawyerId());

                    }
              progressDialog.dismiss();
                }
                else{
                    Toast.makeText(Verify_Pin_Activity.this, "Server Error", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // progressBar.dismiss();
                Toast.makeText(Verify_Pin_Activity.this, "Size is "+error.toString(), Toast.LENGTH_SHORT).show();
             //   progressDialog.dismiss();
               progressDialog.setTitle(error.toString());
                String message = null;

            }
        });


        req.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingletonPattren.getmInstance(this).addToRequestque(req);

    }

    public void LawyerData(final int id ){

        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        String url;
        if (Build.VERSION.SDK_INT < 24) {
            url =StaticData.baseLiveUrl+"/LawyerPostApi/GetLawyerStatus?lawyerid="+id;
        }
        else{
               url = StaticData.baseLiveUrl+"/LawyerPostApi/GetLawyerStatus?lawyerid="+id;
        }

        StringRequest req= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response!=null) {

                    Gson gson=new Gson();

                    editor.putString("LawyerProfileData",response);
                    editor.commit();
                    editor.apply();

                    FirebaseInstanceId.getInstance().getInstanceId()
                            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    if(task.isSuccessful()){
                                        token = task.getResult().getToken();
                                        RefreshFCMToken(id,"Lawyer",token);
                                    }
                                }
                            });

                    progressDialog.dismiss();
                }
                else{
                    Toast.makeText(Verify_Pin_Activity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // progressBar.dismiss();
                progressDialog.dismiss();
                String message = null;



            }
        });


        req.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingletonPattren.getmInstance(this).addToRequestque(req);

    }
    void startTimer() {
        cTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                modifyNumber.setEnabled(false);
                mTimer = false;
                verify.setEnabled(true);
                timer.setTextColor(Color.parseColor("#414A51"));
                timer1.setTextColor(Color.parseColor("#414A51"));
                timer.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                verify.setEnabled(false);
                Toast.makeText(Verify_Pin_Activity.this, "Code Expired. Please Resend Code", LENGTH_SHORT).show();
                timer.setTextColor(Color.parseColor("#9CA1A5"));
                timer1.setTextColor(Color.parseColor("#9CA1A5"));
                timer.setText("00");
                mTimer = true;
                modifyNumber.setEnabled(true);
            }
        };
        cTimer.start();
    }

    void cancelTimer() {
        if (cTimer != null)
            cTimer.cancel();
    }
    public void RefreshFCMToken(int lawyerId, String userType, String fcmToken){

        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

          String url =StaticData.baseLiveUrl+"api/generalapi/TokenRefresh?userId="+lawyerId+"&userType="+userType+"&token="+fcmToken;


        StringRequest req= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response!=null) {

                    Intent intent = new Intent(getApplicationContext(), SampleActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    progressDialog.dismiss();
                }
                else{
                    Toast.makeText(Verify_Pin_Activity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                String message = null;

            }
        });


        req.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingletonPattren.getmInstance(this).addToRequestque(req);

    }
    public class PinOnKeyListener implements View.OnKeyListener {

        private int currentIndex;

        PinOnKeyListener(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (editTexts[currentIndex].getText().toString().isEmpty() && currentIndex != 0)
                    editTexts[currentIndex - 1].requestFocus();
            }
            return false;
        }
    }
}

*/
