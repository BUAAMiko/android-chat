package Observers;

import com.example.dell.jsltevent.ObserverService.Observer.Observer;

import java.util.LinkedList;
import java.util.Map;

import buaa.jj.designpattern.filesystem.File;
import shisong.FactoryBuilder;

public class FileObserver implements Observer {

    private static FileObserver fileObserver;

    private FileObserver(){

    }

    public static FileObserver getInstance(){
        if (fileObserver==null){
            fileObserver=new FileObserver();
        }
        return fileObserver;
    }
    @Override
    public void Updata(Map map) {
        String filepath= (String) map.get("filepath");
        String contactID= (String) map.get("from");
        contactID = contactID.substring(0,contactID.indexOf("@"));
        LinkedList linkedList=new LinkedList();
        linkedList.add(contactID);
        FactoryBuilder.getInstance(false).getFileSystemFactory().getFileSystem(false).addFile(linkedList, File.getFile(filepath));
    }
}
