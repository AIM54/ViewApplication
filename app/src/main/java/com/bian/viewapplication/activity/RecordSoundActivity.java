package com.bian.viewapplication.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bian.viewapplication.R;

import java.util.concurrent.ExecutorService;

public class RecordSoundActivity extends AppCompatActivity implements View.OnClickListener {
    //线程池
    private ExecutorService mExecutorService;
    private ImageView recordMainIv;
    private static final int MIC_REQUEST_CODE = 110;
    private boolean isRecording;
    /**
     * 采样率，现在能够保证在所有设备上使用的采样率是44100Hz, 但是其他的采样率（22050, 16000, 11025）在一些设备上也可以使用。
     */
    public static final int SAMPLE_RATE_HERTZ = 44100;

    /**
     * 声道数。CHANNEL_IN_MONO and CHANNEL_IN_STEREO. 其中CHANNEL_IN_MONO是可以保证在所有设备能够使用的。
     */
    public static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_STEREO;

    /**
     * 返回的音频数据的格式。 ENCODING_PCM_8BIT, ENCODING_PCM_16BIT, and ENCODING_PCM_FLOAT.
     */
    public static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private int bufferSizeInBytes;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_sound);
        initView();
        initEvent();
    }

    private void initView() {
        // 获得缓冲区字节大小
        bufferSizeInBytes = AudioRecord.getMinBufferSize(SAMPLE_RATE_HERTZ,
                CHANNEL_CONFIG, AUDIO_FORMAT);
        recordMainIv = findViewById(R.id.record_sound_main_iv);
    }

    private void initEvent() {
        recordMainIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.record_sound_main_iv:
                requestForPermission();
                break;
        }
    }

    private void requestForPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MIC_REQUEST_CODE);
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {

        } else {
            beginSoundRecord();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MIC_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                beginSoundRecord();
            }
        }
    }

    /**
     * 开始录制音频的相关文件
     */
    private void beginSoundRecord() {
        isRecording = true;
        RecordSoundThread recordSoundThread = new RecordSoundThread();
        recordSoundThread.start();
    }

    public class RecordSoundThread extends Thread {
        AudioRecord mAudioRecord;
        int bufferSize = 10240;

        RecordSoundThread() {
            //获取音频缓冲最小的大小
            bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE_HERTZ, CHANNEL_CONFIG, AUDIO_FORMAT);
            // 参数1：音频源 * 参数2：采样率 主流是44100 * 参数3：声道设置 MONO单声道 STEREO立体声 * 参数4：编码格式和采样大小 编码格式为PCM,主流大小为16BIT * 参数5：采集数据需要的缓冲区大小
            mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE_HERTZ, CHANNEL_CONFIG, AUDIO_FORMAT, bufferSize);
        }

        @Override
        public void run() {
            super.run();
            while (isRecording && !interrupted()) {

            }
        }
    }
}
