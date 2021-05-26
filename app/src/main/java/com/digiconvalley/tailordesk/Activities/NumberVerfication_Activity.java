package com.digiconvalley.tailordesk.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.chaos.view.PinView;
import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.TailorProfile;
import com.digiconvalley.tailordesk.R;
import com.digiconvalley.tailordesk.Singleton.SingletonPattren;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import static android.widget.Toast.LENGTH_SHORT;

public class NumberVerfication_Activity extends AppCompatActivity {
    private PinView pinView;
    private Button verifyBtn;
    private TextView resendCode, tailorPhoneNumber;
    private String phoneNumber,verifyCodeBySystem,token;;
    private PhoneAuthProvider phoneAuthProvider;
    private TextView timer,timer1;
    private FirebaseAuth auth;
    private SharedPreferences pref ;
    private SharedPreferences.Editor editor;
    private TailorProfile tailorExist;

    private Boolean userExist,mTimer;
    private CountDownTimer cTimer;

    private PhoneAuthProvider.ForceResendingToken resendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_verfication_);

        tailorPhoneNumber =findViewById(R.id.phone);
        resendCode=findViewById(R.id.re_send);
        verifyBtn=findViewById(R.id.pin_verify);
        pinView=findViewById(R.id.pinView);

        timer=findViewById(R.id.timer);
        timer1=findViewById(R.id.timer1);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        if(getIntent()!=null){
            phoneNumber=getIntent().getStringExtra("number");
            tailorPhoneNumber.setText(phoneNumber );
            startTimer();
        }


        resendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelTimer();
                startTimer();

                pinView.setText("");

                phoneauth();
            }
        });
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pinView.getText().toString().length()==6){
                // verifyCode(pinView.getText().toString().trim());

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifyCodeBySystem, pinView.getText().toString().trim());
                    if(!mTimer)
                        signInTheUserCredential(credential);
                    else
                        Toast.makeText(NumberVerfication_Activity.this, "Time Out Press Resend code", LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(NumberVerfication_Activity.this, "Kindly Enter Complete Code ", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });



        phoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,// Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);

    }

    public PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code =phoneAuthCredential.getSmsCode();

            if(code!=null){
             //   verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(NumberVerfication_Activity.this, e.getMessage(), LENGTH_SHORT).show();
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
            Toast.makeText(NumberVerfication_Activity.this, "Time out Click on Resend", LENGTH_SHORT).show();
    }

    private void signInTheUserCredential(PhoneAuthCredential credential) {
        //auth = FirebaseAuth.getInstance();
         auth=FirebaseAuth.getInstance();
        auth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                if(authResult.getUser().isAnonymous()){
                    Toast.makeText(NumberVerfication_Activity.this, "Ok ", LENGTH_SHORT).show();
                }
            }
        })
                .addOnCompleteListener(NumberVerfication_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            CheckUser();
                           /* CheckUser();
                            Intent intent =new Intent(getApplicationContext(),User_basic_info_Activity.class);
                            editor.putString("Loged","yes");
                            editor.commit();
                            editor.apply();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
*/
                        }else Toast.makeText(NumberVerfication_Activity.this, task.getException().getMessage(), LENGTH_SHORT).show();
                    }
                });

    }



    public void phoneauth(){
        phoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,resendToken);

    }

    void startTimer() {


        cTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
             //   modifyNumber.setEnabled(false);
                mTimer = false;
                verifyBtn.setEnabled(true);
                timer.setTextColor(Color.parseColor("#414A51"));
                timer1.setTextColor(Color.parseColor("#414A51"));
                timer.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                verifyBtn.setEnabled(true);
                Toast.makeText(NumberVerfication_Activity.this, "Code Expired. Please Resend Code", LENGTH_SHORT).show();
                timer.setTextColor(Color.parseColor("#9CA1A5"));
                timer1.setTextColor(Color.parseColor("#9CA1A5"));
                timer.setText("00");
                mTimer = true;
           //     modifyNumber.setEnabled(true);
            }
        };
        cTimer.start();
    }

    void cancelTimer() {
        if (cTimer != null)
            cTimer.cancel();
    }

    public void CheckUser( ){
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        String url;

        url = StaticData.baseUrl +"TailorGetApi/TailorExist?PhoneNo="+phoneNumber;

        StringRequest req= new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson=new Gson();
                if(response!=null) {
                    try {

                        JSONObject jsonObject=new JSONObject(response);
                        userExist = (Boolean) jsonObject.get("does_exist");
                        if(userExist){
                            JSONObject  tailorInfo =jsonObject.getJSONObject("tailor");
                            tailorExist =new TailorProfile();
                            tailorExist.setTailorName(tailorInfo.getString("tailorName"));
                            tailorExist.setTailorID(tailorInfo.getInt("tailorID"));
                            tailorExist.setTailorPhoneNo(tailorInfo.getString("tailorPhoneNo"));

                            JSONObject  tailorInfo2 =jsonObject.getJSONObject("tailor").getJSONObject("tailorShopData");

                            tailorExist.setTailorShopId(tailorInfo2.getString("tailorShopId"));


                        }
                        //   Toast.makeText(Verify_Pin_Activity.this, ""+userExist, LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    if(userExist) {

                        String tailorinfo = gson.toJson(tailorExist);
                        editor.putString("Loged", "yes");
                        editor.putString("TailorBasicInfo",tailorinfo);



                        if(tailorExist.getTailorShopId()==null) {
                            Intent intent = new Intent(getApplicationContext(), SetupDetail01.class);
                            intent.putExtra("Phone", phoneNumber);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            cTimer.onFinish();
                        }
                        else{
                            editor.putString("Signup", "yes");
                            Intent intent = new Intent(getApplicationContext(), MainHome_Activity.class);
                            intent.putExtra("Phone", phoneNumber);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            cTimer.onFinish();
                        }

                        editor.commit();
                        editor.apply();

                    }
                    else if(!userExist){
                        CreateTailor();

                    }


                    progressDialog.dismiss();
                }
                else{
                    Toast.makeText(NumberVerfication_Activity.this, "Server Error", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // progressBar.dismiss();

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
    public void CreateTailor( ){

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        String url;

        url = StaticData.baseUrl +"TailorGetApi/TailorCreate?PhoneNo="+phoneNumber+"&FirbaseToken=984302";

        StringRequest req= new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response!=null) {

                    progressDialog.dismiss();
                    Gson gson=new Gson();
                    TailorProfile tailorProfile=gson.fromJson(response,TailorProfile.class);
                    String tailorinfo = gson.toJson(tailorProfile);
                    editor.putString("Loged", "yes");

                    editor.putString("TailorBasicInfo",tailorinfo);
                    editor.commit();
                    editor.apply();

                    Intent intent=new Intent(NumberVerfication_Activity.this,SetupDetail01.class);
                    intent.putExtra("Phone",phoneNumber);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(NumberVerfication_Activity.this, "Server Error", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // progressBar.dismiss();
             //   Toast.makeText(NumberVerfication_Activity.this, "Size is "+error.toString(), Toast.LENGTH_SHORT).show();
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
}