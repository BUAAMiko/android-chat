package Database;

import org.litepal.crud.DataSupport;

import java.util.Date;

public class ChatHistory extends DataSupport {
    private int id;
    private String UserId;
    private String ContactId;
    private String content;
    private Date datetime;
    private int dirc;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setUserId(String userId) {
        this.UserId = userId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setContactId(String contactId) {
        this.ContactId = contactId;
    }

    public String getContactId() {
        return ContactId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDirc(int dirc) {
        this.dirc = dirc;
    }

    public int getDirc() {
        return dirc;
    }
}
