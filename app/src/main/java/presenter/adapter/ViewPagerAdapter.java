package presenter.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.s7k.doctroid.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import app.App;
import es.dmoral.toasty.Toasty;
import helpers.Navigator;
import network.model.Image;


public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<Image> imageUrls;

    public ViewPagerAdapter(Context context, List<Image> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ImageView imageView = new ImageView(context);

        Picasso.get()
                .load(imageUrls.get(position).getImg())
                .fit()
                .error(R.drawable.icon_no_connection)
                .into(imageView);

        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}