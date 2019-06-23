package Database;
import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.*;

public class DataFunction {

    private static String UserId;
    private static SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");

    /**
     * 收到来自某人的新消息，存入数据库
     * @param ContactId 来自的人
     * @param content 内容
     */
    public static void addChatInfoFrom(String ContactId, String content){
        ChatHistory chatHistory=new ChatHistory();
        chatHistory.setUserId(UserId);
        chatHistory.setContactId(ContactId);
        chatHistory.setContent(content);
        chatHistory.setDirc(1);
        chatHistory.setDatetime(new Date());
        chatHistory.save();
    }

    /**
     * 查询和某人聊天的全部消息(活动启动时查询)
     * @param ContactId 来自的人
     * @return 一个map列表，每个map中有三个键 content 消息内容  ；dir 等于0则为发出的消息 等于1则为收到的消息；time'时间
     */
    public static List allContactInfo(String ContactId){
        List<ChatHistory> chatHistories=DataSupport.where("UserId=? and ContactId=?", UserId, ContactId).find(ChatHistory.class);
        List<Map> result=new ArrayList<Map>();
        for (ChatHistory chatHistory: chatHistories){
            Map<String,String> res=new HashMap<String, String>();
            res.put("content",chatHistory.getContent());
            res.put("dir", Integer.toString(chatHistory.getDirc()));
            res.put("time", sdf.format(chatHistory.getDatetime()));
            result.add(res);
        }
        return result;
    }

    /**
     * 发送消息时插入本地数据库
     * @param ContactId 联系人
     * @param content 内容
     */
    public static void addSendInfo(String ContactId, String content){
        ChatHistory chatHistory=new ChatHistory();
        chatHistory.setUserId(UserId);
        chatHistory.setContactId(ContactId);
        chatHistory.setContent(content);
        chatHistory.setDirc(0);
        chatHistory.setDatetime(new Date());
        chatHistory.save();
    }

    /**
     * 查找所有联系人
     * @return  联系人集合
     */
    public static Set allcontacts(){
        List<ChatHistory> chatHistories=DataSupport.where("UserId=?",UserId).find(ChatHistory.class);
        Set<String> contacts=new HashSet<String>();
        for (ChatHistory chatHistory: chatHistories){
            contacts.add(chatHistory.getContactId());
        }
        return contacts;
    }

    /**
     * 查找和某人最后一条消息
     * @param ContactId 联系人
     * @return  最后一条消息
     */
    public static String lastInfo(String ContactId){
        List<ChatHistory> chatHistories=DataSupport.where("UserId=? and ContactId=?", UserId, ContactId).find(ChatHistory.class);
        String result=chatHistories.get(0).getContent();
        long time=chatHistories.get(0).getDatetime().getTime();
        for (ChatHistory chatHistory: chatHistories){
            if (chatHistory.getDatetime().getTime()>time){
                result=chatHistory.getContent();
            }
        }
        return result;
    }
}

