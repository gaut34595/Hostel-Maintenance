package com.example.hostelmaintenance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.hostelmaintenance.R;

public class viewpager_complaint_adapter extends PagerAdapter {
    private Context context;
    private LayoutInflater lf;
    private int[] images= {R.drawable.carpentar,R.drawable.plumber,R.drawable.electrician,R.drawable.cleaner};


    public viewpager_complaint_adapter(Context context) {
        this.context=context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public void destroyItem(@NonNull View container, int position, @NonNull Object object) {
        ViewPager vg = (ViewPager) container;
        View view = (View) object;
        vg.removeView(view);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        lf=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v= lf.inflate(R.layout.customlayout_complaint,null);

        ImageView iv = (ImageView) v.findViewById(R.id.myimageview);
        iv.setImageResource(images[position]);
        ViewPager vg = (ViewPager) container;
        vg.addView(v);
        return v;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
