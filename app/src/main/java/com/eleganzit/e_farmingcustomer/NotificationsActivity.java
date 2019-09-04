package com.eleganzit.e_farmingcustomer;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.e_farmingcustomer.adapters.ManageMyFarmAdapter;
import com.eleganzit.e_farmingcustomer.adapters.NotificationsAdapter;
import com.eleganzit.e_farmingcustomer.api.RetrofitAPI;
import com.eleganzit.e_farmingcustomer.api.RetrofitInterface;
import com.eleganzit.e_farmingcustomer.model.FarmingpartnerNotification;
import com.eleganzit.e_farmingcustomer.model.FarmingpartnerNotificationResponse;
import com.eleganzit.e_farmingcustomer.model.ManageFarmData;
import com.eleganzit.e_farmingcustomer.model.NotificationsData;
import com.eleganzit.e_farmingcustomer.utils.UserSessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsActivity extends AppCompatActivity {

    RecyclerView rc_notifications;
    ProgressDialog progressDialog;
    UserSessionManager userSessionManager;
    TextView nodata;
    ArrayList<FarmingpartnerNotification> arrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        nodata=findViewById(R.id.nodata);
        progressDialog = new ProgressDialog(NotificationsActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        userSessionManager=new UserSessionManager(this);
        resetPassword();
        rc_notifications=findViewById(R.id.rc_notifications);



    }

    private void resetPassword() {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<FarmingpartnerNotificationResponse> call=myInterface.customerNotification(""+userSessionManager.getUserDetails().get(UserSessionManager.KEY_USER_ID));
        call.enqueue(new Callback<FarmingpartnerNotificationResponse>() {
            @Override
            public void onResponse(Call<FarmingpartnerNotificationResponse> call, Response<FarmingpartnerNotificationResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1"))
                    {
                        nodata.setVisibility(View.GONE);

                        if (response.body().getData()!=null)
                        {
                            nodata.setVisibility(View.GONE);
                            for (int i=0;i<response.body().getData().size();i++)
                            {

                                Log.d("Dataaaa",i+"-"+response.body().getData().get(i).getVegName());


                                FarmingpartnerNotification sapling_date=new FarmingpartnerNotification();
                                sapling_date.setCustomer_id(response.body().getData().get(i).getCustomer_id());
                                sapling_date.setVegName(response.body().getData().get(i).getVegName());
                                sapling_date.setFarmName(response.body().getData().get(i).getFarmName());
                                sapling_date.setPlotName(response.body().getData().get(i).getPlotName());
                                sapling_date.setVegCalStatusId(response.body().getData().get(i).getVegCalStatusId());
                                sapling_date.setFarm_photo(response.body().getData().get(i).getFarm_photo());




                                if (response.body().getData().get(i).getStatusData()!=null)
                                {
                                    for (int j=0;j<response.body().getData().get(i).getStatusData().size();j++)
                                    {

                                        Log.d("Dataaarrra",i+"-"+response.body().getData().get(i).getVegetableId()+"  "+response.body().getData().get(i).getStatusData().get(j).getSaplingDate() );

                                        sapling_date.setStatusData(response.body().getData().get(i).getStatusData());
                                        //farmingpartnerNotification.setSapling_date_status(response.body().getData().get(i).getStatusData().get(j).getSaplingDateStatus());


                                    }
                                }

                                arrayList.add(sapling_date);
                            }

                            Log.d("Dataaaa",""+arrayList.size());

                            rc_notifications.setAdapter(new NotificationsAdapter(arrayList,NotificationsActivity.this));

                        }

                        else
                        {
                            nodata.setVisibility(View.VISIBLE);
                        }


                    }
                    else
                    {
                        nodata.setVisibility(View.VISIBLE);

                    }
                }
            }

            @Override
            public void onFailure(Call<FarmingpartnerNotificationResponse> call, Throwable t) {
                progressDialog.dismiss();
                nodata.setVisibility(View.VISIBLE);
                Toast.makeText(NotificationsActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
