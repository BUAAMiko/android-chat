package Observers;

import java.util.Map;

import Event.Observer;

public class InfoViewObserver implements Observer {

    private static boolean isChat;

    public static void setIsChat(boolean isChat) {
        InfoViewObserver.isChat = isChat;
    }

    @Override
    public void Updata(Map map) {
        if (InfoViewObserver.isChat){

        }
    }


}
