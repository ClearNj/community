package life.majiang.community.community.mode;

import lombok.Data;

@Data
public class User {
    private int id;
    private String accountId;
    private String name;
    private String token;
    private String bio;
    private long gmtCreate;
    private long gmtModified;
    private String avatarUrl;
}
