package saugat.splashscreen_slide.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.theartofdev.edmodo.cropper.CropImage;
import java.io.File;
import saugat.splashscreen_slide.BuildConfig;
import saugat.splashscreen_slide.R;
import saugat.splashscreen_slide.other.DBHelper;
import saugat.splashscreen_slide.other.Utility;

public class Register extends AppCompatActivity {

    private EditText uName, pwd, confirmpwd;
    private Button btn_register;
    DBHelper mydb;
    private boolean uploadImage = false;
    private ImageView img_upload;
    private String userChoosenTask;
    private int REQUEST_CAMERA, SELECT_FILE, CROP_FROM_CAMERA;
    private Uri mImageCaptureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        uploadImage = false;
        mydb = new DBHelper(this);

        uName = (EditText) findViewById(R.id.editText_uName2);
        pwd = (EditText) findViewById(R.id.editText_pwd2);
        confirmpwd = (EditText) findViewById(R.id.editText_confirmPwd);
        btn_register = (Button) findViewById(R.id.btn_register);
        img_upload = (ImageView) findViewById(R.id.upload_image);

        img_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uName.getText().toString().equals("") || pwd.getText().toString().equals("") || confirmpwd.getText().toString().equals("") || uploadImage == false) {
                    Toast.makeText(getApplicationContext(), "Fill all the fields!!", Toast.LENGTH_SHORT).show();
                } else {
                    if ((pwd.getText().toString()).equals((confirmpwd.getText().toString()))) {
                        mydb.registerNewUser(String.valueOf(uName.getText()), String.valueOf(pwd.getText()));
                        Toast.makeText(getApplicationContext(), "New User Added!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Password Do Not Match!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo")) {
                    }
                    else if (userChoosenTask.equals("Choose from Library")) {
                    }
                } else {
                    //code for deny
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Log.i("tag1", String.valueOf(resultCode));
            if (requestCode == SELECT_FILE && data!=null) {
                Log.i("tag1", String.valueOf(resultCode));
                mImageCaptureUri = data.getData();
                Log.i("uri", String.valueOf(mImageCaptureUri));
                doCrop();

            } else if (requestCode == REQUEST_CAMERA) {
                doCrop();
                Log.i("tag1", String.valueOf(resultCode));
            } else if (requestCode == CROP_FROM_CAMERA) {
                Bundle extras = data.getExtras();
                Log.i("loc", "m here bitches");
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    uploadImage = true;
                    img_upload.setImageBitmap(photo);
                }
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists())
                    f.delete();
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                uploadImage = true;
                img_upload.setImageURI(null);
                img_upload.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(getApplicationContext(), "There is an error. Please try again.", Toast.LENGTH_LONG).show();
            }
        }
        //super.onActivityResult(requestCode, resultCode, data);
    }

    public void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {
                boolean result = Utility.checkPermission(Register.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result) {
                        doCameraTask();
                    }
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result) {
                        galleryIntent();
                    }
                } else if (items[item].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

        /*public void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }*/

    public void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }




    public void doCameraTask() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Log.i("intent", String.valueOf(intent));
        /*Uri mImageCaptureUri = FileProvider.getUriForFile(
                PatientRegistrationActivity.this,
                PatientRegistrationActivity.this.getApplicationContext()
                        .getPackageName() + ".provider", new File(Environment.getExternalStorageDirectory(),
                        "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));*/
        mImageCaptureUri = FileProvider.getUriForFile(Register.this, BuildConfig.APPLICATION_ID + ".provider",new File(Environment.getExternalStorageDirectory(),
                "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
        Register.this.grantUriPermission("com.android.camera", mImageCaptureUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        try {
            intent.putExtra("return-data", true);
            startActivityForResult(intent, REQUEST_CAMERA);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void doCrop() {
        startCropImageActivity(mImageCaptureUri);
    }

    private void startCropImageActivity(Uri CapturedUri) {
        CropImage.activity(CapturedUri)
                .setAspectRatio(1, 1)
                .start(this);
    }

    /*@SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        img_upload.setImageBitmap(bm);
        uploadImage = true;
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        img_upload.setImageBitmap(thumbnail);
        uploadImage = true;
    }*/

}
