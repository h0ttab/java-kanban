package service;

public class IdGenerator {
    private static int id;

    public static int generateUniqueId(){
        return id++;
    }
}