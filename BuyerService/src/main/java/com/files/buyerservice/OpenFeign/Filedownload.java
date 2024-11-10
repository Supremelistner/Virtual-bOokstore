package com.files.buyerservice.OpenFeign;

import com.files.buyerservice.Database.Books;
import com.files.buyerservice.Database.Chapters;
import com.files.buyerservice.Database.ImageDB;
import com.files.buyerservice.Repos.BooksRepository;
import com.files.buyerservice.Repos.ChaptersRepository;
import com.files.buyerservice.Repos.ImageDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class Filedownload {
    @Value("${path.Book}")
    String Bookpath;
    @Value("${path.Chapters}")
    String ChapPath;
    @Autowired
    ChaptersRepository repository;
@Autowired
ImageDBRepository imageDBRepository;
@Autowired
BooksRepository booksRepository;

 public  boolean uploadbook( long id, MultipartFile file){
       try{
           String path=Bookpath+file.getOriginalFilename();
           Path file1= Paths.get(path);
           Files.write(file1, file.getBytes());
           Books user=booksRepository.findById(id).get();
           ImageDB image=new ImageDB();
           image.setImagename(file.getOriginalFilename());
           image.setImagetype("profile");
           image.setImagepath(path);

           user.setImageDB(image);
           imageDBRepository.save(image);
           booksRepository.save(user);
           return true;}
       catch (Exception ex){
           return false;
       }}


   public boolean uploadchap(long id, MultipartFile file){
       try{
           String path=ChapPath+file.getOriginalFilename();
           Path file1= Paths.get(path);
           Files.write(file1, file.getBytes());
           Chapters user=repository.findById(id).get();
           user.setFilePath(path);
           repository.save(user);
           return true;}
       catch (Exception ex){
           return false;
       }}


   public ResponseEntity<Resource> dwbook( long id)throws MalformedURLException {
        Books user=booksRepository.findById(id).get();
        String path=user.getImageDB().getImagepath();
        Path filePath = Paths.get(path);
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, path)
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    public ResponseEntity<Resource> dwchap( long id) throws MalformedURLException {
        Chapters user=repository.findById(id).get();
        String path=user.getFilePath();
        Path filePath = Paths.get(path);
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM )
                    .header(HttpHeaders.CONTENT_DISPOSITION, path)
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }




    public boolean dbook( long id){
        try{
            Books user=booksRepository.findById(id).get();
            ImageDB imageDB=user.getImageDB();
            Files.delete(Paths.get(imageDB.getImagepath()));
            imageDBRepository.delete(imageDB);
            return true;}
        catch(Exception ex){
            return false;
        }}


    public boolean dchap(long id){
        try{
            Chapters user=repository.findById(id).get();
            Files.delete(Paths.get(user.getFilePath()));
            repository.delete(user);
            return true;}
        catch(Exception ex){
            return false;
        }}


}
