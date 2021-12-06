package com.example.hello.data.source.local;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.example.hello.data.model.Comment;
import com.example.hello.data.model.Image;
import com.example.hello.data.model.Sender;
import com.example.hello.data.model.Tweet;
import com.example.hello.data.source.local.room.AppDatabase;
import com.example.hello.data.source.local.room.model.CommentEntity;
import com.example.hello.data.source.local.room.model.ImageEntity;
import com.example.hello.data.source.local.room.model.SenderEntity;
import com.example.hello.data.source.local.room.model.TweetEntity;
import com.example.hello.utils.NullChecker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class LocalStorageImpl implements LocalStorage {
    private static final String TAG = "[LocalStorage]";
    private final Context context;
    private final AppDatabase appDatabase;

    public LocalStorageImpl(Context context) {
        this.context = context;
        this.appDatabase = Room.databaseBuilder(context, AppDatabase.class, "hello-db").build();
    }

    @Override
    public List<Tweet> getTweetsFromAssets() {
        List<Tweet> tweets = new ArrayList<>();

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(context.getAssets().open("tweet_info.json"), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            String jsonString = stringBuilder.toString();

            Gson gson = new GsonBuilder().create();
            tweets = gson.fromJson(jsonString, new TypeToken<List<Tweet>>() {
            }.getType());
            return tweets.stream().filter(tweet -> tweet.getError() == null && tweet.getUnknownError() == null).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return tweets;
    }

    @Override
    public Single<Boolean> updateTweets(List<Tweet> tweets) {
        return Single.create(emitter -> {
            try {
                appDatabase.clearAllTables();
                appDatabase.runInTransaction(() -> tweets.forEach(tweet -> {
                    TweetEntity tweetEntity = toRoomTweet(tweet);
                    tweetEntity.senderId = insertRoomSender(tweet.getSender());
                    long tweetId = appDatabase.tweetDao().insert(tweetEntity).blockingGet();

                    if (tweet.getImages() != null) {
                        tweet.getImages().forEach(image -> {
                            ImageEntity imageEntity = toRoomImage(image, tweetId);
                            appDatabase.imageDao().insert(imageEntity).blockingGet();
                        });
                    }

                    if (tweet.getComments() != null) {
                        tweet.getComments().forEach(comment -> {
                            CommentEntity commentEntity = toRoomComment(comment, tweetId, insertRoomSender(comment.getSender()));
                            appDatabase.commentDao().insert(commentEntity).blockingGet();
                        });
                    }
                }));

            } catch (Throwable throwable) {
                Log.e(TAG, "<updateTweets>" + throwable.toString());
                emitter.onError(throwable);
            }
            emitter.onSuccess(true);
        });
    }

    @Override
    public Flowable<List<Tweet>> getTweets() {
        return appDatabase.tweetDao().getAll()
                .map(tweetEntities -> {
                    List<SenderEntity> senderEntities = appDatabase.senderDao().getAll().blockingFirst();
                    List<ImageEntity> imageEntities = appDatabase.imageDao().getAll().blockingFirst();
                    List<CommentEntity> commentEntities = appDatabase.commentDao().getAll().blockingFirst();

                    List<Tweet> tweets = new ArrayList<>();
                    for (TweetEntity tweetEntity : tweetEntities) {
                        Tweet tweet = toTweet(tweetEntity);
                        senderEntities.stream().filter(senderEntity1 -> senderEntity1.id == tweetEntity.senderId).map(this::toSender).findFirst().ifPresent(tweet::setSender);
                        tweet.setImages(imageEntities.stream().filter(imageEntity -> imageEntity.id == tweetEntity.id).map(imageEntity -> new Image(imageEntity.url)).collect(Collectors.toList()));
                        tweet.setComments(commentEntities.stream().filter(commentEntity -> commentEntity.tweetId == tweetEntity.id).map(commentEntity -> new Comment(commentEntity.content, senderEntities.stream().filter(senderEntity -> senderEntity.id == commentEntity.senderId).map(this::toSender).findFirst().orElse(null))).collect(Collectors.toList()));
                        tweets.add(tweet);
                    }

                    return tweets;
                });
    }


    private Tweet toTweet(TweetEntity tweetEntity) {
        Tweet tweet = new Tweet();
        tweet.setContent(tweetEntity.content);

        return tweet;
    }

    private Sender toSender(SenderEntity senderEntity) {
        Sender sender = new Sender();
        sender.setUserName(senderEntity.userName);
        sender.setNick(senderEntity.nick);
        sender.setAvatar(senderEntity.avatar);

        return sender;
    }

    private TweetEntity toRoomTweet(Tweet tweet) {
        TweetEntity tweetEntity = new TweetEntity();
        tweetEntity.id = 0;
        tweetEntity.content = tweet.getContent();

        return tweetEntity;
    }

    private long insertRoomSender(Sender sender) {
        SenderEntity senderEntity = toRoomSender(sender);
        return appDatabase.senderDao().insert(senderEntity).blockingGet();
    }

    private SenderEntity toRoomSender(Sender sender) {
        SenderEntity senderEntity = new SenderEntity();
        senderEntity.id = 0;
        senderEntity.userName = NullChecker.nullCheckString(sender.getUserName());
        senderEntity.nick = NullChecker.nullCheckString(sender.getNick());
        senderEntity.avatar = NullChecker.nullCheckString(sender.getAvatar());

        return senderEntity;
    }

    private ImageEntity toRoomImage(Image image, long tweetId) {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.id = 0;
        imageEntity.tweetId = tweetId;
        imageEntity.url = image.getUrl();

        return imageEntity;
    }

    private CommentEntity toRoomComment(Comment comment, long tweetId, long senderId) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.id = 0;
        commentEntity.tweetId = tweetId;
        commentEntity.senderId = senderId;
        commentEntity.content = comment.getContent();

        return commentEntity;
    }
}
