package Labyrinth.src.exceptions;

public class MoveNotAllowedException extends RuntimeException {
    public MoveNotAllowedException(String message){
        super(message);
    }
}
