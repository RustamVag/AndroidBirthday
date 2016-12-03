package com.dom.viewapp;

import java.util.Date;

/**
 * Created by Рустам on 03.10.2016.
 */
public class Birthday {

    private String name;

    private Integer day;

    private Integer month;


    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getDay()
    {
        return this.day;
    }

    public void setDate(Integer day)
    {
        this.day = day;
    }

    public Integer getMonth()
    {
        return this.month;
    }

    public void setMonth(Integer month)
    {
        this.month = month;
    }

    // Конструкторы
    public Birthday(String name, Integer day, Integer month)
    {
        this.name = name;
        this.day = day;
        this.month = month;
    }

    public Birthday()
    {
        this.name = "";
        this.day = 1;
        this.month = 0;
    }

}
