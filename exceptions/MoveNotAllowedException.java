package Labyrinth.exceptions;

public class MoveNotAllowedException extends RuntimeException {
    public MoveNotAllowedException(String message){
        super(message);
    }
}
