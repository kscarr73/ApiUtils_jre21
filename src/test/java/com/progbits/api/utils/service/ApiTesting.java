package com.progbits.api.utils.service;

import com.progbits.api.utils.service.ApiInstance;
import com.progbits.api.utils.service.ApiService;

/**
 *
 * @author scarr
 */
public class ApiTesting implements ApiService {

    private static final ApiInstance<ApiTesting> instance = new ApiInstance<>();
    
    public static ApiTesting getInstance() {
        return instance.getInstance(ApiTesting.class);
    }
    
    @Override
    public void configure() {
        something = "else";
    }
    
    private String something = "initial";
    
    public String getSomething() {
        return something;
    }
}
