package group1.itschool.onlinebookstore.models.dto;

import lombok.Data;

@Data
public class BookDTO {

    private String title;
    private String author;
    private String publisher;
    private int publicationDate;
    private int codeISBN;
    private String genre;
    private String synopsis;
    private String coverDesign;
    private int pageCount;
    private String language;
    private String format;
    private double price;
    private String review;
    private String QRCode;


}
