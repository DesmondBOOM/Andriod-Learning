package com.example.hello.viewmodel;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.collection.ArraySet;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hello.activity.HelloApp;
import com.example.hello.activity.RecyclerViewActivity;
import com.example.hello.data.model.Tweet;
import com.example.hello.data.model.User;
import com.example.hello.functors.Action;
import com.example.hello.utils.Dependency;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TweetsViewModel extends ViewModel {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Dependency dependency;

    public MutableLiveData<List<Tweet>> tweetListLiveData = new MutableLiveData<>();
    public MutableLiveData<User> userLiveData = new MutableLiveData<>();

    public void setDependency(Dependency dependency) {
        this.dependency = dependency;
    }
    
    public void setTweetData(@NonNull Action<Throwable> errorHandler) {
        Disposable disposable = dependency.getDataSource()
                .fetchTweets()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        tweets -> tweetListLiveData.postValue(tweets),
                        errorHandler::invoke
                );
        compositeDisposable.add(disposable);
    }

    public void setUserData(@NonNull Action<Throwable> errorHandler) {
        Disposable disposable = dependency.getDataSource()
                .fetchUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        user -> userLiveData.postValue(user),
                        errorHandler::invoke
                );
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
