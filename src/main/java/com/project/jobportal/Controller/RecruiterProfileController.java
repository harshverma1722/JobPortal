package com.project.jobportal.Controller;

import com.project.jobportal.Entity.RecruiterProfile;
import com.project.jobportal.Entity.Users;
import com.project.jobportal.Repository.UsersRepository;
import com.project.jobportal.Services.RecruiterProfileService;
import com.project.jobportal.util.FileUploadUtil;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

    private final UsersRepository usersRepository;

    private final RecruiterProfileService recruiterProfileService;

    public RecruiterProfileController(UsersRepository usersRepository,RecruiterProfileService recruiterProfileService) {
        this.usersRepository = usersRepository;
        this.recruiterProfileService = recruiterProfileService;
    }


    @GetMapping("/")
    public String recruiterProfile(Model model){
       Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
       if(!(authentication instanceof AnonymousAuthenticationToken)){
           String currentUsername = authentication.getName();
           Users users = usersRepository.findByEmail(currentUsername).orElseThrow(()->new UsernameNotFoundException("Could not " + "found user"));
           Optional<RecruiterProfile> recruiterProfile=recruiterProfileService.getOne(users.getUserId());

           if(!recruiterProfile.isEmpty()){
               model.addAttribute("profile",recruiterProfile.get());
           }
       }
       return "recruiter_profile";
    }


    @PostMapping("/addNew")
    // Creates a new recruiter profile (in memory) based on form data
    public String addNew(RecruiterProfile recruiterProfile, @RequestParam("image")MultipartFile
                         multipartFile,Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            Users users = usersRepository.findByEmail(currentUserName).orElseThrow(() -> new UsernameNotFoundException("Could not " + "found user"));

            // Associate recruiter profile with existing user account
            recruiterProfile.setUserId(users);
            recruiterProfile.setUserAccountId(users.getUserId());
        }
        model.addAttribute("profile", recruiterProfile);
        String fileName = "";
        if (!multipartFile.getOriginalFilename().equals("")) {
            fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

            // Set image name in recruiter profile
            recruiterProfile.setProfilePhoto(fileName);
        }
        //sAVE RECRUITER PROFILE TO dB
        RecruiterProfile savedUser = recruiterProfileService.addNew(recruiterProfile);
        String uploadDir = "photos/recruiter" + savedUser.getUserAccountId();


        //Inside the try block we are reading the profile image from request multipartfile and
        // then saving the image on the server in directory photos/recruiter
        try{
            FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return "redirect:/dashboard/";
    }
}
