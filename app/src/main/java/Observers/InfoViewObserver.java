package Observers;

import android.os.Handler;
import android.os.Message;

import com.example.dell.jsltevent.ObserverService.Observer.Observer;

import java.util.Map;


public class InfoViewObserver implements Observer {

    private static InfoViewObserver infoViewObserver;
    private InfoViewObserver(){

    }

    public static InfoViewObserver getInstance(){
        if (infoViewObserver==null){
            infoViewObserver=new InfoViewObserver();
        }
        return infoViewObserver;
    }

    private boolean isChat=false;
    private boolean isList=false;
    private Handler handler;
    private String contactID;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }

    public void setIsChat(boolean ischat) {
        isChat = ischat;
    }

    public void setList(boolean list) {
        isList = list;
    }

    @Override
    public void Updata(Map map) {
        String contactname= ((String) map.get("uname"));
        contactname = contactname.substring(0,contactname.indexOf("@"));
        if (isChat && contactname.equals(contactID)){
            Message message=new Message();
            message.what=2;
            message.obj=map;
            handler.sendMessage(message);
        }
        if (isList){
            Message message=new Message();
            message.what=1;
            message.obj=map;
            handler.sendMessage(message);
        }
    }


}
