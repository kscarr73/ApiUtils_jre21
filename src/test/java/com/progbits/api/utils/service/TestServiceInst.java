package com.progbits.api.utils.service;

import org.testng.annotations.Test;

/**
 *
 * @author scarr
 */
public class TestServiceInst {
    @Test
    public void testClass() {
        ApiTesting test = ApiTesting.getInstance();
        
        assert "else".equals(test.getSomething());
        
        assert "else".equals(ApiTesting.getInstance().getSomething());
    }
}
