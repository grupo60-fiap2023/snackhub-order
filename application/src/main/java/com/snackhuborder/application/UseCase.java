package com.snackhuborder.application;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN inputObject);
}
