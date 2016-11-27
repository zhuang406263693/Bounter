package com.school.lenovo.bounter.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.school.lenovo.bounter.Fragment.SquareFragment;
import com.school.lenovo.bounter.R;
import com.school.lenovo.bounter.Util.ActivityController;
import com.school.lenovo.bounter.Util.HttpUtil;
import com.school.lenovo.bounter.Util.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by lenovo on 2016/11/22.
 */

public class ReleaseActivity extends AppCompatActivity {
    Calendar calendar;
    Toolbar toolbar;
    EditText title, address, content, start_day, start_time, end_day, end_time, label, reward;
    String title_text, address_text, content_text, start_text, end_text, label_text, reward_text, start_time_temp, start_day_temp, end_day_temp, end_time_temp, seek_text;
    String [] imageUrl = new String[5];
    String [] imageText = new String[5];
    private int imageCount = -1;

    TextView number;
    SeekBar seekBar;
    Button publish;
    //图片处理
    ImageView image1,image2,image3,image4;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static int output_X = 200;
    private static int output_Y = 200;

    private final int OK = 0;
    private final int FAIL = 1;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case OK:
                    Intent intent = new Intent(ReleaseActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case FAIL:
                    Toast.makeText(ReleaseActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        ActivityController.addActivity(this);
        calendar = Calendar.getInstance();
        toolbar = (Toolbar) findViewById(R.id.toolbar_release);
        title = (EditText) findViewById(R.id.activity_release_title);
        address = (EditText) findViewById(R.id.activity_release_address);
        content = (EditText) findViewById(R.id.activity_release_content);
        start_day = (EditText) findViewById(R.id.activity_release_start_day);
        start_time = (EditText) findViewById(R.id.activity_release_start_time);
        end_day = (EditText) findViewById(R.id.activity_release_end_day);
        end_time = (EditText) findViewById(R.id.activity_release_end_time);
        number = (TextView) findViewById(R.id.activity_release_seekbar_number);
        seekBar = (SeekBar) findViewById(R.id.activity_release_seekbar);
        label = (EditText) findViewById(R.id.activity_release_label);
        reward = (EditText) findViewById(R.id.activity_release_money);
        publish = (Button) findViewById(R.id.activity_release_publish);
        image1 = (ImageView) findViewById(R.id.activity_release_iv_1);
        image2 = (ImageView) findViewById(R.id.activity_release_iv_2);
        image3 = (ImageView) findViewById(R.id.activity_release_iv_3);
        image4 = (ImageView) findViewById(R.id.activity_release_iv_4);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReleaseActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        start_day.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new DatePickerDialog(ReleaseActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            start_day.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
                            start_day_temp = "" + year + monthOfYear + dayOfMonth;
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });
        start_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new TimePickerDialog(ReleaseActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            //settText的内容为string类型
                            start_time.setText("" + hourOfDay + ":" + minute);
                            start_time_temp = "" + hourOfDay + minute;
                        }
                    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
                }
            }
        });
        end_day.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new DatePickerDialog(ReleaseActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            end_day.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
                            end_day_temp = "" + monthOfYear + dayOfMonth;
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });
        end_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new TimePickerDialog(ReleaseActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            //settText的内容为string类型
                            end_time.setText("" + hourOfDay + ":" + minute);
                            end_time_temp = "" + hourOfDay + minute;
                        }
                    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
                }
            }
        });
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseImage();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                number.setText("当前人数 " + progress + "/100");
                seek_text = "" + progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginPost();
            }
        });
    }
    private void upLoadImg(final String path){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("Release","size is "+imageCount);
                imageText[++imageCount] = HttpUtil.getImageUrl(path);
            }
        }).start();
    }
    private void beginPost(){
        title_text = title.getText().toString();
        address_text = address.getText().toString();
        content_text = content.getText().toString();
        start_text = start_day_temp + start_time_temp;
        end_text = end_day_temp + end_time_temp;
        label_text = label.getText().toString();
        reward_text = reward.getText().toString();

        if (!title_text.equals("")&&!address_text.equals("")&&!content_text.equals("")&&!start_text.equals("")&&!end_text.equals("")&&!label_text.equals("")&&!reward_text.equals("")){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String result = HttpUtil.Release(Token.Token, title_text, address_text, content_text, start_text, end_text, seek_text, label_text, reward_text, imageText,imageCount);
                    if (result.equals("发布成功")) {
                        Log.d("info","发布成功");
                        Message message = new Message();
                        message.what = OK;
                        handler.sendMessage(message);
                    } else {
                        Message message = new Message();
                        message.what = FAIL;
                        handler.sendMessage(message);
                    }
                }
            }).start();
        }else{
            Toast.makeText(ReleaseActivity.this,"存在空值",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.release_action_menu, menu);
        return true;
    }

    private void choseImage(){
        Intent intentFromGallery = new Intent();
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_PICK);
        startActivityForResult(intentFromGallery,CODE_GALLERY_REQUEST);
    }

    //获取本地图片
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED){
            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }
        switch (requestCode){
            case CODE_GALLERY_REQUEST:
                //裁剪图片
                cropRawPhoto(data.getData());
                break;
            case CODE_RESULT_REQUEST:
                //图片设置
                if (data!=null){
                    if (image3.getVisibility() == View.GONE){
                        String path =setImageToHeadView(data,image3);
                        upLoadImg(path);
                        image3.setVisibility(View.VISIBLE);
                    }else if (image2.getVisibility() == View.GONE){
                        String path = setImageToHeadView(data,image2);
                        upLoadImg(path);
                        image2.setVisibility(View.VISIBLE);
                    }else if (image1.getVisibility() == View.GONE){
                        String path =setImageToHeadView(data,image1);
                        upLoadImg(path);
                        image1.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
    }

    //设置图片
    private String setImageToHeadView(Intent intent,ImageView imageView) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
//            Log.d("info",photo.toString());
            imageView.setImageBitmap(photo);
            File file = saveImage(photo);
            Log.d("Release",file.getPath());
            return file.getPath();
        }
        return null;
    }

    public static File saveImage(Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "Releasse");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    //裁剪
    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }
}
