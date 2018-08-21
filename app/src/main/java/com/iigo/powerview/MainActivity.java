package com.iigo.powerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.iigo.library.PowerView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private PowerView powerView1;
    private PowerView powerView2;
    private TextView textView1;
    private TextView textView2;
    private Disposable updateProgressDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        powerView1 = findViewById(R.id.pv1);
        powerView2 = findViewById(R.id.pv2);

        textView1  = findViewById(R.id.tv1);
        textView2  = findViewById(R.id.tv2);

        updateProgress();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (updateProgressDisposable != null){
            updateProgressDisposable.dispose();
        }
    }

    private void updateProgress(){
        updateProgressDisposable = Observable.interval(0, 100, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        int progress = (powerView1.getProgress() + 1);

                        if (progress <= 100) {
                            powerView1.setProgress(progress);
                            textView1.setText("目前电量" + progress + "%");
                        }

                        int progress2 = (powerView2.getProgress() + 1);

                        if (progress2 <= 100) {
                            powerView2.setProgress(progress2);
                            textView2.setText("目前电量" + progress2 + "%");
                        }
                    }
                });
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        powerView1.setBgColor(Color.BLACK);
//        powerView1.setPowerColor(Color.RED);
//
//        ViewGroup.LayoutParams layoutParams = powerView2.getLayoutParams();
//        layoutParams.width = 100;
//        layoutParams.height = 20;
//
//        powerView2.setLayoutParams(layoutParams);
//
//        return super.onKeyDown(keyCode, event);
//    }
}
