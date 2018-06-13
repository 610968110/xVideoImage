
# xVideoImage
        
xVideoImage是一款支持从网络视频地址获取图片并显示的图片框架，如：加载“http://XXX。mp4”的某一帧图片到ImageView，同时也支持
加载sd卡、bitmap、或者网络图片的加载，支持三级缓存，可以动态设置是否缓存到内存/sd卡，
采用lru算法进行图片的回收，高效实用.
        
一、需要权限：
===
````Xml
	<uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
````
二、引入库：
===
````Xml
	compile 'com.lbx:xVideoImage:1.0.0'
````
三、使用：
===

1、初始化VideoImageLoader：
````Java
new ImageBuilder().setCatchType(ImageBuilder.CatchType.MemoryAndFile)
 //图片加载失败时设置成的errImage
 .setImgErrorId(R.mipmap.ic_launcher)
 //设置图片的宽高
 .setImgSize(200, 200)
 //设置缓存路径
 .setPath(getCachePath())
 //设置线程数量
 .setThreadNum(3);

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
````
2.设置图片：
````Java
//初始化图片显示设置
//图片设置参数
mOptions = new Options()
        //居中显示
        .setScaleType(ImageView.ScaleType.CENTER)
        //黑白照片效果
        .setStyle(Options.BitmapStyle.NO_COLOR);
````

````Java
        //根据bitmap设置
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/bda303a6ef804f73bd306bbbb508653f";
//        mVideoImageLoader.displayBitmap(holder.imageView, BitmapFactory.decodeFile(path));
                
                        
        //根据filepath设置
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/bda303a6ef804f73bd306bbbb508653f";
//        mVideoImageLoader.displayDisc(holder.imageView, path);       mVideoImageLoader.displayDisc(holder.imageView, path);
        
            
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
