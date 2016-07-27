package fmuntenescu.reactiveburgers;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import rx.Subscriber;

public final class FileDataReader {

    public static void readFileByLine(@NonNull final String filename,
                                      @NonNull final Subscriber<? super String> lineSubscriber) {
        InputStream inputStream = FileDataReader.class.getResourceAsStream(filename);

        if (inputStream == null) {
            lineSubscriber.onError(new IllegalArgumentException(
                    "File not found on classpath: " + filename));
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,
                Charset.forName("UTF-8")));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                lineSubscriber.onNext(line);
            }
            reader.close();
            lineSubscriber.onCompleted();
        } catch (IOException ex) {
            lineSubscriber.onError(ex);
        }
    }
}
