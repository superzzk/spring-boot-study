package com.zzk.springboot.example.thymeleaf;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ReadingListRepository extends JpaRepository<Book, Long> {
	List<Book> findByReader(String reader);
}