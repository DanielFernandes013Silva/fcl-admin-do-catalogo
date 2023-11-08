package com.fullcycle.admin.catalogo.application;

public abstract class UnityUseCase<IN> {
    public abstract void execute(IN anIn);
}
