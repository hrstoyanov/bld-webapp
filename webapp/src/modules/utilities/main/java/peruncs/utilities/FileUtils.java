package peruncs.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpRequest;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.net.http.HttpClient.newHttpClient;
import static java.net.http.HttpResponse.BodyHandlers.ofInputStream;
import static java.nio.file.Files.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@FunctionalInterface
public interface FileUtils<T> {

    static void download(URI url, FileUtils<InputStream> inputStreamConsumer) {
        try (var client = newHttpClient()) {
            var request = HttpRequest.newBuilder().uri(url).GET().build();
            var response = client.send(request, ofInputStream());
            try (var inputStream = response.body()) {
                inputStreamConsumer.accept(inputStream);
            }
        } catch (Exception e) {
            ExceptionUtils.rethrowUnchecked(e);
        }
    }

    static void download(URI url, Path outputFilePath) {
        downloadZip(url, inputStream -> {
            try {
                copy(inputStream, outputFilePath, REPLACE_EXISTING);
            } catch (IOException e) {
                ExceptionUtils.rethrowUnchecked(e);
            }
        });
    }

    static void downloadZip(URI url, FileUtils<ZipInputStream> zipConsumer) {
        download(url, inputStream -> {
            try (var zipInputStream = new ZipInputStream(inputStream)) {
                zipConsumer.accept(zipInputStream);
            } catch (IOException e) {
                ExceptionUtils.rethrowUnchecked(e);
            }
        });
    }

    static void downloadZip(URI url, Path outputFolderPath) {
        try {
            createDirectories(outputFolderPath);
        } catch (IOException e) {
            ExceptionUtils.rethrowUnchecked(e);
        }
        downloadZip(url, zipInputStream -> unzip(zipInputStream, outputFolderPath));
    }

    static void unzip(ZipInputStream zipInputStream, Path outputFolder) {
        try {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                var filePath = outputFolder.resolve(zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    createDirectories(filePath);
                } else {
                    createFile(filePath);
                    copy(zipInputStream, filePath, REPLACE_EXISTING);
                }
            }
        } catch (IOException e) {
            ExceptionUtils.rethrowUnchecked(e);
        }
    }

    void accept(T t) throws Exception;

}
