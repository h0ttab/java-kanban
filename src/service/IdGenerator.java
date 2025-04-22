package service;

public class IdGenerator {
    private static int id = 1;

    public static int generateUniqueId(){
        return id++;
    }
}