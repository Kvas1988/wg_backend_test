package com.example.wg_backend.controller;

import com.example.wg_backend.model.Cat;
import com.example.wg_backend.repository.CatRepository;
import com.example.wg_backend.service.CatService;
import com.example.wg_backend.service.OffsetBasedPageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Validated
@RestController
// @RequestMapping("/cats")
public class CatController {

    @Autowired
    private CatRepository catRepository;

    Logger logger = LoggerFactory.getLogger(CatController.class);

    @GetMapping(path = "/cats/default")
    public Page<Cat> getAll(@PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
        return catRepository.findAll(pageable);
    }

    @GetMapping(path = "/cats")
    public Page<Cat> getAll(@RequestParam(name = "offset", required = false, defaultValue = "0")
                                @Min(0)
                                long offset,
                            @RequestParam(name = "limit", required = false, defaultValue = "0")
                                @Min(1) @Max(2000)
                                long limit,
                            @RequestParam(name = "attribute", required = false, defaultValue = "name")
                                String sortBy,
                            @RequestParam(name = "order", required = false, defaultValue = "ASC")
                                Sort.Direction sortDirection) {

        OffsetBasedPageRequest pageable = new OffsetBasedPageRequest(offset, limit, sortDirection, sortBy);
        return catRepository.findAll(pageable);

        // TODO: VALIDATE attribute param
        // TODO: sort req param ORDER to lowercase (Converter)

    }

    @PostMapping(path = "/cat")
    public Cat createCat (@Valid @RequestBody Cat cat) {
        return catRepository.save(cat);

    }
}
