package shisong;

import android.os.Environment;

//import buaa.jj.designpattern.factory.FileSystemFactory;
import communicate.XMPPSession;
import communicate.XMPPSessionFactory;
import communicate.XMPPSessionFactoryBuilder;
import communicate.configuration.ConstantConfig;

public class FactoryBuilder {

    private static FactoryBuilder factoryBuilder;
//    private FileSystemFactory fileSystemFactory;
    private XMPPSession session;

    private FactoryBuilder() {
        initSession();
    }

    public static FactoryBuilder getInstance(Boolean state) {
        if (factoryBuilder == null || state) {
            factoryBuilder = new FactoryBuilder();
        }
        return factoryBuilder;
    }

    public XMPPSession getSession() {
        return session;
    }
//    public FileSystemFactory getFileSystemFactory() {
//        return fileSystemFactory;
//    }

    public void initSession() {
        XMPPSessionFactory factory = new XMPPSessionFactoryBuilder().build();
        session = factory.openSession();
        ConstantConfig.FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/chat/";
    }

//    public void initFileSystem() {
//        fileSystemFactory = new FileSystemFactory();
//        fileSystemFactory.getFileSystem(true);
//    }
}
