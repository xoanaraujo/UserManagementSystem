package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import app.App;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import javafx.scene.control.TextField;

public class AppData {
    private static List<User> users;
    private static List<Activity> activities;
    private static User user;

    public static void init(){
        //if (isDataFileCreated("users.dat")){
        //    loadUsersDataFile();
        //} else {
        //    createUsersDataFile();
        //    loadUsersDataFile();
        //}
//
        //if (isDataFileCreated("activities.dat")){
        //    loadActivitiesDataFile();
        //} else {
        //    createActivitiesDataFile();
        //    loadActivitiesDataFile();
        //}

        loadUsersDataFileSQL();
        loadActivitiesDataFileSQL();
    }

    private static void createUsersDataFile(){
        users = new ArrayList<>();
        users.add(new User("admin", "admin@example.com", "abc123"));
        saveUsersDataFile();
    }

    private static void createActivitiesDataFile(){
        activities = new ArrayList<>();
        saveActivitiesDataFile();
    }

    private static void loadUsersDataFile(){
        try(FileInputStream file = new FileInputStream("users.dat");
            ObjectInputStream in = new ObjectInputStream(file)){
            users = (ArrayList<User>) in.readObject();
        } catch(Exception e1){
            e1.printStackTrace();
        }
    }

    public static void loadUsersDataFileSQL(){
        String sql = "SELECT U FROM User U ";
        TypedQuery<User> query = App.em.createQuery(sql, User.class);
        users = query.getResultList();
        
    }

    private static void loadActivitiesDataFile(){
        try(FileInputStream file = new FileInputStream("activities.dat");
            ObjectInputStream in = new ObjectInputStream(file)){
            activities = (ArrayList<Activity>) in.readObject();
        } catch(Exception e1){
            e1.printStackTrace();
        }
    }

    public static void loadActivitiesDataFileSQL(){
        String sql = "SELECT A FROM Activity A ";
        TypedQuery<Activity> query = App.em.createQuery(sql, Activity.class);
        activities = query.getResultList();  
    }

    public static void saveUsersDataFile(){
        try ( FileOutputStream file = new FileOutputStream("users.dat");
            ObjectOutputStream out = new ObjectOutputStream(file)){
            out.writeObject(users);
        } catch (Exception e1){
            e1.printStackTrace();
        }
    }

    public static void saveUsersDataFileSQL(User u){
        EntityTransaction tx = App.em.getTransaction();
        tx.begin();
        App.em.persist(u);
        tx.commit();
    }

    public static void saveActivitiesDataFile(){
        try ( FileOutputStream file = new FileOutputStream("activities.dat");
            ObjectOutputStream out = new ObjectOutputStream(file)){
            out.writeObject(activities);
        } catch (Exception e1){
            e1.printStackTrace();
        }
    }

    public static int getUserPositionByName(String name){
        int index = 0, pos = -1;
        Iterator<User> it = users.iterator();
        while(it.hasNext() && pos != index){
            if(it.next().getName().equals(name)){
                pos = index; 
            } else{
                index++;
            }
        }
        return pos;
    }

    public static int getActivityPositionByName(String title){
        int index = 0, pos = -1;
        Iterator<Activity> it = activities.iterator();
        while(it.hasNext() && pos != index){
            if(it.next().getTitle().equals(title)){
                pos = index; 
            } else{
                index++;
            }
        }
        return pos;
    }

    public static int getUserPositionByEmail(String email){
        int index = 0, pos = -1;
        Iterator<User> it = users.iterator();
        while(it.hasNext() && pos != index){
            if(it.next().getEmail().equals(email)){
                pos = index;  
            } else{
                index++;
            }
        }
        return pos;
    }

    public static boolean isDataFileCreated(String filename){
        boolean exists = true;
        try{
            FileInputStream in = new FileInputStream(filename);
            in.close();
        }catch (FileNotFoundException e1){
            exists = false;
        }catch (Exception e1){
            e1.printStackTrace();
        }
        return exists;
    }
    
    public static boolean isTextFieldAnEmail(TextField txtEmail){
        String email = txtEmail.getText();
        int length = email.length();

        boolean emailOk = false;
        int index = 0;

        while(!emailOk && index < length){
            if(email.charAt(index) == '@')
                emailOk = true;
            index++;
        }

        return emailOk;
    }
    
    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        AppData.user = user;
    }

    public static List<User> getUsers() {
        return users;
    }

    public static List<Activity> getActivities() {
        return activities;
    }
}
