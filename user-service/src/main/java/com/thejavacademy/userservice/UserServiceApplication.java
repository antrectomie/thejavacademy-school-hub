package com.thejavacademy.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thejavacademy.userservice.model.entity.User;
import lombok.SneakyThrows;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class UserServiceApplication  {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

//
//    @Override
//    public void run(String... args) throws Exception {
//
//        //Index API
//        IndexRequest indexRequest = new IndexRequest("users");
////        final String id1 = "1";
////        indexRequest.id(id1);
//        User user = new User();
//        user.setEmail("alin@gmail.com");
//
//        user.setFirstName("alin");
//        user.setUsername("budes");
//        final String user1 = objectMapper.writeValueAsString(user);
//        System.out.println(user1);
//        indexRequest.source(user1, XContentType.JSON);
//		final IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
////		System.out.println(index);
//
//
//        restHighLevelClient.indexAsync(indexRequest, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
//
//            @SneakyThrows
//            @Override
//            public void onResponse(IndexResponse indexResponse) {
//				System.out.println(indexResponse);
//                final String id = indexResponse.getId();
//
//
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//				System.out.println("sugere de utere");
//            }
//        });
//
//        GetRequest getRequest = new GetRequest("users", index.getId());
//        String[] includes = new String[]{"message", "*ame"};
//        String[] excludes = new String[]{"phoneNumber"};
//        FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
//        final GetResponse documentFields = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
//
//
//        SearchRequest searchRequest = new SearchRequest();
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        final FuzzyQueryBuilder query = QueryBuilders
//                .fuzzyQuery("email", "catalin")
//
//                .fuzziness(Fuzziness.TWO);
//        searchSourceBuilder.query(query);
//        searchRequest.source(searchSourceBuilder);
//        final RequestOptions aDefault = RequestOptions.DEFAULT;
//        final SearchResponse searchResponse = restHighLevelClient.search(searchRequest, aDefault);
////        System.out.println(searchResponse);
//
//        MultiMatchQueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery("catalin","username", "email");
//        matchQueryBuilder.fuzzyTranspositions(true);
//        matchQueryBuilder.operator(Operator.AND);
//        searchSourceBuilder.query(matchQueryBuilder);
//        searchRequest.source(searchSourceBuilder);
//        final SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
////        System.out.println(search);
////        System.out.println(search);
////        System.out.println(search);
////        System.out.println(search);
//
//
//        MultiSearchRequest multiSearchRequestBuilder = new MultiSearchRequest();
//        SearchRequest searchRequest1 = new SearchRequest("users");
//        SearchSourceBuilder searchSourceBuilder1 = new SearchSourceBuilder();
//        searchSourceBuilder1.query(QueryBuilders.matchQuery("email", "catalin"));
//        searchRequest1.source(searchSourceBuilder1);
//        multiSearchRequestBuilder.add(searchRequest1);
//
//        SearchRequest searchRequest2 = new SearchRequest("users");
//        SearchSourceBuilder searchSourceBuilder2 = new SearchSourceBuilder();
//
//        searchSourceBuilder2.query(QueryBuilders.matchQuery("username", "catalin"));
//        searchRequest1.source(searchSourceBuilder2);
//
//        multiSearchRequestBuilder.add(searchRequest2);
//
//        final MultiSearchResponse msearch = restHighLevelClient.msearch(multiSearchRequestBuilder, RequestOptions.DEFAULT);
//
////        System.out.println(msearch);
////        System.out.println(msearch);
////        System.out.println(msearch);
////        System.out.println(msearch);
//
//        final BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
//                .should(QueryBuilders.matchQuery("firstName", "ali").fuzziness(Fuzziness.AUTO))
//                .should(QueryBuilders.matchQuery("username", "budes"))
//                .should(QueryBuilders.matchQuery("email", "budes"))
//                .should(QueryBuilders.matchQuery("lastname", "budes"))
//                .minimumShouldMatch(1);
//        SearchSourceBuilder searchSourceBuilder3 = new SearchSourceBuilder();
//        searchSourceBuilder3.query(boolQueryBuilder);
//        SearchRequest searchRequest3 = new SearchRequest("users");
//        searchRequest3.source(searchSourceBuilder3);
//        searchSourceBuilder3.size(1);
//        searchSourceBuilder3.from(0);
//        final SearchResponse search1 = restHighLevelClient.search(searchRequest3, RequestOptions.DEFAULT);
//        System.out.println(search1);
//        System.out.println(search1);
//        System.out.println(search1);
//    }
}
