package com.files.buyerservice.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookListDto {
List<Bookdto> booksList;
int MaxSize;
}
