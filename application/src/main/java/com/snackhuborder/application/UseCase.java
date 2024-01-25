package com.snackhuborder.application;

public abstract class UseCase<I, O> {

    public abstract O execute(I inputObject);
}
