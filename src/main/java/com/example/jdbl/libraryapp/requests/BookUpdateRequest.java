package com.example.jdbl.libraryapp.requests;

import com.example.jdbl.libraryapp.models.Genre;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookUpdateRequest {
    private int id;
    private Genre genre;
    private String name;
    private Integer cost;

}
