package com.lovesport.sportplay.bean;

import java.util.Date;

public class Introduction {
    private int kid;
    private String title;
    private String content;
    private String img;
    private Date created;

    //无参构造器
    public Introduction() {

    }

    public Introduction(int kid,String title,String content,String img,Date created) {
        this.kid = kid;
        this.title = title;
        this.content = content;
        this.img = img;
        this.created = created;
    }

    public int getKid(){    return kid;     }
    public String getTitle(){   return title;    }
    public String getContent(){   return content;    }
    public String getImg(){    return img;    }
    public Date getCreated(){    return created;    }


    public void setKid(int kid){
        this.kid = kid;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setContent(String content){
        this.content = content;
    }
    public void setImg(String img){
        this.img = img;
    }
    public void setCreated(Date created){
        this.created = created;
    }


    @Override
    public String toString() {
        return "Introduction{" +
                "kid=" + kid +
                ",title='" + title + '\'' +
                ",content='" + content + '\'' +
                ",img='" + img + '\'' +
                ",created=" + created + '\'' +
                '}';
    }
}
