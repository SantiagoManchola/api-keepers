
package com.example.keepers.exception;

public class KeeperEmailAlreadyExistsException extends RuntimeException {
    public KeeperEmailAlreadyExistsException(String email) {
        super("Keeper with email " + email + " already exists");
    }
}
