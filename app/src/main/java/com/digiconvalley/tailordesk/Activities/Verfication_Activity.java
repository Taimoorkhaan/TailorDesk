package com.digiconvalley.tailordesk.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.chaos.view.PinView;
import com.digiconvalley.tailordesk.Model.TailorProfile;
import com.digiconvalley.tailordesk.R;
import com.digiconvalley.tailordesk.Singleton.SingletonPattren;
import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.Gson;
import com.tuyenmonkey.mkloader.MKLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import static android.widget.Toast.LENGTH_SHORT;

public class Verfication_Activity extends AppCompatActivity {
    //for otp
    private EditText otp;
    private Button submit;
    private TextView resend;
    private MKLoader loader;
    private String number, id;
    private FirebaseAuth mAuth;
    private String pin;
    private TailorProfile tailorExist;
    //for number show
    private TextView phone;
    private Boolean userExist=false,mTimer;
    //end number show
    //otp end
    private EditText editText1, editText2, editText3, editText4, editText5, editText6;
    private EditText[] editTexts;
    TextView re_send, did_not_receive_code;
    Button pin_verify;
    private SharedPreferences pref ;
    private SharedPreferences.Editor editor;
    private PhoneAuthProvider phoneAuthProvider;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private String verifyCodeBySystem,token;
    private PinView pinView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout6);
        //for otp
        //otp = findViewById(R.id.otp);
        resend = findViewById(R.id.re_send);
        loader = findViewById(R.id.loader);
        mAuth = FirebaseAuth.getInstance();
        number = getIntent().getStringExtra("number");
        //otp end
        //-------------------------------------
        did_not_receive_code = findViewById(R.id.did_not_receive_code);
        re_send = findViewById(R.id.re_send);
        pin_verify = findViewById(R.id.pin_verify);

        pinView=findViewById(R.id.pinView);





        did_not_receive_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Code Not Receive!", Toast.LENGTH_SHORT).show();
            }
        });


        //for number show
        phone=findViewById(R.id.phone);
        phone.setText(number);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        ////////////////////////////////////////////New Code///////////////////////////////////////////////////////////////
        phoneAuthProvider.getInstance().verifyPhoneNumber(
                number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,// Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);


















        ///////////////////////////////////////////////NEW Code/////////////////////////////////////////////////////










        //number show
        //for otp
        re_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    sendVerificationCode();
            }
        });
       // sendVerificationCode();
        pin_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(pinView.getText().toString().length()==6)
                    Toast.makeText(Verfication_Activity.this, "PinData is ="+pinView.getText().toString(), LENGTH_SHORT).show();


                pin = editText1.getText().toString() + editText2.getText().toString() + editText3.getText().toString()
                        + editText4.getText().toString() + editText5.getText().toString() + editText6.getText().toString();
                if (TextUtils.isEmpty(pin)) {
                    Toast.makeText(Verfication_Activity.this, "Enter Otp", Toast.LENGTH_SHORT).show();
                } else if (pin.replace(" ", "").length() != 6) {
                    Toast.makeText(Verfication_Activity.this, "Enter right otp", Toast.LENGTH_SHORT).show();
                } else {
                    loader.setVisibility(View.VISIBLE);
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id, pin.replace(" ", ""));
                    //signInWithPhoneAuthCredential(credential);
                }

            }
        });
        //otp end


        //---------------------------------
        //-----------------for pin
        editText1 = findViewById(R.id.digit1);
        editText2 = findViewById(R.id.digit2);
        editText3 = findViewById(R.id.digit3);
        editText4 = findViewById(R.id.digit4);
        editText5 = findViewById(R.id.digit5);
        editText6 = findViewById(R.id.digit6);
        editTexts = new EditText[]{editText1, editText2, editText3, editText4, editText5, editText6};

        editText1.addTextChangedListener(new PinTextWatcher(0));
        editText2.addTextChangedListener(new PinTextWatcher(1));
        editText3.addTextChangedListener(new PinTextWatcher(2));
        editText4.addTextChangedListener(new PinTextWatcher(3));
        editText5.addTextChangedListener(new PinTextWatcher(4));
        editText6.addTextChangedListener(new PinTextWatcher(5));

        editText1.setOnKeyListener(new PinOnKeyListener(0));
        editText2.setOnKeyListener(new PinOnKeyListener(1));
        editText3.setOnKeyListener(new PinOnKeyListener(2));
        editText4.setOnKeyListener(new PinOnKeyListener(3));
        editText5.setOnKeyListener(new PinOnKeyListener(4));
        editText6.setOnKeyListener(new PinOnKeyListener(5));
    }

    //start otp
   /* private void sendVerificationCode() {
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                resend.setText("" + l / 1000);
                resend.setEnabled(false);
                pin_verify.setEnabled(true);
            }

            @Override
            public void onFinish() {
                resend.setText(" Re-send");
                resend.setEnabled(true);
                pin_verify.setEnabled(false);
            }
        }.start();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,        // Phone number to verify
                60L,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onCodeSent(@NonNull String id, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        Verfication_Activity.this.id = id;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(Verfication_Activity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });        // OnVerificationStateChangedCallbacks
    }*/

   /* private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        loader.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            CheckUser();
                           *//* startActivity(new Intent(Verfication_Activity.this, SetupDetail01.class));
                           *//*// finish();
                            FirebaseUser user = task.getResult().getUser();
                           // Toast.makeText(Verfication_Activity.this, "Verification Successfull!", Toast.LENGTH_SHORT).show();
                            // ...
                        } else {
                            Toast.makeText(Verfication_Activity.this, "Verification Filed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/
    //end otp

    public class PinTextWatcher implements TextWatcher {

        private int currentIndex;
        private boolean isFirst = false, isLast = false;
        private String newTypedString = "";

        PinTextWatcher(int currentIndex) {
            this.currentIndex = currentIndex;

            if (currentIndex == 0)
                this.isFirst = true;
            else if (currentIndex == editTexts.length - 1)
                this.isLast = true;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            newTypedString = s.subSequence(start, start + count).toString().trim();
        }

        @Override
        public void afterTextChanged(Editable s) {

            String text = newTypedString;

            /* Detect paste event and set first char */
            if (text.length() > 1)
                text = String.valueOf(text.charAt(0)); // TODO: We can fill out other EditTexts

            editTexts[currentIndex].removeTextChangedListener(this);
            editTexts[currentIndex].setText(text);
            editTexts[currentIndex].setSelection(text.length());
            editTexts[currentIndex].addTextChangedListener(this);

            if (text.length() == 1)
                moveToNext();
            else if (text.length() == 0)
                moveToPrevious();
        }

        private void moveToNext() {
            if (!isLast)
                editTexts[currentIndex + 1].requestFocus();

            if (isAllEditTextsFilled() && isLast) { // isLast is optional
                editTexts[currentIndex].clearFocus();
                hideKeyboard();
            }
        }

        private void moveToPrevious() {
            if (!isFirst)
                editTexts[currentIndex - 1].requestFocus();
        }

        private boolean isAllEditTextsFilled() {
            for (EditText editText : editTexts)
                if (editText.getText().toString().trim().length() == 0)
                    return false;
            return true;
        }

        private void hideKeyboard() {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }

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
    public void CheckUser( ){
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url;

            url = StaticData.baseUrl +"TailorGetApi/TailorExist?PhoneNo="+number;

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
                            intent.putExtra("Phone", number);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else{
                            editor.putString("Signup", "yes");
                            Intent intent = new Intent(getApplicationContext(), MainHome_Activity.class);
                            intent.putExtra("Phone", number);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
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
                    Toast.makeText(Verfication_Activity.this, "Server Error", Toast.LENGTH_LONG).show();
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
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url;

        url = StaticData.baseUrl +"TailorGetApi/TailorCreate?PhoneNo="+number+"&FirbaseToken=984302";

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

                    Intent intent=new Intent(Verfication_Activity.this,SetupDetail01.class);
                    intent.putExtra("Phone",number);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(Verfication_Activity.this, "Server Error", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // progressBar.dismiss();
                Toast.makeText(Verfication_Activity.this, "Size is "+error.toString(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(Verfication_Activity.this, e.getMessage(), LENGTH_SHORT).show();
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
            Toast.makeText(Verfication_Activity.this, "Time out Click on Resend", LENGTH_SHORT).show();
    }

    private void signInTheUserCredential(PhoneAuthCredential credential) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithCredential(credential)
                .addOnCompleteListener(Verfication_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            CheckUser();
                         /*   Intent intent =new Intent(getApplicationContext(),User_basic_info_Activity.class);
                            editor.putString("Loged","yes");
                            editor.commit();
                            editor.apply();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);*/
                        }else Toast.makeText(Verfication_Activity.this, task.getException().getMessage(), LENGTH_SHORT).show();
                    }
                });

    }



    public void phoneauth(){
        phoneAuthProvider.getInstance().verifyPhoneNumber(
                number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,resendToken);

    }
}