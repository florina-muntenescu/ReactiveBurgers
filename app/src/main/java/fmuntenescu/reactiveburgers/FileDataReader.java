package fmuntenescu.reactiveburgers;

import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import rx.Observable;
import rx.Subscriber;

/**
 * Helper class. Allowing reading data from a file.
 */
public final class FileDataReader {

    /**
     * Read a file line by line. Every line from the file will be a new data emission.
     *
     * @param filename the file being read
     */
    public static Observable<String> readFileByLine(@NonNull final String filename) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> lineSubscriber) {
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
                    // no more lines to read, we can complete our subscriber.
                    lineSubscriber.onCompleted();
                } catch (IOException ex) {
                    lineSubscriber.onError(ex);
                }

            }
        });
    }
}
