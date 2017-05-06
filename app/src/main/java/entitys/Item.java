package entitys;

import java.util.Date;

/**
 * Created by y.baidiuk on 06/05/2017.
 */
public class Item {
    private int slime;
    private Date date;
    private String text;
    private boolean video, photo, voice;

    public Item() {
    }

    public Item(int slime, Date date, String text, boolean video, boolean photo, boolean voice) {
        this.slime = slime;
        this.date = date;
        this.text = text;
        this.video = video;
        this.photo = photo;
        this.voice = voice;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSlime() {
        return slime;
    }

    public void setSlime(int slime) {
        this.slime = slime;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public boolean isPhoto() {
        return photo;
    }

    public void setPhoto(boolean photo) {
        this.photo = photo;
    }

    public boolean isVoice() {
        return voice;
    }

    public void setVoice(boolean voice) {
        this.voice = voice;
    }
}