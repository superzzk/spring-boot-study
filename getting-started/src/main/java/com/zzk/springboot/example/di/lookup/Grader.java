package com.zzk.springboot.example.di.lookup;

import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class Grader {
    public String grade(Collection<Integer> marks) {
        return marks.stream().anyMatch(mark -> mark < 45) ? "FAIL" : "PASS";
    }
}
