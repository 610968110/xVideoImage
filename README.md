# xVideoImage
xVideoImage

一、需要权限：
	<uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
二、引入库：
	compile 'com.lbx:xVideoImage:1.0.0'
三、使用：

1、初始化VideoImageLoader：

new ImageBuilder().setCatchType(ImageBuilder.CatchType.MemoryAndFile)
                .setImgErrorId(R.mipmap.ic_launcher)//图片加载失败时设置成的errImage
                .setImgSize(200, 200)//设置图片的宽高
                .setPath(getCachePath())//设置缓存路径
                .setThreadNum(3);//设置线程数量

//        mVideoImageLoader = VideoImageLoader.getDefault(this);//默认的VideoImageLoader
        mVideoImageLoader = new VideoImageLoader(this, getBuilder());
        mVideoImageLoader.setOnImgDownloadFinish(new VideoImageLoader.OnImgDownloadFinish() {
            @Override
            public void imgDownloadFinish(String url, Bitmap b) {
                //当第一次下载图片成功时调用，运行在主线程
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
            }

            @Override
            public void imgDownloadErr(String err) {
                //当第一次下载图片失败时调用，运行在主线程
            }
        });
2.设置图片：

				//初始化图片显示设置
				mOptions = new Options()//图片设置参数
                .setScaleType(ImageView.ScaleType.CENTER)//居中显示
                .setStyle(Options.BitmapStyle.NO_COLOR);//黑白照片效果


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
        }, mOptions);//设置图片显示属性		
		
		
		
		
		
		
		
		
		
		