package com.zzk.springboot.jpa;

import com.zzk.springboot.jpa.entity.Tutorial;
import com.zzk.springboot.jpa.repository.TutorialRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class JPAUnitTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    TutorialRepository repository;

    @Test
    public void should_find_no_tutorials_if_repository_is_empty() {
        Iterable tutorials = repository.findAll();
        assertThat(tutorials).isEmpty();
    }
    @Test
    public void should_store_a_tutorial() {
        final Tutorial tutorial = repository.save(new Tutorial("title", "description", false));
        assertThat(tutorial).hasFieldOrPropertyWithValue("title", "title");
        assertThat(tutorial).hasFieldOrPropertyWithValue("description", "description");
        assertThat(tutorial).hasFieldOrPropertyWithValue("published", false);
    }
    @Test
    public void should_find_all_tutorials() {
        Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
//        repository.save(tut1);
        entityManager.persist(tut1);
        Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
        entityManager.persist(tut2);
        Tutorial tut3 = new Tutorial("Tut#3", "Desc#3", true);
        entityManager.persist(tut3);
        assertThat(repository.findAll()).hasSize(3).contains(tut1, tut2, tut3);
    }
    @Test
    public void should_find_tutorial_by_id() {
        Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
        entityManager.persist(tut1);
        Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
        entityManager.persist(tut2);
        Tutorial foundTutorial = repository.findById(tut2.getId()).get();
        assertThat(foundTutorial).isEqualTo(tut2);
    }
    @Test
    public void should_find_published_tutorials() {
        Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
        entityManager.persist(tut1);
        Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
        entityManager.persist(tut2);
        Tutorial tut3 = new Tutorial("Tut#3", "Desc#3", true);
        entityManager.persist(tut3);
        assertThat(repository.findByPublished(true)).hasSize(2).contains(tut1, tut3);
    }
    @Test
    public void should_find_tutorials_by_title_containing_string() {
        Tutorial tut1 = new Tutorial("Spring Boot Tut#1", "Desc#1", true);
        entityManager.persist(tut1);
        Tutorial tut2 = new Tutorial("Java Tut#2", "Desc#2", false);
        entityManager.persist(tut2);
        Tutorial tut3 = new Tutorial("Spring Data JPA Tut#3", "Desc#3", true);
        entityManager.persist(tut3);
        assertThat(repository.findByTitleContaining("ring")).hasSize(2).contains(tut1, tut3);
    }
    @Test
    public void should_update_tutorial_by_id() {
        Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
        entityManager.persist(tut1);
        Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
        entityManager.persist(tut2);
        Tutorial updatedTut = new Tutorial("updated Tut#2", "updated Desc#2", true);

        Tutorial tut = repository.findById(tut2.getId()).get();
        tut.setTitle(updatedTut.getTitle());
        tut.setDescription(updatedTut.getDescription());
        tut.setPublished(updatedTut.isPublished());
        repository.save(tut);
        Tutorial checkTut = repository.findById(tut2.getId()).get();

        assertThat(checkTut.getId()).isEqualTo(tut2.getId());
        assertThat(checkTut.getTitle()).isEqualTo(updatedTut.getTitle());
        assertThat(checkTut.getDescription()).isEqualTo(updatedTut.getDescription());
        assertThat(checkTut.isPublished()).isEqualTo(updatedTut.isPublished());
    }
    @Test
    public void should_delete_tutorial_by_id() {
        Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
        entityManager.persist(tut1);
        Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
        entityManager.persist(tut2);
        Tutorial tut3 = new Tutorial("Tut#3", "Desc#3", true);
        entityManager.persist(tut3);
        repository.deleteById(tut2.getId());
        assertThat(repository.findAll()).hasSize(2).contains(tut1, tut3);
    }
    @Test
    public void should_delete_all_tutorials() {
        entityManager.persist(new Tutorial("Tut#1", "Desc#1", true));
        entityManager.persist(new Tutorial("Tut#2", "Desc#2", false));
        repository.deleteAll();
        assertThat(repository.findAll()).isEmpty();
    }
}
