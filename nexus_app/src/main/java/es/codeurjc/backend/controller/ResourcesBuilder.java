package es.codeurjc.backend.controller;

import io.vavr.control.Try;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.sql.Blob;
import java.util.Optional;

public class ResourcesBuilder {

    public static ResponseEntity.BodyBuilder getImgResponseHeader() {
        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_TYPE, "image/jpeg");
    }

    public static ResponseEntity<Resource> buildImgResponseOrNotFound(Resource img) {
        return Try.of(() -> getImgResponseHeader()
            .contentLength(img.getFile().length())
            .body(img)
        ).getOrElse(ResponseEntity.notFound().build());
    }

    public static Try<ResponseEntity<Resource>> tryBuildImgResponse(Optional<Blob> img) {
        return Try.of(() -> getImgResponseHeader()
            .contentLength(img.get().length())
            .body(new InputStreamResource(img.get().getBinaryStream()))
        );
    }

    
}
