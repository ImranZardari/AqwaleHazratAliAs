package nanodevlab.imran.aqwalehazratalias.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import nanodevlab.imran.aqwalehazratalias.R;

public class VpDispAdapter extends PagerAdapter {

    private Context context;
    private List<String> imageUrls;
    private LayoutInflater layoutInflater;

    public VpDispAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    public VpDispAdapter() {
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=layoutInflater.inflate(R.layout.viewpager_aqwalitem,null);
        ImageView showimage=v.findViewById(R.id.vp_aqwalItem);
        String url= imageUrls.get(position);
        GetCurrentSelectedItem(position);
        Glide.with(context).load(url).apply(new RequestOptions().centerInside()).into(showimage);
        ViewPager viewPager=(ViewPager)container;
        viewPager.addView(v,0);

        return  v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager=(ViewPager)container;
        View v=(View)object;
        viewPager.removeView(v);
    }


    public String GetCurrentSelectedItem(int p){
        return imageUrls.get(p);
    }

    public String getItemAt(int po) {
        return imageUrls.get(po);
    }

}
