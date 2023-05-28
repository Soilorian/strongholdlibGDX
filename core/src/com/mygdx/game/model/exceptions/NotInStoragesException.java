package org.example.model.exceptions;

public class NotInStoragesException extends Exception {
    public NotInStoragesException() {
        super("the resource given is not in any storage");
    }
}
