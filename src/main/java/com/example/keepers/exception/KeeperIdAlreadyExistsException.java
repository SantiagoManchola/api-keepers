package com.example.keepers.exception;

public class KeeperIdAlreadyExistsException extends RuntimeException {
    public KeeperIdAlreadyExistsException(String message) {
        super(message);
    }
    
    public KeeperIdAlreadyExistsException(Long id) {
        super("Keeper with id " + id + " already exists");
    }
}
