package com.zzk.springboot.example.readinglist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderRepository extends JpaRepository<Reader, String>{

}