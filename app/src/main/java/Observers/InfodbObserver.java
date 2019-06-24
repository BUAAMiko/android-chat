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
        DataFunction.addChatInfoFrom((String) map.get("uname"), (String) map.get("msg"));
    }
}
