package com.darkuros.selenium.listeners;

import com.darkuros.selenium.failurecapture.FailureCapture;
import com.darkuros.selenium.failurecapture.FailureContext;
import com.darkuros.selenium.failurecapture.FileSystemFailureCapture;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FailureCaptureListener implements ITestListener {

    private static final Path BASE_DIR = Path.of("failure-input");

    private final FailureCapture failureCapture =
            new FileSystemFailureCapture(BASE_DIR);

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("🔥 FailureCaptureListener invoked");
        System.out.println("📁 Working dir: " + System.getProperty("user.dir"));

        Throwable throwable = result.getThrowable();
        if (throwable == null) {
            System.out.println("Throwable is null");
            return; // nothing to capture
        }

        FailureContext context = buildFailureContext(result, throwable);

        try {
            failureCapture.capture(context);
            System.out.println("FailureCapture working dir: " + System.getProperty("user.dir"));
        } catch (IOException e) {
            // Fail loudly, but never swallow the original test failure
            System.err.println("❌ Failed to capture failure artifacts");
            e.printStackTrace();
        }
    }

    private FailureContext buildFailureContext(ITestResult result, Throwable t) {

        Map<String, String> environment = new HashMap<>();
        environment.put("os", System.getProperty("os.name"));
        environment.put("java_version", System.getProperty("java.version"));

        // Extend later: browser, grid, etc.

        return new FailureContext(
                generateRunId(),
                result.getMethod().getMethodName(),
                result.getTestClass().getName(),
                result.getTestContext().getSuite().getName(),
                t.getClass().getName(),
                t.getMessage(),
                getfullStackTrace(t),
                Instant.now().toEpochMilli(),
                environment
        );
    }

    private String generateRunId() {
        return Instant.now().toString().replace(":", "-")
                + "-" + UUID.randomUUID();
    }

    private String getfullStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }   
}
