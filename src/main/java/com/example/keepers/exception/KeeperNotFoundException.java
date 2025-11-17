package com.example.keepers.exception;

public class KeeperNotFoundException extends RuntimeException {
    public KeeperNotFoundException(String message) {
        super(message);
    }
    
    public KeeperNotFoundException(Long id) {
        super("Keeper with id " + id + " not found");
    }
}
