package Observers;

import java.util.Map;

import Database.DataFunction;
import Event.Observer;

public class InfodbObserver implements Observer {
    @Override
    public void Updata(Map map) {
        DataFunction.addChatInfoFrom((String) map.get("uname"), (String) map.get("msg"));
    }
}
