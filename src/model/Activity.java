package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Activity implements Serializable{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    String title;
    String description;
    LocalDateTime startLocalDateTime;
    LocalDateTime endLocalDateTime;
    String place;
    User organizer;
    int plazas;
    int plazasDisponibles;
    ArrayList<User> participants;

    public Activity(){

    }

    public Activity(String title, String description, LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime, String place, User organizer, int plazas) {
        this.title = title;
        this.description = description;
        this.startLocalDateTime = startLocalDateTime;
        this.endLocalDateTime = endLocalDateTime;
        this.place = place;
        this.organizer = organizer;
        this.plazas = plazas;
        this.plazasDisponibles = plazas;
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

    public int getParticipantByName(String name){
        int index = 0, pos = -1;
        Iterator<User> it = participants.iterator();
        while(it.hasNext() && pos != index){
            if(it.next().getName().equals(name)){
                pos = index; 
            } else{
                index++;
            }
        }
        return pos;
    }
    @Override
    public String toString() {
        return "Activity [title=" + title + ", description=" + description + ", startLocalDateTime="
                + startLocalDateTime + ", endLocalDateTime=" + endLocalDateTime + ", place=" + place + ", organizer="
                + organizer + ", plazas=" + plazas + "]";
    }

    
}
