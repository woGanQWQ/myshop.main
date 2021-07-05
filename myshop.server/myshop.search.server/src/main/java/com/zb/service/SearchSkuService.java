package com.zb.service;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

@Service
public class SearchSkuService {

    @Autowired
    private RestHighLevelClient client;

    public void createSkuIndex() throws Exception {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("tb_sku");
        //创建索引库
        createIndexRequest.settings(Settings.builder().put("number_of_shards", "1").put("number_of_replicas", "0"));
        //创建映射
        createIndexRequest.mapping("doc", "{\n" +
                "\t\"properties\": {\n" +
                "\t\t\"sn\": {\n" +
                "\t\t\t\"type\": \"text\"\n" +
                "\t\t},\n" +
                "\t\t\"name\": {\n" +
                "\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\"analyzer\": \"ik_max_word\",\n" +
                "\t\t\t\"search_analyzer\": \"ik_smart\"\n" +
                "\t\t},\n" +
                "\t\t\"price\": {\n" +
                "\t\t\t\"type\": \"float\"\n" +
                "\t\t},\n" +
                "\t\t\"num\": {\n" +
                "\t\t\t\"type\": \"integer\"\n" +
                "\t\t},\n" +
                "\t\t\"alert_num\": {\n" +
                "\t\t\t\"type\": \"integer\"\n" +
                "\t\t},\n" +
                "\t\t\"image\": {\n" +
                "\t\t\t\"type\": \"text\"\n" +
                "\t\t},\n" +
                "\t\t\"images\": {\n" +
                "\t\t\t\"type\": \"text\"\n" +
                "\t\t},\n" +
                "\t\t\"weight\": {\n" +
                "\t\t\t\"type\": \"integer\"\n" +
                "\t\t},\n" +
                "\t\t\"create_time\": {\n" +
                "\t\t\t\"type\": \"date\",\n" +
                "\t\t\t\"format\": \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd\"\n" +
                "\t\t},\n" +
                "\t\t\"update_time\": {\n" +
                "\t\t\t\"type\": \"date\",\n" +
                "\t\t\t\"format\": \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd\"\n" +
                "\t\t},\n" +
                "\t\t\"spu_id\": {\n" +
                "\t\t\t\"type\": \"text\"\n" +
                "\t\t},\n" +
                "\t\t\"category_id\": {\n" +
                "\t\t\t\"type\": \"integer\"\n" +
                "\t\t},\n" +
                "\t\t\"category_name\": {\n" +
                "\t\t\t\"type\": \"keyword\"\n" +
                "\t\t},\n" +
                "\t\t\"brand_name\": {\n" +
                "\t\t\t\"type\": \"keyword\"\n" +
                "\t\t},\n" +
                "\t\t\"spec\": {\n" +
                "\t\t\t\"type\": \"keyword\"\n" +
                "\t\t},\n" +
                "\t\t\"sale_num\": {\n" +
                "\t\t\t\"type\": \"integer\"\n" +
                "\t\t},\n" +
                "\t\t\"comment_num\": {\n" +
                "\t\t\t\"type\": \"integer\"\n" +
                "\t\t},\n" +
                "\t\t\"status\": {\n" +
                "\t\t\t\"type\": \"keyword\"\n" +
                "\t\t},\n" +
                "\t\t\"version\": {\n" +
                "\t\t\t\"type\": \"integer\"\n" +
                "\t\t},\n" +
                "\t\t\"specMap\":{\n" +
                "\t\t\t\"properties\":{\n" +
                "\t\t\t\t\"test\":{\n" +
                "\t\t\t\t\t\"type\": \"keyword\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}", XContentType.JSON);
        CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        boolean acknowledged = createIndexResponse.isAcknowledged();
        System.out.println(acknowledged);
    }

    public void importData() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop_goods", "root", "root");
        String sql = "select * from tb_sku";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("sn", rs.getString("sn"));
            jsonMap.put("name", rs.getString("name"));
            jsonMap.put("price", rs.getFloat("price"));
            jsonMap.put("num", rs.getInt("num"));
            jsonMap.put("alert_num", rs.getInt("alert_num"));
            jsonMap.put("image", rs.getString("image"));
            jsonMap.put("images", rs.getString("images"));
            jsonMap.put("weight", rs.getInt("weight"));
            jsonMap.put("spu_id", rs.getString("spu_id"));
            jsonMap.put("category_id", rs.getInt("category_id"));
            jsonMap.put("category_name", rs.getString("category_name"));
            jsonMap.put("brand_name", rs.getString("brand_name"));
            jsonMap.put("create_time", rs.getDate("create_time").toString());
            jsonMap.put("update_time", rs.getDate("update_time").toString());
            jsonMap.put("spec", rs.getString("spec"));
            jsonMap.put("sale_num", rs.getInt("sale_num"));
            jsonMap.put("comment_num", rs.getInt("comment_num"));
            jsonMap.put("status", rs.getString("status"));
            jsonMap.put("version", rs.getInt("version"));

            Map<String, Object> specMap = JSON.parseObject(rs.getString("spec"), Map.class);
            jsonMap.put("specMap", specMap);

            IndexRequest request = new IndexRequest("tb_sku", "doc", rs.getString("id"));
            request.source(jsonMap);
            IndexResponse index = client.index(request, RequestOptions.DEFAULT);
            RestStatus status = index.status();
            System.out.println(status);
        }


    }


}
