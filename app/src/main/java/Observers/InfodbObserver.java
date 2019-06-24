package Observers;

import com.example.dell.jsltevent.ObserverService.Observer.Observer;

import java.util.Map;

import Database.DataFunction;

public class InfodbObserver implements Observer {

    private static InfodbObserver infodbObserver;
    private InfodbObserver(){

    }

    public static InfodbObserver getInstance(){
        if (infodbObserver==null){
            infodbObserver=new InfodbObserver();
        }
        return infodbObserver;
    }
    @Override
    public void Updata(Map map) {
        String id = (String) map.get("uname");
//        id = id.substring(0,id.indexOf("@"));
        String msg=(String) map.get("msg");
        if (msg.length()>8&&msg.substring(0,8).equals("WITHDREW"))
        {
            DataFunction.deleteWithdraw(id,msg.substring(8));
        }
        else
        {
            DataFunction.addChatInfoFrom(id,(String) map.get("msg"));
        }
    }
}
