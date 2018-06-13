package lbx.xvideoimage;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import lbx.xvideoimagelib.ImageBuilder;
import lbx.xvideoimagelib.VideoImageLoader;

public class MainActivity extends AppCompatActivity {

    private ListView lv_main;
    private VideoImageLoader mVideoImageLoader;
    private List<String> mList;
    private ImageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initVideoImage();
        initData();
        initListener();
    }

    private void initView() {
        lv_main = (ListView) findViewById(R.id.lv_main);
    }

    private void initVideoImage() {
//        mVideoImageLoader = VideoImageLoader.getDefault(this);//默认的VideoImageLoader
        mVideoImageLoader = new VideoImageLoader(this, getBuilder());
        mVideoImageLoader.setOnImgDownloadFinish(new VideoImageLoader.OnImgDownloadFinish() {
            @Override
            public void imgDownloadFinish(String url, Bitmap b) {
                //当第一次下载图片成功时调用，运行在主线程
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void imgDownloadErr(String err) {
                //当第一次下载图片失败时调用，运行在主线程
            }
        });
    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add("");
        mList.add("http://114.242.230.12/PoliceMeetingData/replay/bda303a6ef804f73bd306bbbb508653f.mp4");
        mList.add("");
        mList.add("http://114.242.230.12/PoliceMeetingData/replay/bda303a6ef804f73bd306bbbb508653f.mp4");
        mAdapter = new ImageAdapter(this, mList, mVideoImageLoader);
        lv_main.setAdapter(mAdapter);
    }

    private void initListener() {
    }

    private ImageBuilder getBuilder() {
        return new ImageBuilder().setCatchType(ImageBuilder.CatchType.MemoryAndFile)
                //图片加载失败时设置成的errImage
                .setImgErrorId(R.mipmap.ic_launcher)
                //设置图片的宽高
                .setImgSize(200, 200)
                //设置缓存路径
                .setPath(getCachePath())
                //设置线程数量
                .setThreadNum(3);
    }

    private String getCachePath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            return getFilesDir().getAbsolutePath();
        }
    }
}
