package com.gmail.dreamleaguekit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CreateActivity extends AppCompatActivity{

    /*private static final int GALLERY = 1;
    private CreateCanvas create_canvas;

    private ConstraintLayout nCanvas_layout, nResult_layout;
    private HorizontalScrollView scrollview;

    private Button b1, b2, b3, b4, nSave, nImg;
    private ImageView nIv_promo;
    private Bitmap result;

    private PhotoEditorSDK photoEditorSDK;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        /*nCanvas_layout = findViewById(R.id.canvas_layout);
        nResult_layout = findViewById(R.id.result_layout);

        Toast.makeText(this, "scroll width = "+nCanvas_layout.getWidth()+
                        "\nscroll height = "+nCanvas_layout.getHeight(),
                Toast.LENGTH_SHORT).show();
        create_canvas = new CreateCanvas(this);
        nCanvas_layout.addView(create_canvas);

        b1 = findViewById(R.id.one_btn);
        b2 = findViewById(R.id.two_btn);
        b3 = findViewById(R.id.three_btn);
        b4 = findViewById(R.id.four_btn);
        nSave = findViewById(R.id.save);
        nImg = findViewById(R.id.img);
        nIv_promo = findViewById(R.id.iv_promo);




        nSave.setOnClickListener(view -> {
            //startSave();
            if (ActivityCompat.checkSelfPermission(CreateActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED)
            {
                //saveToMediaStore();
                //startSave();
                storeImage();
            }
            else {
                ActivityCompat.requestPermissions(CreateActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100);
            }

        });
        nImg.setOnClickListener(view -> {
            choosePhotoFromGallery();
        });*/
    }
    /*public void choosePhotoFromGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }
    public void onActivityResult(int requstCode, int resultCode, Intent data){
        super.onActivityResult(requstCode, resultCode, data);
        if(requstCode == RESULT_CANCELED){
            return;
        }
        if(requstCode == GALLERY)
        {
            if(data != null)
            {
                Uri contentURI = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);

                    //result = Bitmap.createBitmap(iv_width, iv_height, Bitmap.Config.ARGB_8888);

                    Canvas canvas = new Canvas(result);
                    CreateCanvas createCanvas = new CreateCanvas(this);
                    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));


                    //canvas.drawBitmap(bitmap,null,new RectF(0,0,iv_width,iv_height), null);
                    createCanvas.draw(canvas);


                    paint.setXfermode(null);

                    nIv_promo.setImageBitmap(result);
                    nCanvas_layout.addView(createCanvas);
                    createCanvas.invalidate();


                }catch (IOException e){
                    e.printStackTrace();
                    Toast.makeText(CreateActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                }

            }
        }
    }
    
    private void storeImage() {

        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("TAG",
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            //Bitmap image = viewToBitmap(nResult_layout, nResult_layout.getWidth(), nResult_layout.getHeight());
            //image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

        } catch (FileNotFoundException e) {
            Log.d("TAG", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("TAG", "Error accessing file: " + e.getMessage());
        }
    }
    *//*
    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Kits");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".png";

        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }
    public void resizeKit(String KIT_PATH) {
        //File dir=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File dir = getOutputMediaFile();
        Bitmap b= BitmapFactory.decodeFile(KIT_PATH);
        Bitmap out = Bitmap.createScaledBitmap(b, 512, 512, false);

        File file = new File(dir, "resize.png");
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(file);
            out.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            b.recycle();
            out.recycle();
        } catch (Exception e) {e.printStackTrace();}
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && (grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
            //startSave();
            //saveToMediaStore();
        }else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
        }
    }

    public void saveToMediaStore() {
        *//*
         * Save bitmap to MediaStore
         *//*

        //get bitmap from ImageVIew
        //not always valid, depends on your drawable
        //Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

        //Bitmap bitmap = viewToBitmap(nCanvas_layout, nCanvas_layout.getWidth(), nCanvas_layout.getHeight());

        *//*try {
            saveBitmap(this, bitmap, Bitmap.CompressFormat.PNG, "image/png", "displayName");
        } catch (IOException e) {
            e.printStackTrace();
        }*//*
        *//*try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmddhhmmss");
            String date = simpleDateFormat.format(new Date());
            String name = "Img"+date;
            saveImage(bitmap, name);
        } catch (IOException e) {
            e.printStackTrace();
        }*//*


        ContentResolver cr = getContentResolver();
        String title = "myBitmap";
        String description = "My bitmap created by Android-er";

        *//*String savedURL = MediaStore.Images.Media
                .insertImage(cr, bitmap, title, description);*//*


    }

    public void startSave(){
        FileOutputStream fileOutputStream = null;
        File file = getDisc();
        if (!file.exists() && !file.mkdir())
        {
            Toast.makeText(this, "can't make directory to save image", Toast.LENGTH_LONG).show();
            return;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = simpleDateFormat.format(new Date());
        String name = "Img"+date;
        String file_name = file.getAbsolutePath()+"/"+name;

        File new_file = new File(file_name);

        try {
            fileOutputStream = new FileOutputStream(new_file);
            //Bitmap bitmap = viewToBitmap(nCanvas_layout, nCanvas_layout.getWidth(), nCanvas_layout.getHeight());
            //bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            //Toast.makeText(this, "image saved successfully", Toast.LENGTH_LONG).show();
            fileOutputStream.flush();
            fileOutputStream.close();
        }catch (FileNotFoundException e)
        {
           e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        refreshGallery(new_file);
    }

    private void refreshGallery(File new_file) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(new_file));
        sendBroadcast(intent);
        Toast.makeText(this, "image saved successfully", Toast.LENGTH_LONG).show();
    }

    public File getDisc() {
        //File file = Environment.getExternalStoragePublicDirectory(Environment.getExternalStorageState());
        File file = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //File file = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsoluteFile();

        //String destPath = CreateActivity.this.getExternalFilesDir(null).getAbsolutePath();
        //File file = new File(destPath);
        return new File(file, "Image Demo");
        //return file;
    }

    *//*public Bitmap viewToBitmap(ConstraintLayout nCanvas_layout, int width, int height) {

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_app_temp_master_tmp);
        //Bitmap bitmap = Bitmap.createScaledBitmap(create_canvas.drawableToBitmap(drawable),width, height, true);
        //Bitmap bitmap = Bitmap.createScaledBitmap(canvas.scaledDrawableToBitmap(drawable),width, height, true);
        //Bitmap bitmap = Bitmap.createBitmap(canvas.drawableToBitmap(drawable));
        Canvas canvas = new Canvas(bitmap);
        nCanvas_layout.draw(canvas);
        //ImageResizer.reduceBitmapSize(bitmap, 1024);
        return bitmap;
    }*//*

    private void saveImage(Bitmap bitmap, @NonNull String name) throws IOException {
        OutputStream fos;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentResolver resolver = getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name + ".png");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
            contentValues.put(MediaStore.MediaColumns.SIZE, 512);
            Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));
            Toast.makeText(this, "GALLERY", Toast.LENGTH_SHORT).show();
        } else {
            String imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            File image = new File(imagesDir, name + ".png");
            fos = new FileOutputStream(image);
            //bitmap = viewToBitmap(nCanvas_layout, nCanvas_layout.getWidth(), nCanvas_layout.getHeight());
            Toast.makeText(this, "INTERNAL", Toast.LENGTH_SHORT).show();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        Objects.requireNonNull(fos).close();
    }*/
}