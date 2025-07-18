package com.targetcar.agendador_tarefas.infrastructure.exceptions;

public class ResourceNotFoudException extends RuntimeException{
    public ResourceNotFoudException(String mensagem) {
        super(mensagem);
    }

    public ResourceNotFoudException(String mensagem,Throwable throwable) {
        super(mensagem, throwable);
    }
}
