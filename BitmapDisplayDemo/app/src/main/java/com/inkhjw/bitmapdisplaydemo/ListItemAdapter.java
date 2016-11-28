package com.inkhjw.bitmapdisplaydemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.inkhjw.bitmapdisplaydemo.ImageLoader.ImageCacheManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hjw
 * @deprecated
 */
public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ItemViewHolder> {
    private List<String> list;
    private Context context;
    private ImageCacheManager cacheManager;
    private RequestQueue queue;

    private int width;
    private int height;
    /**
     * 是否正在滑动
     */
    private boolean isScroll = false;
    /**
     * 缓存ImageView对象，滑动过程中添加标签的ImageView
     */
    ArrayList<ImageView> imageViews;

    public ListItemAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
        init(context);
    }

    public void init(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
        height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度

        cacheManager = ImageCacheManager.getInstance();
        queue = Volley.newRequestQueue(context);
        cacheManager.addRequestQueue(queue);
        cacheManager.setFileCache(true);
        cacheManager.setMemoryCache(true);
        imageViews = new ArrayList<>();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewHolder holder = new ItemViewHolder(LayoutInflater.from(
                context).inflate(R.layout.list_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        //确定ImageView的宽高
        final int imageHeight = height / 4;
        final int imageWidth = width / 3;

        Log.e("test", "滑动状态:" + isScroll);
        if (isScroll) {
            ViewGroup.LayoutParams params = holder.imageView.getLayoutParams();
            params.height = imageHeight;
            params.width = imageWidth;
            holder.imageView.setLayoutParams(params);
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER);
            holder.imageView.setImageResource(R.mipmap.icon_stub);
            holder.imageView.setTag(position);
            imageViews.add(holder.imageView);
        } else {
            Bitmap bitmap = cacheManager.loaderImage(list.get(position), holder.imageView, R.mipmap.icon_stub, R.mipmap.icon_error, imageWidth, imageHeight);
            if (bitmap == null) {
                Log.e("test", "缓存bitmap==null");
            }
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ImageDetailActivity.class);
                intent.putExtra("imageUrl", list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addScrollListener(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new ScrollListener());
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ItemViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image);
        }
    }


    class ScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.e("test", "滑动状态:" + newState);
            if (newState == 0) {
                isScroll = false;
                int imageViewsSize = imageViews.size();//滑动过程中新的ImageView的数量
                int visSize = recyclerView.getChildCount();//滑动完成后可见的ImageView数量

                int i;
                if (imageViewsSize >= visSize) {
                    i = imageViewsSize - visSize;
                } else {
                    i = 0;
                }

                for (; i < imageViewsSize; i++) {
                    ImageView imageView = imageViews.get(i);
                    //确定ImageView的宽高
                    final int imageHeight = height / 4;
                    final int imageWidth = width / 3;
                    Bitmap bitmap = cacheManager.loaderImage(list.get((Integer) imageView.getTag()), imageView, R.mipmap.icon_stub, R.mipmap.icon_error, imageWidth, imageHeight);
                    if (bitmap == null) {
                        Log.e("test", "缓存bitmap==null");
                    }
                }
                imageViews.removeAll(imageViews);

            } else

            {
                isScroll = true;
            }

            int c = recyclerView.getChildCount();

            Log.e("test", "recyclerView.getChildCount()=" + c);
        }
    }
}
