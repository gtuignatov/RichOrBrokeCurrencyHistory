package com.beginwithsoftware.richorbrokecurrencyhistory.helperdto;

import com.beginwithsoftware.richorbrokecurrencyhistory.exchangedto.ExchangeResponse;
import com.beginwithsoftware.richorbrokecurrencyhistory.exchangedto.Rates;
import com.beginwithsoftware.richorbrokecurrencyhistory.giphydto.Datum;
import com.beginwithsoftware.richorbrokecurrencyhistory.giphydto.GiphyResponse;
import com.beginwithsoftware.richorbrokecurrencyhistory.giphydto.Images;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;

@Component
public class DtoMethods {

    Method findGetMethodName(Class className, String annotationValue) {
        Method getMethod = null;
        for(Method method : className.getMethods()) {
            if (method.isAnnotationPresent(JsonProperty.class)) {
                JsonProperty annotation = method.getAnnotation(JsonProperty.class);
                if (annotation.value().equals(annotationValue) && method.getName().startsWith("get")) {
                    getMethod = method;
                    break;
                }
            }
        }
        return getMethod;
    }

    public Double getCurrencyCourse(ExchangeResponse exchangeResponse, String currencyCode)
            throws InvocationTargetException, IllegalAccessException {
        Method currencyGetMethod = findGetMethodName(Rates.class, currencyCode);
        Rates rates = exchangeResponse.getRates();
        return (Double) invokeGetMethod(rates, currencyGetMethod);
    }

    public Object getRandomGiphyDtoObject(GiphyResponse giphyResponse, String giphyDtoJsonPropertyAnnotationValue)
            throws InvocationTargetException, IllegalAccessException {
        List<Datum> datumList = giphyResponse.getData();
        Random random = new Random();
        int randomInt = random.nextInt(datumList.size());
        System.out.println("Random Image Number = " + randomInt);
        Datum datum = datumList.get(randomInt);
        Images images = datum.getImages();
        Method giphyGetMethod = findGetMethodName(Images.class, giphyDtoJsonPropertyAnnotationValue);
        return invokeGetMethod(images, giphyGetMethod);
    }

    private Object invokeGetMethod(Object srcObject, Method getMethod)
            throws InvocationTargetException, IllegalAccessException {
        Object getMethodValue = null;
        try {
            getMethodValue = getMethod.invoke(srcObject);
        }
        catch (InvocationTargetException ite) {
            System.out.println("InvocationTargetException: " + ite);
        }
        catch (IllegalAccessException iae) {
            System.out.println("IllegalAccessException: " + iae);
        }
        return getMethodValue;
    }

}
