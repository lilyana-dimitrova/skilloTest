package DTOs.APIDTOs;

/*
{
"caption": "bean",
"commentsCount": "0",
"coverUrl": "https://i.imgur.com/HUdmONP.jpg",
"createdAt": "2022-05-24T20:00:42.082Z",
"id": 4783,
"postStatus": "public"
}
 */
public class PostPOJO {
    private String caption;
    private int commentsCount;
    private String coverUrl;
    private String createdAt;
    private int id;
    private String postStatus;

    public PostPOJO(String caption, String coverUrl, String postStatus) {
        this.caption = caption;
        this.coverUrl = coverUrl;
        this.postStatus = postStatus;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(String postStatus) {
        this.postStatus = postStatus;
    }
}
