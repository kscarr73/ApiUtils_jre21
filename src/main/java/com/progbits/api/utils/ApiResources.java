package com.progbits.api.utils;

import com.progbits.api.exception.ApiException;
import com.progbits.api.utils.service.ApiInstance;
import com.progbits.api.utils.service.ApiService;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ResourceList;
import io.github.classgraph.ScanResult;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author scarr
 */
public class ApiResources implements ApiService {

    private static final ApiInstance<ApiResources> instance = new ApiInstance();

    public static ApiResources getInstance() {
        return instance.getInstance(ApiResources.class);
    }

    @Override
    public void configure() {
        _scanResult = new ClassGraph().disableNestedJarScanning().scan();
    }

    private ScanResult _scanResult;

    public InputStream getResourceInputStream(String fileName) throws ApiException {
        ResourceList resources = _scanResult.getResourcesMatchingWildcard(fileName);

        try {
            if (!resources.isEmpty()) {
                return resources.get(0).open();
            } else {
                throw new ApiException(700, "File: " + fileName + " Not Found");
            }
        } catch (IOException iox) {
            throw new ApiException(701, "File: " + fileName + " Error: " + iox.getMessage());
        }
    }

    public String getResourceAsString(String fileName) throws ApiException {
        ResourceList resources = _scanResult.getResourcesMatchingWildcard(fileName);

        try {
            if (!resources.isEmpty()) {
                return resources.get(0).getContentAsString();
            } else {
                throw new ApiException(700, "File: " + fileName + " Not Found");
            }
        } catch (IOException iox) {
            throw new ApiException(701, "File: " + fileName + " Error: " + iox.getMessage());
        }
    }

    public ResourceList getResourcesWithWildcard(String wildcard) throws ApiException {
        return _scanResult.getResourcesMatchingWildcard(wildcard);
    }
}
