package com.example.hello.data.source.local.room.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sender")
public class SenderEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo(name = "user_name")
    public String userName;
    @ColumnInfo(name = "nick")
    public String nick;
    @ColumnInfo(name = "avatar")
    public String avatar;
}
