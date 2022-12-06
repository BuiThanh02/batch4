package com.example.springprojecttasc.search;

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
    private String icon;
    private String description;
    private String start;
    private String end;
    private int isRoot;
    private String sort;
    private Long id;

        public static final class SearchBodyBuilder {
            private int page;
            private int limit;
            private String name;
            private String icon;
            private String description;
            private String start;
            private String end;
            private int isRoot;
            private Long id;
            private String sort;

            private SearchBodyBuilder() {
            }

            public static SearchBodyBuilder aSearchBody() {
                return new SearchBodyBuilder();
            }

            public SearchBodyBuilder withPage(int page) {
                this.page = page;
                return this;
            }

            public SearchBodyBuilder withIsRoot(int isRoot) {
                this.isRoot = isRoot;
                return this;
            }

            public SearchBodyBuilder withId(Long id){
                this.id = id;
                return this;
            }

            public SearchBodyBuilder withLimit(int limit) {
                this.limit = limit;
                return this;
            }

            public SearchBodyBuilder withStart(String start) {
                this.start = start;
                return this;
            }

            public SearchBodyBuilder withSort(String sort) {
                this.sort = sort;
                return this;
            }

            public SearchBodyBuilder withEnd(String end) {
                this.end = end;
                return this;
            }

            public SearchBodyBuilder withName(String name) {
                this.name = name;
                return this;
            }

            public SearchBodyBuilder withIcon(String icon) {
                this.icon = icon;
                return this;
            }

            public SearchBodyBuilder withDescription(String description) {
                this.description = description;
                return this;
            }

            public SearchBody build() {
                SearchBody searchBody = new SearchBody();
                searchBody.setPage(page);
                searchBody.setLimit(limit);
                searchBody.setStart(start);
                searchBody.setEnd(end);
                searchBody.setDescription(description);
                searchBody.setIcon(icon);
                searchBody.setIsRoot(isRoot);
                searchBody.setName(name);
                searchBody.setId(id);
                searchBody.setSort(sort);
                return searchBody;
            }
        }
    }
