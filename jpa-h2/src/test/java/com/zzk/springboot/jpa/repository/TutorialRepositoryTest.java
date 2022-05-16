package com.zzk.springboot.jpa.repository;

import com.zzk.springboot.jpa.entity.Tutorial;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TutorialRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TutorialRepository repository;

    List<Tutorial> tutorials = new ArrayList<>(
            Arrays.asList(new Tutorial("Spring Boot @WebMvcTest 1", "Description 1", false),
                    new Tutorial("Spring Boot @WebMvcTest 2", "Description 2", true),
                    new Tutorial("Spring Boot @WebMvcTest 3", "Description 3", true)));

    @Test
    void findAllNative(){
        tutorials.forEach(entityManager::persist);
        assertThat(repository.findAllNative()).containsOnly(tutorials.toArray(new Tutorial[0]));
    }

    @Test
    void findByTitleAndSort() {
        tutorials.forEach(entityManager::persist);
        tutorials.sort(new Comparator<Tutorial>() {
            @Override
            public int compare(Tutorial o1, Tutorial o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        assertThat(
                repository.findByTitleAndSort("Spring", Sort.by("id").descending()))
                .containsExactlyElementsOf(tutorials);
    }

    @Test
    void findAllWithPagination() {
        tutorials.forEach(entityManager::persist);
        Pageable pageable = PageRequest.of(0, 2);
        final Page<Tutorial> allWithPagination = repository.findAllWithPagination(pageable);
        assertEquals(2, allWithPagination.getTotalPages());
        assertEquals(3, allWithPagination.getTotalElements());
        assertEquals(allWithPagination.getContent().size(),2);
    }

    /**
     * 不知道为什么失败
     * */
    @Test
    void publishTutorial() {
        tutorials.forEach(entityManager::persist);
        assertFalse(repository.findById(1L).get().isPublished());
        repository.publishTutorialNative(1L);
        assertTrue(repository.findById(1L).get().isPublished());
    }
}
