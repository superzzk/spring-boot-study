package com.zzk.springboot.example.basic.resttemplate;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/foos")
public class FooController {
    private Map<Long, Foo> foos = Maps.newHashMap(ImmutableMap.of(1L, new Foo(1L, "foo1")));

    @RequestMapping(value = "",method = RequestMethod.GET)
    public Collection<Foo> getAllFoos() {
        return foos.values();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Foo getFoo(@PathVariable Long id) {
        final Foo foo = foos.get(id);
        if (foo == null)
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        return foo;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Foo update(@PathVariable Long id, @RequestBody Foo foo) {
        foos.put(id, foo);
        return foo;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Foo> postFoo(@RequestBody Foo foo) {
        foos.put(foo.getId(), foo);
        final URI location = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path("/foos/{id}")
                .build()
                .expand(foo.getId())
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(foo, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public long deleteFoo(@PathVariable Long id) {
        foos.remove(id);
        return id;
    }

    @Data
    @NoArgsConstructor
    public static class Foo {
        private Long id;
        private String name;

        public Foo(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
