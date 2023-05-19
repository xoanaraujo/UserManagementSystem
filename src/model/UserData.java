package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.scene.control.TextField;

public class UserData {

    private static ArrayList<User> users;
    private static User user;

    public static void init(){
        if (isDataFileCreated()){
            loadDataFile();
        } else {
            createDataFile();
        }
    }

    private static void createDataFile(){
        users = new ArrayList<>();
        users.add(new User("admin", "abc123", "admin@example.com"));
        saveDataFile();
        loadDataFile();
    }

    private static void loadDataFile(){
        try(FileInputStream file = new FileInputStream("users.dat");
            ObjectInputStream in = new ObjectInputStream(file)){
            users = (ArrayList<User>) in.readObject();
        } catch(Exception e1){
            e1.printStackTrace();
        }
    }

    public static void saveDataFile(){
        try ( FileOutputStream file = new FileOutputStream("users.dat");
            ObjectOutputStream out = new ObjectOutputStream(file)){
            out.writeObject(users);
        } catch (Exception e1){
            e1.printStackTrace();
        }
    }

    public static int getUserPositionByName(String name){
        int index = 0, pos = -1;
        boolean located = false;
        Iterator<User> it = users.iterator();
        while(it.hasNext() && !located){
            if(it.next().getName().equals(name)){
                pos = index;
                located = true;    
            } else{
                index++;
            }
        }
        return pos;
    }

    public static int getUserPositionByEmail(String email){
        int index = 0, pos = -1;
        boolean located = false;
        Iterator<User> it = users.iterator();
        while(it.hasNext() && !located){
            if(it.next().getEmail().equals(email)){
                pos = index;
                located = true;    
            } else{
                index++;
            }
        }
        return pos;
    }

    public static boolean isDataFileCreated(){
        boolean exists = true;
        try{
            FileInputStream in = new FileInputStream("users.dat");
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
        UserData.user = user;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }
}
