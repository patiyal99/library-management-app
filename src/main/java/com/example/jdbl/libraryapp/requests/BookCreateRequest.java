package com.example.jdbl.libraryapp.requests;

import com.example.jdbl.libraryapp.models.Genre;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookCreateRequest {

    //These are books parameters
    private String name;
    private Genre genre;
    private int cost;

    //Needed for author creation if required
    private String authorName;
    private String authorCountry;
    private int authorAge;
    private String authorEmail;
}
