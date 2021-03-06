package com.blive.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blive.model.Audience;
import com.blive.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.net.URLDecoder;
import java.util.ArrayList;

public class AdapterBroadcastToppers extends RecyclerView.Adapter<AdapterBroadcastToppers.ViewHolder> {

    private Context mContext;
    private ArrayList<Audience> audiences;
    private Listener listener;

    public AdapterBroadcastToppers(Context mContext, ArrayList<Audience> audiences) {
        this.mContext = mContext;
        this.audiences = audiences;
    }

    @NonNull
    @Override
    public AdapterBroadcastToppers.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_broadcsat_toppers, parent, false);
            return new AdapterBroadcastToppers.ViewHolder(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterBroadcastToppers.ViewHolder holder, final int position) {
        try {
            final Audience audience = audiences.get(position);

            String base64 = audience.getProfile_pic();
            String image = URLDecoder.decode(base64,"UTF-8");

            if (image != null && !image.isEmpty()) {
                Picasso.get().load(image).fit().centerCrop().memoryPolicy(MemoryPolicy.NO_STORE).placeholder(R.drawable.user).into(holder.iv);
            }else {
                Picasso.get().load(R.drawable.user).fit().centerCrop().memoryPolicy(MemoryPolicy.NO_STORE).placeholder(R.drawable.user).into(holder.iv);
            }

            String posi = String.valueOf(position+1);
            holder.tvPosition.setText(posi);
            holder.tvName.setText(audience.getUsername());


            holder.ll.setOnClickListener(v -> {
                if (listener != null) {
                    listener.OnClicked(audiences.get(holder.getAdapterPosition()));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return audiences.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;
        TextView tvPosition;
        TextView tvName;
        LinearLayout ll;

        private ViewHolder(View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.iv);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPosition = itemView.findViewById(R.id.tv_position);
            ll = itemView.findViewById(R.id.ll);
        }
    }

    public void setOnClickListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void OnClicked(Audience audience);
    }
}