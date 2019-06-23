package Observers;

import java.util.Map;

import Database.DataFunction;
import Event.Observer;

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
