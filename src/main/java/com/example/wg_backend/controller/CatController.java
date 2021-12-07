package com.example.wg_backend.controller;

import com.example.wg_backend.model.Cat;
import com.example.wg_backend.repository.CatRepository;
import com.example.wg_backend.service.CatService;
import com.example.wg_backend.service.OffsetBasedPageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cats")
public class CatController {

    @Autowired
    private CatRepository catRepository;

    Logger logger = LoggerFactory.getLogger(CatController.class);

    @GetMapping(path = "/default")
    public Page<Cat> getAll(@PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
        return catRepository.findAll(pageable);
    }

    // public Page<Cat> getAll(@RequestParam(name = "attribute", defaultValue = "name") String sortBy,
    //                         @RequestParam(name = "order", defaultValue = "desc") String orderDirection
    @GetMapping(path = "")
    public Page<Cat> getAll(@RequestParam(name = "offset", required = false, defaultValue = "0") long offset,
                            @RequestParam(name = "limit", required = false, defaultValue = "0") long limit,
                            @RequestParam(name = "attribute", required = false, defaultValue = "name") String sortBy,
                            @RequestParam(name = "order", required = false, defaultValue = "ASC") Sort.Direction sortDirection) {

        OffsetBasedPageRequest pageable = new OffsetBasedPageRequest(offset, limit, sortDirection, sortBy);
        return catRepository.findAll(pageable);

        // VALIDATE attribute param (req param as sql column names)
        // sort req param to lowercase (Converter)

    }
}
