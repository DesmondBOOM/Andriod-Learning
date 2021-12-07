package com.example.hello.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hello.R;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxjavaActivity extends AppCompatActivity {

    Button button_rxjava;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rxjava_layout);
        initUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void initUI() {
        button_rxjava = findViewById(R.id.btn_rxjava);
        button_rxjava.setOnClickListener(v -> showNumberString());
    }

    private void showNumberString() {
        button_rxjava.setEnabled(false);
        button_rxjava.setClickable(false);

        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            for (int index = 0; index < 10; ++index) {
                emitter.onNext(index);
                SystemClock.sleep(1000);
            }
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        String buttonText = (String) button_rxjava.getText();
                        button_rxjava.setText(buttonText + String.valueOf(integer));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(RxjavaActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        button_rxjava.setClickable(true);
                        button_rxjava.setEnabled(true);
                    }
                });
    }
}