package kr.gringrape.hamp.interfaces;

public class AccessWithoutTokenException extends RuntimeException {

    public AccessWithoutTokenException() {
        super("unaurthorized accesss(without token)");
    }

}
