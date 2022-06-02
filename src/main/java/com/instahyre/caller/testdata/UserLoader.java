package com.instahyre.caller.testdata;

import com.instahyre.caller.model.User;
import com.instahyre.caller.model.UserContact;
import com.instahyre.caller.model.UserContactRequestCommand;
import com.instahyre.caller.model.UserRequestCommand;
import com.instahyre.caller.serviceImpl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile({"local"})
public class UserLoader implements CommandLineRunner {

    private final UserServiceImpl userServiceImpl;
    private final ModelMapper modelMapper;

    @Value("${app.load.user:false}")
    private boolean load;

    @Value("classpath:testdata/user.csv")
    private Resource userFile;

    @Value("classpath:testdata/userContact.csv")
    private Resource userContactFile;

    List<Long> idList = new ArrayList<>();

    public void UserContactLoadFromCsv(Resource file){
        List<String> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                try {
                    if (Objects.equals(file, userContactFile)) {
                        loadCsvUserContact(values);
                    } else {
                        loadCsvUser(values);
                    }
                }
                catch(Exception e) {
                    log.warn("Error loading user - {}", e.getMessage());
                }
            }
        }
        catch(Exception e){
            log.warn("Error reading test file", e);
        }
    }

    private void loadCsvUserContact(String[] values) {

        UserContact userContact = new UserContact();
//       userContact.setId(Long.parseLong(values[0]));
        Random rand = new Random();
        int randomNum = rand.nextInt((idList.size()-1 - 0) + 1) + 0;
        userContact.setContactName(values[0]);
        userContact.setContactNumber(values[1]);
        userContact.setSpamStatus(Boolean.parseBoolean(values[2]));
        userServiceImpl.saveUserContact(idList.get(randomNum).toString(), userContact);
    }

    private void loadCsvUser(String values[]) {
        User user = new User();
        user.setName(values[0]);
        user.setPhone(values[1]);
        user.setEmail(values[2]);
        user.setPassword(values[3]);
        User savedUser =userServiceImpl.saveUser(user);
        idList.add(savedUser.getId());
    }

    @Override
    public void run(String... args) {
        if(!load) return;
        log.info("## Loading user - {}", userFile.exists());
        log.info("## Loading user contact - {}", userContactFile.exists());

        UserContactLoadFromCsv(userFile);
        UserContactLoadFromCsv(userContactFile);
    }
}
