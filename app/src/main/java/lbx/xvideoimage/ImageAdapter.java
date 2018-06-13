package lbx.xvideoimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import lbx.xvideoimagelib.Options;
import lbx.xvideoimagelib.VideoImageLoader;

/**
 * Created by admin on 2017/8/31.
 */

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mList;
    private VideoImageLoader mVideoImageLoader;
    private Options mOptions;

    public ImageAdapter(Context context, List<String> mList, VideoImageLoader videoImageLoader) {
        this.mContext = context;
        this.mList = mList;
        this.mVideoImageLoader = videoImageLoader;
        //初始化图片显示设置
        //图片设置参数
        mOptions = new Options()
                //居中显示
                .setScaleType(ImageView.ScaleType.CENTER)
                //黑白照片效果
                .setStyle(Options.BitmapStyle.NO_COLOR);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public String getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String s = mList.get(position);

        //根据bitmap设置
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/bda303a6ef804f73bd306bbbb508653f";
//        mVideoImageLoader.displayBitmap(holder.imageView, BitmapFactory.decodeFile(path));

        //根据filepath设置
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/bda303a6ef804f73bd306bbbb508653f";
//        mVideoImageLoader.displayDisc(holder.imageView, path);

        //根据url设置
        mVideoImageLoader.displayUrl(holder.imageView, s, new VideoImageLoader.OnImgSetCallback() {
            @Override
            public void success(View view, Bitmap bitmap) {
                //设置成功回调
            }

            @Override
            public void errorLoad(View view) {
                //设置失败回调
            }

            @Override
            public void setErrorImgFinish(View view, Bitmap bitmap, int errId) {
                //设置成预先定义好的errImage的回调
            }
            //设置图片显示属性
        }, mOptions);
        return convertView;
    }

    static class ViewHolder {

        private ImageView imageView;

        public ViewHolder(View convertView) {
            imageView = (ImageView) convertView.findViewById(R.id.iv);
        }
    }

}
