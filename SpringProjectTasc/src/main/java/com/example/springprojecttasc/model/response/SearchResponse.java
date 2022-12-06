package com.example.springprojecttasc.model.response;

import com.example.springprojecttasc.model.BaseResponse;
import com.example.springprojecttasc.model.BaseSearchResponse;
import lombok.Data;

import java.util.List;
@Data
public class SearchResponse extends BaseResponse {
    private Item item;
    public SearchResponse(){
        super();
    }

    public SearchResponse(int code, String messsage, Item item) {
        super(code, messsage);
        this.item = item;
    }

    @Data
    public static class Item extends BaseSearchResponse{
        private List<?> items;
    }
}
