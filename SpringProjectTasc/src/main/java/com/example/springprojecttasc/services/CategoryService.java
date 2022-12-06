package com.example.springprojecttasc.services;

import com.example.springprojecttasc.database.entity.BaseEntity;
import com.example.springprojecttasc.database.entity.Category;
import com.example.springprojecttasc.database.repository.CategoryRepository;
import com.example.springprojecttasc.model.ApiException;
import com.example.springprojecttasc.model.BaseResponse;
import com.example.springprojecttasc.model.ERROR;
import com.example.springprojecttasc.model.request.CategoryRequest;
import com.example.springprojecttasc.model.response.SearchResponse;
import com.example.springprojecttasc.search.SearchBody;
import com.example.springprojecttasc.utils.Constant;
import com.github.javafaker.Cat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CategoryService {

    //    private Logger
    @Autowired
    CategoryRepository categoryRepository;

    @Transactional
    public BaseResponse createCategory(CategoryRequest request) throws ApiException {

        // step  1 : validate request
        validateRequestCreateException(request);

        if (!request.checkIsRoot()){
            // validate parent co ton tai khong

            Optional<Category> checkParentOpt = categoryRepository.findById(request.getParentId());

            if (!checkParentOpt.isPresent()){
                throw new ApiException(ERROR.INVALID_PARAM , "parent is invalid");
            }
            Category parent = checkParentOpt.get();
            Category category = new Category();
            category.setDescription(request.getDescription());
            category.setIcon(request.getIcon());
            category.setName(request.getName());
            category.setIsRoot(request.checkIsRoot() ? Constant.ONOFF.ON : Constant.ONOFF.OFF);
            if(parent.getCategories().contains(category)){
                throw new ApiException(ERROR.INVALID_PARAM , "AllReady exist!");
            }
            Set<Category> childrenList = new HashSet<>();
            category.setCategories(childrenList);
            Set<Category> parentList = new HashSet<>();
            parentList.add(parent);
            category.setParentCategories(parentList);
            parent.getCategories().add(category);
            categoryRepository.save(category);
            return new BaseResponse(200, "success", category);
        }

        Category category = new Category();
        category.setDescription(request.getDescription());
        category.setIcon(request.getIcon());
        category.setName(request.getName());
        category.setIsRoot(request.checkIsRoot() ? Constant.ONOFF.ON : Constant.ONOFF.OFF);
        Set<Category> childrenList = new HashSet<>();
        category.setCategories(childrenList);
        category.setParentCategories(null);
        categoryRepository.save(category);
        return new BaseResponse(200, "success", category);
    }

    private void validateRequestCreateException(CategoryRequest request) throws ApiException{

        if (StringUtils.isBlank(request.getIcon())){
            throw new ApiException(ERROR.INVALID_PARAM , "icon is blank");
        }

        if (StringUtils.isBlank(request.getName())){
            throw new ApiException(ERROR.INVALID_PARAM , "name is banl");
        }

        if (StringUtils.isBlank(request.getDescription())){
            throw new ApiException(ERROR.INVALID_PARAM , "description is Blank");
        }

        if (request.checkIsRoot() && request.getParentId() != null){
            throw new ApiException(ERROR.INVALID_PARAM , "level is invalid");
        }

        if (!request.checkIsRoot() && request.getParentId() == null){
            throw new ApiException(ERROR.INVALID_PARAM , "parent is blank");
        }
    }

    public BaseResponse updateCategory(Long id, CategoryRequest request) throws ApiException{
        validateRequestCreateException(request);
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()){
            throw new ApiException(ERROR.INVALID_PARAM , "category does not exist!");
        }
        category.get().setIcon(request.getIcon());
        category.get().setDescription(request.getDescription());
        category.get().setName(request.getName());
        categoryRepository.save(category.get());
        throw new ApiException(ERROR.SUCCESS , "success");
    }

    public BaseResponse findByName(String name) throws ApiException{
        List<Category> categories = categoryRepository.findAllByNameContains(name);
        if (categories.size() <= 0){
            throw new ApiException(ERROR.SUCCESS , "no category founded");
        }
        throw new ApiException(ERROR.SUCCESS, "success", categories);
    }

    public BaseResponse findById(Long id) throws ApiException{
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()){
            throw new ApiException(ERROR.INVALID_PARAM , "category does not exist!");
        }
        return new BaseResponse(200, "success", category.get());
    }
    public BaseResponse findAllParent(Long id) throws ApiException{
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()){
            throw new ApiException(ERROR.INVALID_PARAM , "category does not exist!");
        }
        return new BaseResponse(200, "success", category.get().getParentCategories());
    }
    public BaseResponse findAllChildren(Long id) throws ApiException{
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()){
            throw new ApiException(ERROR.INVALID_PARAM , "category does not exist!");
        }
        return new BaseResponse(200, "success", category.get().getCategories());
    }

    public BaseResponse delete(Long id) throws ApiException{
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()){
            throw new ApiException(ERROR.INVALID_PARAM , "category does not exist!");
        }

        if (category.get().getIsRoot() == 0){
            for (Category cate:category.get().getParentCategories()
                 ) {
                cate.getCategories().remove(category.get());
            }
        }

        for (Category cate : category.get().getCategories()
             ) {
            int numberOfParent = cate.getParentCategories().size();
            if (numberOfParent == 1){
                this.delete(cate.getId());
            }else {
                cate.getParentCategories().remove(category.get());
            }
        }
        categoryRepository.deleteById(id);
        return new BaseResponse<>(200, "success");
    }

    public SearchResponse findAll(SearchBody searchBody) throws ApiException{
        String query = "SELECT * FROM category c WHERE 1 = 1";
        if (searchBody.getName() != null && searchBody.getName().length() > 0){
            query += "AND c.name LIKE '%" + searchBody.getName() + "%' ";
        }
        if (searchBody.getId() != null && searchBody.getId() > 0){
            query += "AND c.id = " + searchBody.getId();
        }
        query += " ORDER BY c.created_at " + searchBody.getSort();
        SearchResponse.Item item = new SearchResponse.Item();
        item.setCurrentPage(searchBody.getPage());
        item.setPageSize(searchBody.getLimit());
        item.setItems(categoryRepository.findAllWithPage(query, searchBody.getPage() -1, searchBody.getLimit()));
        item.setTotalItem(categoryRepository.count());
        return new SearchResponse(200, "success", item);
    }

    public BaseResponse findAllChildrenByQuery(Long id) throws ApiException{
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()){
            throw new ApiException(ERROR.INVALID_PARAM , "category does not exist!");
        }
        String query = "select * from category c join category_relationship cr where c.id = cr.parent_id and c.id ="+id+" group by c.id";
        return new BaseResponse(200 , "success", categoryRepository.findAll(query));
    }

    public BaseResponse findAllParentByQuery(Long id) throws ApiException{
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()){
            throw new ApiException(ERROR.INVALID_PARAM , "category does not exist!");
        }
        String query = "select * from category c join category_relationship cr where c.id = cr.category_id and c.id ="+id+" group by c.id";
        return new BaseResponse(200 , "success", categoryRepository.findAll(query));
    }

}
