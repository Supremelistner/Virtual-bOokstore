package com.seller.sellerservice.services;

import com.seller.sellerservice.DTOs.AutherDTO;
import com.seller.sellerservice.DTOs.ChapDto;
import com.seller.sellerservice.Database.Books;
import com.seller.sellerservice.Database.Chapters;
import com.seller.sellerservice.Database.Sellerinventory;
import com.seller.sellerservice.OpenFeign.Filedownload;
import com.seller.sellerservice.Repos.BooksRepository;
import com.seller.sellerservice.Repos.ChaptersRepository;
import com.seller.sellerservice.Repos.LocalUsersRepository;
import com.seller.sellerservice.Repos.SellerinventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChapterService {


    LocalUsersRepository localUsersRepository;
    SellerinventoryRepository sellerinventory;
    ChaptersRepository chapters;
    BooksRepository booksRepository;
    @Autowired
    Filedownload filedownload;

    public ChapterService(LocalUsersRepository localUsersRepository, SellerinventoryRepository sellerinventory, ChaptersRepository chapters, BooksRepository booksRepository, BookServices bookServices) {
        this.localUsersRepository = localUsersRepository;
        this.sellerinventory = sellerinventory;
        this.chapters = chapters;
        this.booksRepository = booksRepository;
        this.bookServices = bookServices;
    }

    BookServices bookServices;

   public  Map<Long,String> getChapterbybooks(long id){
        Books book=booksRepository.findById(id).get();
        Map<Long,String> booknames=new HashMap();
        List<Chapters> chaptersList=book.getChapters();
        for(Chapters c:chaptersList){
            booknames.put(c.getId(),c.getName());
        }
        return booknames;
    }

    public ChapDto getChapter(long id){
        Chapters chap=chapters.getById(id);
        ChapDto dto=new ChapDto();
        dto.setId(chap.getId());
        dto.setName(chap.getName());
        dto.setPrice(chap.getPrice());
        return dto;
    }

    public MultipartFile file(long id) throws MalformedURLException {
        ResponseEntity<Resource> resource=filedownload.dwchap(id);
        Resource res= resource.getBody();
        return ((MultipartFile) res);

    }
    public boolean updateChap(ChapDto dto){
        try {
            Chapters chap = chapters.getById(dto.getId());
            chap.setName(dto.getName());
            chap.setPrice(dto.getPrice());
            chapters.save(chap);
            return true;
        }
        catch(Exception ex){
            return false;
        }
    }

    public boolean setFile(long id,MultipartFile file){
        try {
            Chapters chap = chapters.getById(id);
            filedownload.uploadchap(chap.getId(), file);
            return true;
        }
        catch(Exception ex){
            return false;
        }
    }
   public long createChap(ChapDto autherDTO,long Bookid){
        Chapters chap=new Chapters();
        chap.setName(autherDTO.getName());
        chap.setPrice(autherDTO.getPrice());
        chap.setName(autherDTO.getName());
        Books books=booksRepository.findById(Bookid).get();
        chap.setBooks(books);
        chap.setSellerinventory(books.getSellerinventory());
        Chapters chapters1=chapters.save(chap);
        return chapters1.getId();

    }
    public String getSeller(long id){
        Chapters chap=chapters.getById(id);
        return chap.getSellerinventory().getLocalUsers().getUsername();
    }

  public  long getbook(long id){
        Chapters chap=chapters.getById(id);
        return chap.getBooks().getId();}

  public boolean DeleteChap(long chapId,long id){
       Sellerinventory sellerinventory1=localUsersRepository.findById(id).get().getSellerinventory();
       Chapters chapters1=chapters.findById(chapId).get();
       if(chapters1.getBooks().getSellerinventory()==sellerinventory1){
           filedownload.dchap(chapId);
           chapters.delete(chapters1);
           return true;
       }
       return false;
  }
}

