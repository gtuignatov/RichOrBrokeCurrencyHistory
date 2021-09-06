package com.beginwithsoftware.richorbrokecurrencyhistory.helperdto;

import com.beginwithsoftware.richorbrokecurrencyhistory.giphydto.Images;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;

@SpringBootTest
class DtoMethodsTest {

    @Autowired
    private DtoMethods dtoMethods;

    @Test
    void getRandomGiphyDtoObject_Test_Images_Check_getMethod_Name_Original() {
        Method giphyGetMethod = dtoMethods.findGetMethodName(Images.class, "original");
        Assertions.assertEquals("getOriginal", giphyGetMethod.getName());
    }
}