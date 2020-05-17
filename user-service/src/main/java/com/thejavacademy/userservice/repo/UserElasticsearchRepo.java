package com.thejavacademy.userservice.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thejavacademy.userservice.model.UserElastic;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.thejavacademy.userservice.model.serde.UserFields.*;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Repository
public class UserElasticsearchRepo {

    public static final String USERS_INDEX = "users";

    private RestHighLevelClient restHighLevelClient;
    private ObjectMapper objectMapper;


    public UserElasticsearchRepo(RestHighLevelClient restHighLevelClient, ObjectMapper objectMapper) {
        this.restHighLevelClient = restHighLevelClient;
        this.objectMapper = objectMapper;
    }

    public void save(String id, UserElastic userElastic) {
        IndexRequest indexRequest = new IndexRequest(USERS_INDEX);
        indexRequest.id(id);
        try {
            indexRequest.source(objectMapper.writeValueAsString(userElastic), XContentType.JSON);
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<UserElastic> search(String term) {
        SearchRequest searchRequest = buildSearchRequest(term);
        try {
            final SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            return Arrays.stream(search.getHits().getHits())
                    .map(SearchHit::getSourceAsString)
                    .map(this::deserializeUserElastic)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private SearchRequest buildSearchRequest(String term) {
        SearchRequest searchRequest = new SearchRequest(USERS_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        final BoolQueryBuilder query = boolQuery();
        query.should(matchQuery(USERNAME, term).fuzziness(Fuzziness.AUTO))
                .should(matchQuery(EMAIL, term).fuzziness(Fuzziness.AUTO))
                .should(matchQuery(FIRST_NAME, term).fuzziness(Fuzziness.AUTO))
                .should(matchQuery(LAST_NAME, term).fuzziness(Fuzziness.AUTO))
                .minimumShouldMatch(1);

        searchSourceBuilder.query(query);
        searchRequest.source(searchSourceBuilder);
        return searchRequest;
    }

    private UserElastic deserializeUserElastic(String source) {
        try {
            return objectMapper.readValue(source, UserElastic.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
