package com.eleganzit.e_farmingcustomer.adapters;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.e_farmingcustomer.R;
import com.eleganzit.e_farmingcustomer.api.RetrofitInterface;
import com.eleganzit.e_farmingcustomer.model.NotificationData;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    String status,status_name,progress_name;
    ArrayList<NotificationData> arr;
    Context context;
    ProgressDialog progressDialog;

    public NotificationAdapter(ArrayList<NotificationData> arr, Context context) {
        this.arr = arr;
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notifications_layout,viewGroup,false);

        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        final NotificationData notificationsData=arr.get(i);

        Log.d("dsss",""+notificationsData.getVeg_cal_status_id());
        Log.d("sdsd",""+notificationsData.getFarm_name());
        Log.d("sdsd",""+notificationsData.getCustomer_id());
            myViewHolder.txt_status.setText("" + notificationsData.getStatusData());
            myViewHolder.txt_title.setText(notificationsData.getName()+" of " + notificationsData.getVeg_name() + " in " + notificationsData.getPlot_name());

        Glide
                .with(context)
                .load(notificationsData.getFarm_photo())
                .apply(new RequestOptions().transforms(new CircleCrop())).into(myViewHolder.img_farm);


    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout cardviewdashboard;
        ImageView img_farm;
        TextView txt_title,txt_status;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            cardviewdashboard=itemView.findViewById(R.id.cardviewdashboard);
            img_farm=itemView.findViewById(R.id.img_farm);
            txt_title=itemView.findViewById(R.id.txt_title);
            txt_status=itemView.findViewById(R.id.txt_status);

        }
    }





}
