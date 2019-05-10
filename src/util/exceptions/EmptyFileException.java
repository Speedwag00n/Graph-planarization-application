package util.exceptions;

import java.io.IOException;

public class EmptyFileException extends IOException {

    public EmptyFileException(){
        super();
    }

    public EmptyFileException(String message){
        super(message);
    }

}
