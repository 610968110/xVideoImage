# xVideoImage
xVideoImage<br><br>

一、需要权限：<br>
	uses-permission android:name="android.permission.INTERNET" <br>
    uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" <br><br><br>
二、引入库：\<br>
	compile 'com.lbx:xVideoImage:1.0.0'<br>
三、使用：\<br>\<br>

1、初始化VideoImageLoader：<br><br>

new ImageBuilder().setCatchType(ImageBuilder.CatchType.MemoryAndFile)<br>
                .setImgErrorId(R.mipmap.ic_launcher)//图片加载失败时设置成的errImage<br>
                .setImgSize(200, 200)//设置图片的宽高<br>
                .setPath(getCachePath())//设置缓存路径<br>
                .setThreadNum(3);//设置线程数量\<br>

//        mVideoImageLoader = VideoImageLoader.getDefault(this);//默认的VideoImageLoader\<br>
        mVideoImageLoader = new VideoImageLoader(this, getBuilder());\<br>
        mVideoImageLoader.setOnImgDownloadFinish(new VideoImageLoader.OnImgDownloadFinish() {\<br>
            @Override\<br>
            public void imgDownloadFinish(String url, Bitmap b) {\<br>
                //当第一次下载图片成功时调用，运行在主线程\<br>
                if (mAdapter != null)\<br>
                    mAdapter.notifyDataSetChanged();\<br>
            }\<br>\<br>

            @Override\<br>
            public void imgDownloadErr(String err) {\<br>
                //当第一次下载图片失败时调用，运行在主线程\<br>
            }\<br>
        });\<br>\<br>
2.设置图片：\<br>\<br>

				//初始化图片显示设置\<br>
				mOptions = new Options()//图片设置参数\<br>
                .setScaleType(ImageView.ScaleType.CENTER)//居中显示\<br>
                .setStyle(Options.BitmapStyle.NO_COLOR);//黑白照片效果\<br>


 //根据bitmap设置\<br>
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/bda303a6ef804f73bd306bbbb508653f";\<br>
//        mVideoImageLoader.displayBitmap(holder.imageView, BitmapFactory.decodeFile(path));\<br>\<br>\<br>\<br>



        //根据filepath设置\<br>
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/bda303a6ef804f73bd306bbbb508653f";\<br>
//        mVideoImageLoader.displayDisc(holder.imageView, path);\<br>\<br>\<br>



        //根据url设置\<br>
        mVideoImageLoader.displayUrl(holder.imageView, s, new VideoImageLoader.OnImgSetCallback() {\<br>
            @Override\<br>
            public void success(View view, Bitmap bitmap) {\<br>
                //设置成功回调\<br>
            }\<br>

            @Override\<br>
            public void errorLoad(View view) {\<br>
                //设置失败回调\<br>
            }\<br>

            @Override\<br>
            public void setErrorImgFinish(View view, Bitmap bitmap, int errId) {\<br>
                //设置成预先定义好的errImage的回调\<br>
            }\<br>
        }, mOptions);//设置图片显示属性		\<br>
		
		
		
		
		
		
		
		
		
		
