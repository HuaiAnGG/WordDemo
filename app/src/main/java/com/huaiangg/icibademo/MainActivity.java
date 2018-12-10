package com.huaiangg.icibademo;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String[] TARGET = {"fy", "ja", "zh", "ko", "de", "es", "fr"};
    private static Boolean AM_RES_FLAG = false;
    private static Boolean EN_RES_FLAG = false;
    private volatile static String MP_EN_URL = "";
    private volatile static String MP_AM_URL = "";
    MediaPlayer mediaPlayer = null;

    @BindView(R.id.ph_am_txt)
    TextView phAmTxt;
    @BindView(R.id.ph_am_image)
    ImageView phAmImage;
    @BindView(R.id.ph_en_txt)
    TextView phEnTxt;
    @BindView(R.id.ph_en_image)
    ImageView phEnImage;
    @BindView(R.id.pronunciation_layout)
    LinearLayout pronunciationLayout;
    private String target = "fy";
    @BindView(R.id.ev_word)
    EditText evWord;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.translate_target)
    Spinner translateTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnItemSelected({R.id.translate_target})
    public void onItemSelected(int position) {
        target = TARGET[position];
        Log.d(TAG, "onItemSelected: 选择的是:" + target);
    }

    @OnClick({R.id.ph_am_image, R.id.ph_en_image, R.id.btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ph_am_image:
                if (!AM_RES_FLAG) {
                    Toast.makeText(MainActivity.this,
                            "no am resources", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDataSource(MP_AM_URL);
                    mediaPlayer.prepare(); // might take long! (for buffering, etc)
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ph_en_image:
                if (!EN_RES_FLAG) {
                    Toast.makeText(MainActivity.this,
                            "no en resources", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDataSource(MP_EN_URL);
                    mediaPlayer.prepare();                  // might take long! (for buffering, etc)
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_search:
                String word = evWord.getText().toString().trim();
                String target = "fy";
                if (word.length() == 0) {
                    Toast.makeText(MainActivity.this,
                            "type something please!", Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG, "onViewClicked: " + word);
                try {
                    request(target, word);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void request(String tar, String word) {

        //  创建 Retrofit 对象
        Retrofit retrofit = new Retrofit.Builder()
                // 设置 网络请求 Url
                .baseUrl("http://fy.iciba.com/")
                //设置使用Gson解析(记得加入依赖)
                .addConverterFactory(GsonConverterFactory.create())
                // 支持RxJava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        //  创建 网络请求接口 的实例
        IGetRequest request = retrofit.create(IGetRequest.class);

        // 采用Observable<...>形式 对 网络请求 进行封装
        Observable<TransalteWord> observable = request.getCall(tar, word);

        //  发送网络请求
        // 在IO线程进行网络请求
        observable.subscribeOn(Schedulers.io())
                // 回到主线程 处理请求结果
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TransalteWord>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: 初始化ing..." + d.toString());
                    }

                    @Override
                    public void onNext(TransalteWord transalteWord) {
                        Log.d(TAG, "onNext: " + transalteWord.toString());
                        //  对返回的数据进行处理
                        if (transalteWord.getStatus() != 0) {
                            tvResult.setText("抱歉,词库没有解析!");
                            return;
                        }

                        List mean = transalteWord.getContent().getWord_mean();
                        StringBuilder str = new StringBuilder();
                        //str.append("美 [").append(transalteWord.getContent().getPh_am()).append("]\t").append("英 [").append(transalteWord.getContent().getPh_am()).append("]\n");
                        phAmTxt.setText("美 [" + transalteWord.getContent().getPh_am() + "]");
                        phEnTxt.setText("英 [" + transalteWord.getContent().getPh_en() + "]");
                        MP_EN_URL = transalteWord.getContent().getPh_en_mp3();
                        MP_AM_URL = transalteWord.getContent().getPh_am_mp3();
                        Log.d(TAG, "onNext: \n" + MP_EN_URL + "\n" + MP_AM_URL);
                        AM_RES_FLAG = MP_AM_URL.length() > 0;
                        EN_RES_FLAG = MP_EN_URL.length() > 0;
                        for (Object aMean : mean) {
                            str.append(aMean.toString()).append("\n");
                        }
                        tvResult.setText(str.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this,
                                "type the excellent word", Toast.LENGTH_SHORT).show();
                        tvResult.setText(e.getMessage());
                        phAmTxt.setText("");
                        phEnTxt.setText("");
                        MP_AM_URL = "";
                        MP_EN_URL = "";
                        EN_RES_FLAG = false;
                        AM_RES_FLAG = false;
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: 完成");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}
