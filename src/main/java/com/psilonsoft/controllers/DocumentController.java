package com.psilonsoft.controllers;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.psilonsoft.auth.CustomAuthenticationToken;
import com.psilonsoft.dao.DocumentDao;
import com.psilonsoft.dao.UserDao;
import com.psilonsoft.entities.Document;
import com.psilonsoft.entities.User;

@Controller
public class DocumentController {
    private static final Logger log = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private DocumentDao documentDao;

    @Autowired
    private UserDao userDao;

    /**
     * Displays document list
     * 
     * @param model
     * @param principal
     * @return
     */
    @RequestMapping("/doc")
    public String list(final ModelMap model, final Principal principal) {
        CustomAuthenticationToken token = ((CustomAuthenticationToken) principal);

        List<Document> docs = documentDao.findDocuments(token.getUser().getId());
        model.addAttribute("documents", docs);

        return "documentList";
    }

    /**
     * Displays document creation form
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/doc/add")
    public String addForm(final ModelMap model, @ModelAttribute("doc") final Document doc) {

        List<User> users = userDao.getAllUsers();
        model.addAttribute("users", users);

        return "documentAdd";
    }

    /**
     * Displays document
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/doc/{id}")
    public String view(@PathVariable("id") final Long id, final ModelMap model,
            final Principal principal) {

        CustomAuthenticationToken token = ((CustomAuthenticationToken) principal);
        Document doc = documentDao.getById(id);

        log.debug("User with id " + token.getUser().getId()
                + " attempting to access file that belongs to " + doc.getOwnerId());
        if (token.getUser().getId().equals(doc.getOwnerId())) {
            model.addAttribute("doc", doc);
        }

        return "documentView";
    }

    /**
     * Submit new document
     * 
     * @param model
     * @param doc
     * @return
     */
    @RequestMapping(value = "/doc/add", method = { RequestMethod.POST })
    public String add(final ModelMap model, @ModelAttribute("doc") final Document doc,
            final BindingResult result) {

        if (doc.getTitle() == null || doc.getTitle().isEmpty()) {
            result.rejectValue("title", "empty-title", "Title is empty");
        }

        if (doc.getContent() == null || doc.getContent().isEmpty()) {
            result.rejectValue("content", "empty-content", "Content is empty");
        }

        if (result.hasErrors()) {
            List<User> users = userDao.getAllUsers();
            model.addAttribute("users", users);

            return "documentAdd";
        }

        documentDao.save(doc);

        return "redirect:/doc";
    }
}
