package profile.profiler.profile.DTO;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import profile.profiler.profile.Database.ImageDB;
import profile.profiler.profile.Database.LocalUsers;
import profile.profiler.profile.Repos.ImageDBRepository;
import profile.profiler.profile.Repos.LocalUsersRepository;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Service

public class Filedownload {



LocalUsersRepository localUsersRepository;
@Value("${path.pfp}")
    String Profilepath;


    public Filedownload(LocalUsersRepository localUsersRepository, ImageDBRepository imageDBRepository) {
        this.localUsersRepository = localUsersRepository;
        this.imageDBRepository = imageDBRepository;
    }

    ImageDBRepository imageDBRepository;

    public boolean uploadprofile(long id, MultipartFile file){
        try{
            String path=Profilepath+file.getOriginalFilename();
            Path file1= Paths.get(path);
            LocalUsers user=localUsersRepository.findById(id).get();
            Files.write(file1, file.getBytes());
            ImageDB image=new ImageDB();
            image.setImagename(file.getOriginalFilename());
            image.setImagetype("profile");
            image.setImagepath(path);
            user.setImageDB(image);
            image.setLocalUsers(user);
            imageDBRepository.save(image);

            localUsersRepository.save(user);
            return true;}
        catch (Exception ex){
            return false;
        }}

    public ResponseEntity<Resource> dwprofile(long id)throws MalformedURLException {
        LocalUsers user=localUsersRepository.findById(id).get();
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

    public boolean dpfp(long id){
        try{
            LocalUsers user=localUsersRepository.findById(id).get();
            ImageDB imageDB=user.getImageDB();
            Files.delete(Paths.get(imageDB.getImagepath()));
            imageDBRepository.delete(imageDB);
            return true;}
        catch(Exception ex){
            return false;
        }}
}
