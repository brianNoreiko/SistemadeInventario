package utils;


import com.example.demo.models.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import response.Response;

import java.util.List;

public class EntityResponse {

    public static <T> ResponseEntity<List<T>> listResponse(List<Product> page) {
        if (!page.getContent().isEmpty()) {
            return ResponseEntity.
                    status(HttpStatus.OK).
                    header("X-Total-Count", Long.toString(page.getTotalElements())).
                    header("X-Total-Pages", Long.toString(page.getTotalPages())).
                    body(page.getContent());
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(page.getContent());
        }
    }

    public static Response messageResponse(String message) {
        return Response.builder().message(message).build();
    }
}