package com.example.shiping.module;



import com.example.shiping.module.monitor.Monitor;
import com.example.shiping.module.monitor.emevent.EMEvent;

import java.util.ArrayList;

/**
 * @author: qndroid
 * @function: 广告json value节点， 节点名字记得修改一下
 * @date: 16/6/13
 */
public class AdValue {

    public String resourceID;
    public String adid;
    public String resource;
    public String thumb;
  //  public ArrayList<Monitor> startMonitor;
  //  public ArrayList<Monitor> middleMonitor;
   // public ArrayList<Monitor> endMonitor;
    public String clickUrl;
   // public ArrayList<Monitor> clickMonitor;
   // public EMEvent event;
    public String type;

    public AdValue(String resourceID, String adid, String resource, String thumb, String clickUrl, String type) {
        this.resourceID = resourceID;
        this.adid = adid;
        this.resource = resource;
        this.thumb = thumb;
        this.clickUrl = clickUrl;
        this.type = type;
    }
}
