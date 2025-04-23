package service;

public class IdGenerator {
    private int id = 1;

    public int generateUniqueId(){
        return id++;
    }
}