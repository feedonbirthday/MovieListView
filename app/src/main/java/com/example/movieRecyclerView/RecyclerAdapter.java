package com.example.movieRecyclerView;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Viewholder> {
    private ArrayList<Movie> movieList;
    Intent intent;
    private Context context;
    String movie_name, date, info = "";
    int img;
    Bundle bag;
    String idReserve = "reserve_movie";

    public RecyclerAdapter(Context context,ArrayList<Movie> movieList) {
        this.movieList = movieList;
        this.context = context;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView movie_img;
        private TextView txt_name,txt_date,txt_info;
        private Button btn_buy,btn_more;

        public Viewholder(@NonNull final View itemView) {
            super(itemView);
            movie_img = itemView.findViewById(R.id.movieImg);
            txt_name = itemView.findViewById(R.id.movieName);
            txt_date = itemView.findViewById(R.id.movieDate);
            txt_info = itemView.findViewById(R.id.movieInfo);
            btn_more = itemView.findViewById(R.id.btnMore);
            btn_buy = itemView.findViewById(R.id.btnBuy);
            btn_buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(),"已購票 ! ",Toast.LENGTH_SHORT).show();

                    // 設定 Notification
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(v.getContext().NOTIFICATION_SERVICE);
                    // 通知 1 設定
                    NotificationChannel channelReserve = new NotificationChannel(
                            idReserve,
                            "Channel Reserve",
                            NotificationManager.IMPORTANCE_HIGH);
                    channelReserve.setDescription("已成功訂票！祝您觀影愉快！");
                    channelReserve.enableLights(true);
                    channelReserve.enableVibration(true);
                    // 依設定建立通知
                    notificationManager.createNotificationChannel(channelReserve);
                    // 第 1 個訊息
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(v.getContext(), idReserve)
                            .setSmallIcon(R.mipmap.video)
                            .setContentTitle("已成功訂票！")
                            .setContentText("GoodGoodSee電影院，祝您觀影愉快！")
                            .setAutoCancel(true);
                    // 啟動通知
                    notificationManager.notify(0,builder.build());
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_view,parent,false);
        Viewholder vh = new Viewholder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.Viewholder holder, final int position) {
        holder.movie_img.setImageResource(movieList.get(position).img);
        //holder.movie_img.setImageResource(movieList.get(position).getImg());
        holder.txt_name.setText(movieList.get(position).movie_name);
        //holder.txt_name.setText(movieList.get(position).getMovie_name());
        holder.txt_date.setText(movieList.get(position).date);
        //holder.txt_date.setText(movieList.get(position).getDate());
        holder.txt_info.setText(movieList.get(position).info);
        //holder.txt_info.setText(movieList.get(position).getInfo());

        // 跳到 information頁
        holder.btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();
                intent.setClass(v.getContext(),InformationPage.class);

                bag = new Bundle();

                movie_name = movieList.get(position).getMovie_name();
                date = movieList.get(position).getDate();
                info = movieList.get(position).getInfo();
                img = movieList.get(position).getImg();

                bag.putString("chosenName", String.valueOf(movie_name));
                bag.putString("chosenDate", String.valueOf(date));
                bag.putString("chosenInfo", String.valueOf(info));
                bag.putInt("chosenImg", img);

                intent.putExtras(bag); //要加這句才能run
                intent.setClass(v.getContext(), InformationPage.class);

                context.startActivity(intent);
                ((Activity)context).finish(); // 強制轉型
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}