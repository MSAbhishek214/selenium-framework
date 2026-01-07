package com.darkuros.selenium.failurecapture;

import java.util.Collections;
import java.util.Map;

public final class FailureContext {

    private final String schemaVersion = "1.0";

    private final String runId;

    // test identity
    private final String testName;
    private final String testClass;
    private final String suiteName;

    // failure identity
    private final String exceptionMessage;
    private final String exceptionType;

    // raw signal
    private final String fullStackTrace;

    private final long timeStampUTC;
    private final Map<String, String> environment;

    public FailureContext(
            String runId,
            String testName,
            String testClass,
            String suiteName,
            String exceptionType,
            String exceptionMessage,
            String fullStackTrace,
            long timeStampUTC,
            Map<String, String> environment) {
        this.runId = runId;
        this.testName = testName;
        this.testClass = testClass;
        this.suiteName = suiteName;
        this.exceptionType = exceptionType;
        this.exceptionMessage = exceptionMessage;
        this.fullStackTrace = fullStackTrace;
        this.timeStampUTC = timeStampUTC;
        this.environment = environment == null
                ? Collections.emptyMap()
                : Collections.unmodifiableMap(environment);
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public String getRunId() {
        return runId;
    }

    public String getTestName() {
        return testName;
    }

    public String getTestClass() {
        return testClass;
    }

    public String getSuiteName() {
        return suiteName;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public String getFullStackTrace() {
        return fullStackTrace;
    }

    public long getTimeStampUTC() {
        return timeStampUTC;
    }

    public Map<String, String> getEnvironment() {
        return environment;
    }

}
