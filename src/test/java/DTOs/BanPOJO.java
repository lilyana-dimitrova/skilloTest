package DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BanPOJO {
    @JsonProperty("isBanned")
    private boolean isBanned;
    private String description;

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public String getDescription() {
        return description;
    }
}
