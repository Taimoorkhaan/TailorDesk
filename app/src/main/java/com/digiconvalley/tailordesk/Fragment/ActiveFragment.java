package com.digiconvalley.tailordesk.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digiconvalley.tailordesk.Activities.AddCustomer;
import com.digiconvalley.tailordesk.Activities.AllCustomersActivity;
import com.digiconvalley.tailordesk.Activities.CustomerHomeActivity;
import com.digiconvalley.tailordesk.Activities.MainHome_Activity;
import com.digiconvalley.tailordesk.Activities.SelectCategoryActivity;
import com.digiconvalley.tailordesk.Activities.SetupDetail02;
import com.digiconvalley.tailordesk.Activities.TailorOrder_Detail_Activity;
import com.digiconvalley.tailordesk.Adapter.CategoryAdapter;
import com.digiconvalley.tailordesk.Adapter.TailorOrdersAdapter;
import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.CatagoryMain;
import com.digiconvalley.tailordesk.Model.TailorCustomers;
import com.digiconvalley.tailordesk.Model.TailorOrders.TailorOrders;
import com.digiconvalley.tailordesk.Model.TailorProfile;
import com.digiconvalley.tailordesk.R;
import com.digiconvalley.tailordesk.Singleton.SingletonPattren;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class ActiveFragment extends Fragment implements TailorOrdersAdapter.OrderStatus {

    private RecyclerView tailorOrders_Rcv;
    private SharedPreferences pref ;
    private SharedPreferences.Editor editor;
    private TailorProfile tailorProfile;
    private ArrayList<TailorOrders> tailorOrders,tailorOrders2;
    private TailorOrdersAdapter tailorOrdersAdapter;
    private ImageView threeDot;
    private FloatingActionButton addFloatingBtn;
    private SearchView searchView;
    private TextView noOrderText;
    private LinearLayout rcvLayout;
    private Boolean activeOrders=false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_active, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noOrderText=view.findViewById(R.id.noOrdersText);
        rcvLayout=view.findViewById(R.id.rcvLayout);



        hideSoftKeyboard();
        final Gson gson=new Gson();
        pref = getActivity().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        tailorProfile= gson.fromJson(pref.getString("TailorBasicInfo",null), TailorProfile.class);

        tailorOrders_Rcv=view.findViewById(R.id.tailor_customer_order);
        addFloatingBtn=view.findViewById(R.id.add_floatingBtn);
        searchView=view.findViewById(R.id.searchView);




        tailorOrders_Rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        getTailorOrders(StaticData.tailorProfile.getTailorShopId());

        Toast.makeText(getContext(), "Shop Id"+StaticData.tailorProfile.getTailorShopId(), Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), "Tailor Id "+StaticData.tailorProfile.getTailorID(), Toast.LENGTH_SHORT).show();


        addFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupFloating(view);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(tailorOrders!=null)
                searchOrders(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(tailorOrders!=null)
                searchOrders(newText);
                return false;
            }
        });
    }

    public void getTailorOrders(String shopId) {
        if(tailorOrders!=null)
        tailorOrders.clear();
        if(StaticData.tailorPastDuaOrders!=null)
        StaticData.tailorPastDuaOrders.clear();

        final ProgressDialog progressDialog = new ProgressDialog(getContext());

        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url;


        url = StaticData.baseUrl +"TailorGetApi/OrderGet?TailorShopId="+shopId;

        StringRequest req = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null) {
                 if(response.equalsIgnoreCase("\"No Such Order Exist\"")){
                     noOrderText.setVisibility(View.VISIBLE);
                     rcvLayout.setVisibility(View.GONE);
                     noOrderText.setText("NO ORDER IN LIST");

                     progressDialog.dismiss();
                 return;
                 }

                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<TailorOrders>>() {
                    }.getType();

                    tailorOrders = gson.fromJson(response, listType);

                    /////////////////////////////////////////
                    Iterator<TailorOrders> orders = tailorOrders.iterator();
                    while (orders.hasNext()) {
                        TailorOrders tailorOrders = orders.next(); // must be called before you can call i.remove()

                        if (tailorOrders.getOrderStatus().equalsIgnoreCase("Order Delivered")||tailorOrders.getOrderStatus().equalsIgnoreCase("Order Trash")) {
                            StaticData.tailorPastDuaOrders.add(tailorOrders);
                            orders.remove();
                            //slotType.remove();
                        }
                    }

                    for(TailorOrders tailorOrders:tailorOrders){
                        if(!tailorOrders.getOrderStatus().equalsIgnoreCase("Pending")||tailorOrders.getOrderStatus().equalsIgnoreCase("Order Hold")){
                           activeOrders=true;

                        }
                    }

                    if(!activeOrders || tailorOrders.size()==0){
                        noOrderText.setVisibility(View.VISIBLE);
                        rcvLayout.setVisibility(View.GONE);
                        noOrderText.setText("NO ORDER IN LIST");

                    }


                     tailorOrdersAdapter=new TailorOrdersAdapter(getContext(),tailorOrders,ActiveFragment.this);
                     tailorOrders_Rcv.setAdapter(tailorOrdersAdapter);

                     PastDueFragment.tailorOrdersAdapter.notifyDataSetChanged();

                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                progressDialog.setTitle(error.toString());
                String message = null;

            }
        });


        req.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingletonPattren.getmInstance(getContext()).addToRequestque(req);

    }
    public void hideSoftKeyboard() {
        if(getActivity().getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }
    public void showPopup(View v) {

        PopupMenu popup = new PopupMenu(getContext(), v);
        final MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.active_fragment_menu, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.change_order_status){

                }

                return false;
            }
        });


    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void changeStatus(final TailorOrders tailorOrders , final int index) {


        Button orderHoldBtn,orderDeliveredBtn,orderTrashBtn,orderCancelBtn,orderPendingBtn;
        final Dialog dialog=new Dialog(getContext(),android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_view_status_change);

        dialog.setCancelable(false);

        orderHoldBtn=dialog.findViewById(R.id.btnOrderHold);
        orderDeliveredBtn=dialog.findViewById(R.id.btnOD);
        orderTrashBtn=dialog.findViewById(R.id.btnOT);
        orderPendingBtn=dialog.findViewById(R.id.btnPending);
        orderCancelBtn=dialog.findViewById(R.id.btnCancel);

        if(tailorOrders.getOrderStatus().equalsIgnoreCase("Order Delivered")){
            orderDeliveredBtn.setBackgroundColor(R.color.app_color);
        }
        if(tailorOrders.getOrderStatus().equalsIgnoreCase("Order Pending")){
            orderPendingBtn.setBackgroundColor(R.color.app_color);
        }
        if(tailorOrders.getOrderStatus().equalsIgnoreCase("Order Hold")){
            orderHoldBtn.setBackgroundColor(R.color.app_color);
        }
        if(tailorOrders.getOrderStatus().equalsIgnoreCase("Order Trash")){
            orderTrashBtn.setBackgroundColor(R.color.app_color);
        }

        orderCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog.dismiss();
            }
        });

        orderDeliveredBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                warningBox("Order Delivered",tailorOrders.getOrderId(),index);
                dialog.dismiss();
            }
        });

        orderHoldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                warningBox("Order Hold",tailorOrders.getOrderId(),index);
                dialog.dismiss();
            }
        });

        orderTrashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                warningBox("Order Trash",tailorOrders.getOrderId(),index);
                dialog.dismiss();
            }
        });

        orderPendingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //changeStatus("Order Pending",tailorOrders.getOrderId(),index);
                warningBox("Order Pending",tailorOrders.getOrderId(),index);
                dialog.dismiss();
            }
        });


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

    }

    @Override
    public void orderClick(TailorOrders tailorOrders) {

             Intent intent=new Intent(getContext(), TailorOrder_Detail_Activity.class);
           //  intent.putExtra("OrderDetails",tailorOrders.getOrderSuit());
               intent.putExtra("OrderDetails", tailorOrders);

             startActivity(intent);
    }

    public void changeStatus(final String status, Integer orderId, final int index){

        final ProgressDialog progressDialog = new ProgressDialog(getContext());

        progressDialog.setMessage("Please Wait Changing Status....");
        progressDialog.show();
        String url;

        url = StaticData.baseUrl +"TailorPostApi/ChangeStatus?OrderId="+orderId+"&OrderStatus="+status;

        StringRequest req = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    tailorOrders.get(index).setOrderStatus(status);
                    if(status.equalsIgnoreCase("Order Delivered") ||status.equalsIgnoreCase("Order Trash")) {
                        tailorOrders.get(index).setOrderStatus(status);
                        StaticData.tailorPastDuaOrders.add(tailorOrders.get(index));
                        //  tailorOrders.get(index).setOrderStatus(status);
                        tailorOrders.remove(index);
                        tailorOrdersAdapter.notifyDataSetChanged();
                    }

                    if(tailorOrders.size()<1){
                        noOrderText.setVisibility(View.VISIBLE);
                        rcvLayout.setVisibility(View.GONE);
                        noOrderText.setText("NO ORDER IN LIST");
                    }
                    else{
                        tailorOrdersAdapter.notifyDataSetChanged();
                    }


                    progressDialog.dismiss();
                } else {

                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                progressDialog.setTitle(error.toString());
                String message = null;

            }
        });

        req.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingletonPattren.getmInstance(getContext()).addToRequestque(req);

    }


    public void showPopupFloating (View v) {

        PopupMenu popup = new PopupMenu(getContext(), v);
        final MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.floating_active_fragment_menu, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.addOrder){
                   // orderStatus.changeStatus(tailorOrders);
                    Intent intent=new Intent(getContext(), CustomerHomeActivity.class);
                  /*  StaticData.customerid=customers.getCustomerId().toString();
                    StaticData.tailorShopId=tailorProfile.getTailorShopId().toString();*/
                    startActivity(intent);
                }

                if(menuItem.getItemId()==R.id.addCustomer){
                    // orderStatus.changeStatus(tailorOrders);
                    Intent intent=new Intent(getContext(),AddCustomer.class);
                    startActivity(intent);
                }

                return false;
            }
        });

    }

    public void searchOrders(String data){
        tailorOrders2=new ArrayList<>();

        for(TailorOrders tailorOrders:tailorOrders){
            if(tailorOrders.getOrderSuit().get(0).getCustomerName().toLowerCase().contains(data.toLowerCase())){
              tailorOrders2.add(tailorOrders);
            }
        }

        tailorOrdersAdapter=new TailorOrdersAdapter(getContext(),tailorOrders2,ActiveFragment.this);
        tailorOrders_Rcv.setAdapter(tailorOrdersAdapter);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.home_main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.add_new_conatct:
                Intent intent=new Intent(getContext(),AddCustomer.class);
                startActivity(intent);
                break;

            case R.id.show_customer:
                Intent intentt=new Intent(getContext(),CustomerHomeActivity.class);
                startActivity(intentt);
                break;

            }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        MainHome_Activity.active=true;
    }
    private void warningBox(final String statusTitle, final Integer orderId, final int index){
        Button confirmBtn,cancelBtn;
        TextView statusText;

        final Dialog dialog=new Dialog(getContext());
        dialog.setContentView(R.layout.status_warning_box);


        confirmBtn=dialog.findViewById(R.id.confrim);
        cancelBtn=dialog.findViewById(R.id.cancelbtn);
        statusText=dialog.findViewById(R.id.statusText);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeStatus(statusTitle,orderId,index);
                dialog.dismiss();
            }
        });

        statusText.setText(statusText.getText()+" "+statusTitle);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        dialog.show();
    }
}