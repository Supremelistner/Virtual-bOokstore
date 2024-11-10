package com.seller.sellerservice.DTOs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class BookListDto {
List<Bookdto> booksList;
int MaxSize;
}
