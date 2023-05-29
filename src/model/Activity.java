package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Activity {
    String title;
    String description;
    LocalDateTime startLocalDateTime;
    LocalDateTime endLocalDateTime;
    String place;
    User organizer;
    int plazas;
    ArrayList<User> participants;

    

    public Activity(String title, String description, LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime, String place, User organizer, int plazas) {
        this.title = title;
        this.description = description;
        this.startLocalDateTime = startLocalDateTime;
        this.endLocalDateTime = endLocalDateTime;
        this.place = place;
        this.organizer = organizer;
        this.plazas = plazas;
        this.participants = new ArrayList<>();
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public LocalDateTime getStartLocalDateTime() {
        return startLocalDateTime;
    }
    public LocalDateTime getEndLocalDateTime() {
        return endLocalDateTime;
    }
    public String getPlace() {
        return place;
    }
    public User getOrganizer() {
        return organizer;
    }
    public int getPlazas() {
        return plazas;
    }
    public ArrayList<User> getParticipants() {
        return participants;
    }

    
}
