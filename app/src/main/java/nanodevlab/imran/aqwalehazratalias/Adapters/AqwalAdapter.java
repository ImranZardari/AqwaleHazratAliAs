package nanodevlab.imran.aqwalehazratalias.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import java.util.List;
import nanodevlab.imran.aqwalehazratalias.Interfaces.recyclerItemClickListener;
import nanodevlab.imran.aqwalehazratalias.R;

public class AqwalAdapter extends RecyclerView.Adapter<AqwalAdapter.ImageViewHolder> {
    private RequestBuilder<Bitmap> glideBuilder;
    private Context mContext;
    private List<String> Urls;
    private recyclerItemClickListener.RecyclerViewClickListener listener;

    public AqwalAdapter(Context context, List<String> url, recyclerItemClickListener.RecyclerViewClickListener listener) {
        mContext = context;
        Urls = url;

        glideBuilder = Glide.with(mContext).asBitmap()
                .apply(new RequestOptions().override(80, 80));

        this.listener = listener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.aqwal_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {
        final ProgressBar progressBar=holder.itemProgress;
        glideBuilder.load(Urls.get(position)).listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.aqwalImageView);
    }



    @Override
    public int getItemCount() {
        return Urls.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView aqwalImageView;
        private ProgressBar itemProgress;

        private ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            aqwalImageView = itemView.findViewById(R.id.aqwalItemImage);
            itemProgress = itemView.findViewById(R.id.aqwalProgressBar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }
}
