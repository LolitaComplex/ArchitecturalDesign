package com.doing.dinavigator.entity;

public class Destination {
    public String pageUrl;  //页面url
    public int id;          //路由节点（页面）的id
    public boolean isStarter;//是否作为路由的第一个启动页
    public String pageType;//路由节点(页面)的类型,activity,dialog,fragment
    public String className;//全类名
}
