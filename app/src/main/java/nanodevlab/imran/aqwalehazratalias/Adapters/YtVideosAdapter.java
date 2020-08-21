package nanodevlab.imran.aqwalehazratalias.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import nanodevlab.imran.aqwalehazratalias.Interfaces.onYTItemClickListner;
import nanodevlab.imran.aqwalehazratalias.Models.Item;
import nanodevlab.imran.aqwalehazratalias.Models.VideoResponse;
import nanodevlab.imran.aqwalehazratalias.R;
import nanodevlab.imran.aqwalehazratalias.service.OnYtItemClickLisetner;
import retrofit2.Callback;

public class YtVideosAdapter extends RecyclerView.Adapter<YtVideosAdapter.VH> {


    private Context context;
    private List<Item> itemList = new ArrayList<>();
    private OnYtItemClickLisetner clickListener;


    public YtVideosAdapter(Context context, OnYtItemClickLisetner clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ytvideo_itemlayout, parent, false);
        return new VH(view);

    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Item item = itemList.get(position);
        if (item != null) {


            Glide.with(context).load(item.getSnippet().getThumbnails().getMedium().getUrl()).apply(new RequestOptions().centerInside()).into(holder.ytthumbnail);
            holder.ytvideoTitle.setText(item.getSnippet().getTitle());
            holder.ytChannelName.setText(item.getSnippet().getChannelTitle());


        }


    }

    public void addAll(List<Item> items) {
        itemList.addAll(items);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    public class VH extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ytthumbnail;
        TextView ytvideoTitle, ytChannelName;

        public VH(@NonNull View itemView) {
            super(itemView);

            ytthumbnail = itemView.findViewById(R.id.ytvideothumbnail);
            ytvideoTitle = itemView.findViewById(R.id.ytVideoTitle);
            ytChannelName = itemView.findViewById(R.id.ytChannelTitle);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            clickListener.itemClick(v, getAdapterPosition());
        }
    }


}
