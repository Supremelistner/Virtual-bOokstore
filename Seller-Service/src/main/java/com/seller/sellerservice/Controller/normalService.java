package com.seller.sellerservice.Controller;

import com.seller.sellerservice.DTOs.*;
import com.seller.sellerservice.Database.LocalUsers;
import com.seller.sellerservice.Database.Sellerinventory;
import com.seller.sellerservice.OpenFeign.Jwtservicing;
import com.seller.sellerservice.Repos.BooksRepository;
import com.seller.sellerservice.Repos.ChaptersRepository;
import com.seller.sellerservice.Repos.LocalUsersRepository;
import com.seller.sellerservice.Repos.SellerinventoryRepository;
import com.seller.sellerservice.services.BookServices;
import com.seller.sellerservice.services.ChapterService;
import com.seller.sellerservice.services.Sellerservice;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home")
public class normalService {


    Sellerservice sellerservice;
    BookServices bookServices;
    ChapterService chapterService;
    private final LocalUsersRepository localUsersRepository;
    private final BooksRepository booksRepository;
    private final ChaptersRepository chaptersRepository;
    private final SellerinventoryRepository sellerinventoryRepository;

    public normalService(Jwtservicing jwtservicing, Sellerservice sellerservice, BookServices bookServices, ChapterService chapterService,
                         LocalUsersRepository localUsersRepository,
                         BooksRepository booksRepository,
                         ChaptersRepository chaptersRepository,
                         SellerinventoryRepository sellerinventoryRepository) {
        this.sellerservice = sellerservice;
        this.bookServices = bookServices;
        this.chapterService = chapterService;
        this.localUsersRepository = localUsersRepository;
        this.booksRepository = booksRepository;
        this.chaptersRepository = chaptersRepository;
        this.sellerinventoryRepository = sellerinventoryRepository;
    }

    @GetMapping("/sellerid")
    ResponseEntity<Long> Sellerid(@AuthenticationPrincipal LocalUsers user){
        try{
            Sellerinventory sellerinventory=new Sellerinventory();

            LocalUsers users=localUsersRepository.findById(user.getId()).get();
            users.setSellerinventory(sellerinventory);
            sellerinventoryRepository.save(sellerinventory);
            localUsersRepository.save(users);

            long id=localUsersRepository.findById(user.getId()).get().getSellerinventory().getId();

            return new ResponseEntity<>(id,HttpStatus.ACCEPTED);
        }catch(Exception ex){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }@GetMapping("/seecreds")

    ResponseEntity<CredDto> Seecreds(@AuthenticationPrincipal LocalUsers user){
        try{

            CredDto creds=sellerservice.SeeCreds(user.getId());
            return new ResponseEntity<>(creds,HttpStatus.ACCEPTED);
        }catch(Exception ex){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }@PutMapping("/setcreds")

    ResponseEntity<Boolean> SetCreds(@AuthenticationPrincipal LocalUsers user,@RequestParam CredDto cred){
        try{
        long id=user.getSellerinventory().getId();
        Boolean creds=sellerservice.SetCredentials(id,cred);
        return new ResponseEntity<>(creds,HttpStatus.ACCEPTED);
    }catch(Exception ex){
        return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
    }

    }
    @GetMapping("/orderlist")

    ResponseEntity<List<OrderDto>> orderList(@AuthenticationPrincipal LocalUsers user,@RequestParam int pageno){
        try{
            long id=user.getSellerinventory().getId();
            List<OrderDto> creds=sellerservice.getOrderList(id,pageno);
            sellerservice.BuyerCount(id);
            return new ResponseEntity<>(creds,HttpStatus.ACCEPTED);
        }catch(Exception ex){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }

    }@GetMapping("/buyercount")

    ResponseEntity<Integer> BuyerCount(@AuthenticationPrincipal LocalUsers user){
        try{
            long id=user.getId();
            int creds= sellerservice.BuyerCount(id);
            return new ResponseEntity<>(creds,HttpStatus.ACCEPTED);
        }catch(Exception ex){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getsellerbybook")

    ResponseEntity<Long> getSellerBybook(@RequestParam long Bookid){ try{
        long creds= localUsersRepository.findBySellerinventory_Id(sellerservice.getsellerbyBook(Bookid)).getId();
        return new ResponseEntity<>(creds,HttpStatus.ACCEPTED);
    }catch(Exception ex){
        return new ResponseEntity<>( HttpStatus.BAD_REQUEST);}
    }
    @GetMapping("/getbychap")

    ResponseEntity<Long> getByChap(@RequestParam long Chapid){
        try{
            long creds= sellerservice.getsellerbychapter(Chapid);
            return new ResponseEntity<>(creds,HttpStatus.ACCEPTED);
        }catch(Exception ex){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);}
    }@PostMapping("/savebook")

    ResponseEntity<Bookdto> saveBook(@RequestParam Bookdto book,@AuthenticationPrincipal LocalUsers users,@RequestParam long Authorid,@RequestParam long Pubid){  try{
       Bookdto creds= bookServices.saveBook(book,users.getSellerinventory());
       if(bookServices.setAuthor(Authorid, book.getId())&&bookServices.setPub(Pubid, book.getId()))
       return new ResponseEntity<>(creds,HttpStatus.ACCEPTED);
   }catch(Exception ex){
       return new ResponseEntity<>( HttpStatus.BAD_REQUEST);}
        return new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED);
   }
    @GetMapping("/booksaffiliated")

   ResponseEntity<BookListDto> booksaffiliated(@AuthenticationPrincipal LocalUsers User, @RequestParam int pageno){
        try{
       BookListDto creds= bookServices.booksaffiliated(User.getId(),pageno);
       return new ResponseEntity<>(creds,HttpStatus.ACCEPTED);
   }catch(Exception ex){
       return new ResponseEntity<>( HttpStatus.BAD_REQUEST);}
   }
    @GetMapping("/booksrating")

    ResponseEntity<ReviewListdto> Ratingofbook(@RequestParam long Bookid, @RequestParam int pageno){
        try{
            ReviewListdto creds= bookServices.Rating(Bookid,pageno);
            return new ResponseEntity<>(creds,HttpStatus.ACCEPTED);
        }catch(Exception ex){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);}

    }
    @GetMapping("/getbookbyid")

    ResponseEntity<Bookdto> getBookbyid(long Bookid){
        try{
            Bookdto creds= bookServices.getBook(Bookid);
            return new ResponseEntity<>(creds,HttpStatus.ACCEPTED);
        }catch(Exception ex){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);}

    }


    @GetMapping("/getauthor")

    ResponseEntity<List<AutherDTO>> getAuthor(@RequestParam String name){
        try{
            List creds= bookServices.getAuthor(name);
            return new ResponseEntity<>(creds,HttpStatus.ACCEPTED);
        }catch(Exception ex){ return new ResponseEntity<>( HttpStatus.BAD_REQUEST);


    }
        }
    @PostMapping("/createauthor")
    ResponseEntity<Boolean> createAuthor(@RequestParam AutherDTO author){
        try{
            boolean creds= bookServices.createAuthor(author);
            return new ResponseEntity<>(creds,HttpStatus.ACCEPTED);
        }catch(Exception ex){ return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }}
    @GetMapping("/author")
    ResponseEntity<AutherDTO> auther(@RequestParam long Authorid){
        try{
        AutherDTO creds= bookServices.auther(Authorid);
        return new ResponseEntity<>(creds,HttpStatus.ACCEPTED);
    }catch(Exception ex){ return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
    }

    }@GetMapping("/authorbooks")

    ResponseEntity<BookListDto> AuthorBooks(@RequestParam long Authorid, @RequestParam int pageno){
        try{
        BookListDto creds= bookServices.AuthorBooks(Authorid,pageno);
        return new ResponseEntity<>(creds,HttpStatus.ACCEPTED);
    }catch(Exception ex){
        return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
    }

}


    @GetMapping("/getpublisher")

ResponseEntity<List<PublisherDTO>> getpUBLISHER(@RequestParam String name){
    try{
        List creds= bookServices.getpUBLISHER(name);
        return new ResponseEntity<>(creds,HttpStatus.ACCEPTED);
    }catch(Exception ex){ return new ResponseEntity<>( HttpStatus.BAD_REQUEST);


    }

}

    @GetMapping("/publisher")

ResponseEntity<PublisherDTO> publisher(@RequestParam long Pubid){  try{
    PublisherDTO creds= bookServices.publisher(Pubid);
    return new ResponseEntity<>(creds,HttpStatus.ACCEPTED);
}catch(Exception ex){ return new ResponseEntity<>( HttpStatus.BAD_REQUEST);


}}
    @PostMapping("/createpublisher")

    ResponseEntity<Boolean> createPublisher(@RequestParam PublisherDTO Pub){try{
        Boolean creds= bookServices.createPublisher(Pub);
        return new ResponseEntity<>(creds,HttpStatus.ACCEPTED);
    }catch(Exception ex){ return new ResponseEntity<>( HttpStatus.BAD_REQUEST);

    }

    }
    @GetMapping("/publisherbooks")

    ResponseEntity<BookListDto> PublisherBooks(@RequestParam long Pubid,@RequestParam int pageno){
        try{
            BookListDto creds= bookServices.PublisherBooks(Pubid,pageno);
            return new ResponseEntity<>(creds,HttpStatus.ACCEPTED);
        }catch(Exception ex){ return new ResponseEntity<>( HttpStatus.BAD_REQUEST);

        }

    }
    @GetMapping("/booksearch")
    ResponseEntity<BookListDto> booksearch(@RequestParam String name,@RequestParam int pageno){
        try{
            BookListDto creds= bookServices.booksearch(name,pageno);
            return new ResponseEntity<>(creds,HttpStatus.ACCEPTED);
        }catch(Exception ex){ return new ResponseEntity<>( HttpStatus.BAD_REQUEST);

        }

    }
    @DeleteMapping("/deletebook")
    ResponseEntity<Boolean> DeleteBook(@RequestParam long Bookid,@AuthenticationPrincipal LocalUsers user){
        try{
            if(user.getSellerinventory().getId()==booksRepository.getById(Bookid).getSellerinventory().getId()){
            bookServices.DeleteBook(Bookid);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);}
        }catch(Exception ex){ return new ResponseEntity<>( HttpStatus.BAD_REQUEST);

        }return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

    @PostMapping("/setimage")
    ResponseEntity<Boolean> SetImage(@RequestParam long Bookid, @AuthenticationPrincipal LocalUsers user, @RequestParam MultipartFile file){
        try{
            if(user.getSellerinventory().getId()==booksRepository.getById(Bookid).getSellerinventory().getId()){
                bookServices.SetImage(Bookid,file);
                return new ResponseEntity<>(HttpStatus.ACCEPTED);}
        }catch(Exception ex){ return new ResponseEntity<>( HttpStatus.BAD_REQUEST);

        }return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

@GetMapping("/getimage")
    ResponseEntity<Resource> GetImage(@RequestParam long Bookid){

        try{

                Resource resource=bookServices.GetImage(Bookid);
                return new ResponseEntity<>(resource,HttpStatus.ACCEPTED);
        }catch(Exception ex){ return new ResponseEntity<>( HttpStatus.BAD_REQUEST);

        }
    }

@GetMapping("/deleteimage")
ResponseEntity<Boolean> DeleteImage(@RequestParam long Bookid, @AuthenticationPrincipal LocalUsers user) {
    try {
        if (user.getSellerinventory().getId() == booksRepository.getById(Bookid).getSellerinventory().getId()) {
            bookServices.DeleteImage(Bookid);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    } catch (Exception ex) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
}


@GetMapping("/getchapbybook")
ResponseEntity<Map<?,?>> getBybook(@RequestParam long Bookid){

    try { Map m=chapterService.getChapterbybooks(Bookid);
        return new ResponseEntity<>(m,HttpStatus.ACCEPTED);
    }catch (Exception ex) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}

@GetMapping("/getchapbyid")
ResponseEntity<?> getChapbyid(@RequestParam long Chapid){
    try { ChapDto m=chapterService.getChapter(Chapid);
        return new ResponseEntity<>(m,HttpStatus.ACCEPTED);
    }catch (Exception ex) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}

@GetMapping("/getbookbychap")
ResponseEntity<?> getBook(@RequestParam long Chapid){
    try { long m=chapterService.getbook(Chapid);
        return new ResponseEntity<>(m,HttpStatus.ACCEPTED);
    }catch (Exception ex) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}

@PostMapping("/createchapter")
ResponseEntity<?> saveChapter(@RequestParam ChapDto chapDto,@AuthenticationPrincipal LocalUsers user,@RequestParam long Bookid){
    try {
        if (user.getSellerinventory().getId() == chaptersRepository.getById(chapDto.getId()).getSellerinventory().getId()) {
            long m=chapterService.createChap(chapDto,Bookid);
            return new ResponseEntity<>(m,HttpStatus.ACCEPTED);
        }
    } catch (Exception ex) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
}

@PutMapping("/updatechapter")
ResponseEntity<?> updateChapter(@RequestParam ChapDto chapDto,@AuthenticationPrincipal LocalUsers user){
    try {
        if (user.getSellerinventory().getId() == chaptersRepository.getById(chapDto.getId()).getSellerinventory().getId()) {
            boolean m=chapterService.updateChap(chapDto);
            return new ResponseEntity<>(m,HttpStatus.ACCEPTED);
        }
    } catch (Exception ex) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
}

@GetMapping("/deletechap")
ResponseEntity<?> DeleteChap(@RequestParam long Chapid, @AuthenticationPrincipal LocalUsers user) {
        try {
        if (user.getSellerinventory().getId() == chaptersRepository.getById(Chapid).getSellerinventory().getId()) {
            boolean m=chapterService.DeleteChap(Chapid, user.getId());
            return new ResponseEntity<>(m,HttpStatus.ACCEPTED);
        }
    } catch (Exception ex) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
}
@GetMapping("/readfile")
    ResponseEntity<MultipartFile> readfile(@RequestParam long Chapid,@AuthenticationPrincipal LocalUsers user){
        try {
        if (user.getSellerinventory().getId() == chaptersRepository.getById(Chapid).getSellerinventory().getId()) {
            MultipartFile m=chapterService.file(Chapid);
            return new ResponseEntity<>(m,HttpStatus.ACCEPTED);
        }
    } catch (Exception ex) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
}

@GetMapping("/setfiles")
    ResponseEntity<?> setfile(@RequestParam long Chapid,@AuthenticationPrincipal LocalUsers user,@RequestParam MultipartFile file){
        try {
        if (user.getSellerinventory().getId() == chaptersRepository.getById(Chapid).getSellerinventory().getId()) {
            boolean m=chapterService.setFile(Chapid,file);
            return new ResponseEntity<>(m,HttpStatus.ACCEPTED);
        }
    } catch (Exception ex) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
}


    }






















