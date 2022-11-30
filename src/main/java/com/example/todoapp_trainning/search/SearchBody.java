package com.example.todoapp_trainning.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchBody {
    private int page;
    private int limit;
    private String name;
    private String sort;
    private int status;

        public static final class SearchBodyBuilder {
            private int page;
            private int limit;
            private String name;
            private String sort;
            private int status;

            private SearchBodyBuilder() {
            }

            public static SearchBodyBuilder aSearchBody() {
                return new SearchBodyBuilder();
            }

            public SearchBodyBuilder withPage(int page) {
                this.page = page;
                return this;
            }

            public SearchBodyBuilder withStatus(int status) {
                this.status = status;
                return this;
            }

            public SearchBodyBuilder withLimit(int limit) {
                this.limit = limit;
                return this;
            }

            public SearchBodyBuilder withSort(String sort) {
                this.sort = sort;
                return this;
            }

            public SearchBodyBuilder withName(String name){
                this.name = name;
                return this;
            }

            public SearchBody build() {
                SearchBody searchBody = new SearchBody();
                searchBody.setPage(page);
                searchBody.setLimit(limit);
                searchBody.setStatus(status);
                searchBody.setSort(sort);
                searchBody.setName(name);
                return searchBody;
            }
        }
    }
