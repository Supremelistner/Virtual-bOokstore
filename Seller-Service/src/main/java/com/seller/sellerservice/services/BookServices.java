package com.seller.sellerservice.services;

import com.seller.sellerservice.DTOs.*;
import com.seller.sellerservice.Database.*;
import com.seller.sellerservice.OpenFeign.Filedownload;
import com.seller.sellerservice.Repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service

public class BookServices {
    @Autowired
    Filedownload filedownload;
    @Autowired
    LocalUsersRepository localUsersRepository;
    @Autowired
    SellerinventoryRepository sellerinventoryRepository;
    AuthorRepository authorRepository;

    @Autowired
    ReviewsRepository repository;

    public BookServices(AuthorRepository authorRepository, BooksRepository booksRepository, PublisherRepository publisherRepository,
                        ChaptersRepository chaptersRepository) {
        this.authorRepository = authorRepository;
        this.booksRepository = booksRepository;
        this.publisherRepository = publisherRepository;
        this.chaptersRepository = chaptersRepository;
    }

    BooksRepository booksRepository;
    PublisherRepository publisherRepository;
    private final ChaptersRepository chaptersRepository;






    public Bookdto saveBook(Bookdto dto,Sellerinventory  seller) {
        Books books = new Books();
        books.setBookname(dto.getName());
        books.setSynopsis(dto.getSynopsis());
        books.setLanguage_name(dto.getLanguage());
        books.setSellerinventory(seller);
        booksRepository.save(books);
        dto.setId(books.getId());
        return dto;
    }

    public BookListDto booksaffiliated(long User, int pageno) {
        Pageable page = PageRequest.of(pageno, 10, Sort.by(Sort.Direction.ASC));
        Optional<LocalUsers> users = localUsersRepository.findById(User);
        Optional<Sellerinventory> sellerinventory = sellerinventoryRepository.findByLocalUsers(users.get());
        if (sellerinventory != null) {
            Sellerinventory seller = sellerinventory.get();
            Page<Books> books = booksRepository.findBySellerinventory_Id(seller.getId(), page);
            int a = books.getTotalPages();
            BookListDto dto = new BookListDto();
            dto.setMaxSize(a);

            List<Bookdto> dtos = new ArrayList<>();
            for (Books i : books) {
                Bookdto b = new Bookdto();
                b.setId(i.getId());
                b.setLanguage(i.getLanguage_name());
                b.setName(i.getBookname());
                b.setSynopsis(i.getSynopsis());
                b.setImageID(i.getImageDB().getId());
                dtos.add(b);
            }
            dto.setBooksList(dtos);
            return dto;

        }

        return null;
    }

    public ReviewListdto Rating(long id, int pageno) {
        Pageable page = PageRequest.of(pageno, 5, Sort.by(Sort.Order.asc("rating")));
        Optional<Books> book = booksRepository.findById(id);
        if (book != null) {
            Books book1 = book.get();
            Page<Reviews> reviews = repository.findByBooks(book1, page);
            int a = reviews.getTotalPages();
            ReviewListdto reviewListdto = new ReviewListdto();
            List<ReviewDto> reviewDto = new ArrayList<>();
            for (Reviews r : reviews) {

                ReviewDto reviewDto1 = new ReviewDto();
                reviewDto1.setId(r.getId());
                reviewDto1.setRating(r.getRating());
                reviewDto1.setReview(r.getContent());
                reviewDto1.setUser(r.getUserInventory().getLocalUsers().getId());
                reviewDto.add(reviewDto1);
            }
            reviewListdto.setReview(reviewDto);
            reviewListdto.setMaxpages(a);
            return reviewListdto;
        }
        return null;
    }

    public Bookdto getBook(long id) {
        Optional<Books> book = booksRepository.findById(id);
        Bookdto ne = new Bookdto();
        ne.setId(book.get().getId());
        ne.setSynopsis(book.get().getSynopsis());
        ne.setLanguage(book.get().getLanguage_name());
        ne.setName(book.get().getBookname());
        return ne;
    }

    public List<AutherDTO> getAuthor(String Name) {

        List<Author> Auth = authorRepository.findByNameContainsAllIgnoreCase(Name);
        List<AutherDTO> nw = new ArrayList<>();
        for (Author e : Auth) {
            AutherDTO nq = new AutherDTO();
            nq.setName(e.getName());
            nq.setId(e.getId());
            nq.setLanguage(e.getNationality());
            nq.setAbout(e.getAbout());

            nw.add(nq);
        }
        return nw;
    }

    public  boolean createAuthor(AutherDTO autherDTO) {
        try {
            Author author1 = new Author();
            author1.setName(autherDTO.getName());
            author1.setNationality(autherDTO.getLanguage());
            author1.setAbout(autherDTO.getAbout());
            authorRepository.save(author1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public  AutherDTO auther(long id){
        Author au= authorRepository.findById(id).get();
        AutherDTO nq=new AutherDTO();
        nq.setName(au.getName());
        nq.setId(au.getId());
        nq.setLanguage(au.getNationality());
        nq.setAbout(au.getAbout());
        return nq;


    }

    public  BookListDto AuthorBooks(long author2, int pageno) {
        Pageable page = PageRequest.of(pageno,10, Sort.by(Sort.Direction.ASC));

        Optional<Author> sellerinventory = authorRepository.findById(author2);
        if (sellerinventory != null) {
            Author seller = sellerinventory.get();
            Page<Books> books = booksRepository.findByAuthor(seller, page);
            int a = books.getTotalPages();
            BookListDto dto = new BookListDto();
            dto.setMaxSize(a);

            List<Bookdto> dtos = new ArrayList<>();
            for (Books i : books) {
                Bookdto b = new Bookdto();
                b.setId(i.getId());
                b.setLanguage(i.getLanguage_name());
                b.setName(i.getBookname());
                b.setSynopsis(i.getSynopsis());
                b.setImageID(i.getImageDB().getId());
                dtos.add(b);
            }
            dto.setBooksList(dtos);
            return dto;
        }
        return null;
    }
    public  List<PublisherDTO> getpUBLISHER(String Name) {

        List<Publisher> Auth = publisherRepository.findByPubContains(Name);
        List<PublisherDTO> nw = new ArrayList<>();
        for (Publisher e : Auth) {
            PublisherDTO nq = new PublisherDTO();
            nq.setName(e.getPub());
            nq.setId(e.getId());
            nq.setEmail(e.getEmail());

            nw.add(nq);
        }
        return nw;
    }
    public  PublisherDTO publisher(long id){
        Publisher e=publisherRepository.getById(id);
        PublisherDTO nq = new PublisherDTO();
        nq.setName(e.getPub());
        nq.setId(e.getId());
        nq.setEmail(e.getEmail());
        return nq;

    }

    public  boolean createPublisher(PublisherDTO autherDTO) {
        try {
          Publisher author1 = new Publisher();
            author1.setPub(autherDTO.getName());
            author1.setEmail(autherDTO.getEmail());

            publisherRepository.save(author1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public  BookListDto PublisherBooks(long author2, int pageno) {
        Pageable page = PageRequest.of(pageno,10, Sort.by(Sort.Direction.ASC));

        Optional<Publisher> sellerinventory = publisherRepository.findById(author2);
        if (sellerinventory != null) {
            Publisher seller = sellerinventory.get();
            Page<Books> books = booksRepository.findByPublisher(seller, page);
            int a = books.getTotalPages();
            BookListDto dto = new BookListDto();
            dto.setMaxSize(a);

            List<Bookdto> dtos = new ArrayList<>();
            for (Books i : books) {
                Bookdto b = new Bookdto();
                b.setId(i.getId());
                b.setLanguage(i.getLanguage_name());
                b.setName(i.getBookname());
                b.setSynopsis(i.getSynopsis());
                b.setImageID(i.getImageDB().getId());
                dtos.add(b);
            }
            dto.setBooksList(dtos);
            return dto;
        }
        return null;
    }

    public  BookListDto booksearch(String title,int pageno){

        BookListDto dto=new BookListDto();
        Pageable page=PageRequest.of(pageno,10);
        Page<Books> booksList=booksRepository.findByBooknameContains(title,page);
        List<Bookdto> dtos = new ArrayList<>();
        for(Books i:booksList){
            Bookdto b = new Bookdto();
            b.setId(i.getId());
            b.setLanguage(i.getLanguage_name());
            b.setName(i.getBookname());
            b.setSynopsis(i.getSynopsis());
            b.setImageID(i.getImageDB().getId());
            dtos.add(b);
        }
        dto.setBooksList(dtos);
        dto.setMaxSize(booksList.getTotalPages());
        return dto;
    }

    public boolean setAuthor(long authid,long bookid){

        Optional<Author> a=authorRepository.findById(authid);
        Optional<Books> b= booksRepository.findById(bookid);
        if(a!=null&&b!=null){
            b.get().setAuthor(a.get());
            booksRepository.save(b.get());
            return true;}
        return false;

    }
    public boolean setPub(long pubid,long bookid){

        Optional<Publisher> a=publisherRepository.findById(pubid);
        Optional<Books> b= booksRepository.findById(bookid);
        if(a!=null&&b!=null){
            b.get().setPublisher(a.get());
            booksRepository.save(b.get());
            return true;}
        return false;

    }





    public Boolean SetImage(long BookId, MultipartFile file){
        return filedownload.uploadbook(BookId,file);
    }

    public Resource GetImage(long Bookid) throws MalformedURLException {
        return filedownload.dwbook(Bookid).getBody();
    }
    public Boolean DeleteImage(long Bookid){
        return filedownload.dbook(Bookid);
    }
    public void DeleteBook(long Book){
        DeleteImage(Book);
        chaptersRepository.deleteAll(booksRepository.findById(Book).get().getChapters());
        booksRepository.deleteById(Book);
    }

}






