package com.example.journelApp.service;

import com.example.journelApp.entity.JournalEntry;
import com.example.journelApp.entity.User;
import com.example.journelApp.repository.JournalEntryRepository;
import com.example.journelApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import java.util.List;

@Component
 public class JournalEntryService {

     @Autowired
     private JournalEntryRepository journalEntryRepository;
     @Autowired
     private UserService userService;

     @Transactional
     public void saveEntry(JournalEntry journalEntry, String userName){
         try{
             User user=userService.findByUserName(userName);
             journalEntry.setDate(LocalDateTime.now());
             JournalEntry save = journalEntryRepository.save(journalEntry);
             user.getJournalEntries().add(save);
             userService.saveUser(user);

         } catch (Exception e) {
             System.out.println(e);
             throw new RuntimeException("An error while saving entry" , e);
         }
     }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

     public List<JournalEntry> getAll(){
         return journalEntryRepository.findAll();
     }

     public Optional<JournalEntry> findById(ObjectId id){
         return journalEntryRepository.findById(id);
     }

     @Transactional
     public boolean deleteById(ObjectId id, String userName){
         boolean removed=false;
         try{
             User user=userService.findByUserName(userName);
             removed = user.getJournalEntries().removeIf(( x -> x.getId().equals(id)));
             if(removed) {
                 userService.saveUser(user);
                 journalEntryRepository.deleteById(id);
             }
         } catch (Exception e) {
             System.out.println(e);
             throw new RuntimeException("An error ocurred while deleting the entry", e);
         }
         return removed;
     }
}
