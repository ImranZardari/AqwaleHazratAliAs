package nanodevlab.imran.aqwalehazratalias.OfflineDataBase.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nanodevlab.imran.aqwalehazratalias.OfflineDataBase.Models.DuaModel;
import nanodevlab.imran.aqwalehazratalias.R;
import nanodevlab.imran.aqwalehazratalias.ViewDuasActivity;

public class ZiaratAdapter extends RecyclerView.Adapter<ZiaratAdapter.viewHolder> {

    private List<DuaModel> duaModelList;
    private Context context;

    public ZiaratAdapter(List<DuaModel> duaModelList) {
        this.duaModelList = duaModelList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ziarat_childitem, parent, false);
        context = parent.getContext();
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
        holder.ziaratTitleTextView.setText(duaModelList.get(position).getTitle());
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "PDMS_Saleem_QuranFont.ttf");
        holder.ziaratTitleTextView.setTypeface(typeface);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewDuasActivity.class);
                intent.putExtra("duaName", duaModelList.get(position).getTitle());
                intent.putExtra("duaText", duaModelList.get(position).getDuaText());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return duaModelList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        private TextView ziaratTitleTextView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ziaratTitleTextView = itemView.findViewById(R.id.ziaratTitleTextView);
        }
    }


}
