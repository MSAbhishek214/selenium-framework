package com.darkuros.selenium.failurecapture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSystemFailureCapture implements FailureCapture {

    private final Path baseDirectory;
    private final ObjectMapper objectMapper;

    public FileSystemFailureCapture(Path baseDirectory) {
        this.baseDirectory = baseDirectory;
        this.objectMapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public void capture(FailureContext context) throws IOException {

        Path runDir = baseDirectory.resolve(context.getRunId());
        Files.createDirectories(runDir);

        writeRawLog(runDir, context);
        writeMetadata(runDir, context);
    }

    // ---------------- raw.log ----------------
    // Canonical JVM output, first line always:
    // <ExceptionType>: <message>

    private void writeRawLog(Path runDir, FailureContext context) throws IOException {

        Files.writeString(
                runDir.resolve("raw.log"),
                context.getFullStackTrace(),
                StandardCharsets.UTF_8
        );
    }

    // ---------------- metadata.json ----------------
    // Structured, non-redundant, stable forever

    private void writeMetadata(Path runDir, FailureContext context) throws IOException {

        objectMapper.writeValue(
                runDir.resolve("metadata.json").toFile(),
                new MetadataView(context)
        );
    }

    /**
     * Internal projection class to keep metadata clean.
     * Prevents raw stack trace from leaking into metadata.json.
     */
    @SuppressWarnings("unused")
    private static final class MetadataView {

        public final String schemaVersion;
        public final String runId;
        public final Test test;
        public final Failure failure;
        public final long timestampUtc;
        public final Object environment;

        MetadataView(FailureContext ctx) {
            this.schemaVersion = ctx.getSchemaVersion();
            this.runId = ctx.getRunId();
            this.test = new Test(
                    ctx.getTestName(),
                    ctx.getTestClass(),
                    ctx.getSuiteName()
            );
            this.failure = new Failure(
                    ctx.getExceptionType(),
                    ctx.getExceptionMessage()
            );
            this.timestampUtc = ctx.getTimeStampUTC();
            this.environment = ctx.getEnvironment();
        }
    }

    @SuppressWarnings("unused")
    private static final class Test {
        public final String name;
        public final String className;
        public final String suite;

        Test(String name, String className, String suite) {
            this.name = name;
            this.className = className;
            this.suite = suite;
        }
    }

    @SuppressWarnings("unused")
    private static final class Failure {
        public final String exceptionType;
        public final String message;

        Failure(String exceptionType, String message) {
            this.exceptionType = exceptionType;
            this.message = message;
        }
    }
}
