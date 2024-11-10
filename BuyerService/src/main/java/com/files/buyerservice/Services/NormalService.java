package com.files.buyerservice.Services;

import com.files.buyerservice.DTOs.*;
import com.files.buyerservice.Database.*;
import com.files.buyerservice.OpenFeign.Filedownload;
import com.files.buyerservice.Repos.*;
import com.files.buyerservice.Services.Razorpay.PaymentService;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.*;

@Service
public class NormalService {

    @Autowired
    UserInventoryRepository userInventoryRepository;

    @Autowired
    LocalUsersRepository localUsersRepository;
    @Autowired
    BooksRepository booksRepository;
    @Autowired
    ChaptersRepository chaptersRepository;
    @Autowired
    private ReviewsRepository reviewsRepository;
    @Autowired
    private Filedownload filedownload;
    @Autowired
    PaymentService paymentService;
    @Autowired
    private OrdersRepository ordersRepository;

    public BookListDto booksaffiliated(long User, int pageno) {
        Pageable page = PageRequest.of(pageno, 10, Sort.by(Sort.Direction.ASC));
        Optional<LocalUsers> users = localUsersRepository.findById(User);
        Optional<UserInventory> userinventory = userInventoryRepository.findByLocalUsers(users.get());
        if (userinventory != null) {
            UserInventory seller = userinventory.get();
            Page<Books> books = booksRepository.findByUserInventory_Id(seller.getId(), page);
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
public Boolean WriteReview(ReviewDto dto,long localId,long booksId){
        Optional<Books> books=booksRepository.findById(booksId);
        UserInventory inventory=localUsersRepository.findById(localId).get().getUserInventory();
        if(inventory.getBookses().contains(books.get())){
            Reviews review=new Reviews();
            review.setContent(dto.getReview());
            review.setRating(dto.getRating());
            review.setBooks(books.get());
            review.setUserInventory(inventory);
            reviewsRepository.save(review);
            return true;
        }
        return false;
}
public Boolean UpdateReview(ReviewDto dto,long localId,int ReviewId){
        Reviews reviews=reviewsRepository.findById(ReviewId).get();
        UserInventory inventory=localUsersRepository.findById(localId).get().getUserInventory();
    if(inventory.getReviews().contains(reviews)){
        reviews.setContent(dto.getReview());
        reviews.setRating(dto.getRating());
        reviewsRepository.save(reviews);
        return true;
    }
    return false;
    }
public ReviewDto seeReviewByBook(long localId,long booksId){
    Optional<Books> books=booksRepository.findById(booksId);
    UserInventory inventory=localUsersRepository.findById(localId).get().getUserInventory();
    Reviews reviews=reviewsRepository.findFirstByUserInventory_IdAndBooks_Id(inventory.getId(),booksId);
    if(reviews!=null){
        ReviewDto dto= new ReviewDto();
        dto.setId(reviews.getId());
        dto.setRating(reviews.getRating());
        dto.setReview(reviews.getContent());
        return dto;
    }return null;

}
public Resource readChapter(long chapid, long localid) throws MalformedURLException {
    UserInventory inventory=localUsersRepository.findById(localid).get().getUserInventory();
    Chapters chapters=chaptersRepository.findById(chapid).get();
    if(inventory.getChapterses().contains(chapters)){
        Orders order=ordersRepository.findByChapters_IdAndUserInventory_Id(chapid, inventory.getId());
        if(order.getCompleted())
       return  filedownload.dwchap(chapid).getBody();
        else{
            ordersRepository.delete(order);
        }}
    return null;
    }
public String orderCreation(long ChapId,long LocalId) throws Exception {
        Chapters chapters=chaptersRepository.findById(ChapId).get();
        UserInventory inventory=localUsersRepository.findById(LocalId).get().getUserInventory();
        Orders orders=new Orders();
        orders.setUserInventory(inventory);
        orders.setCompleted(false);
        orders.setSellerinventory(chapters.getSellerinventory());
        orders.setChapters(chapters);
        orders.setPrice((float) (chapters.getPrice()*0.95));
        if(chapters.getPrice()==0){
        orders.setCompleted(true);
        return "free item";
    }

        String order =paymentService.createOrder(chapters.getPrice());
        orders.setInvoice(order);
        ordersRepository.save(orders);
        return order;
    }
    public boolean verifyOrder(String orderId, String paymentId, String razorpaySignature){
        Orders order=ordersRepository.findByInvoice(orderId);
        if(paymentService.verifyPayment(orderId,paymentId,razorpaySignature)==true){

            Chapters chap=new Chapters();
            chap.setUserInventory(order.getUserInventory());
            Books book=chap.getBooks();
            book.setUserInventory(order.getUserInventory());
            order.setCompleted(true);
            ordersRepository.save(order);
            chaptersRepository.save(chap);
            booksRepository.save(book);
            return true;
        }
        else ordersRepository.delete(order)
                ;return false;
    }
   public List<OrderDto> getAllOrders(long LocalId,int pageno){
        UserInventory inventory=localUsersRepository.findById(LocalId).get().getUserInventory();
        Pageable page = PageRequest.of(pageno, 10, Sort.by(Sort.Direction.ASC));
        List<Orders> order1= ordersRepository.findByUserInventory(inventory,page);
        List<OrderDto> order=new ArrayList<>();
        for(Orders e:order1){
            if(e.getCompleted()==true){
                OrderDto dto=new OrderDto();
                dto.setId(e.getId());
                dto.setPrice(e.getPrice());
                dto.setBuyerName(e.getUserInventory().getLocalUsers().getUsername());
                dto.setDate(e.getDate());
                dto.setChapterId(e.getChapters().getId());
                dto.setSellername(e.getSellerinventory().getLocalUsers().getUsername());
                order.add(dto);
            }else ordersRepository.delete(e);
        }
        return order;}

    public String RAzorpayOrderId(int orderId){
        return ordersRepository.findById(orderId).get().getInvoice();
    }
    public int OrderId2(String orderId){
        return ordersRepository.findByInvoice(orderId).getId();}
    public double getPrice(String id){
        return ordersRepository.findByInvoice(id).getPrice();
    }

   public JSONObject createtranfer(long Chapid,double amount) throws RazorpayException {
        Sellerinventory sellerinventory=chaptersRepository.findById(Chapid).get().getSellerinventory();
        Credentials creds=sellerinventory.getLocalUsers().getCredentials();
       return paymentService.createTransfer(creds.getDetails(),amount);
    }

    public Map<ChapDto,String> BoughtChap(long bookId,long id){
        Map map=new HashMap<ChapDto,String>();
        Books book=booksRepository.findById(bookId).get();
        UserInventory user=localUsersRepository.findById(id).get().getUserInventory();
        for(Chapters chap:book.getChapters()){
            ChapDto chapDto=new ChapDto();
            chapDto.setId(chap.getId());
            chapDto.setName(chap.getName());
            chapDto.setPrice(chap.getPrice());
            if(user.getChapterses().contains(chap))
                map.put(chapDto,"Bought");
            else
                map.put(chapDto,"Not Bought");
        }
        return map;
    }
}
