package kr.gringrape.hamp.interfaces.exceptions;

public class AccessWithoutTokenException extends RuntimeException {

    public AccessWithoutTokenException() {
        super("unaurthorized accesss(without token)");
    }

}
