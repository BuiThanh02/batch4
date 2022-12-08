package com.example.springprojecttasc.controllers;

import com.example.springprojecttasc.model.BaseResponse;
import com.example.springprojecttasc.model.request.CategoryRequest;
import com.example.springprojecttasc.model.request.ProductRequest;
import com.example.springprojecttasc.model.response.SearchResponse;
import com.example.springprojecttasc.search.SearchBody;
import com.example.springprojecttasc.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController{
    @Autowired
    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody CategoryRequest request){
        return createdResponse(categoryService.createCategory(request));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> update(@RequestBody CategoryRequest request, @PathVariable Long id){
        return createdResponse(categoryService.updateCategory(id, request));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> findById(@PathVariable Long id){
        return createdResponse(categoryService.findById(id));
    }
    @GetMapping(path = "/parent/{id}")
    public ResponseEntity<BaseResponse> findAllParent(@PathVariable Long id){
        return createdResponse(categoryService.findAllParent(id));
    }
    @GetMapping(path = "/children/{id}")
    public ResponseEntity<BaseResponse> findALlChildren(@PathVariable Long id){
        return createdResponse(categoryService.findAllChildrenByQuery(id));
    }
    @GetMapping(path = "/name/{name}")
    public ResponseEntity<BaseResponse> findByName(@PathVariable String name){
        return createdResponse(categoryService.findByName(name));
    }

    @GetMapping(path = "/view/{id}")
    public ResponseEntity<BaseResponse> findALlWithView(@PathVariable Long id){
        return createdResponse(categoryService.findAllParentAndChildWithView(id));
    }

    @GetMapping(path = "/query/{id}")
    public ResponseEntity<BaseResponse> findALlWithQuery(@PathVariable Long id){
        return createdResponse(categoryService.findAllParentAndChildWithQuery(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable Long id){
        return createdResponse(categoryService.delete(id));
    }
    @GetMapping()
    public SearchResponse findAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sort", defaultValue = "asc") String sort,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "id", required = false) Long id){
        SearchBody searchBody = SearchBody.SearchBodyBuilder.aSearchBody()
                .withPage(page)
                .withLimit(pageSize)
                .withName(name)
                .withId(id)
                .withSort(sort)
                .build();
        return categoryService.findAll(searchBody);
    }
}
