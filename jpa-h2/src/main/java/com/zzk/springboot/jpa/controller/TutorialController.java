package com.zzk.springboot.jpa.controller;

import com.zzk.springboot.jpa.entity.Tutorial;
import com.zzk.springboot.jpa.exception.ResourceNotFoundException;
import com.zzk.springboot.jpa.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TutorialController {

    @Autowired
    private TutorialRepository tutorialRepository;

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorial(@RequestParam(required = false) String title) {
        List<Tutorial> result = new ArrayList<>();
        if (title == null) {
            tutorialRepository.findAll().forEach(result::add);
        } else {
            tutorialRepository.findByTitleContaining(title).forEach(result::add);
        }
        if(result.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/tutorials/page")
    public ResponseEntity<Map<String, Object>> getAllTutorialsPageable(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        try {
            List<Tutorial> tutorials = new ArrayList<Tutorial>();
            Pageable paging = PageRequest.of(page, size);

            Page<Tutorial> pageTuts;
            if (title == null)
                pageTuts = tutorialRepository.findAll(paging);
            else
                pageTuts = tutorialRepository.findByTitleContaining(title, paging);
            if (pageTuts == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            tutorials = pageTuts.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("tutorials", tutorials);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
        Tutorial tutorialData = tutorialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found tutorial with id=" + id));
        return new ResponseEntity<>(tutorialData, HttpStatus.OK);
    }

    @PostMapping("/tutorials")
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
        Tutorial tut = tutorialRepository
                .save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
        return new ResponseEntity<>(tut, HttpStatus.CREATED);
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
        Tutorial tut = tutorialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found tutorial with id=" + id));
        tut.setTitle(tutorial.getTitle());
        tut.setDescription(tutorial.getDescription());
        tut.setPublished(tutorial.isPublished());
        return new ResponseEntity<>(tutorialRepository.save(tut), HttpStatus.OK);
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        tutorialRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        tutorialRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<Tutorial>> findByPublished() {
        List<Tutorial> tutorials = tutorialRepository.findByPublished(true);
        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }
}
