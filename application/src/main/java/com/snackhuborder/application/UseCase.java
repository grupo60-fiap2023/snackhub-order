package com.snackhuborder.application;

public abstract class UseCase<InputType, OutputType> {

    public abstract OutputType execute(InputType inputObject);
}
