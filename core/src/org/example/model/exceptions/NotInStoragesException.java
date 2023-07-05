package org.example.model.exceptions;

import java.io.Serializable;

public class NotInStoragesException extends Exception  implements Serializable {
    public NotInStoragesException() {
        super("the resource given is not in any storage");
    }
}
