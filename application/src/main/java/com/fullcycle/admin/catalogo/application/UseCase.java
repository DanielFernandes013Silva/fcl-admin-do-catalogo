package com.fullcycle.admin.catalogo.application;

public abstract class UseCase <IN, OUT> {
    public abstract OUT execute(final IN anIn);
}
