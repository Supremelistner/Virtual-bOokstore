package com.seller.sellerservice.services;

import com.seller.sellerservice.DTOs.CredDto;
import com.seller.sellerservice.DTOs.OrderDto;
import com.seller.sellerservice.Database.*;
import com.seller.sellerservice.Repos.*;
import org.apache.catalina.CredentialHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Sellerservice {

    LocalUsersRepository localUsersRepository;
    SellerinventoryRepository sellerinventoryRepository;

    public Sellerservice(LocalUsersRepository localUsersRepository, SellerinventoryRepository sellerinventoryRepository, ImageDBRepository imageDBRepository, BooksRepository books, PublisherRepository publisherRepository, AuthorRepository authorRepository, CredentialsRepository credentialsRepository,
                         BooksRepository booksRepository,
                         ChaptersRepository chaptersRepository) {
        this.localUsersRepository = localUsersRepository;
        this.sellerinventoryRepository = sellerinventoryRepository;
        this.credentialsRepository = credentialsRepository;
        this.booksRepository = booksRepository;
        this.chaptersRepository = chaptersRepository;
    }

    @Autowired
    BookServices bookServices;
    @Autowired
    OrdersRepository ordersRepository;
    CredentialsRepository credentialsRepository;
    private final BooksRepository booksRepository;
    private final ChaptersRepository chaptersRepository;

    public long GetSellerId(long id){
      return  localUsersRepository.getById(id).getSellerinventory().getId();
    }

    public List<OrderDto> getOrderList(long id,int pageno){
        Pageable page = PageRequest.of(pageno, 10, Sort.by(Sort.Direction.ASC));
        List<Orders> order1= ordersRepository.findBySellerinventory_Id(id,page);
        List<OrderDto> order=new ArrayList<>();
        for(Orders e:order1){
            if(e.getCompleted()==true){
            OrderDto dto=new OrderDto();
            dto.setId(e.getId());
            dto.setBuyerName(e.getUserInventory().getLocalUsers().getUsername());
            dto.setDate(e.getDate());
            dto.setPrice(e.getPrice());
            dto.setChapterId(e.getChapters().getId());
            dto.setSellername(e.getSellerinventory().getLocalUsers().getUsername());
            order.add(dto);
        }else ordersRepository.delete(e);
        }
        return order;}

   public Boolean SetCredentials(long id, CredDto credDto){
        LocalUsers user=localUsersRepository.getById(id);
        Credentials cred=user.getCredentials();
        cred.setType(credDto.getType());
        cred.setDetails(credDto.getAbout());
        credentialsRepository.save(cred);
        return true;}

    public CredDto SeeCreds(long id){
        LocalUsers user=localUsersRepository.getById(id);
        Credentials cred=user.getCredentials();
        CredDto dto=new CredDto();
        dto.setId(cred.getId());
        dto.setType("RazorpayId");
        dto.setAbout(cred.getDetails());
        return dto;
    }


public Integer BuyerCount(long id){
    Sellerinventory sellerinventory=localUsersRepository.findById(id).get().getSellerinventory();
    return sellerinventory.getBuyers();
}
public long getsellerbyBook(long id){
        Books books=booksRepository.findById(id).get();
        return books.getSellerinventory().getId();
}

public long getsellerbychapter(long id){
        Chapters chapters=chaptersRepository.findById(id).get();
        return chapters.getSellerinventory().getLocalUsers().getId();
}



}
