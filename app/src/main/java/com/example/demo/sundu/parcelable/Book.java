package com.example.demo.sundu.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */

public class Book implements Parcelable {
    private int id;
    private String name;

    public Book(int id,String name){
        this.id = id;
        this.name = name;
    }

    //setter & getter & constructor
    //...

    //下面是实现Parcelable接口的内容
    //除了要序列化特殊的文件描述符场景外，一般返回零就可以了
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            //自定义的私有构造函数，反序列化对应的成员变量值
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    //根据反序列化得到的各个属性，生成与之前对象内容相同的对象
    private Book(Parcel in) {
        //切记反序列化的属性的顺序必须和之前写入的顺序一致！！
        id = in.readInt();
        name = in.readString();
    }
}