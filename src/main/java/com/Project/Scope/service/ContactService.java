package com.Project.Scope.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.Scope.model.Contact;
import com.Project.Scope.repository.ContactRepository;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public void saveContact(Contact contact) {
        contactRepository.save(contact);
    }
}
