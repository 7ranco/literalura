package com.alurachallenge.literalura.services;

public interface IConvertData {
    <T> T getData(String json, Class<T> _class);
}
