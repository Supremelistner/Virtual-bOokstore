package com.files.buyerservice.Controller;

import com.files.buyerservice.DTOs.BookListDto;
import com.files.buyerservice.DTOs.ChapDto;
import com.files.buyerservice.DTOs.OrderDto;
import com.files.buyerservice.DTOs.ReviewDto;
import com.files.buyerservice.Database.LocalUsers;
import com.files.buyerservice.Database.UserInventory;
import com.files.buyerservice.Repos.LocalUsersRepository;
import com.files.buyerservice.Repos.UserInventoryRepository;
import com.files.buyerservice.Services.NormalService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyers")
public class UserController {
    @Autowired
    NormalService service;
    @Autowired
    private LocalUsersRepository localUsersRepository;
    @Autowired
    private UserInventoryRepository userInventoryRepository;

    @GetMapping("/buyerrid")
    ResponseEntity<Long> Sellerid(@AuthenticationPrincipal LocalUsers user){
        try{
            UserInventory sellerinventory=new UserInventory();

            LocalUsers users=localUsersRepository.findById(user.getId()).get();
            users.setUserInventory(sellerinventory);
            userInventoryRepository.save(sellerinventory);
            localUsersRepository.save(users);

            long id=localUsersRepository.findById(user.getId()).get().getSellerinventory().getId();

            return new ResponseEntity<>(id,HttpStatus.ACCEPTED);
        }catch(Exception ex){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }}javadoc -d doc -f text MyJavaClass.java

    @GetMapping("/bookinventory")
    ResponseEntity<BookListDto> books(@AuthenticationPrincipal LocalUsers User, int pageno) {
        try {
            BookListDto books = service.booksaffiliated(User.getId(), pageno);
            return new ResponseEntity<>(books, HttpStatus.ACCEPTED);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/postreview")
    ResponseEntity<Boolean> writerev(@RequestParam ReviewDto dto, @RequestParam long Bookid, @AuthenticationPrincipal LocalUsers user) {
        try {
            Boolean books = service.WriteReview(dto, user.getId(), Bookid);
            return new ResponseEntity<>(books, HttpStatus.ACCEPTED);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
    }


@GetMapping("/myreview")
ResponseEntity<ReviewDto> responsereview(@AuthenticationPrincipal LocalUsers users,long Bookid){
    try{
 ReviewDto books=service.seeReviewByBook(users.getId(),Bookid);
        return new ResponseEntity<>(books,HttpStatus.ACCEPTED);
        }
        catch(RuntimeException exception){
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);}
        }
    @PostMapping("/updatereview")
    ResponseEntity<Boolean> updaterev(@RequestParam ReviewDto dto, @RequestParam int Revid, @AuthenticationPrincipal LocalUsers user) {
        try {
            Boolean books = service.UpdateReview(dto, user.getId(), Revid);
            return new ResponseEntity<>(books, HttpStatus.ACCEPTED);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("/readChapter")
    ResponseEntity<Resource> readChapter(@RequestParam int Chapid,@AuthenticationPrincipal LocalUsers users){   try {
        Resource books = service.readChapter(Chapid, users.getId());
        return new ResponseEntity<>(books, HttpStatus.ACCEPTED);
    } catch (RuntimeException exception) {
        return new ResponseEntity<>( HttpStatus.UNAUTHORIZED);
    } catch (MalformedURLException e) {
        return new ResponseEntity<>( HttpStatus.CONFLICT);
    }
    }
@PostMapping("/orderCreation")
    ResponseEntity<String> orderCreation(@RequestParam int Chapid,@AuthenticationPrincipal LocalUsers users){
        try{
    String books = service.orderCreation(Chapid, users.getId());
    return new ResponseEntity<>(books, HttpStatus.ACCEPTED);
} catch (Exception exception) {
        return new ResponseEntity<>( HttpStatus.UNAUTHORIZED);
    }

}

@PostMapping("/verifypay")
    ResponseEntity<JSONObject>  verifyOrder(@RequestParam String orderId,@RequestParam String paymentId, @RequestParam String razorpaySignature){
    try{
        boolean books = service.verifyOrder(orderId,paymentId,razorpaySignature);
        if(books){
            JSONObject book=service.createtranfer(service.OrderId2(orderId), service.getPrice(orderId));

            return new ResponseEntity<>(book, HttpStatus.ACCEPTED);}
    } catch (Exception exception) {
        return new ResponseEntity<>( HttpStatus.UNAUTHORIZED);
    }return new ResponseEntity<>( HttpStatus.PAYMENT_REQUIRED);
    }
    @GetMapping("/rzrpayID")
    ResponseEntity<String> rzr(@RequestParam int orderId){
        try{
            String books = service.RAzorpayOrderId(orderId);
            return new ResponseEntity<>(books, HttpStatus.ACCEPTED);
        } catch (Exception exception) {
            return new ResponseEntity<>( HttpStatus.UNAUTHORIZED);
        }}
    @GetMapping("/getordrs")
    ResponseEntity<List<OrderDto>> getAllOrders(@AuthenticationPrincipal LocalUsers user, int pageno){
        try{
            List<OrderDto> books = service.getAllOrders(user.getId(),pageno);
            return new ResponseEntity<>(books, HttpStatus.ACCEPTED);
        } catch (Exception exception) {
            return new ResponseEntity<>( HttpStatus.UNAUTHORIZED);
        }

    }
    @GetMapping("/Invchaps")
  ResponseEntity<Map<ChapDto,String>> BoughtChap(@RequestParam int BookId,@AuthenticationPrincipal LocalUsers user ) {
        try{
          Map books = service.BoughtChap(user.getId(),BookId);
          return new ResponseEntity<>(books, HttpStatus.ACCEPTED);
      } catch (Exception exception) {
          return new ResponseEntity<>( HttpStatus.UNAUTHORIZED);
      }
  }



}




