package com.darkuros.selenium.failurecapture;

import java.io.IOException;

public interface FailureCapture {

    void capture(FailureContext context) throws IOException;
}
