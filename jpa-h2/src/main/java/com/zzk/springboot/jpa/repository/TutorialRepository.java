package com.zzk.springboot.jpa.repository;

import com.zzk.springboot.jpa.entity.Tutorial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
    /*
    * JPQL
    * */
    List<Tutorial> findAll();
    Optional<Tutorial> findById(long id);
    List<Tutorial> findByLevelIs(int level);
    List<Tutorial> findByLevelEquals(int level);
    List<Tutorial> findByLevelNot(int level);
    List<Tutorial> findByLevelIsNot(int level);

    List<Tutorial> findByLevelAndPublished(int level, boolean isPublished);
    List<Tutorial> findByTitleOrDescription(String title, String description);

    List<Tutorial> findByTitleLike(String title);
    List<Tutorial> findByTitleStartingWith(String title);
    List<Tutorial> findByTitleEndingWith(String title);
    List<Tutorial> findByTitleContaining(String title);
    List<Tutorial> findByTitleContainingOrDescriptionContaining(String title, String description);
    List<Tutorial> findByTitleContainingAndPublished(String title, boolean isPublished);

    List<Tutorial> findByPublishedTrue();
    List<Tutorial> findByPublishedFalse();
    List<Tutorial> findByPublished(boolean published);

    List<Tutorial> findByLevelGreaterThan(int level);
    List<Tutorial> findByCreatedAtGreaterThanEqual(Date date);
    List<Tutorial> findByCreatedAtAfter(Date date);
    List<Tutorial> findByLevelBetween(int start, int end);
    List<Tutorial> findByLevelBetweenAndPublished(int start, int end, boolean isPublished);
    List<Tutorial> findByCreatedAtBetween(Date start, Date end);

    List<Tutorial> findByOrderByLevel();
    // same as
    List<Tutorial> findByOrderByLevelAsc();
    List<Tutorial> findByOrderByLevelDesc();
    List<Tutorial> findByTitleContainingOrderByLevelDesc(String title);
    List<Tutorial> findByPublishedOrderByCreatedAtDesc(boolean isPublished);

    List<Tutorial> findByTitleContaining(String title, Sort sort);
    List<Tutorial> findByPublished(boolean isPublished, Sort sort);

    Page<Tutorial> findAll(Pageable pageable);
    Page<Tutorial> findByTitle(String title, Pageable pageable);

    @Transactional
    void deleteAllByCreatedAtBefore(Date date);


    @Query(value = "SELECT * FROM tutorials t WHERE t.published=true", nativeQuery = true)
    List<Tutorial> findByPublishedNative();

    @Query("SELECT t FROM Tutorial t WHERE t.published=?1")
    List<Tutorial> findByPublishedUseQuery(boolean isPublished);

    @Query("SELECT t FROM Tutorial t WHERE t.title LIKE %?1%")
    List<Tutorial> findByTitleLikeUseQuery(String title);

    @Query("SELECT t FROM Tutorial t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', ?1,'%'))")
    List<Tutorial> findByTitleLikeCaseInsensitive(String title);


    @Query("SELECT t FROM Tutorial t WHERE t.level >= ?1")
    List<Tutorial> findByLevelGreaterThanEqual(int level);
    @Query("SELECT t FROM Tutorial t WHERE t.createdAt >= ?1")
    List<Tutorial> findByDateGreaterThanEqual(Date date);

    @Query("SELECT t FROM Tutorial t WHERE t.level BETWEEN ?1 AND ?2")
    List<Tutorial> findByLevelBetweenUseQuery(int start, int end);

    @Query("SELECT t FROM Tutorial t WHERE t.published=:isPublished AND t.level BETWEEN :start AND :end")
    List<Tutorial> findByLevelBetween(@Param("start") int start, @Param("end") int end, @Param("isPublished") boolean isPublished);


    @Query("SELECT t FROM Tutorial t WHERE t.createdAt BETWEEN ?1 AND ?2")
    List<Tutorial> findByDateBetween(Date start, Date end);

    // order by
    @Query("SELECT t FROM Tutorial t ORDER BY t.level DESC")
    List<Tutorial> findAllOrderByLevelDesc();
    @Query("SELECT t FROM Tutorial t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', ?1,'%')) ORDER BY t.level ASC")
    List<Tutorial> findByTitleOrderByLevelAsc(String title);
    @Query("SELECT t FROM Tutorial t WHERE t.published=true ORDER BY t.createdAt DESC")
    List<Tutorial> findAllPublishedOrderByCreatedDesc();


    /*
     * sort by
     * Spring Data JPA don’t adjust the query to database’s specific SQL dialect,
     * so ensure that the provided statement is supported by RDBMS.
     * Spring Data JPA does not currently support dynamic sorting for native queries,
     * because it would have to manipulate the actual query declared, which it cannot do reliably for native SQL.
     * */
    @Query("SELECT t FROM Tutorial t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', ?1,'%'))")
    List<Tutorial> findByTitleAndSort(String title, Sort sort);
    @Query("SELECT t FROM Tutorial t WHERE t.published=?1")
    List<Tutorial> findByPublishedAndSort(boolean isPublished, Sort sort);

    // Native query: throw InvalidJpaQueryMethodException
//    @Query(value = "SELECT * FROM tutorials t WHERE t.title LIKE %?1%", nativeQuery = true)
//    List<Tutorial> findByTitleAndSortNative(String title, Sort sort);


    /// pagination
    @Query("SELECT t FROM Tutorial t")
    Page<Tutorial> findAllWithPagination(Pageable pageable);

    Page<Tutorial> findByTitleContaining(String title, Pageable pageable);
    Page<Tutorial> findByPublished(boolean published, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Tutorial t SET t.published=true WHERE t.id=?1")
    int publishTutorial(Long id);


    /*
    * native query
    * */
    @Query(value = "SELECT * FROM tutorials", nativeQuery = true)
    List<Tutorial> findAllNative();

    @Query(value = "SELECT * FROM tutorials t WHERE t.published=?1", nativeQuery = true)
    List<Tutorial> findByPublishedNative(boolean isPublished);

    @Query(value = "SELECT * FROM tutorials t WHERE t.title LIKE %?1%", nativeQuery = true)
    List<Tutorial> findByTitleLikeNative(String title);

    @Query(value = "SELECT * FROM tutorials t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', ?1,'%'))", nativeQuery = true)
    List<Tutorial> findByTitleLikeCaseInsensitiveNative(String title);

    @Query(value = "SELECT * FROM tutorials t WHERE t.level >= ?1", nativeQuery = true)
    List<Tutorial> findByLevelGreaterThanEqualNative(int level);

    @Query(value = "SELECT * FROM tutorials t WHERE t.created_at >= ?1", nativeQuery = true)
    List<Tutorial> findByDateGreaterThanEqualNative(Date date);

    @Query(value = "SELECT * FROM tutorials t WHERE t.published=:isPublished AND t.level BETWEEN :start AND :end", nativeQuery = true)
    List<Tutorial> findByLevelBetweenNative(@Param("start") int start, @Param("end") int end, @Param("isPublished") boolean isPublished);

    @Query(value = "SELECT * FROM tutorials t ORDER BY t.level DESC", nativeQuery = true)
    List<Tutorial> findAllOrderByLevelDescNative();
    @Query(value = "SELECT * FROM tutorials t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', ?1,'%')) ORDER BY t.level ASC", nativeQuery = true)
    List<Tutorial> findByTitleOrderByLevelAscNative(String title);
    @Query(value = "SELECT * FROM tutorials t WHERE t.published=true ORDER BY t.created_at DESC", nativeQuery = true)
    List<Tutorial> findAllPublishedOrderByCreatedDescNative();

    @Query(value = "SELECT * FROM tutorials t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', ?1,'%'))", nativeQuery = true)
    Page<Tutorial> findByTitleLikeNative(String title, Pageable pageable);
    @Query(value = "SELECT * FROM tutorials t WHERE t.published=?1", nativeQuery = true)
    Page<Tutorial> findByPublishedNative(boolean isPublished, Pageable pageable);

    @Query(value = "SELECT * FROM tutorials", nativeQuery = true)
    Page<Tutorial> findAllWithPaginationNative(Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tutorials SET published=true WHERE id=?1", nativeQuery = true)
    int publishTutorialNative(Long id);
}
